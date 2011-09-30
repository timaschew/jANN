package de.unikassel.ann.controller;

import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.swing.JPanel;

import EDU.oswego.cs.dl.util.concurrent.CopyOnWriteArrayList;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.model.Edge;
import de.unikassel.ann.gui.model.Vertex;
import de.unikassel.ann.gui.mouse.GraphMouse;
import de.unikassel.ann.gui.sidebar.StandardOptionsPanel;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Network.NetworkLayer;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.Synapse;
import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.GraphElementAccessor;
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
import edu.uci.ics.jung.visualization.picking.PickedState;
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

					VertexController<Vertex> vertexController = VertexController.getInstance();
					int neuronIndex = -1;
					for (Neuron neuron : neurons) {
						neuronIndex++;
						Vertex vertex = vertexController.getVertexByModel(neuron);

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
		// Create Graph Mouse (NOTE: Disable zoom by setting "in" and "out" parameter to "1f")
		graphMouse = new GraphMouse<Vertex, Edge>(viewer.getRenderContext(), VertexController.getInstance().getVertexFactory(),
				EdgeController.getInstance().getEdgeFactory());

		viewer.setGraphMouse(graphMouse);
		viewer.addKeyListener(graphMouse.getModeKeyListener());

		graphMouse.setMode(ModalGraphMouse.Mode.PICKING);
		graphMouse.setZoomAtMouse(false);
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

	/**
	 * Get a set of all actual picked vertices.
	 * 
	 * @return Set<Vertex>
	 */
	public Set<Vertex> getPickedVertices() {
		GraphElementAccessor<Vertex, Edge> pickSupport = viewer.getPickSupport();
		PickedState<Vertex> pickedVertexState = viewer.getPickedVertexState();
		Set<Vertex> picked = new HashSet<Vertex>();
		if (pickSupport != null && pickedVertexState != null) {
			picked.addAll(pickedVertexState.getPicked());
		}
		return picked;
	}

	/**
	 * Get a set of all actual picked edges.
	 * 
	 * @return Set<Edge>
	 */
	public Set<Edge> getPickedEdges() {
		GraphElementAccessor<Vertex, Edge> pickSupport = viewer.getPickSupport();
		PickedState<Edge> pickedEdgeState = viewer.getPickedEdgeState();
		Set<Edge> picked = new HashSet<Edge>();
		if (pickSupport != null && pickedEdgeState != null) {
			picked.addAll(pickedEdgeState.getPicked());
		}
		return picked;
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

				// Add the new vertex to the graph
				graph.addVertex(vertex);
			}
		}

		//
		// Render Synapses
		//
		EdgeController<Edge> edgeController = EdgeController.getInstance();
		for (Synapse synapse : network.getSynapseSet()) {
			// Get the vertices of the synapse by their models (neurons)
			Neuron fromNeuron = synapse.getFromNeuron();
			Neuron toNeuron = synapse.getToNeuron();

			Vertex fromVertex = vertexController.getVertexByModel(fromNeuron);
			Vertex toVertex = vertexController.getVertexByModel(toNeuron);
			if (fromVertex == null || toVertex == null) {
				// Problem! Both vertices are mandatory for the edge!
				System.out.println("Synapse " + synapse + " from " + fromVertex + " to " + toVertex + " will be removed!");
				edgeController.getLostSynapses().add(synapse);
				continue;
			}

			// Create new edge with its synapse and the both vertices
			Edge edge = edgeController.getEdgeFactory().create();

			// Set synapse weight to random value if it's missing
			if (synapse.getWeight() == null) {
				// Get random default weight between min and max defined in the standard options panel in the sidebar.
				StandardOptionsPanel panel = Main.instance.sidebar.standardOptionsPanel;
				double weight = panel.getRandomInitialWeight();
				synapse.setWeight(weight);
			}
			edge.setModel(synapse);

			// Add the new edge to the graph
			graph.addEdge(edge, fromVertex, toVertex, EdgeType.DIRECTED);
		}

		// Clean lost synpases out of the network
		for (Synapse synapse : edgeController.getLostSynapses()) {
			Network.getNetwork().removeSynapse(synapse);
		}

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
	 * @param vertices
	 */
	public void removeVertices(final Collection<Vertex> vertices) {
		// Remove vertices in two steps:
		// 1. Vertices in hidden layers
		// 2. Vertices in the input and output layer
		List<Vertex> inOutVerts = new ArrayList<Vertex>();
		VertexController<Vertex> vertexController = VertexController.getInstance();

		// Sort and reverse vertices
		List<Vertex> verts = new ArrayList(vertices);
		Collections.sort(verts);
		Collections.reverse(verts);
		for (Vertex vertex : verts) {
			if (vertexController.isVertexInHiddenLayer(vertex)) {
				removeVertex(vertex);
			} else {
				// add in- and output vertices into a new list to remove them later.
				inOutVerts.add(vertex);
			}
		}

		for (Vertex vertex : inOutVerts) {
			removeVertex(vertex);
		}
	}

	/**
	 * @see createEdge
	 */
	public boolean createEdge(final Vertex fromVertex, final Vertex toVertex) {
		return createEdge(fromVertex, toVertex, true);
	}

	/**
	 * Create and setup a new edge and adding it to the graph.
	 * 
	 * @param toVertex
	 * @param toVertex
	 * @param repaint
	 * @return boolean
	 */
	public boolean createEdge(final Vertex fromVertex, final Vertex toVertex, final boolean repaint) {
		if (fromVertex.equals(toVertex)) {
			// No self-edges
			return false;
		}
		Neuron fromNeuron = fromVertex.getModel();
		Neuron toNeuron = toVertex.getModel();

		if (fromVertex.mayHaveEdgeTo(toVertex)) {
			// Create a new edge with its synapse between the both vertices
			EdgeController<Edge> edgeController = EdgeController.getInstance();
			Edge edge = edgeController.getEdgeFactory().create();
			edge.createModel(fromNeuron, toNeuron);

			// Add new synapse (edge model) to the network
			Network.getNetwork().addSynapse(edge.getModel());

			// Add the new edge to the graph
			graph.addEdge(edge, fromVertex, toVertex, EdgeType.DIRECTED);

			if (repaint) {
				repaint();
			}
			return true;
		}

		return false;
	}

	/**
	 * Create and setup a new edge between each vertex in the set and adding it to the graph.
	 * 
	 * @param vertices
	 */
	public void createEdge(final Collection<Vertex> vertices) {
		for (Vertex v1 : vertices) {
			for (Vertex v2 : vertices) {
				if (createEdge(v1, v2) == false) {
					createEdge(v2, v1);
				}
			}
		}
	}

	/**
	 * @see removeEdge
	 */
	public void removeEdge(final Edge edge) {
		removeEdge(edge, true);
	}

	/**
	 * Remove the edge and optional repaint the graph.
	 * 
	 * @param edge
	 * @param repaint
	 */
	public void removeEdge(final Edge edge, final boolean repaint) {
		Synapse synapse = edge.getModel();
		Neuron fromNeuron = synapse.getFromNeuron();
		Neuron toNeuron = synapse.getToNeuron();

		// Remove synapse from the neurons
		fromNeuron.getOutgoingSynapses().remove(synapse);
		toNeuron.getIncomingSynapses().remove(synapse);

		// Remove synapse (edge model) from the network
		Network.getNetwork().removeSynapse(synapse);

		graph.removeEdge(edge);

		if (repaint) {
			repaint();
		}
	}

	/**
	 * Remove every edge in the list.
	 * 
	 * @param edges
	 */
	public void removeEdges(final Collection<Edge> edges) {
		for (Edge edge : edges) {
			removeEdge(edge, false);
		}
		repaint();
	}

	/**
	 * Remove all edges in the network.
	 */
	public void removeAllEdges() {
		Collection<Edge> edges = new CopyOnWriteArrayList(graph.getEdges());
		removeEdges(edges);
		Network.getNetwork().getSynapseSet().clear();
		repaint();
	}

	/**
	 * Returns whether the layer is a hidden layer.
	 * 
	 * @param layer
	 * @return
	 */
	public boolean isLayerHidden(final Layer layer) {
		int layerSize = Network.getNetwork().getLayers().size();
		return layerSize > 2 && layer.getIndex() > 0 && layer.getIndex() < layerSize - 1;
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
	 * Reset the selected symbol panel in the sidebar.
	 */
	public void select() {
		Main.instance.sidebar.selectedSymbolsPanel.reset();
	}

	/**
	 * Show a picked vertex in the selected symbol panel in the sidebar.
	 * 
	 * @param vertex
	 */
	public void selectVertex(final Vertex vertex) {
		Set<Vertex> set = new HashSet<Vertex>();
		set.add(vertex);
		selectVertex(set);
	}

	/**
	 * Show a picked set of vertices in the selected symbol panel in the sidebar.
	 * 
	 * @param set
	 */
	public void selectVertex(final Set<Vertex> set) {
		Main.instance.sidebar.selectedSymbolsPanel.updateVertex(set);
	}

	/**
	 * Show a picked edge in the selected symbol panel in the sidebar.
	 * 
	 * @param vertex
	 */
	public void selectEdge(final Edge edge) {
		Set<Edge> set = new HashSet<Edge>();
		set.add(edge);
		selectEdge(set);
	}

	/**
	 * Show a picked set of edges in the selected symbol panel in the sidebar.
	 * 
	 * @param set
	 */
	public void selectEdge(final Set<Edge> set) {
		Main.instance.sidebar.selectedSymbolsPanel.updateEdge(set);
	}

}
