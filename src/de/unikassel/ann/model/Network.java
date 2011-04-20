package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

public class Network {
	
	private List<Layer> layers;
	private Boolean finalyzed;
	
	
	public Network() {
		layers = new ArrayList<Layer>();
		finalyzed = false;
	}
	
	public void addLayer(Layer l) {
		l.setIndex(layers.size());
		layers.add(l);
		if ((l.getNet() != null && l.getNet().equals(this)) == false) {
			l.setNet(this);
		}
	}
	
	public void finalizeStructure() {
		Layer previousLayer = null;
		for (Layer l : layers) {
			if (previousLayer != null) {
				for (Neuron fromNeuron : previousLayer.getNeurons()) {
					for (Neuron toNeuron : l.getNeurons()) {
						if (toNeuron.isBias() == false) {
							new Synapse(fromNeuron, toNeuron);
						}
					}
				}
			}
			previousLayer = l;
		}
		finalyzed = true;
	}
	
	public Layer getInputLayer() {
		return layers.get(0);
	}
	
	public Layer getOutputLayer() {
		return layers.get(layers.size()-1);
	}
	
	public List<Layer> getLayers() {
		return layers;
	}

	/**
	 * Set the inputValue for each neuron of the input layer<br>
	 * Lenght of array must match to input layer neuron count!
	 * @param input for whole layer
	 */
	public void setInputLayerValues(Double[] input) {
		Layer layer = getInputLayer();
		List<Neuron> neuronList = layer.getNeurons();
		int biasOffset = layer.hasBias() ? 1 : 0;
		if (input.length != neuronList.size()-biasOffset) {
			throw new IllegalArgumentException("input layer count != input.lenght");
		}
		for (int i=biasOffset; i<neuronList.size(); i++) {
			neuronList.get(i).setInputValue(input[i-biasOffset]);
		}
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("finalized: ");
		sb.append(finalyzed);
		sb.append(", ");
		sb.append(layers.size());
		sb.append(" layers");
		
		return sb.toString();
	}

	public boolean isFinalized() {
		return finalyzed;
	}

	public void printSynapses() {
		System.out.println("synapse net");
		for (Layer l : layers) {
			for (Neuron n : l.getNeurons()) {
				List<Synapse> synapses = n.getOutgoingSynapses();
				for (Synapse s : synapses) {
					System.out.println(s);
				}
			}
		}
	}

	public List<Layer> reverse() {
		ArrayList<Layer> reversedLayers = new ArrayList<Layer>(layers);
		Collections.reverse(reversedLayers);
		return reversedLayers;
	}
}
