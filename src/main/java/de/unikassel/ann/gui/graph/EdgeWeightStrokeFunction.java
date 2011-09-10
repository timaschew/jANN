package de.unikassel.ann.gui.graph;

import java.awt.BasicStroke;
import java.awt.Stroke;
import org.apache.commons.collections15.Transformer;

import edu.uci.ics.jung.visualization.RenderContext;

final class EdgeWeightStrokeFunction<E> implements Transformer<Edge, Stroke> {
	protected static final Stroke basic = new BasicStroke(1);
	protected static final Stroke heavy = new BasicStroke(2);
	protected static final Stroke dotted = RenderContext.DOTTED;

	protected boolean weighted = false;

	public EdgeWeightStrokeFunction() {
		this.weighted = true;
	}

	public void setWeighted(boolean weighted) {
		this.weighted = weighted;
	}

	public Stroke transform(Edge e) {
		if (weighted) {
			if (drawHeavy(e)) {
				return heavy;
			} else {
				return dotted;
			}
		} else {
			return basic;
		}
	}

	protected boolean drawHeavy(Edge e) {
		double value = e.getModel().getWeight();
		return (value > 0.7);
	}

}
