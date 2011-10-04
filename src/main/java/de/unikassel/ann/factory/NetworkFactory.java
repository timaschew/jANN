package de.unikassel.ann.factory;

import java.util.List;

import de.unikassel.ann.algo.BackPropagation;
import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.model.func.ActivationFunction;
import de.unikassel.ann.model.func.SigmoidFunction;
import de.unikassel.ann.strategy.MaxLearnIterationsStrategy;

public class NetworkFactory {

	/**
	 * Creates a network with SigmoidFunction
	 * 
	 * @see #createSimpleNet()
	 */
	public static NetConfig createSimpleNet(final int input, final int hidden[], final int output, final boolean bias) {
		return createSimpleNet(input, hidden, output, bias, new SigmoidFunction());
	}

	/**
	 * @param inputNeurons
	 * @param inputBias
	 * @param hiddenNeuronList
	 * @param hiddenBiasList
	 * @param outputNeurons
	 * @param activation
	 */
	public NetConfig createNetwork(final Integer inputNeurons, final Boolean inputBias, final List<Integer> hiddenNeuronList,
			final List<Boolean> hiddenBiasList, final Integer outputNeurons, final ActivationFunction activation) {
		NetConfig config = new NetConfig();

		config.getNetwork().addLayer(inputNeurons, inputBias, activation);
		for (int i = 0; i < hiddenNeuronList.size(); i++) {
			config.getNetwork().addLayer(hiddenNeuronList.get(i), hiddenBiasList.get(i), activation);
		}
		config.getNetwork().addLayer(outputNeurons, false, activation);

		return config;
	}

	/**
	 * Creates a network with backpropagation algorithm, stopping after 1000 iterations
	 * 
	 * @param input
	 *            size
	 * @param hidden
	 *            array with neuron size for each element
	 * @param output
	 *            size
	 * @param bias
	 *            flag
	 * @param func
	 *            activation function
	 * @return
	 */
	public static NetConfig createSimpleNet(final int input, final int hidden[], final int output, final boolean bias,
			final ActivationFunction func) {
		NetConfig config = new NetConfig();

		config.getNetwork().addLayer(input, bias, func); // input
		for (int i = 0; i < hidden.length; i++) {
			config.getNetwork().addLayer(hidden[i], bias, func); // hidden
		}
		config.getNetwork().addLayer(output, false, func); // output

		BackPropagation backProp = new BackPropagation();
		config.addTrainingModule(backProp);
		config.addWorkModule(backProp);
		config.addOrUpdateExisting(new MaxLearnIterationsStrategy(1000));

		config.getNetwork().connectFeedForward();
		config.initWeights();

		return config;
	}

	/**
	 * Creates a 3x3x1 network with bias<br>
	 * Same result for:
	 * 
	 * <pre>
	 * createSimpleNet(2, new int[] { 2 }, 1, true, new SigmoidFunction());
	 * </pre>
	 * 
	 * @param iterations
	 * @param bias
	 * @return
	 */
	public static NetConfig createXorNet(final int iterations, final boolean bias) {
		NetConfig config = createSimpleNet(2, new int[] { 2 }, 1, bias, new SigmoidFunction());
		config.addOrUpdateExisting(new MaxLearnIterationsStrategy(iterations));
		return config;
	}

}
