package de.unikassel.ann.gui;

import org.apache.commons.collections15.Transformer;

public class VertexTooltip<V> implements Transformer<Vertex, String> {

	private static VertexTooltip<Vertex> instance;

	public static VertexTooltip<Vertex> getInstance() {
		if (instance == null) {
			instance = new VertexTooltip<Vertex>();
		}
		return instance;
	}

	@Override
	public String transform(Vertex v) {
		// TODO get value of the vertex by its model
		// v.getModel().getOutputValue();
		return "The amazing Tooltip of the Vertex #" + v.getIndex();
	}
}
