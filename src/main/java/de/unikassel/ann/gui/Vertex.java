package de.unikassel.ann.gui;

import de.unikassel.ann.model.Neuron;

public class Vertex {

	// Neuron data model
	private Neuron model;

	// Vertex index (raised by the VertexFactory)
	private int index;

	/**
	 * Constructor
	 */
	public Vertex() {
		// TODO create model (which activation function)
		// neuron = new Neuron("", false);

		// TODO get selected layer to set it
		// neuron.setLayer(layer);
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setModel(Neuron model) {
		this.model = model;
	}

	public Neuron getModel() {
		return model;
	}

}
