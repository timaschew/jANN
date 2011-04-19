package de.unikassel.ann.factory;

import java.util.ArrayList;
import java.util.List;

import de.unikassel.ann.model.ActivationFunction;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;

public class NetworkFactory {
	
	
	public static Network createNetwork(int input, int[] hidden, int output, ActivationFunction standardFunc, boolean bias) {
		
		Network net = new Network();
		
		Layer inputLayer = createLayer(input, standardFunc, bias);
		net.addLayer(inputLayer);
		for (int i=0; i<hidden.length; i++) {
			Layer currentHiddenLayer = createLayer(hidden[i], standardFunc, bias);
			net.addLayer(currentHiddenLayer);
		}
		Layer outputLayer = createLayer(output, standardFunc, bias);
		net.addLayer(outputLayer);

		net.finalizeStructure();
		return net;
	}
	
	public static Layer createLayer(int neuronCount, ActivationFunction standardFunc, boolean bias) {
		
		Layer l = new Layer();
		for (int i=0; i<neuronCount; i++) {
			Neuron n = new Neuron(standardFunc);
			l.addNeuron(n);
		}
		
		return l;
	}

}
