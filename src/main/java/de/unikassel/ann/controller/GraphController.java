package de.unikassel.ann.controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Set;

import javax.swing.JPanel;

import de.unikassel.ann.gui.model.Edge;
import de.unikassel.ann.gui.model.Vertex;
import de.unikassel.ann.gui.mouse.GraphMouse;
import de.unikassel.ann.model.EdgeMap;
import de.unikassel.ann.model.FromTo;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Network.NetworkLayer;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.Synapse;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.DirectedGraph;
import edu.uci.ics.jung.graph.DirectedSparseGraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationServer.Paintable;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.layout.LayoutTransition;
import edu.uci.ics.jung.visualization.util.Animator;

public class GraphController implements PropertyChangeListener {

	/*
	 * fields
	 */
	protected DirectedGraph<Vertex, Edge> graph;
	protected AbstractLayout<Vertex, Edge> layout;
	protected VisualizationViewer<Vertex, Edge> viewer;
	public GraphMouse<Vertex, Edge> graphMouse;

	private Dimension dim;
	private Container parent;
	private boolean init;

	private static GraphController instance;

	/**
	 * Private Constructor
	 */
	private GraphController() {
	}

	public static GraphController getInstance() {
		if (instance == null) {
			instance = new GraphController();
		}
		return instance;
	}

	/**
	 * Initialize
	 */
	public void init() {
		//
		// Graph - Layout - Viewer
		//
		graph = new DirectedSparseGraph<Vertex, Edge>();
		layout = new StaticLayout<Vertex, Edge>(graph, dim);
		viewer = new VisualizationViewer<Vertex, Edge>(layout);
		viewer.setBackground(Color.white);
		viewer.addPreRenderPaintable(getViewerPreRenderer());

		//
		// Controller
		//
		VertexController.getInstance().init();
		EdgeController.getInstance().init();

		//
		// Panel
		//
		parent.add(new GraphZoomScrollPane(viewer));

		//
		// Graph Mouse
		//
		initGraphMouse();
		// addMouseModeMenu();
		init = true;
	}

	public boolean isInitialized() {
		return init;
	}

	/*
	 * Getter & Setter
	 */

	public DirectedGraph<Vertex, Edge> getGraph() {
		return graph;
	}

	public void setGraph(final DirectedGraph<Vertex, Edge> graph) {
		this.graph = graph;
	}

	public AbstractLayout<Vertex, Edge> getLayout() {
		return layout;
	}

	public void setLayout(final AbstractLayout<Vertex, Edge> layout) {
		this.layout = layout;
	}

	public VisualizationViewer<Vertex, Edge> getViewer() {
		return viewer;
	}

	public void setViewer(final VisualizationViewer<Vertex, Edge> viewer) {
		this.viewer = viewer;
	}

	public void setDimension(final Dimension dim) {
		this.dim = dim;
	}

	public void setParent(final JPanel parent) {
		this.parent = parent;
	}

	public void repaint() {
		repaint(false);
	}

	public void repaint(final boolean animate) {
		if (animate) {
			Layout<Vertex, Edge> startLayout = viewer.getGraphLayout();
			animate(startLayout);
		}
		viewer.repaint();
	}

	/**
	 * Animate the layout transistion
	 * 
	 * @param startLayout
	 */
	public void animate(final Layout<Vertex, Edge> startLayout) {
		LayoutTransition<Vertex, Edge> transition = new LayoutTransition<Vertex, Edge>(viewer, startLayout, layout);
		Animator animator = new Animator(transition);
		animator.start();
	}

	/**
	 * Remove all vertices and their edges from the graph and clear their controller.
	 */
	public void clear() {
		// Clear controllers
		VertexController.getInstance().clear();
		EdgeController.getInstance().clear();

		// Create new graph
		graph = new DirectedSparseGraph<Vertex, Edge>();
		layout.setGraph(graph);

		// Update view
		repaint();
	}

