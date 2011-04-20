package de.unikassel.ann.algo;

import java.util.List;

import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.Synapse;

public class BackPropagation {
	
	public static void train(Network net, DataPairSet set) {
		for (DataPair pair : set.getPairs()) {
			forwardStep(net, pair);
			backwardStep(net, pair);
		}
	}
	
	public static void printStep(Network net, DataPair pair) {
		forwardStep(net, pair);
		
		StringBuilder sb = new StringBuilder();
		sb.append("SOLL / IST\n");
		int index = 0;
		for (Neuron n : net.getOutputLayer().getNeurons()) {
			sb.append(n.getActivationValue());
			sb.append( " / ");
			sb.append(pair.getIdeal()[index]);
			sb.append("\n");
			index++;
		}
		System.out.println(sb.toString());
	}
	
	public static void forwardStep(Network net, DataPair pair) {
		
		if (net.isFinalized() == false) {
			throw new IllegalArgumentException("net not finalized yet");
		}
		net.setInputLayerValues(pair.getInput());
		
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
	}
	


	private final static Double LEARN_RATE = 0.350;
	private final static Double MOMENTUM = 0.8;
	
	// learnRate * (delta rn) * s + momentum * w
	// W(D)[2][1][1] = L * (D)[3][1] * N[2][1] + M * this(t-1)
	
	// W(D) = weight delta
	// L = LEARN_RATE
	// (D) = Neuron delta/error
	// N = Neuron state/activation value
	// M = MOMENTUM
	public static void backwardStep(Network net, DataPair pair) {
		List<Layer> reversedLayers = net.reverse();
		for (Layer l : reversedLayers) {
			if (l.equals(net.getOutputLayer())) {
				calculateOutputError(l, pair);
			} else {
				calculateError(l);
			}
			
			if (net.getInputLayer().equals(l) == false) {
				calculateWeightDeltaAndUpdateWeights(l);
			}
			
		}
	}
	
	private static void calculateOutputError(Layer outputLayer, DataPair pair) {
		List<Neuron> neuronList = outputLayer.getNeurons();
		Double[] ideal = pair.getIdeal();
		for (int i=0; i<ideal.length; i++) {
			Neuron n = neuronList.get(i);
			Double o = n.getActivationValue();
			Double t = ideal[i];
			// ((t - o) * o * (1 - o)
			double error = (t-o) * o * (1-o);
			n.setError(error);
		}
	}
	
	private static void calculateError(Layer currentLayer) {
		double sum = 0.d;
		for (Neuron n : currentLayer.getNeurons()) {
			for (Synapse s : n.getOutputSynapses()) {
				sum += s.getWeight() * s.getToNeuron().getError();
			}
			Double o = n.getActivationValue();
			double delta = o * ( 1 - o) * sum;
			n.setError(delta);
		}
	}

	private static void calculateWeightDeltaAndUpdateWeights(Layer l) {
		Layer prevLayer = l.getPrevLayer();
		if (prevLayer == null) {
			throw new NullPointerException("prev layer is null");
		}
		for (Neuron n : l.getNeurons()) {
			for (Synapse s : n.getInputSynapses()) {
				Double oldDeltaWeight = s.getDeltaWeight();
				double delta = LEARN_RATE * s.getToNeuron().getError() * s.getFromNeuron().getActivationValue() + MOMENTUM * oldDeltaWeight;
				s.setDeltaWeight(delta);
				Double oldWeight = s.getWeight();
				s.setWeight(oldWeight+delta);
			}
		}
	}
}
