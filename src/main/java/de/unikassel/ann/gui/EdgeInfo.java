package de.unikassel.ann.gui;

import org.apache.commons.collections15.Transformer;

public class EdgeInfo<E> implements Transformer<Edge, String> {

	private static EdgeInfo<Edge> instance;

	public static EdgeInfo<Edge> getInstance() {
		if (instance == null) {
			instance = new EdgeInfo<Edge>();
		}
		return instance;
	}

	@Override
	public String transform(Edge e) {
		return "Edge (" + e.getIndex() + ")";
	}
}