	/**
	 * Reset all controller to their initial state.
	 */
	public void reset() {
		clear();

		// Reset controllers
		VertexController.getInstance().reset();
		EdgeController.getInstance().reset();
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
		VertexController<Vertex> vertexController = VertexController.getInstance();
		List<Layer> layers = network.getLayers();
		for (Layer layer : layers) {
			List<Neuron> neurons = layer.getNeurons();
			for (Neuron neuron : neurons) {
				Vertex vertex = vertexController.getVertexFactory().create();

				// Set the id of the neuron as index of the vertex
				vertex.setIndex(neuron.getId());
				vertex.setModel(neuron);

				// Add the new vertex with its neuron id as key to the vertex map
				vertexController.getVertexMap().put(neuron.getId(), vertex);

				// Add the new vertex to the graph
				graph.addVertex(vertex);
			}
		}

		//
		// Render Edges
		// If the network is not finalized, the network will not have a synapseset.
		// In order to render the created edges, use the current edgemap.
		//
		EdgeController<Edge> edgeController = EdgeController.getInstance();
		EdgeMap<Double> edgeMap = edgeController.getEdgeMap();
		Set<Entry<FromTo, Double>> edgeSet = edgeMap.getMap().entrySet();
		for (Entry<FromTo, Double> edgeEntry : edgeSet) {
			// Get the vertices of the synapse by their id of the their models
			Integer fromId = edgeEntry.getKey().from;
			Integer toId = edgeEntry.getKey().to;

			Vertex fromVertex = vertexController.getVertexMap().get(fromId);
			Vertex toVertex = vertexController.getVertexMap().get(toId);

			if (fromVertex == null || toVertex == null) {
				// maybe one or both vertices has been removed!
				continue;
			}

			// Create new edge with its synapse and the both vertices
			Edge edge = edgeController.getEdgeFactory().create();
			edge.createModel(fromVertex.getModel(), toVertex.getModel());

			// Add the edge to the graph
			graph.addEdge(edge, fromVertex, toVertex, EdgeType.DIRECTED);
		}

		//
		// Render Synapses
		//
		for (Synapse synapse : network.getSynapseSet()) {
			// Get the vertices of the synapse by their id of the their models
			Integer fromId = synapse.getFromNeuron().getId();
			Integer toId = synapse.getToNeuron().getId();

			Vertex fromVertex = vertexController.getVertexMap().get(fromId);
			Vertex toVertex = vertexController.getVertexMap().get(toId);
			if (fromVertex == null || toVertex == null) {
				// Problem! Both vertices are mandatory for the edge!
				continue;
			}

			// Create new edge with its synapse and the both vertices
			Edge edge = edgeController.getEdgeFactory().create();
			edge.createModel(fromVertex.getModel(), toVertex.getModel());

			// Add the new edge with the FromTo key and its weight as value to the edgemap
			edgeMap.put(new FromTo(fromId, toId), edge.getWeight());

			// Add the new edge to the graph
			graph.addEdge(edge, fromVertex, toVertex, EdgeType.DIRECTED);
		}

		// Store current layout before repainting in order to animate the transition
		repaint();
	}

	/**
	 * Create a new vertex in the given layer.<br>
	 * NOTE: It's not the layer index, its the layer type: INPUT, OUTPUT or HIDDEN
	 * 
	 * @param layer
	 */
	public void createVertex(final NetworkLayer layer) {
		createVertex(layer, 0);
	}

	public void createVertex(final NetworkLayer layer, final Integer layerIndex) {
		// System.out.println("createVertex(" + layer + ", " + layerIndex + ")");
		switch (layer) {
		case INPUT:
			Network.getNetwork().addInputNeuron();
			break;
		case OUTPUT:
			Network.getNetwork().addOutputNeuron();
			break;
		case HIDDEN:
			Network.getNetwork().addHiddenNeuron(layerIndex);
			break;
		}
	}

	/**
	 * Remove the vertex.
	 * 
	 * @param vertex
	 */
	public void removeVertex(final Vertex vertex) {
		if (vertex != null) {
			Network.getNetwork().removeNeuron(vertex.getModel());
		}
	}

	/**
	 * Remove all vertices in the set.
	 * 
	 * @param picked
	 */
	public void removeVertex(final Collection<Vertex> vertices) {
		for (Vertex vertex : vertices) {
			removeVertex(vertex);
		}
	}

	/**
	 * Create and setup a new edge and adding it to the graph.
	 * 
	 * @param toVertex
	 * @param toVertex
	 */
	public void createEdge(final Vertex fromVertex, final Vertex toVertex) {
		if (fromVertex.equals(toVertex)) {
			// No self-edges
			return;
		}
		Neuron fromNeuron = fromVertex.getModel();
		Neuron toNeuron = toVertex.getModel();

		// Get the vertices of the synapse by their id of the their models
		Integer fromId = fromNeuron.getId();
		Integer toId = toNeuron.getId();

		if (fromVertex.mayHaveEdgeTo(toVertex)) {
			// Create a new edge with its synapse between the both vertices
			EdgeController<Edge> edgeController = EdgeController.getInstance();
			Edge edge = edgeController.getEdgeFactory().create();
			edge.createModel(fromNeuron, toNeuron);

			// Add the new edge with the FromTo key and its weight as value to the edgemap
			EdgeMap<Double> edgeMap = edgeController.getEdgeMap();
			edgeMap.put(new FromTo(fromId, toId), edge.getWeight());

			// Add the new edge to the graph
			graph.addEdge(edge, fromVertex, toVertex, EdgeType.DIRECTED);
			repaint();
		}
	}

