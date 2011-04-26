package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import de.unikassel.ann.config.NetConfig;

/**
 * The network is a container for the layers.<br>
 * The first layer is the inputLayer and the last the outputLayer.<br>
 * When creating a network with the factory,
 *  you need to call {@link #finalizeStructure()} for linking the neurons (set synapses).<br>
 *
 */
public class Network {
	
	private List<Layer> layers;
	private Boolean finalyzed;
	private NetConfig config;
	
	public Network() {
		layers = new ArrayList<Layer>();
		finalyzed = false;
	}
	
	/**
	 * Adds a layer and creates a backlink from the layer to this net
	 * @param layer
	 */
	public void addLayer(Layer l) {
		l.setIndex(layers.size());
		layers.add(l);
		if ((l.getNet() != null && l.getNet().equals(this)) == false) {
			l.setNet(this);
		}
	}
	
	/**
	 * Creates the synapses between all neurons. After this call you can train the network.
	 */
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
	 * Sets values for inputLayer.<br> 
	 * Lenght of array must match to input layer neuron count!
	 * @param input for whole input layer
	 */
	public void setInputLayerValues(Double[] input) {
		Layer layer = getInputLayer();
		List<Neuron> neuronList = layer.getNeurons();
		int biasOffset = layer.hasBias() ? 1 : 0;
		if (input.length != neuronList.size()-biasOffset) {
			throw new IllegalArgumentException("input layer count != input.lenght");
		}
		for (int i=biasOffset; i<neuronList.size(); i++) {
			neuronList.get(i).setOutputValue(input[i-biasOffset]);
		}
	}
	
	public void setOutput(Double[] output) {
		Layer layer = getOutputLayer();
		List<Neuron> neuronList = layer.getNeurons();
		if (output.length != neuronList.size()) {
			throw new IllegalArgumentException("output layer count != ouput.length");
		}
		for (int i=0; i<neuronList.size(); i++) {
			output[i] = neuronList.get(i).getOutputValue();
		}
	}

	/**
	 * @return true if {@link #finalizeStructure()} was called; otherwise false
	 */
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
	
	public void setSynapseMatrix(Double[][][] matrix) {
		for (Layer l : layers) {
			for (Neuron n : l.getNeurons()) {
				List<Synapse> synapses = n.getOutgoingSynapses();
				for (Synapse s : synapses) {
					s.setWeight(matrix[l.getIndex()][s.getFromNeuron().getIndex()][s.getToNeuron().getIndex()]);
				}
			}
		}
	}
	
	public Double[][][] getSynapseMatrix() {
		Double[][][] matrix = new Double[3][3][3];
		for (Layer l : layers) {
			for (Neuron n : l.getNeurons()) {
				List<Synapse> synapses = n.getOutgoingSynapses();
				for (Synapse s : synapses) {
					matrix[l.getIndex()][s.getFromNeuron().getIndex()][s.getToNeuron().getIndex()] = s.getWeight();
				}
			}
		}
		return matrix;
	}

	/**
	 * @return the network in reverse order for backpropagation
	 */
	public List<Layer> reverse() {
		ArrayList<Layer> reversedLayers = new ArrayList<Layer>(layers);
		Collections.reverse(reversedLayers);
		return reversedLayers;
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

	public void setConfig(NetConfig config) {
		this.config = config;
	}
	public NetConfig getConfig() {
		return config;
	}

}
