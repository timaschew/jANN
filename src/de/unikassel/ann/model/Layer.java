package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.List;

public class Layer {
	
	private Integer index = -1;
	
	private List<Neuron> neurons;
	
	public Layer() {
		neurons = new ArrayList<Neuron>();
	}
	
	public Layer(Integer index) {
		this();
		this.index = index;
	}
	
	public void addNeuron(final Neuron n) {
		n.setIndex(neurons.size());
		neurons.add(n);
		if ((n.getLayer() != null && n.getLayer().equals(this)) == false) {
			n.setLayer(this);
		}
	}
	
	public List<Neuron> getNeurons() {
		return neurons;
	}
	
	public Neuron getNeuron(Integer i) {
		return neurons.get(i);
	}

	public void setIndex(int size) {
		index = size;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(index);
		sb.append("]");
		sb.append(" ");
		sb.append(neurons.size());
		sb.append(" neurons");
		
		return sb.toString();
	}

	public Integer getIndex() {
		return index;
	}

}
