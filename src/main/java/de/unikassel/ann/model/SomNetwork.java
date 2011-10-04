package de.unikassel.ann.model;

import java.util.Random;
import java.util.Set;

import de.unikassel.ann.model.func.SigmoidFunction;
import de.unikassel.mdda.MDDA;

public class SomNetwork extends BasicNetwork {

	public long delay = 20;

	private Layer inputLayer;

	private MDDA<Neuron> neuronArrayWrapper;

	private Layer outputLayer;

	private int neuronIdCounter;

	private int inputLayerSize;

	public int getInputSize() {
		return inputLayerSize;
	}

	private double patternRange;

	// private Board3D listener;

	public SomNetwork(final int inputSize, final int... outputDimension) {
		this(2, inputSize, outputDimension);
	}

	/**
	 * Creates a som with a grid neighborhood relation.<br>
	 * Input size can be used for visualisation: 2 inputs -> x,y coordinates,<br>
	 * 3 inputs -> x,y,z coordinates<br>
	 * The outputDimension influence the view of the som:<br>
	 * 1 dimension -> lines, 2 dimensions -> gridded plane<br>
	 * 3 dimension -> gridded cube, 4 dimensions -> gridded hypercube*<br>
	 * Each size of a dimension configure the grid size.<br>
	 * * its not easy to visualise a hypercube with 3 dimensions!
	 * 
	 * @param inputSize
	 * @param outputDimension
	 */
	public SomNetwork(final double patternRange, final int inputSize, final int... outputDimension) {
		super();
		neuronIdCounter = 0;
		inputLayerSize = inputSize;
		inputLayer = new Layer();
		inputLayer.setNet(this);
		addLayer(inputLayer);
		outputLayer = new Layer();
		addLayer(outputLayer);
		this.patternRange = patternRange;
		for (int i = 0; i < inputSize; i++) {
			Neuron neuron = new Neuron(new SigmoidFunction(), false);
			neuron.setId(neuronIdCounter++);
			inputLayer.addNeuron(neuron);
		}

		Random r = new Random();
		// set and init synapses, add to multi array
		neuronArrayWrapper = new MDDA<Neuron>(outputDimension);
		Object[] multiDimArray = neuronArrayWrapper.getArray();
		synapseMatrix = new SynapseMatrix(this, inputSize, multiDimArray.length);
		for (int i = 0; i < multiDimArray.length; i++) {
			Neuron n = new Neuron(new SigmoidFunction(), false);
			n.setId(neuronIdCounter++);
			outputLayer.addNeuron(n);
			if (i != n.getLayerIndex()) {
				throw new IllegalArgumentException("multi dim array index != neuron index");
			}
			multiDimArray[i] = n;
			for (Neuron fromNeuron : inputLayer.getNeurons()) {
				Synapse s = new Synapse(fromNeuron, n);
				s.setWeight(r.nextDouble() * patternRange - patternRange / 2);
				// for som DO NOT use glaobel id, only the index of the layer
				synapseMatrix.addOrUpdateSynapse(s, fromNeuron.getLayerIndex(), n.getLayerIndex());
			}
		}
	}

	/**
	 * Train withe the default weightRange
	 */
	public void train() {
		double half = patternRange / 2;
		train(-half, half);
	}

	public void train(final double min, final double max) {
		trainExp(min, max);
	}

	/**
	 * @param min
	 * @param max
	 */
	private void trainExp(final double min, final double max) {
		double maxIterations = 100000;
		double initalLearningRate = 0.1;
		int neighbor = 6;
		for (int i = 1; i <= maxIterations; i++) {
			double factor = i / maxIterations;
			double learningRate = initalLearningRate * Math.exp(-factor);
			System.out.println(learningRate);
			double[] inputVector = createRandomVector(min, max);
			run(inputVector, learningRate, (int) (neighbor * (1 - factor)));
			try {
				Thread.sleep(delay);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

	public MDDA<Neuron> getMultiArray() {
		return neuronArrayWrapper;
	}

	private void run(final double[] inputVector, final double factor, int neighborRadius) {
		if (neighborRadius < 1) {
			neighborRadius = 1;
		}
		int winnerOneDimIndex = -1;
		double min = 0.0;
		double max = Double.MAX_VALUE;

		Object[] multiDimArray = neuronArrayWrapper.getArray();
		for (int i = 0; i < multiDimArray.length; i++) {
			double sum = 0;
			for (int j = 0; j < inputLayerSize; j++) {
				Synapse synapse = synapseMatrix.getSynapse(j, i);
				if (synapse == null) {
					throw new IllegalArgumentException("synapse is null at [" + j + "]->[" + i + "]");
				}
				sum += Math.pow(inputVector[j] - synapse.getWeight(), 2);
			}
			min = Math.sqrt(sum);
			if (min < max) {
				winnerOneDimIndex = i;
				max = min;
			}
			min = 0.0;

			// für jedes output neuron, ermittele gewinner neuron
			// und speichere index (pseudo multi dim indizes)
		}

		int[] indices = neuronArrayWrapper.getMultiDimIndices(winnerOneDimIndex);

		Set<Integer> neighborIndices = neuronArrayWrapper.getNeighborForAllDims(neighborRadius, indices);
		neighborIndices.add(winnerOneDimIndex);

		for (int neighbor : neighborIndices) {
			for (int j = 0; j < inputLayerSize; j++) {
				Synapse synapse = synapseMatrix.getSynapse(j, neighbor);
				if (synapse == null) {
					throw new IllegalArgumentException("synapse is null at [" + j + "]->[" + neighbor + "]");
				}
				Double oldValue = synapse.getWeight();
				synapse.setWeight(oldValue + factor * (inputVector[j] - oldValue));
			}
		}
	}

	private double[] createRandomVector(final double min, final double max) {
		double[] randomVector = new double[inputLayerSize];
		Random r = new Random();
		double factor = max - min;
		if (true) {
			double q = r.nextDouble() * Math.PI * 2;
			double q2 = r.nextDouble() * Math.PI * 2;
			double s = Math.sqrt(r.nextDouble());
			double x = factor / 2 * s * Math.sin(q) * Math.cos(q2);
			double y = factor / 2 * s * Math.sin(q) * Math.sin(q2);
			double z = factor / 2 * s * Math.cos(q);
			randomVector[0] = x;
			randomVector[1] = y;
			randomVector[2] = z;
		} else if (false) {
			double q = r.nextDouble() * Math.PI * 2;
			double s = Math.sqrt(r.nextDouble());
			double x = factor / 2 * s * Math.cos(q);
			double y = factor / 2 * s * Math.sin(q);
			randomVector[0] = x;
			randomVector[1] = y;
		} else if (false) {
			for (int i = 0; i < inputLayerSize; i++) {
				randomVector[i] = r.nextDouble() * factor + min;
			}
		}

		return randomVector;
	}

	// public void addChangeListener(final Board3D board) {
	// listener = board;
	// }

	/**
	 * 
	 */
	public void reset() {

	}

}
