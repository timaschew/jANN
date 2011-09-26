package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicNetwork {

	protected SynapseMatrix synapseMatrix;

	protected List<Layer> layers;

	public BasicNetwork() {
		layers = new ArrayList<Layer>();
	}

	public SynapseMatrix getSynapseMatrix() {
		return synapseMatrix;
	}

	/**
	 * Adds a layer and creates a backlink from the layer to this net
	 * 
	 * @param layer
	 */
	public void addLayer(final Layer l) {
		l.setIndex(layers.size());
		layers.add(l);
		if ((l.getNet() != null && l.getNet().equals(this)) == false) {
			l.setNet(this);
		}
	}

	public Layer getInputLayer() {
		return layers.get(0);
	}

	public Layer getOutputLayer() {
		return layers.get(layers.size() - 1);
	}

	public List<Layer> getLayers() {
		return layers;
	}

	public Layer getLayer(final Integer i) {
		return layers.get(i);
	}

	/**
	 * Returns the size minus one if a bias exist
	 * 
	 * @return
	 */
	public int getInputSizeIgnoringBias() {
		int biasOffset = getInputLayer().hasBias() ? 1 : 0;
		return getInputLayer().getNeurons().size() - biasOffset;
	}

	/**
	 * Returns the size with bias, if anyone exist
	 * 
	 * @return
	 */
	public int getTotalInputSize() {
		return getInputLayer().getNeurons().size();
	}

	public int getTotalLayerSize(final int layerIndex) {
		return getLayer(layerIndex).getNeurons().size();
	}

	public int getOutputSize() {
		return getOutputLayer().getNeurons().size();
	}

	public Integer getBiggestLayer() {
		int tempSize = -1;
		for (Layer l : layers) {
			tempSize = Math.max(tempSize, l.getNeurons().size());
		}
		return tempSize;
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
