package de.unikassel.ann.algo;

import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;

public class BackPropagation {
	
	
	
	void forwardPass(Network net, DataPair pair) {
		for (Layer l : net.getLayers()) {
			// skip input layer
			if (l.equals(net.getInputLayer())) {
				continue;
			}
			for (Neuron n : l.getNeurons()) {
				// calculate netnput
				// ...
			}
		}
	}

}
