package de.unikassel.ann.gui.graph;

import java.text.DecimalFormat;

import de.unikassel.ann.gui.Main;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.Synapse;

public class Edge {

	// Synapse data model
	private Synapse model;

	// Edge index (raised by the EdgeFactory)
	private int index;

	// Edge weight format
	private static DecimalFormat df;

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

	/*
	 * Edge model
	 */

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
	 *            from
	 * @param Neuron
	 *            to
	 */
	public void createModel(Neuron from, Neuron to) {
		model = new Synapse(from, to);

		// TODO remove later (it's just for testing purpose)
		updateWeight(new Double(Math.random()));
	}

	public Double getWeight() {
		if (model == null) {
			return null;
		}
		return model.getWeight();
	}

	public void updateWeight(Double weight) {
		if (model == null) {
			return;
		}
		model.setWeight(weight);
	}

	@Override
	public String toString() {
		if (df == null) {
			df = new DecimalFormat(
					Main.properties.getProperty("gui.decimalFormat"), Main.decimalSymbols);
		}
		return df.format(getWeight());
	}

}
