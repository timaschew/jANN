package de.unikassel.ann.gui.graph;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Point2D;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import org.apache.commons.collections15.Transformer;

import de.unikassel.ann.controller.EdgeController;
import de.unikassel.ann.controller.LayerController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.controller.VertexController;
import de.unikassel.ann.factory.EdgeFactory;
import de.unikassel.ann.factory.VertexFactory;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.Synapse;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;

public class GraphLayoutViewer {

	/*
	 * fields
	 */
	protected DirectedGraph<Vertex, Edge> graph;
	protected AbstractLayout<Vertex, Edge> layout;
	protected VisualizationViewer<Vertex, Edge> viewer;
	protected GraphMouse<Vertex, Edge> graphMouse;

	private JFrame frame;
	private Dimension dim;
	private Container parent;

	private static GraphLayoutViewer instance;

	public static GraphLayoutViewer getInstance() {
		if (instance == null) {
			instance = new GraphLayoutViewer();
		}
		return instance;
	}

	/**
	 * Private Constructor
	 */
	private GraphLayoutViewer() {
	}

	public DirectedGraph<Vertex, Edge> getGraph() {
		return graph;
	}

	public void setGraph(DirectedGraph<Vertex, Edge> graph) {
		this.graph = graph;
	}

	public AbstractLayout<Vertex, Edge> getLayout() {
		return layout;
	}

	public void setLayout(AbstractLayout<Vertex, Edge> layout) {
		this.layout = layout;
	}

	public VisualizationViewer<Vertex, Edge> getViewer() {
		return viewer;
	}

	public void setViewer(VisualizationViewer<Vertex, Edge> viewer) {
		this.viewer = viewer;
	}

	/**
	 * Initialize
	 */
	public void init() {
		graph = new DirectedSparseGraph<Vertex, Edge>();
		layout = new StaticLayout<Vertex, Edge>(graph, dim);

		viewer = new VisualizationViewer<Vertex, Edge>(layout);
		viewer.setBackground(Color.white);
		viewer.addPreRenderPaintable(new VisualizationViewer.Paintable() {
			@Override
			public void paint(final Graphics g) {
				final int height = viewer.getHeight();
				final int width = viewer.getWidth();

				// Position of the vertex depends on the number of vertices that
				// are already in the same layer.
				// Since new vertices are always added at the last position, get
				// the number of vertices to compute the position for the new
				// vertex.
				LayerController<Layer> layerController = LayerController
						.getInstance();
				HashMap<Layer, List<Vertex>> vertices = layerController
						.getVertices();

				// No vertices? -> No fun!
				if (vertices.size() == 0) {
					return;
				}

				Point2D location;

				// Gap between the layers
				int gapY = height / layerController.getLayer().size();

				Iterator<Entry<Layer, List<Vertex>>> iter;
				Map.Entry<Layer, List<Vertex>> entry;
				Layer layer;
				List<Vertex> layerVertices;

				iter = vertices.entrySet().iterator();
				while (iter.hasNext()) {
					entry = iter.next();
					layer = entry.getKey();
					layerVertices = entry.getValue();

					// Skip empty layers
					if (layerVertices.size() == 0) {
						continue;
					}

					// Compute the gap between two vertices
					int gapX = width / layerVertices.size();

					int vertexIndex = -1;
					for (Vertex vertex : layerVertices) {
						vertexIndex++;

						// Compute location of the vertex
						int x = vertexIndex * gapX + gapX / 2;
						int y = layer.getIndex() * gapY + gapY / 2;
						location = new Point2D.Double(x, y);

						// Set vertex location and lock it
						layout.setLocation(vertex, location);
						layout.lock(vertex, true);
					}
				}
			}

			@Override
			public boolean useTransform() {
				return false;
			}
		});

		//
		// Controller
		//

		// Vertex
		VertexController.getInstance().init();

		// Edge
		EdgeController.getInstance().init();

		//
		// Panel
		// TODO DISCUSS use really the GraphZoomScollPane or maybe create an own
		//
		final GraphZoomScrollPane panel = new GraphZoomScrollPane(viewer);
		parent.add(panel);

		//
		// Graph Mouse
		//
		initGraphMouse();
		addMouseModeMenu();
	}

