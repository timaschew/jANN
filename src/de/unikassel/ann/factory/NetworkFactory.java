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
	
	public static NetConfig createSimpleNet(int input, int hidden[], int output, boolean bias) {
		return createSimpleNet(input, hidden, output, bias, new SigmoidFunction());
	}
	
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
	
	public static NetConfig createXorNet(int iterations) {
		
		NetConfig config = createSimpleNet(2, new int[]{2}, 1, true, new SigmoidFunction());
		config.addOrUpdateExisting(new MaxLearnIterationsStrategy(iterations));
		return config;
	}
	

}
