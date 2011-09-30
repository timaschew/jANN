package de.unikassel.ann.gui.model;

import java.text.DecimalFormat;
import java.util.Set;

import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.Synapse;
import de.unikassel.ann.model.func.ActivationFunction;

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
		// Get the activateFunction
		ActivationFunction activateFunction = Network.getNetwork().getStandardFunction();

		// Create model
		model = new Neuron(activateFunction, false);
	}

	public void setIndex(final int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	/*
	 * Vertex model
	 */

	public void setModel(final Neuron model) {
		this.model = model;
	}

	public Neuron getModel() {
		return model;
	}

	public int getLayerIndex() {
		if (model == null) {
			return -1;
		}
		return model.getLayer().getIndex();
	}

	public void setValue(final Double value) {
		model.setValue(value);
	}

	public Double getValue() {
		if (model == null) {
			return null;
		}
		return model.getValue();
	}

	@Override
	public String toString() {
		if (df == null) {
			df = new DecimalFormat(Settings.properties.getProperty("gui.decimalFormat"), Settings.decimalSymbols);
		}
		String value = df.format(getValue());
		return "#" + index + " (" + value + ")";
	}

	@Override
	public int compareTo(final Vertex v) {
		return getIndex() - v.getIndex();
	}

	/**
	 * @param toVertex
	 * @return
	 */
	public boolean hasEdgeTo(final Vertex toVertex) {
		return getEdgeTo(toVertex) != null;
	}

	/**
	 * Get the synapse between this vertex and the toVertex.
	 * 
	 * @param toVertex
	 * @return
	 */
	public Synapse getEdgeTo(final Vertex toVertex) {
		Neuron fromNeuron = getModel();
		Neuron toNeuron = toVertex.getModel();

		Set<Synapse> synapseSet = Network.getNetwork().getSynapseSet();
		for (Synapse synapse : synapseSet) {
			if (synapse.getFromNeuron().equals(fromNeuron) && synapse.getToNeuron().equals(toNeuron)) {
				return synapse;
			}
		}
		return null;
	}

	/**
	 * Returns whether the vertex may have an edge to the target vertex.
	 * 
	 * @param toVertex
	 * @return
	 */
	public boolean mayHaveEdgeTo(final Vertex toVertex) {
		if (toVertex == null) {
			return false;
		}

		// The neurons do not have to be connected already
		if (hasEdgeTo(toVertex)) {
			return false;
		}

		// Avoid incoming synapses to a bias
		if (toVertex.getModel().isBias()) {
			return false;
		}

		// The "to"-vertex has to be in a layer with a higher index than the "from"-vertex
		int layerIndex = getModel().getLayer().getIndex();
		int toLayerIndex = toVertex.getModel().getLayer().getIndex();
		if (layerIndex >= toLayerIndex) {
			return false;
		}

		// Connect them dude! May the force be with you!
		return true;
	}

}
