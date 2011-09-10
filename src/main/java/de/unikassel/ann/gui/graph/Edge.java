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
		// Create edge (synapse) between two vertexes (neurons)
		// TODO get from and to neuron
		Neuron from = new Neuron("SigmoidFunction", false);
		Neuron to = new Neuron("SigmoidFunction", false);
		model = new Synapse(from, to);
		
		// TODO remove later (it's just for testing purpose)
		model.setWeight(new Float(Math.random()));
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
