package de.unikassel.ann.gui.graph;

import de.unikassel.ann.model.Neuron;
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

	/**
	 * Create edge (synapse) between two vertexes (neurons)
	 * 
	 * @param Neuron
	 * @param Neuron
	 */
	public void createModel(Neuron from, Neuron to) {
		model = new Synapse(from, to);

		// TODO remove later (it's just for testing purpose)
		updateWeight(new Double(Math.random()));
	}

	public void updateWeight(Double weight) {
		if (model == null) {
			return;
		}
		model.setWeight(weight);
	}
}
