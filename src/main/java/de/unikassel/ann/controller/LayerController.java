package de.unikassel.ann.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import de.unikassel.ann.gui.graph.GraphLayoutViewer;
import de.unikassel.ann.gui.graph.Vertex;
import de.unikassel.ann.model.Layer;

public class LayerController<T> {

	private static LayerController<Layer> instance;

	public static LayerController<Layer> getInstance() {
		if (instance == null) {
			instance = new LayerController<Layer>();
		}
		return instance;
	}

	protected LinkedList<JungLayer> layers;

	/**
	 * Constructor
	 */
	private LayerController() {
		layers = new LinkedList<JungLayer>();
	}

	/**
	 * Add a layer and create its layer model.
	 */
	public void addLayer() {
		addLayer(layers.size());
	}

	/**
	 * Add a layer and create its layer model with the given index.
	 * 
	 * @param index
	 */
	public void addLayer(final int index) {
		Layer layer = new Layer();
		layer.setIndex(index);
		addLayer(layer);
	}

	/**
	 * Add a layer with an already existing layer model.
	 * 
	 * @param layer
	 */
	public void addLayer(final Layer layer) {
		int index = layer.getIndex();

		// Check if there is already a layer with the same index available
		if (index < layers.size() && layers.get(index) != null) {
			// Layer already exists -> Just replace its layer model
			layers.get(index).setLayer(layer);
			return;
		}

		// Create new Junglayer as a wrapper which contains the layer and its
		// vertices. The Junglayer has the same index as the layer.
		JungLayer jungLayer = new JungLayer(index);
		jungLayer.setLayer(layer);
		layers.add(jungLayer);
	}

	/**
	 * Add Vertex (1)
	 * 
	 * Add a new vertex to the layer with the index.
	 * 
	 * @param layerIndex
	 * @param addToGraph
	 * @return Layer
	 */
	public Layer addVertex(final int layerIndex) {
		return addVertex(layerIndex, false);
	}

	/**
	 * Add Vertex (2)
	 * 
	 * Add a new vertex to the layer with the index.
	 * 
	 * @param layerIndex
	 * @param addToGraph
	 * @return
	 */
	public Layer addVertex(final int layerIndex, boolean addToGraph) {
		System.out.println("(2) addVertex(" + layerIndex + ", " + addToGraph
				+ ")");
		// Create a new vertex
		Vertex vertex = VertexController.getInstance().getVertexFactory()
				.create();
		vertex.setup();

		return addVertex(layerIndex, vertex, addToGraph);
	}

	/**
	 * Add Vertex (3)
	 * 
	 * Add a vertex with the index to the layer with the index.
	 * 
	 * @param layerIndex
	 * @param vertexIndex
	 * @return Layer
	 */
	public Layer addVertex(final int layerIndex, final int vertexIndex) {
		return addVertex(layerIndex, vertexIndex, false);
	}

	/**
	 * Add Vertex (4)
	 * 
	 * Add a vertex with the index to the layer with the index and add the
	 * vertex to the graph.
	 * 
	 * @param layerIndex
	 * @param vertexIndex
	 * @param addToGraph
	 * @return
	 */
	public Layer addVertex(int layerIndex, int vertexIndex, boolean addToGraph) {
		int layerSize = getLayerSize(layerIndex);
		System.out.println("(4) addVertex() layerIndex = " + layerIndex
				+ ", layerSize = " + layerSize + ", vertexIndex = "
				+ vertexIndex);
		if (layerSize >= vertexIndex) {
			// Vertex already exists
			return layers.get(layerIndex).getLayer();
		}

		return addVertex(layerIndex, addToGraph);
	}

	/**
	 * Add Vertex (5)
	 * 
	 * Add the vertex to the layer with the index.
	 * 
	 * @param layerIndex
	 * @param vertex
	 * @param addToGraph
	 * @return Layer
	 */
	public Layer addVertex(final int layerIndex, final Vertex vertex,
			final boolean addToGraph) {
		if (layerIndex >= layers.size()) {
			// Layer with the index does not exist -> Create new Layer
			addLayer(layerIndex);
		}

		System.out.println("(5) addVertex(" + layerIndex + ", " + vertex + ", "
				+ addToGraph + ")");

		// Add vertex to the layer
		JungLayer layer = layers.get(layerIndex);
		layer.addVertex(vertex);

		// Add vertex to the graph
		if (addToGraph) {
			GraphLayoutViewer glv = GraphLayoutViewer.getInstance();
			glv.getGraph().addVertex(vertex);
			glv.repaint();
		}

		// Return the layer to which the vertex was added
		return layer.getLayer();
	}

