package de.unikassel.ann.model;

import java.util.List;
import java.util.Random;
import java.util.Set;

import de.unikassel.ann.model.func.SigmoidFunction;
import de.unikassel.mdda.MDDAPseudo;
import de.unikassel.threeD.Board3D;

public class SomNetwork extends BasicNetwork {
	
	public static long WAIT = 20; 
		
	private Layer inputLayer;
	
	private MDDAPseudo<Neuron> neuronArrayWrapper;
	
	private Layer outputLayer;

	private int neuronIdCounter;

private int inputLayerSize;

private Board3D listener;
	
	/**
	 * Creates a som with a grid neighborhood relation.<br>
	 * Input size can be used for visualisation: 2 inputs -> x,y coordinates,<br>
	 * 3 inputs -> x,y,z coordinates<br>
	 * The outputDimension influence the view of the som:<br>
	 * 1 dimension -> lines, 2 dimensions -> gridded plane<br>
	 * 3 dimension -> gridded cube, 4 dimensions -> gridded hypercube*<br>
	 * Each size of a dimension configure the grid size.<br>
	 * * its not easy to visualise a hypercube with 3 dimensions!
	 * @param inputSize
	 * @param outputDimension
	 */
	public SomNetwork(int inputSize, int... outputDimension) {
		super();
		neuronIdCounter = 0;
		inputLayerSize = inputSize;
		inputLayer = new Layer();
		addLayer(inputLayer);
		outputLayer = new Layer();
		addLayer(outputLayer);
		
		for (int i=0; i<inputSize; i++) {
			Neuron neuron = new Neuron(new SigmoidFunction(), false);
			neuron.setId(neuronIdCounter++);
			inputLayer.addNeuron(neuron);
		}
		
		
		// set and init synapses, add to multi array
		neuronArrayWrapper = new MDDAPseudo<Neuron>(outputDimension);
		Object[] multiDimArray = (Object[]) neuronArrayWrapper.getArray();
		synapseMatrix = new SynapseMatrix(this, inputSize, multiDimArray.length);
		for (int i=0; i<multiDimArray.length; i++) {
			Neuron n = new Neuron(new SigmoidFunction(), false);
			n.setId(neuronIdCounter++);
			outputLayer.addNeuron(n);
			if (i != n.getIndex()) {
				throw new IllegalArgumentException("multi dim array index != neuron index");
			}
			multiDimArray[i] = n;
			for (Neuron fromNeuron : inputLayer.getNeurons()) {
				Synapse s = new Synapse(fromNeuron, n);
				// for som DO NOT use glaobel id, only the index of the layer
				synapseMatrix.addOrUpdateSynapse(s, fromNeuron.getIndex(), n.getIndex());
			}
		}
	}
	
	public void train() {
		trainStep1();
		System.err.println("finished part 1");
		trainStep2();
		System.err.println("finished part 2");

	}
	
	public MDDAPseudo<Neuron> getMultiArray() {
		return neuronArrayWrapper;
	}
	
	private void trainStep1() {
		double factorDecrementor = 0.0032; // = 0.0032
		int neighborRadius = 6;
		double factor = 0.9D;

		for (int i = 0; i < 250; i++) {
			for (int k = 0; k < 50; k++) {
				double[] inputVector = createRandomVector(-1, 1);
				run(inputVector, factor, neighborRadius);
			}

			factor -= factorDecrementor;
			
			neighborRadius--;

			try {
				Thread.sleep(WAIT);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	private void trainStep2() {
		double factor = 0.1; 
		double factorDecrementor = 0.001; // 0.08

		for (int i = 0; i < 100; i++) {
			for (int k = 0; k < 75; k++) {
				double[] inputVector = createRandomVector(-1, 1);
				run(inputVector, factor, 1);
			}

			factor -= factorDecrementor;

			try {
				Thread.sleep(WAIT);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void run(double[] inputVector, double factor, int neighborRadius) {
		if (neighborRadius > 1) {
//			System.out.println(neighborRadius);
		}
		if (neighborRadius < 1) {
			neighborRadius = 1;
		}

		int winnerOneDimIndex = -1;
		double min = 0.0;
		double max = Double.MAX_VALUE;

		Object[] multiDimArray = (Object[]) neuronArrayWrapper.getArray();
		for (int i=0; i<multiDimArray.length; i++) {
			double sum = 0;
			for (int j=0; j<inputLayerSize; j++) {
				Synapse synapse = synapseMatrix.getSynapse(j, i);
				if (synapse == null) {
					throw new IllegalArgumentException("synapse is null at ["+j+"]->["+i+"]");
				}
				sum += Math.pow(inputVector[j] - synapse.getWeight(), 2);
				
				
			}
			min = Math.sqrt(sum);
			if (min < max) {
				winnerOneDimIndex = i;
				max = min;
			}
			min = 0.0;
			
			// euclidic distance ?!?
				
			// für jedes output neuron, ermittele gewinner neuron
			// und speichere index (pseudo multi dim indizes)
			
		}
		
		int[] indices = neuronArrayWrapper.getMultiDimIndices(winnerOneDimIndex);

		Set<Integer> neighborIndices = neuronArrayWrapper.getNeighborForAllDims(neighborRadius, indices);
		neighborIndices.add(winnerOneDimIndex);
		
		for (int neighbor : neighborIndices) {
			for (int j=0; j<inputLayerSize; j++) {
				Synapse synapse = synapseMatrix.getSynapse(j, neighbor);
				if (synapse == null) {
					throw new IllegalArgumentException("synapse is null at ["+j+"]->["+neighbor+"]");
				}
				Double oldValue = synapse.getWeight();
				synapse.setWeight(oldValue+(factor * (inputVector[j]-oldValue)));
			}
		}
		
		if (listener != null) {
			listener.update();
		}
		
	}

	private double[] createRandomVector(double min, double max) {
		double[] randomVector = new double[inputLayerSize];
		Random r = new Random();
		for (int i=0; i<inputLayerSize; i++) {
			randomVector[i] = r.nextDouble() * (max - min) + min;
		}
		return randomVector;
	}

	public void addChangeListener(Board3D board) {
		listener = board;
	}
	
}
