package de.unikassel.ann.gui.graph;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.awt.event.InputEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;

import org.apache.commons.collections15.Factory;

import de.unikassel.ann.gui.model.Edge;
import de.unikassel.ann.gui.model.Vertex;
import de.unikassel.ann.model.Neuron;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.UndirectedGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.VisualizationServer;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractGraphMousePlugin;
import edu.uci.ics.jung.visualization.util.ArrowFactory;

/**
 * A plugin that can create vertices, undirected edges, and directed edges using mouse gestures.
 * 
 * @author Tom Nelson
 * 
 */
public class GraphMousePlugin<V, E> extends AbstractGraphMousePlugin implements MouseListener, MouseMotionListener {

	protected Vertex startVertex;
	protected Point2D down;

	// protected CubicCurve2D rawEdge = new CubicCurve2D.Float();
	protected Line2D rawEdge = new Line2D.Float();
	protected Shape edgeShape;
	protected Shape rawArrowShape;
	protected Shape arrowShape;
	protected VisualizationServer.Paintable edgePaintable;
	protected VisualizationServer.Paintable arrowPaintable;
	protected EdgeType edgeIsDirected;
	protected Factory<Vertex> vertexFactory;
	protected Factory<Edge> edgeFactory;

	public GraphMousePlugin(final Factory<Vertex> vertexFactory, final Factory<Edge> edgeFactory) {
		this(InputEvent.BUTTON1_MASK, vertexFactory, edgeFactory);
	}

