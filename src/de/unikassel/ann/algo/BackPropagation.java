package de.unikassel.ann.algo;

import java.util.List;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.Synapse;
import de.unikassel.ann.strategy.Strategy;

public class BackPropagation extends TrainingModule implements WorkModule {
	
	private Double momentum;
	private Double learnRate;
	
	public BackPropagation() {
		this(0.35, 0.8);
	}

	public BackPropagation(Double learnRate, Double momentum) {
		this.learnRate = learnRate;
		this.momentum = momentum;
	}

	@Override
	public void work(Network net, DataPairSet testData) {
		for(DataPair p : testData.getPairs()) {
			forwardStep(net, p);
			net.setOutput(p.getIdeal());
		}
	}
	
	private void forwardStep(Network net, DataPair pair) {
		
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

	@Override
	public void train(DataPairSet trainingData) {
		Network net = config.getNetwork();
		while(true) {
			
			for (Strategy s : config.getStrategies()) {
				s.preIteration();
			}
			
			for (DataPair pair : trainingData.getPairs()) {
				forwardStep(net, pair);
				backwardStep(net, pair);
				if (config.stopTraining()) {
					return;
				}
				currentStep++;
			}
			currentIteration++;
			
			for (Strategy s : config.getStrategies()) {
				s.postIteration();
			}
		}
	}
	
	private void backwardStep(Network net, DataPair pair) {
		List<Layer> reversedLayers = net.reverse();
		for (Layer l : reversedLayers) {
			if (l.equals(net.getInputLayer())) {
				break;
			}
			if (l.equals(net.getOutputLayer())) {
				Double rmseError = calculateOutputError(l, pair);
				currentError = rmseError;
			} else {
				calculateError(l);
			}
			calculateWeightDeltaAndUpdateWeights(l);
		}
	}
	
	private Double calculateOutputError(Layer outputLayer, DataPair pair) {
		List<Neuron> neuronList = outputLayer.getNeurons();
		Double[] ideal = pair.getIdeal();
		Double rmseError = 0.0;
		for (int i=0; i<ideal.length; i++) {
			Neuron n = neuronList.get(i);
			Double o = n.getOutputValue();
			Double t = ideal[i];
			// ((t - o) * o * (1 - o)
			double errorFactor = t-o;
			double delta = o * (1-o) * errorFactor;
			n.setDelta(delta);
			rmseError += (0.5 * Math.pow(errorFactor, 2));
		}
		return rmseError;
	}
	
	private void calculateError(Layer currentLayer) {
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
	
	private void calculateWeightDeltaAndUpdateWeights(Layer l) {
		for (Neuron n : l.getNeurons()) {
			for (Synapse s : n.getIncomingSynapses()) {
				Double oldDeltaWeight = s.getDeltaWeight();
				double delta = learnRate * s.getToNeuron().getDelta() * s.getFromNeuron().getOutputValue() + momentum * oldDeltaWeight;
				Double oldWeight = s.getWeight();
				s.setWeight(oldWeight+delta);
				s.setDeltaWeight(delta);
			}
		}
	}
	
//	public static void printStep(Network net, DataPair pair) {
//		forwardStep(net, pair);
//		
//		StringBuilder sb = new StringBuilder();
//		int index = 0;
//		for (Neuron n : net.getOutputLayer().getNeurons()) {
//			sb.append(n.getOutputValue());
//			sb.append( " / ");
//			sb.append(pair.getIdeal()[index]);
//			sb.append("\n");
//			index++;
//		}
//		System.out.println(sb.toString());
//	}
	
}
