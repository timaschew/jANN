package de.unikassel.ann.gui;

import java.text.DecimalFormat;

import org.apache.commons.collections15.Transformer;

public class EdgeInfo<E> implements Transformer<Edge, String> {

	private static EdgeInfo<Edge> instance;
	private DecimalFormat df = new DecimalFormat("0.000");

	public static EdgeInfo<Edge> getInstance() {
		if (instance == null) {
			instance = new EdgeInfo<Edge>();
		}
		return instance;
	}

	@Override
	public String transform(Edge e) {
		Number weight = e.getModel().getWeight();
		return df.format(weight);
	}
}
