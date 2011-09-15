package de.unikassel.ann.gui.graph;

import java.text.DecimalFormat;

import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Neuron;

public class Vertex implements Comparable<Vertex> {

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

	public int getLayer() {
		if (this.model == null) {
			return -1;
		}
		return this.model.getLayer().getIndex();
	}

	public void setValue(Double value) {
		this.model.setOutputValue(value);
	}

	public Double getValue() {
		if (this.model == null) {
			return null;
		}
		return this.model.getValue();
	}

	public void remove() {
		// TODO Auto-generated method stub
		System.out.println(this + " remove()");
	}

	@Override
	public String toString() {
		if (df == null) {
			df = new DecimalFormat(
					Settings.properties.getProperty("gui.decimalFormat"),
					Settings.decimalSymbols);
		}
		String value = df.format(getValue());
		return "#" + index + " (" + value + ")";
	}

	@Override
	public int compareTo(Vertex v) {
		return getIndex() - v.getIndex();
	}

}