	public void removeLayer() {
		int index = layers.size() - 1;

		JungLayer layer = layers.get(index);
		List<Vertex> vertices = layer.getVertices();

		// Remove the last layer and all its vertices
		for (Vertex vertex : vertices) {
			vertex.remove();
		}
		layers.remove(index);
	}

	public void removeLayer(final Integer index) {
		layers.remove(index);

		// TODO are the vertices automatically removed as well?
	}

	public void removeVertex(final int layerIndex, final int vertexIndex) {
		int layerSize;
		try {
			layerSize = getLayerSize(layerIndex);
		} catch (IndexOutOfBoundsException ex) {
			// Layer not found -> doesn't it exist?
			return;
		}
		if (layerSize <= vertexIndex) {
			return;
		}
		removeVertex(layerIndex);
	}

	public void removeVertex(final int layerIndex) {
		JungLayer layer = layers.get(layerIndex);
		layer.removeVertex();
	}

	public Set<Layer> getLayers() {
		return getVertices().keySet();
	}

	/**
	 * Get number of vertices in the layer with the index.
	 * 
	 * @param layerIndex
	 * @return
	 */
	public int getLayerSize(int layerIndex) {
		JungLayer jungLayer;
		try {
			jungLayer = layers.get(layerIndex);
		} catch (Exception ex) {
			// Layer not found!
			return 0;
		}
		return jungLayer.getVertices().size();
	}

	/**
	 * Get all vertices mapped to their layer.
	 * 
	 * @return
	 */
	public HashMap<Layer, List<Vertex>> getVertices() {
		// Sort layers by their index
		Collections.sort(layers);

		HashMap<Layer, List<Vertex>> map = new HashMap<Layer, List<Vertex>>();
		for (JungLayer jungLayer : layers) {
			map.put(jungLayer.getLayer(), jungLayer.getVertices());
		}
		return map;
	}

	/**
	 * Get all vertices on the layer with the index.
	 * 
	 * @param index
	 * @return
	 */
	public List<Vertex> getVerticesInLayer(final int index) {
		try {
			return layers.get(index).getVertices();
		} catch (IndexOutOfBoundsException ex) {
			// Return empty list
			return new ArrayList<Vertex>();
		}
	}

	/**
	 * Get a vertice by its id.
	 * 
	 * @param id
	 * @return
	 */
	public Vertex getVertexById(final Integer id) {
		HashMap<Layer, List<Vertex>> vertexMap = LayerController.getInstance()
				.getVertices();
		Iterator<Entry<Layer, List<Vertex>>> iter;
		Map.Entry<Layer, List<Vertex>> entry;
		List<Vertex> vertices;

		iter = vertexMap.entrySet().iterator();
		while (iter.hasNext()) {
			entry = iter.next();
			vertices = entry.getValue();

			for (Vertex vertex : vertices) {
				if (vertex.getModel().getId().equals(id)) {
					return vertex;
				}
			}
		}

		// Vertex not found!
		return null;
	}

	public void clear() {
		// Remove all layers with their vertices
		layers.clear();
	}

	/*
	 * Private JungLayer class
	 */
	private final class JungLayer implements Comparable<JungLayer> {
		private int index = -1;

		private Layer layer;
		private List<Vertex> vertices;

		public JungLayer(final int index) {
			this.index = index;
			vertices = new ArrayList<Vertex>();
		}

		public void setIndex(final int index) {
			this.index = index;
		}

		public int getIndex() {
			return index;
		}

		public void setLayer(final Layer layer) {
			this.layer = layer;
		}

		public Layer getLayer() {
			return layer;
		}

		public List<Vertex> getVertices() {
			return vertices;
		}

		public void addVertex(final Vertex vertex) {
			// Prevent duplicate vertices
			if (contains(vertex)) {
				return;
			}
			vertices.add(vertex);
		}

		public void removeVertex() {
			// Try to remove last vertex in this layer
			try {
				vertices.remove(vertices.size() - 1);
			} catch (IndexOutOfBoundsException ex) {
				// ex.printStackTrace();
			}
		}

		@Override
		public int compareTo(final JungLayer layer) {
			return getIndex() - layer.getIndex();
		}

		private boolean contains(Vertex vertex) {
			for (Vertex v : vertices) {
				if (v.getIndex() == vertex.getIndex()) {
					// Vertex found by its index!
					return true;
				}
			}
			// Vertex not found!
			return false;
		}
	}

}
