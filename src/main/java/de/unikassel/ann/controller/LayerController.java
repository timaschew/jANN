package de.unikassel.ann.controller;

import java.util.ArrayList;
import java.util.List;

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

	protected List<JungLayer> layers;

	/**
	 * Constructor
	 */
	private LayerController() {
		layers = new ArrayList<JungLayer>();

		// Default create at least on layer
		addLayer();
	}

	public void addLayer() {
		// TODO DICUSS add automatically one vertex to this new layer?
		JungLayer layer = new JungLayer(layers.size());
		layers.add(layer);
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

	public Layer addVertex(final int index, final Vertex vertex) {
		if (index > layers.size()) {
			// First vertex for this layer -> Create new Layer
			addLayer();
		}
		JungLayer layer = layers.get(index);
		layer.addVertex(vertex);

		// Return the layer to which the vertex was added
		return layer.getLayer();
	}

	public void removeVertex(final int index) {
		JungLayer layer = layers.get(index);
		layer.removeVertex();
	}

	private final class JungLayer {
		private int index = -1;

		private Layer layer;
		private List<Vertex> vertices;

		public JungLayer(final int index) {
			this.index = index;
			layer = new Layer(); // TODO from where do we get the layers?
			layer.setIndex(index);
			vertices = new ArrayList<Vertex>();
		}

		public int getIndex() {
			return index;
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
	}
}
