package de.unikassel.ann.factory;

import java.util.ArrayList;
import java.util.List;

import de.unikassel.ann.model.ActivationFunction;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;

public class NetworkFactory {
	
	
	public static Network createNetwork(int inputCount, int[] hiddenCount, int outputCount, ActivationFunction standardFunc, boolean bias) {
		
		Network net = new Network();
		
		Layer inputLayer = createLayer(inputCount, standardFunc, bias);
		net.addLayer(inputLayer);
		for (int i=0; i<hiddenCount.length; i++) {
			Layer currentHiddenLayer = createLayer(hiddenCount[i], standardFunc, bias);
			net.addLayer(currentHiddenLayer);
		}
		Layer outputLayer = createLayer(outputCount, standardFunc, bias);
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
