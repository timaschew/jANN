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

import de.unikassel.ann.factory.VertexFactory;
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
	private VertexFactory vertexFactory;

	/**
	 * Constructor
	 */
	private LayerController() {
		layers = new LinkedList<JungLayer>();
		vertexFactory = new VertexFactory();

		// Default create at least on layer
		addLayer();
	}

	/**
	 * Add a layer and create its layer model.
	 */
	public void addLayer() {
		Layer layer = new Layer();
		layer.setIndex(layers.size());
		addLayer(layer);
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

	public Layer addVertex(final int layerIndex, final int vertexIndex) {
		if (layers.get(layerIndex).getVertices().size() >= vertexIndex) {
			return null;
		}
		return addVertex(layerIndex);
	}

	public Layer addVertex(final int layerIndex) {
		Vertex vertex = vertexFactory.create();
		vertex.setup();

		return addVertex(layerIndex, vertex);
	}

	public Layer addVertex(final int layerIndex, final Vertex vertex) {
		if (layerIndex >= layers.size()) {
			// Layer with the index does not exist -> Create new Layer
			addLayer(layerIndex);
		}
		JungLayer layer = layers.get(layerIndex);
		layer.addVertex(vertex);

		// Return the layer to which the vertex was added
		return layer.getLayer();
	}

	public void removeVertex(final int layerIndex, final int vertexIndex) {
		if (layers.get(layerIndex).getVertices().size() <= vertexIndex) {
			return;
		}
		removeVertex(layerIndex);
	}

	public void removeVertex(final int layerIndex) {
		JungLayer layer = layers.get(layerIndex);
		layer.removeVertex();
	}

	public Set<Layer> getLayer() {
		return getVertices().keySet();
	}

	public HashMap<Layer, List<Vertex>> getVertices() {
		// Sort layers by their index
		Collections.sort(layers);

		HashMap<Layer, List<Vertex>> map = new HashMap<Layer, List<Vertex>>();
		for (JungLayer jungLayer : layers) {
			map.put(jungLayer.getLayer(), jungLayer.getVertices());
		}
		return map;
	}

	public int getNumVerticesInLayer(final int index) {
		return layers.get(index).getVertices().size();
	}

	public Vertex getVertexById(final Integer id) {
		HashMap<Layer, List<Vertex>> vertexMap = LayerController.getInstance().getVertices();
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
			vertices.add(vertex);
		}

		public void removeVertex() {
			// Remove last vertex
			vertices.remove(vertices.size() - 1);
		}

		@Override
		public int compareTo(final JungLayer layer) {
			return getIndex() - layer.getIndex();
		}
	}

}