	/**
	 * Create and setup a new edge between each vertex in the set and adding it to the graph.
	 * 
	 * @param vertices
	 */
	public void createEdge(final Collection<Vertex> vertices) {
		for (Vertex v1 : vertices) {
			for (Vertex v2 : vertices) {
				createEdge(v1, v2);
				createEdge(v2, v1);
			}
		}
	}

	/**
	 * Remove the edge.
	 * 
	 * @param edge
	 */
	public void removeEdge(final Edge edge) {
		// Remove the edgemap entry as well
		EdgeMap<Double> edgeMap = EdgeController.getInstance().getEdgeMap();
		Synapse synapse = edge.getModel();
		Neuron fromNeuron = synapse.getFromNeuron();
		Neuron toNeuron = synapse.getToNeuron();
		FromTo fromTo = new FromTo(fromNeuron.getId(), toNeuron.getId());
		edgeMap.remove(fromTo);

		// Remove synapse from the neurons
		fromNeuron.getOutgoingSynapses().remove(synapse);
		toNeuron.getIncomingSynapses().remove(synapse);

		System.out.println("getOutgoingSynapses: " + fromNeuron.getOutgoingSynapses());
		System.out.println("getIncomingSynapses: " + toNeuron.getIncomingSynapses());

		graph.removeEdge(edge);
		repaint();
	}

	/**
	 * 
	 * Remove each edge in the list.
	 * 
	 * @param edges
	 */
	public void removeEdge(final Collection<Edge> edges) {
		for (Edge edge : edges) {
			removeEdge(edge);
		}
	}

	@Override
	public void propertyChange(final PropertyChangeEvent evt) {
		if (evt.getSource() instanceof Network) {
			// Render the changes network
			Network network = (Network) evt.getSource();
			renderNetwork(network);
		}
	}

	/**
	 * Arrange vertices accordingly to their layer and position.
	 * 
	 * @return Paintable
	 */
	private Paintable getViewerPreRenderer() {
		return new VisualizationViewer.Paintable() {
			@Override
			public void paint(final Graphics g) {
				final int height = viewer.getHeight();
				final int width = viewer.getWidth();

				// The Position of a vertex depends on the number of vertices that
				// are in the same layer.
				// Since new vertices are always added at the last position, get
				// the number of vertices to compute the position for the new
				// vertex.
				// LayerController<Layer> layerController = LayerController.getInstance();
				// ArrayList<JungLayer> layers = layerController.getLayers();

				List<Layer> layers = Network.getNetwork().getLayers();

				// No layers? -> No need to positionize anything!
				if (layers.size() == 0) {
					viewer.repaint();
					return;
				}

				// Gap between the layers
				int gapY = height / layers.size();

				Iterator<Layer> layerIterator = layers.iterator();
				while (layerIterator.hasNext()) {
					Layer layer = layerIterator.next();
					// }
					//
					// for (JungLayer jungLayer : layers) {
					List<Neuron> neurons = layer.getNeurons();
					int layerIndex = layer.getIndex();
					int layerSize = neurons.size();

					// System.out.println(layerIndex + ": " + vertices);

					// Skip empty layers
					if (layerSize == 0) {
						continue;
					}

					// Compute the gap between two vertices
					int gapX = width / layerSize;

					int neuronIndex = -1;
					for (Neuron neuron : neurons) {
						neuronIndex++;
						Vertex vertex = VertexController.getInstance().getVertexMap().get(neuron.getId());

						// Compute location of the vertex
						int x = neuronIndex * gapX + gapX / 2;
						int y = layerIndex * gapY + gapY / 2;
						Point2D location = new Point2D.Double(x, y);

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
		};
	}

	private void initGraphMouse() {
		// Create Graph Mouse (set in and out parameter to '1f' to disable zoom)
		graphMouse = new GraphMouse<Vertex, Edge>(viewer.getRenderContext(), VertexController.getInstance().getVertexFactory(),
				EdgeController.getInstance().getEdgeFactory(), 1f, 1f);

		viewer.setGraphMouse(graphMouse);
		viewer.addKeyListener(graphMouse.getModeKeyListener());

		graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
		graphMouse.setZoomAtMouse(false);
	}

	/**
	 * Show vertex details in the sidebar.
	 * 
	 * @param vertex
	 */
	public void showVertex(final Vertex vertex) {
		// TODO
		System.out.println(vertex);
	}

}
