package de.unikassel.ann.gui.graph;

import org.apache.commons.collections15.Transformer;

public class EdgeTooltip<E> implements Transformer<Edge, String> {

	private static EdgeTooltip<Edge> instance;

	public static EdgeTooltip<Edge> getInstance() {
		if (instance == null) {
			instance = new EdgeTooltip<Edge>();
		}
		return instance;
	}

	@Override
	public String transform(Edge e) {
		// TODO get value of the Edge by its model
		// e.getModel().getWeight();
		return "The amazing Tooltip of the Edge #" + e.getIndex();
	}
}
