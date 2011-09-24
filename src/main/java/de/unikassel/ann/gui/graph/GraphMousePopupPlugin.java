package de.unikassel.ann.gui.graph;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.geom.Point2D;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.JMenu;
import javax.swing.JPopupMenu;

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
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.AbstractPopupGraphMousePlugin;
import edu.uci.ics.jung.visualization.picking.PickedState;

/**
 * a plugin that uses popup menus to create vertices, undirected edges, and directed edges.
 */
public class GraphMousePopupPlugin<V, E> extends AbstractPopupGraphMousePlugin {

	protected Factory<Vertex> vertexFactory;
	protected Factory<Edge> edgeFactory;
	protected JPopupMenu popup = new JPopupMenu();

	public GraphMousePopupPlugin(final Factory<Vertex> vertexFactory, final Factory<Edge> edgeFactory) {
		this.vertexFactory = vertexFactory;
		this.edgeFactory = edgeFactory;
	}

	@Override
	@SuppressWarnings({ "unchecked", "serial" })
	protected void handlePopup(final MouseEvent e) {
		final VisualizationViewer<Vertex, Edge> vv = (VisualizationViewer<Vertex, Edge>) e.getSource();
		final Layout<Vertex, Edge> layout = vv.getGraphLayout();
		final Graph<Vertex, Edge> graph = layout.getGraph();
		final Point2D p = e.getPoint();
		final Point2D ivp = p;
		GraphElementAccessor<Vertex, Edge> pickSupport = vv.getPickSupport();
		if (pickSupport != null) {

			popup.removeAll();

			final Vertex vertex = pickSupport.getVertex(layout, ivp.getX(), ivp.getY());
			final Edge edge = pickSupport.getEdge(layout, ivp.getX(), ivp.getY());
			final PickedState<Vertex> pickedVertexState = vv.getPickedVertexState();
			final PickedState<Edge> pickedEdgeState = vv.getPickedEdgeState();

			if (vertex != null) {
				Set<Vertex> picked = pickedVertexState.getPicked();
				if (picked.size() > 0) {
					if (graph instanceof UndirectedGraph == false) {
						JMenu directedMenu = new JMenu("Create Directed Edge");
						popup.add(directedMenu);
						for (final Vertex other : picked) {
							directedMenu.add(new AbstractAction("[" + other + "," + vertex + "]") {
								@Override
								public void actionPerformed(final ActionEvent e) {
									// Check if the two neurons are in different
									// layers
									Neuron fromNeuron = other.getModel();
									Neuron toNeuron = vertex.getModel();
									if (fromNeuron.getLayer().getIndex() < toNeuron.getLayer().getIndex()) {
										// Create edge with its synapse between
										// the both vertexes
										Edge edge = edgeFactory.create();
										edge.createModel(fromNeuron, toNeuron);
										graph.addEdge(edge, other, vertex, EdgeType.DIRECTED);
									}
									// vv.repaint();
									GraphLayoutViewer.repaint(vv);
								}
							});
						}
					}
					if (graph instanceof DirectedGraph == false) {
						JMenu undirectedMenu = new JMenu("Create Undirected Edge");
						popup.add(undirectedMenu);
						for (final Vertex other : picked) {
							undirectedMenu.add(new AbstractAction("[" + other + "," + vertex + "]") {
								@Override
								public void actionPerformed(final ActionEvent e) {
									// Check if the two neurons are in different
									// layers
									Neuron fromNeuron = other.getModel();
									Neuron toNeuron = vertex.getModel();
									if (fromNeuron.getLayer().getIndex() < toNeuron.getLayer().getIndex()) {
										// Create edge with its synapse between
										// the both vertexes
										Edge edge = edgeFactory.create();
										edge.createModel(fromNeuron, toNeuron);
										graph.addEdge(edge, other, vertex);
									}
									// vv.repaint();
									GraphLayoutViewer.repaint(vv);
								}
							});
						}
					}
				}
				popup.add(new AbstractAction("Delete Vertex") {
					@Override
					public void actionPerformed(final ActionEvent e) {
						pickedVertexState.pick(vertex, false);
						vertex.remove(); // TODO check
						graph.removeVertex(vertex);
						// vv.repaint();
						GraphLayoutViewer.repaint(vv);
					}
				});
			} else if (edge != null) {
				popup.add(new AbstractAction("Delete Edge") {
					@Override
					public void actionPerformed(final ActionEvent e) {
						pickedEdgeState.pick(edge, false);
						graph.removeEdge(edge);
						// vv.repaint();
						GraphLayoutViewer.repaint(vv);
					}
				});
			} else {
				popup.add(new AbstractAction("Create Vertex") {
					@Override
					public void actionPerformed(final ActionEvent e) {
						// Create a new vertex
						Vertex newVertex = vertexFactory.create();
						newVertex.setup();

						// Add the new vertex to the graph
						graph.addVertex(newVertex);

						// layout.setLocation(newVertex, vv.getRenderContext()
						// .getMultiLayerTransformer().inverseTransform(p));
						// vv.repaint();
						GraphLayoutViewer.repaint(vv);
					}
				});
			}
			if (popup.getComponentCount() > 0) {
				popup.show(vv, e.getX(), e.getY());
			}
		}
	}

}
