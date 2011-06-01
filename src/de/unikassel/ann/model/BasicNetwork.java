package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.List;

public abstract class BasicNetwork {
	
	protected List<Layer> layers;
	
	public BasicNetwork() {
		layers = new ArrayList<Layer>();
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

	public Integer getBiggestLayer() {
		int tempSize = -1;
		for (Layer l : layers) {
			tempSize = Math.max(tempSize, l.getNeurons().size());
		}
		return tempSize;
	}
}
