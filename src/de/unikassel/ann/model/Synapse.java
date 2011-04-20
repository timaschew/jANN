package de.unikassel.ann.model;

import java.util.Random;

public class Synapse {
	
	private Double deltaWeight = 0.0d;
	
	private Double weight;
	
	private Neuron from;
	
	private Neuron to;
	
	
	public Synapse(Neuron fromNeuron, Neuron toNeuron) {
		weight = (2 * new Random().nextDouble()) -1;
		setFromNeuron(fromNeuron);
		setToNeuron(toNeuron);
	}

	public void setFromNeuron(Neuron n) {
		this.from = n;
		if ((n.getOutgoingSynapses() != null && n.getOutgoingSynapses().contains(this)) == false) {
			n.addOutgoingSynapse(this);
		}
	}
	public void setToNeuron(Neuron n) {
		this.to = n;
		if ((n.getIncomingSynapses() != null && n.getIncomingSynapses().contains(this)) == false) {
			n.addIncomingSynapse(this);
		}
	}
	
	public Neuron getFromNeuron() {
		return from;
	}
	
	public Neuron getToNeuron() {
		return to;
	}
 
	public Double getWeight() {
		return weight;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(from.getPosition());
		sb.append(" -> ");
		sb.append(to.getPosition());
		sb.append(" weight = ");
		sb.append(weight);
		return sb.toString();
	}

	public Double getDeltaWeight() {
		return deltaWeight;
	}

	public void setDeltaWeight(double delta) {
		deltaWeight = delta;
	}

	public void setWeight(double d) {
		weight = d;
	}

}