	public void setDimension(Dimension dim) {
		this.dim = dim;
	}

	public void setParent(JPanel parent) {
		this.parent = parent;
	}

	public void setFrame(final JFrame frame) {
		this.frame = frame;
	}

	public void repaint() {
		viewer.repaint();
	}

	/**
	 * Remove all vertices and their edges from the graph.
	 */
	public void clear() {
		// Clear current LayerController
		LayerController.getInstance().clear();

		// Reset factories
		VertexController.getInstance().getVertexFactory().reset();
		EdgeController.getInstance().getEdgeFactory().reset();

		// Create new graph
		graph = new DirectedSparseGraph<Vertex, Edge>();
		layout.setGraph(graph);
		repaint();
	}

	/**
	 * Render neurons and their synapses as Vertices and Edges into the Graph.
	 * 
	 * @param network
	 */
	public void renderNetwork(final Network network) {
		// Clear current graph view
		clear();

		//
		// Render Vertices into their Layers
		//
		LayerController<Layer> layerController = LayerController.getInstance();
		VertexController<Vertex> vertexController = VertexController
				.getInstance();
		List<Layer> layers = network.getLayers();
		for (Layer layer : layers) {
			layerController.addLayer(layer);

			List<Neuron> neurons = layer.getNeurons();
			for (Neuron neuron : neurons) {
				Vertex vertex = vertexController.getVertexFactory().create();

				// Set the id of the neuron as index of the vertex
				vertex.setIndex(neuron.getId());
				vertex.setModel(neuron);

				// Add the new vertex to the current jung layer
				layerController.addVertex(layer.getIndex(), vertex, false);

				// Add the new vertex to the graph
				graph.addVertex(vertex);
			}
		}

		//
		// Render Synapses
		//
		for (Synapse synapse : network.getSynapseSet()) {

			// Get the vertices of the synapse by their id of the their models
			Integer fromId = synapse.getFromNeuron().getId();
			Integer toId = synapse.getToNeuron().getId();

			Vertex fromVertex = layerController.getVertexById(fromId);
			Vertex toVertex = layerController.getVertexById(toId);

			if (fromVertex == null || toVertex == null) {
				// Problem! Both vertices are mandatory for the edge!
				continue;
			}

			// Create new edge with its synapse and the both vertexes
			Edge edge = EdgeController.getInstance().getEdgeFactory().create();
			edge.createModel(fromVertex.getModel(), toVertex.getModel());

			// Add the new edge to the graph
			graph.addEdge(edge, fromVertex, toVertex, EdgeType.DIRECTED);
		}

		repaint();
	}

	private void initGraphMouse() {
		// Create Graph Mouse (set in and out parameter to '1f' to disable zoom)
		graphMouse = new GraphMouse<Vertex, Edge>(viewer.getRenderContext(),
				VertexController.getInstance().getVertexFactory(),
				EdgeController.getInstance().getEdgeFactory(), 1f, 1f);

		viewer.setGraphMouse(graphMouse);
		viewer.addKeyListener(graphMouse.getModeKeyListener());
		viewer.addMouseWheelListener(new MouseWheelListener() {

			@Override
			public void mouseWheelMoved(final MouseWheelEvent e) {
			}
		});

		graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
		graphMouse.setZoomAtMouse(false);
	}

	/**
	 * Add MouseMode Menu to the MainMenu of the frame to set the mode of the
	 * Graph Mouse.
	 */
	private void addMouseModeMenu() {
		if (frame == null) {
			// No frame -> No menu -> No fun!
			return;
		}
		JMenu modeMenu = graphMouse.getModeMenu();
		modeMenu.setText(Settings.getI18n("menu.mousemode", "Mode"));
		JMenuBar menu = frame.getJMenuBar();
		menu.add(modeMenu, menu.getComponentCount() - 1);
		graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
	}

}