	/**
	 * create instance and prepare shapes for visual effects
	 * 
	 * @param modifiers
	 */
	public GraphMousePlugin(final int modifiers, final Factory<Vertex> vertexFactory, final Factory<Edge> edgeFactory) {
		super(modifiers);
		this.vertexFactory = vertexFactory;
		this.edgeFactory = edgeFactory;
		// rawEdge.setCurve(0.0f, 0.0f, 0.33f, 100, .66f, -50, 1.0f, 0.0f);
		rawEdge.setLine(0.0f, 0.0f, 1.0f, 0.0f);
		rawArrowShape = ArrowFactory.getNotchedArrow(20, 16, 8);
		edgePaintable = new EdgePaintable();
		arrowPaintable = new ArrowPaintable();
		cursor = Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR);
	}

	/**
	 * Overridden to be more flexible, and pass events with key combinations. The default responds to both ButtonOne and ButtonOne+Shift
	 */
	@Override
	public boolean checkModifiers(final MouseEvent e) {
		return (e.getModifiers() & modifiers) != 0;
	}

	/**
	 * If the mouse is pressed in an empty area, create a new vertex there. If the mouse is pressed on an existing vertex, prepare to create
	 * an edge from that vertex to another
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void mousePressed(final MouseEvent e) {
		if (checkModifiers(e)) {
			final VisualizationViewer<Vertex, Edge> vv = (VisualizationViewer<Vertex, Edge>) e.getSource();
			final Point2D p = e.getPoint();
			GraphElementAccessor<Vertex, Edge> pickSupport = vv.getPickSupport();
			if (pickSupport != null) {
				Graph<Vertex, Edge> graph = vv.getModel().getGraphLayout().getGraph();
				// set default edge type
				if (graph instanceof DirectedGraph) {
					edgeIsDirected = EdgeType.DIRECTED;
				} else {
					edgeIsDirected = EdgeType.UNDIRECTED;
				}

				final Vertex vertex = pickSupport.getVertex(vv.getModel().getGraphLayout(), p.getX(), p.getY());
				if (vertex != null) {
					// get ready to make an edge
					startVertex = vertex;
					down = e.getPoint();
					transformEdgeShape(down, down);
					vv.addPostRenderPaintable(edgePaintable);
					if ((e.getModifiers() & InputEvent.SHIFT_MASK) != 0
							&& vv.getModel().getGraphLayout().getGraph() instanceof UndirectedGraph == false) {
						edgeIsDirected = EdgeType.DIRECTED;
					}
					if (edgeIsDirected == EdgeType.DIRECTED) {
						transformArrowShape(down, e.getPoint());
						vv.addPostRenderPaintable(arrowPaintable);
					}
				} else {
					// Create a new vertex
					Vertex newVertex = vertexFactory.create();
					newVertex.setup();

					// Add the new vertex to the graph
					graph.addVertex(newVertex);

					// Set position of the new vertex
					// Point2D location = vv.getRenderContext()
					// .getMultiLayerTransformer()
					// .inverseTransform(e.getPoint());
					// Layout<Vertex, Edge> layout = vv.getModel()
					// .getGraphLayout();
					// layout.setLocation(newVertex, location);
				}
			}
			// vv.repaint();
			GraphLayoutViewer.repaint(vv);
		}
	}

	/**
	 * If startVertex is non-null, and the mouse is released over an existing vertex, create an undirected edge from startVertex to the
	 * vertex under the mouse pointer. If shift was also pressed, create a directed edge instead.
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void mouseReleased(final MouseEvent e) {
		if (checkModifiers(e)) {
			final VisualizationViewer<Vertex, Edge> vv = (VisualizationViewer<Vertex, Edge>) e.getSource();
			final Point2D p = e.getPoint();
			Layout<Vertex, Edge> layout = vv.getModel().getGraphLayout();
			GraphElementAccessor<Vertex, Edge> pickSupport = vv.getPickSupport();
			if (pickSupport != null) {
				final Vertex vertex = pickSupport.getVertex(layout, p.getX(), p.getY());
				if (vertex != null && startVertex != null) {
					Graph<Vertex, Edge> graph = vv.getGraphLayout().getGraph();

					// Check if the two neurons are in different layers
					Neuron fromNeuron = startVertex.getModel();
					Neuron toNeuron = vertex.getModel();
					if (fromNeuron.getLayer().getIndex() < toNeuron.getLayer().getIndex()) {
						// Create edge with its synapse between the both vertexes
						Edge edge = edgeFactory.create();
						edge.createModel(fromNeuron, toNeuron);
						graph.addEdge(edge, startVertex, vertex, edgeIsDirected);
					}
					// vv.repaint();
					GraphLayoutViewer.repaint(vv);
				}
			}
			startVertex = null;
			down = null;
			edgeIsDirected = EdgeType.UNDIRECTED;
			vv.removePostRenderPaintable(edgePaintable);
			vv.removePostRenderPaintable(arrowPaintable);
		}
	}

	/**
	 * If startVertex is non-null, stretch an edge shape between startVertex and the mouse pointer to simulate edge creation
	 */
	@Override
	@SuppressWarnings("unchecked")
	public void mouseDragged(final MouseEvent e) {
		if (checkModifiers(e)) {
			if (startVertex != null) {
				transformEdgeShape(down, e.getPoint());
				if (edgeIsDirected == EdgeType.DIRECTED) {
					transformArrowShape(down, e.getPoint());
				}
			}
			VisualizationViewer<Vertex, Edge> vv = (VisualizationViewer<Vertex, Edge>) e.getSource();
			// vv.repaint();
			GraphLayoutViewer.repaint(vv);
		}
	}

	/**
	 * code lifted from PluggableRenderer to move an edge shape into an arbitrary position
	 */
	private void transformEdgeShape(final Point2D down, final Point2D out) {
		float x1 = (float) down.getX();
		float y1 = (float) down.getY();
		float x2 = (float) out.getX();
		float y2 = (float) out.getY();

		AffineTransform xform = AffineTransform.getTranslateInstance(x1, y1);

		float dx = x2 - x1;
		float dy = y2 - y1;
		float thetaRadians = (float) Math.atan2(dy, dx);
		xform.rotate(thetaRadians);
		float dist = (float) Math.sqrt(dx * dx + dy * dy);
		xform.scale(dist / rawEdge.getBounds().getWidth(), 1.0);
		edgeShape = xform.createTransformedShape(rawEdge);
	}

	private void transformArrowShape(final Point2D down, final Point2D out) {
		float x1 = (float) down.getX();
		float y1 = (float) down.getY();
		float x2 = (float) out.getX();
		float y2 = (float) out.getY();

		AffineTransform xform = AffineTransform.getTranslateInstance(x2, y2);

		float dx = x2 - x1;
		float dy = y2 - y1;
		float thetaRadians = (float) Math.atan2(dy, dx);
		xform.rotate(thetaRadians);
		arrowShape = xform.createTransformedShape(rawArrowShape);
	}

	/**
	 * Used for the edge creation visual effect during mouse drag
	 */
	class EdgePaintable implements VisualizationServer.Paintable {

		@Override
		public void paint(final Graphics g) {
			if (edgeShape != null) {
				Color oldColor = g.getColor();
				g.setColor(Color.black);
				((Graphics2D) g).draw(edgeShape);
				g.setColor(oldColor);
			}
		}

		@Override
		public boolean useTransform() {
			return false;
		}
	}

	/**
	 * Used for the directed edge creation visual effect during mouse drag
	 */
	class ArrowPaintable implements VisualizationServer.Paintable {

		@Override
		public void paint(final Graphics g) {
			if (arrowShape != null) {
				Color oldColor = g.getColor();
				g.setColor(Color.black);
				((Graphics2D) g).fill(arrowShape);
				g.setColor(oldColor);
			}
		}

		@Override
		public boolean useTransform() {
			return false;
		}
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
		JComponent c = (JComponent) e.getSource();
		c.setCursor(cursor);
	}

	@Override
	public void mouseExited(final MouseEvent e) {
		JComponent c = (JComponent) e.getSource();
		c.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
	}
}
