package de.unikassel.ann.model;

import java.util.Random;

/**
 * Connection between two neurons.<br>
 * The weights of all synapses act like a database.<br>
 * The ANN is a directed graph: {@link #fromNeuron} and {@link #toNeuron} related to the direction.<br>
 * But the API provides bidirectional access:
 * 
 * <pre>
 * 	N = Neuron; S = Synapse
 *  
 *  N -> outgoingSynapses() -> S -> toNeuron() ---------> N
 *  N <- fromNeuron() <------- S <- incomingSynapses() <- N
 * </pre>
 *
 */
public class Synapse {
	
	/**
	 * Delta or error between the actual and desired value.<br>
	 * Needed for backpropagation
	 */
	private Double deltaWeight = 0.0d;
	
	/**
	 * database of the net
	 */
	private Double weight;
	
	/**
	 * 
	 */
	private Neuron fromNeuron;
	
	private Neuron toNeuron;
	
	/**
	 * Creates a synapse between the two neurons.
	 * @param fromNeuron
	 * @param toNeuron
	 */
	public Synapse(Neuron fromNeuron, Neuron toNeuron) {
		weight = null;
		setFromNeuron(fromNeuron);
		setToNeuron(toNeuron);
	}

	/**
	 * Sets the incoming neuron.
	 * @param n
	 */
	public void setFromNeuron(Neuron n) {
		this.fromNeuron = n;
		if ((n.getOutgoingSynapses() != null && n.getOutgoingSynapses().contains(this)) == false) {
			n.addOutgoingSynapse(this);
		}
	}
	/**
	 * Sets the outgoging neuron.
	 * @param n
	 */
	public void setToNeuron(Neuron n) {
		this.toNeuron = n;
		if ((n.getIncomingSynapses() != null && n.getIncomingSynapses().contains(this)) == false) {
			n.addIncomingSynapse(this);
		}
	}
	
	/**
	 * @return the neuron, where the synapse come from
	 */
	public Neuron getFromNeuron() {
		return fromNeuron;
	}
	
	/**
	 * @return the neuron where the synapse go to
	 */
	public Neuron getToNeuron() {
		return toNeuron;
	}
 
	/**
	 * @return weight
	 */
	public Double getWeight() {
		return weight;
	}

	/**
	 * @return delta weight / error
	 */
	public Double getDeltaWeight() {
		return deltaWeight;
	}

	/**
	 * Sets the delta weight / error
	 * @param delta
	 */
	public void setDeltaWeight(double delta) {
		deltaWeight = delta;
	}

	/**
	 * Sets the weight
	 * @param value
	 */
	public void setWeight(double value) {
		weight = value;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(fromNeuron.getPosition());
		sb.append(" -> ");
		sb.append(toNeuron.getPosition());
		sb.append(" weight = ");
		sb.append(weight);
		return sb.toString();
	}

}
