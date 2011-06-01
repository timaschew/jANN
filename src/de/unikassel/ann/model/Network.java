package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.io.beans.SynapseBean;
import de.unikassel.ann.io.beans.TopologyBean;

/**
 * The network is a container for the layers.<br>
 * The first layer is the inputLayer and the last the outputLayer.<br>
 * When creating a network with the factory,
 *  you need to call {@link #finalizeStructure()} for linking the neurons (set synapses).<br>
 *
 */
public class Network extends BasicNetwork {
	
	private Boolean finalyzed;
	private NetConfig config;
	private List<Neuron> flatNet;
	private SynapseMatrix synapseMatrix;
	
	public Network() {
		super();
		flatNet = new ArrayList<Neuron>();
		synapseMatrix = new SynapseMatrix(this, null, null);
		finalyzed = false;
	}
	

	
	public void finalizeFromFlatNet(List<TopologyBean> topoBeanList, List<SynapseBean> synapsesBanList) {
		if (finalyzed) {
			return;
		}
		
		int maxHiddenIndex = 0;
		for (TopologyBean b : topoBeanList) {
			maxHiddenIndex = Math.max(maxHiddenIndex, b.getLayer());
		}
		Layer[] hiddenLayer = new Layer[maxHiddenIndex+1];
		// add layers to net
		Layer inputLayer = new Layer();
		addLayer(inputLayer);
		for (int i=0; i<maxHiddenIndex+1; i++) {
			hiddenLayer[i] = new Layer();
			addLayer(hiddenLayer[i]);
		}
		Layer outputLayer = new Layer();
		addLayer(outputLayer);
		
		// creating neurons and adding it to flatNet and layers
		for (TopologyBean b : topoBeanList) {
			Neuron n = new Neuron(b.getFunction(), b.getBias());
			n.setId(b.getId());
			flatNet.add(n);
			if (b.getLayer() == -1) {
				inputLayer.addNeuron(n);
			} else if (b.getLayer() == -2) {
				outputLayer.addNeuron(n);
			} else {
				hiddenLayer[b.getLayer()].addNeuron(n);
			}
		}
		
		synapseMatrix.setSize(flatNet.size(), flatNet.size());
		
		setFlatSynapses(synapsesBanList);
		
		finalyzed = true;
	}
	

	public void setFlatSynapses(List<SynapseBean> synapsesBanList) {
		// connect neurons / create synapses
		for (SynapseBean b : synapsesBanList) {
			synapseRangeCheck(b);
			Neuron fromNeuron = flatNet.get(b.getFrom());
			Neuron toNeuron = flatNet.get(b.getTo());
			Synapse s = new Synapse(fromNeuron, toNeuron);
			if (b.getRandom() == false) {
				s.setWeight(b.getValue());
			}
			neuronRangeCheck(fromNeuron, toNeuron);
			synapseMatrix.addOrUpdateSynapse(s);
		}


	}

	private void neuronRangeCheck(Neuron fromNeuron, Neuron toNeuron) {
		if (fromNeuron.getId() < 0) {
			throw new IllegalArgumentException("invalid neuron id, negative value not permitted\n"+fromNeuron);
		} else if (toNeuron.getId() >= synapseMatrix.getSynapses().length) {
			throw new IllegalArgumentException("invalid neuron id, don't fit into flat synapse array \n"+toNeuron);
		}
		
	}

	private void synapseRangeCheck(SynapseBean b) {
		if (b.getFrom() < flatNet.get(0).getId()) {
			throw new IllegalArgumentException("synapse connections does not match with neuron ids, 1st id is too small\n"+b);
		} else if (b.getTo() > flatNet.get(flatNet.size()-1).getId()) { 
			throw new IllegalArgumentException("synapse connections does not match with neuron ids, 2nd id is too high\n"+b);
		}
		
	}

	/**
	 * Creates the synapses between all neurons. After this call you can train the network.
	 */
	public void finalizeStructure() {
		if (finalyzed) {
			return;
		}

		Layer previousLayer = null;
		for (Layer l : layers) {
			
			// set flat net
			for (Neuron n : l.getNeurons()) {
				n.setId(flatNet.size());
				flatNet.add(n);
			}
		}
		synapseMatrix.setSize(flatNet.size(), flatNet.size());
		

		// set synapses (strict forward feedback)
		for (Layer l : layers) {
			if (previousLayer != null) {
				for (Neuron fromNeuron : previousLayer.getNeurons()) {
					for (Neuron toNeuron : l.getNeurons()) {
						if (toNeuron.isBias() == false) {
							Synapse s = new Synapse(fromNeuron, toNeuron);
							synapseMatrix.addOrUpdateSynapse(s);
						}
					}
				}
			}
			previousLayer = l;
		}

		finalyzed = true;
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
	
	public void setOutputToPair(Double[] output) {
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

	/**
	 * @return the network in reverse order for backpropagation
	 */
	public List<Layer> reverse() {
		ArrayList<Layer> reversedLayers = new ArrayList<Layer>(layers);
		Collections.reverse(reversedLayers);
		return reversedLayers;
	}

	public void setConfig(NetConfig config) {
		this.config = config;
	}
	public NetConfig getConfig() {
		return config;
	}

	public List<Neuron> getFlatNet() {
		return flatNet;	
	}
	public SynapseMatrix getSynapseMatrix() {
		return synapseMatrix;
	}

	public int getInputSizeIgnoringBias() {
		int biasOffset = getInputLayer().hasBias() ? 1 : 0;
		return getInputLayer().getNeurons().size() - biasOffset;
	}
	public int getOutputSize() {
		return getOutputLayer().getNeurons().size();
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

}
