package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.lang.ArrayUtils;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.config.Topology;
import de.unikassel.ann.io.beans.SynapseBean;

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
	private List<Neuron> flatNet;
	private FlatSynapses flatSynpases;
	private int biggestLayer = -1;
	private Topology topo;
	
	public Network() {
		topo = new Topology();
		layers = new ArrayList<Layer>();
		flatNet = new ArrayList<Neuron>();
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
			biggestLayer = Math.max(biggestLayer, l.getNeurons().size());
			// set flat net
			for (Neuron n : l.getNeurons()) {
				n.setId(flatNet.size());
				flatNet.add(n);
			}
		}
		flatSynpases = new FlatSynapses(flatNet.size(), flatNet.size());
		
		if (topo.isStrictFF()) {
			// set synapses (strict forward feedback)
			for (Layer l : layers) {
				if (previousLayer != null) {
					for (Neuron fromNeuron : previousLayer.getNeurons()) {
						for (Neuron toNeuron : l.getNeurons()) {
							if (toNeuron.isBias() == false) {
								Synapse s = new Synapse(fromNeuron, toNeuron);
								flatSynpases.addSynapse(fromNeuron.getId(), toNeuron.getId(), s);
							}
						}
					}
				}
				previousLayer = l;
			}
		} else {
			List<SynapseBean> snypseBeanList = new ArrayList<SynapseBean>(); // getSynapseBean()
			for (SynapseBean b : snypseBeanList) {
				Neuron fromNeuron = flatNet.get(b.getFrom());
				Neuron toNeuron = flatNet.get(b.getTo());
				Synapse s = new Synapse(fromNeuron, toNeuron);
				flatSynpases.addSynapse(fromNeuron.getId(), toNeuron.getId(), s);
			}
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
	
	public Layer getLayer(Integer i) {
		return layers.get(i);
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
	
	/**
	 * 
	 * @param matrix
	 */
	public void setSynapseFlatMatrix(Double[][] matrix) {
		for (int from=0; from<matrix.length; from++) {
			for (int to=0; to<matrix[from].length; to++) {
				if (matrix[from][to] != null && matrix[from][to] != Double.NaN) {
					flatSynpases.setSynapseWeight(from, to, matrix[from][to]);
				}
			}
		}
	}
	
	public Double[][] getSynapseFlatMatrix() {
		Double[][] matrix = new Double[flatNet.size()][flatNet.size()];
		for (int from=0; from<flatNet.size(); from++) {
			for (int to=0; to<flatNet.size(); to++) {
				matrix[from][to] = flatSynpases.getSynapseWeight(from, to);
			}
		}
		return matrix;
	}
	
	public Double[][][][] getSynapseBigMatrix() {
		Double[][][][] matrix = new Double[layers.size()][biggestLayer][layers.size()][biggestLayer];
		for (int from=0; from<flatNet.size(); from++) {
			for (int to=0; to<flatNet.size(); to++) {
				Synapse s = flatSynpases.getSynapse(from, to);
				if (s != null) {
					int fromLayer = s.getFromNeuron().getLayer().getIndex();
					int fromNeuron = s.getFromNeuron().getIndex();
					int toLayer = s.getToNeuron().getLayer().getIndex();
					int toNeuron = s.getToNeuron().getIndex();
					matrix[fromLayer][fromNeuron][toLayer][toNeuron] = s.getWeight();
				}
			}
		}
		return matrix;
	}
	
	public void setSynapseBigMatrix(Double[][][][] m) {
		for (int fromLayer=0; fromLayer<m.length; fromLayer++) {
			if (fromLayer == getOutputLayer().getIndex()) {
				continue;
			}
			for (int fromNeuron=0; fromNeuron<m[fromLayer].length; fromNeuron++) {
				for (int toLayer=0; toLayer<m[fromLayer][fromNeuron].length; toLayer++) {
					for (int toNeuron=0; toNeuron<m[fromLayer][fromNeuron][toLayer].length; toNeuron++) {
						
						Neuron from = getLayer(fromLayer).getNeuron(fromNeuron);
						Neuron to = getLayer(toLayer).getNeuron(toNeuron);
						if (from != null && to != null) {
							Synapse s = flatSynpases.getSynapse(from.getId(), to.getId());
							if (s != null) {
								s.setWeight(m[fromLayer][fromNeuron][toLayer][toNeuron]);
							}
						}
					}
				}
			}
		}
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
