package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.List;

public class Layer {
	
	private int index = -1;
	
	private List<Neuron> neurons;

	private Network network;

	private boolean layerWithBias;
	
	public Layer() {
		neurons = new ArrayList<Neuron>();
	}
	
	public void addNeuron(final Neuron n) {
		if (layerWithBias && n.isBias()) {
			throw new IllegalArgumentException("second bias neuron not allowed");
		}
		n.setIndex(neurons.size());
		neurons.add(n);
		if ((n.getLayer() != null && n.getLayer().equals(this)) == false) {
			n.setLayer(this);
		}
		if (n.isBias()) {
			layerWithBias = true;
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
		sb.append(layerWithBias ? " (B)" : "");
		sb.append(" ");
		sb.append(neurons.size());
		sb.append(" neurons\n");
		
		return sb.toString();
	}

	public int getIndex() {
		return index;
	}

	public Layer getNextLayer() {
		return network.getLayers().get(index+1);
	}

	public Layer getPrevLayer() {
		if (index > 0) {
			return network.getLayers().get(index-1);
		}
		return null;
	}
	
	public void setNet(Network net) {
		network = net;
		if (net.getLayers().contains(this) == false) {
			throw new IllegalAccessError("should use addLayer() in Network class");
		}
	}
	
	public Network getNet() {
		return network;
	}

	public boolean hasBias() {
		return layerWithBias;
	}

	public void setBias(boolean bias) {
		layerWithBias = bias;
		
	}



}
