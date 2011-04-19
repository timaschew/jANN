package de.unikassel.ann.algo;

import java.util.List;

import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.Synapse;

public class BackPropagation {
	
	public static void forwardPass(Network net, DataPair pair) {
		
		if (net.isFinalized() == false) {
			throw new IllegalArgumentException("net not finalized yet");
		}
		net.setInputLayer(pair.getInput());
		
		for (Layer l : net.getLayers()) {
			for (Neuron n : l.getNeurons()) {
				// calculate netnput (not for input layer)
				if (l.equals(net.getInputLayer()) == false) {
					// calculate netnput
					List<Synapse> synapseList = n.getInputSynapses();
					Double sum = 0.0d;
					for (Synapse s : synapseList) {
						sum += s.getWeight() * s.getFromNeuron().getActivationValue();
					}
					n.setInputValue(sum);
				} 
				n.activate(); // activate neurons for all layers
			}
		}
		net.printSynapses();
	}
	
	// TODO
	private void calculateError() {
		
	}

	// TODO
	private void backwardPass() {
		
	}
}
