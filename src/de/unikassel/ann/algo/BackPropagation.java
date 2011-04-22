package de.unikassel.ann.algo;

import java.util.List;

import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.Synapse;

public class BackPropagation {

	private final static Double LEARN_RATE = 0.35;
	private final static Double MOMENTUM = 0.8;
	private final static Integer PRINT_FREQ = 1000000;
	private static Integer stepCounter = 0;
	
	public static void train(Network net, DataPairSet set) {
		for (DataPair pair : set.getPairs()) {
			forwardStep(net, pair);
			backwardStep(net, pair);
		}
	}
	
	public static void printStep(Network net, DataPair pair) {
		forwardStep(net, pair);
		
		StringBuilder sb = new StringBuilder();
		int index = 0;
		for (Neuron n : net.getOutputLayer().getNeurons()) {
			sb.append(n.getOutputValue());
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
				if (l.equals(net.getInputLayer())) {
					continue;
				} else {
					// calculate netnput
					List<Synapse> synapseList = n.getIncomingSynapses();
					Double sum = 0.0d;
					for (Synapse s : synapseList) {
						sum += (s.getWeight() * s.getFromNeuron().getOutputValue());
					}
					if (n.isBias() == false) {
						n.activate(sum);
					}
				} 
			}
		}
	}
	


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
			if (l.equals(net.getInputLayer())) {
				break;
			}
			if (l.equals(net.getOutputLayer())) {
				calculateOutputError(l, pair);
			} else {
				calculateError(l);
			}
			calculateWeightDeltaAndUpdateWeights(l);
		}
	}
	
	private static void calculateOutputError(Layer outputLayer, DataPair pair) {
		List<Neuron> neuronList = outputLayer.getNeurons();
		Double[] ideal = pair.getIdeal();
		for (int i=0; i<ideal.length; i++) {
			Neuron n = neuronList.get(i);
			Double o = n.getOutputValue();
			Double t = ideal[i];
			// ((t - o) * o * (1 - o)
			double errorFactor = t-o;
			double delta = o * (1-o) * errorFactor;
			n.setDelta(delta);
			if (stepCounter % PRINT_FREQ == 0) {
				System.err.println(n.toString()+" error = "+delta);
			}
			stepCounter++;
		}
	}
	
	private static void calculateError(Layer currentLayer) {
		for (Neuron n : currentLayer.getNeurons()) {
			double errorFactor = 0.d;
			for (Synapse s : n.getOutgoingSynapses()) {
				errorFactor += (s.getWeight() * s.getToNeuron().getDelta());
			}
			Double o = n.getOutputValue();
			double delta = o * ( 1 - o) * errorFactor;
			n.setDelta(delta);
		}
	}

	private static void calculateWeightDeltaAndUpdateWeights(Layer l) {
		for (Neuron n : l.getNeurons()) {
			for (Synapse s : n.getIncomingSynapses()) {
				// TODO: check calculation is in correct loop
				Double oldDeltaWeight = s.getDeltaWeight();
				double delta = LEARN_RATE * s.getToNeuron().getDelta() * s.getFromNeuron().getOutputValue() + MOMENTUM * oldDeltaWeight;
				Double oldWeight = s.getWeight();
				s.setWeight(oldWeight+delta);
				s.setDeltaWeight(delta);
			}
		}
	}
}
