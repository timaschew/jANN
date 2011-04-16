package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.List;

public class Layer {
	
	private List<Neuron> neurons;
	
	public Layer() {
		neurons = new ArrayList<Neuron>();
	}
	
	public void addNeuron(final Neuron n) {
		neurons.add(n);
	}
	
	public List<Neuron> getNeurons() {
		return neurons;
	}
	
	public Neuron getNeuron(Integer i) {
		return neurons.get(i);
	}

}
