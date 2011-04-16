package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.List;

public class Network {
	
	private List<Layer> layers;
	private Boolean finalyzed;
	
	
	public Network() {
		layers = new ArrayList<Layer>();
		finalyzed = false;
	}
	
	public void addLayer(Layer l) {
		layers.add(l);
	}
	
	public void finalyze() {
		
	}
	
	public Layer getInputLayer() {
		if (finalyzed) {
			return layers.get(0);
		}
		return null;
	}
	
	public Layer getOutputLayer() {
		if (finalyzed) {
			return layers.get(layers.size()-1);
		}
		return null;
	}
	
	public List<Layer> getLayers() {
		return layers;
	}

}
