package de.unikassel.ann.factory;

import de.unikassel.ann.algo.BackPropagation;
import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.func.ActivationFunction;
import de.unikassel.ann.model.func.SigmoidFunction;
import de.unikassel.ann.strategy.MaxLearnIterationsStrategy;
import de.unikassel.ann.strategy.MinErrorStrategy;

public class NetworkFactory {
	
	/**
	 * Creates a network with SigmoidFunction
	 * @see #createSimpleNet()
	 */
	public static NetConfig createSimpleNet(int input, int hidden[], int output, boolean bias) {
		return createSimpleNet(input, hidden, output, bias, new SigmoidFunction());
	}
	
	/**
	 * Creates a network with backpropagation algorithm, stopping after 1000 iterations
	 * @param input size
	 * @param hidden array with neuron size for each element
	 * @param output size
	 * @param bias flag
	 * @param func activation function
	 * @return
	 */
	public static NetConfig createSimpleNet(int input, int hidden[], int output, boolean bias, ActivationFunction func) {
		NetConfig config = new NetConfig();
		
		config.addLayer(input, bias, func); // input 
		for (int i=0; i<hidden.length; i++) {
			config.addLayer(hidden[i], bias, func); // hidden
		}
		config.addLayer(output, false, func); // output
		
		BackPropagation backProp = new BackPropagation();
		config.addTrainingModule(backProp);
		config.addWorkModule(backProp);
		config.addOrUpdateExisting(new MaxLearnIterationsStrategy(1000));
		
		config.buildNetwork();
		return config;
	}
	
	/**
	 * Creates a 3x3x1 network with bias<br>
	 * Same result for:
	 * <pre>createSimpleNet(2, new int[]{2}, 1, true, new SigmoidFunction());</pre>
	 * @param iterations
	 * @param bias
	 * @return
	 */
	public static NetConfig createXorNet(int iterations, boolean bias) {
		NetConfig config = createSimpleNet(2, new int[]{2}, 1, bias, new SigmoidFunction());
		config.addOrUpdateExisting(new MaxLearnIterationsStrategy(iterations));
		return config;
	}

	public static NetConfig createSOM(int input, int outputN, int outputM) {
		
		NetConfig config = new NetConfig();
		config.addLayer(input, false, new SigmoidFunction());
		config.addLayer(outputN*outputM, false, new SigmoidFunction());
		SomAlgorithm somAlgo = new SomAlgorithm();
		config.addWorkModule(somAlgo);
		
		config.buildNetwork();
		
		return config;
	}
	

}
