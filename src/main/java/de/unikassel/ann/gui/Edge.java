package de.unikassel.ann.gui;

import de.unikassel.ann.model.Synapse;

public class Edge {

	// Synapse data model
	private Synapse model;

	// Edge index (raised by the EdgeFactory)
	private int index;

	/**
	 * Constructor
	 */
	public Edge() {
		// TODO create model (which activation function)
		// neuron = new Synapse("", false);

		// TODO get selected layer to set it
		// neuron.setLayer(layer);
	}

	public void setIndex(int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public void setModel(Synapse model) {
		this.model = model;
	}

	public Synapse getModel() {
		return model;
	}
}
