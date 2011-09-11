package de.unikassel.ann.gui.graph;

import java.text.DecimalFormat;

import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Neuron;

public class Vertex {

	// Neuron data model
	private Neuron model;

	// Vertex index (raised by the VertexFactory)
	private int index;

	// Vertex value format
	private static DecimalFormat df;

	/**
	 * Constructor
	 */
	public Vertex() {
		// TODO get activateFunction
		String activateFunc = "SigmoidFunction";

		// TODO bias enabled?
		boolean bias = false;

		// Create model
		model = new Neuron(activateFunc, bias);

		// TODO get actual selected layer
		setLayer(1);

		// TODO remove later, just for testing purpose
		setValue(new Double(Math.random()));
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	/*
	 * Vertex model
	 */

	public void setModel(Neuron model) {
		this.model = model;
	}

	public Neuron getModel() {
		return model;
	}

	public void setLayer(int index) {
		// TODO Get layer by its index
		Layer layer = new Layer();
		layer.setIndex(index);
		this.model.setLayer(layer);
	}

	public void setValue(Double value) {
		this.model.setOutputValue(value);
	}

	public Double getValue() {
		if (this.model == null) {
			return null;
		}
		return this.model.getOutputValue();
	}

	@Override
	public String toString() {
		if (df == null) {
			df = new DecimalFormat(
					GraphLayoutViewer.getProperty("gui.decimalFormat"));
		}
		String value = df.format(getValue());
		return "#" + index + " (" + value + ")";
	}

}
