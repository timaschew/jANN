package de.unikassel.ann.gui;

import org.apache.commons.collections15.Transformer;

public class VertexInfo<V> implements Transformer<Vertex, String> {

	private static VertexInfo<Vertex> instance;

	public static VertexInfo<Vertex> getInstance() {
		if (instance == null) {
			instance = new VertexInfo<Vertex>();
		}
		return instance;
	}

	@Override
	public String transform(Vertex v) {
		return "Vertex (" + v.getIndex() + ")";
	}
}
