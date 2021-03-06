package de.unikassel.ann.model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.io.beans.SynapseBean;
import de.unikassel.ann.io.beans.TopologyBean;
import de.unikassel.ann.model.func.ActivationFunction;

/**
 * The network is a container for the layers.<br>
 * The first layer is the inputLayer and the last the outputLayer.<br>
 * When creating a network with the factory, you need to call {@link #connectFeedForward()} for linking the neurons (set synapses).<br>
 * 
 */
public class Network extends BasicNetwork {

	/**
	 * Neuron or Synapse
	 */
	public enum PropertyChanges {
		NEURONS, SYNAPSES, INPUT_NEURON
	};

	/**
	 * Layer types
	 */
	public enum NetworkLayer {
		INPUT, OUTPUT, HIDDEN
	};

	private PropertyChangeSupport pcs;
	private List<PropertyChangeListener> listeners;

	private NetConfig config;
	/**
	 * List of all neurons of the network
	 */
	private List<Neuron> flatNet;
	/**
	 * All synapses of the network as a set
	 */
	private Set<Synapse> synapseSet;
	/**
	 * Matrix of synapses for importing/exporting from/to file
	 */
	private SynapseMatrix synapseMatrix;

	public Network() {
		super();
		pcs = new PropertyChangeSupport(this);
		listeners = new ArrayList<PropertyChangeListener>();
		synapseSet = new HashSet<Synapse>();
		flatNet = new ArrayList<Neuron>();
		synapseMatrix = new SynapseMatrix(this, null, null);
	}

	public PropertyChangeSupport getPCS() {
		return pcs;
	}

	/**
	 * Wrapper to ease the access to the network stored in the current session.<br>
	 * NOTE: Be sure to use and call this method only if there is a network and session initiated!
	 * 
	 * @return
	 */
	public static Network getNetwork() {
		return Settings.getInstance().getCurrentSession().getNetworkConfig().getNetwork();
	}

	/**
	 * For all property {@link PropertyChanges}
	 */
	public void addPropertyChangeListener(final PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(listener);
		listeners.add(listener);
	}

	/**
	 * For event propertyname
	 * 
	 * @param propertyName
	 *            {@link PropertyChanges}
	 * @param listener
	 */
	public void addPropertyChangeListener(final String propertyName, final PropertyChangeListener listener) {
		pcs.addPropertyChangeListener(propertyName, listener);
		listeners.add(listener);
	}

	/**
	 * Adds a layer to the end of the layers<br>
	 * First time it becomes the input layer<br>
	 * 2nd time it becomes the output layer<br>
	 * 3rd time and more the new layer becomes output, and the previous becomes hidden layer
	 * 
	 * @param neuronCount
	 * @param bias
	 * @param function
	 */
	public void addLayer(final int neuronCount, final boolean bias, final ActivationFunction function) {
		Layer l = new Layer();
		if (bias) {
			l.addNeuron(new Neuron(function, true));
		}
		for (int i = 0; i < neuronCount; i++) {
			Neuron n = new Neuron(function, false);
			l.addNeuron(n);
		}
		addLayer(l);
	}

	/**
	 * @param neuron
	 */
	public void removeNeuron(final Neuron neuron) {
		int layerIndex = neuron.getLayer().getIndex();
		Layer layer;
		try {
			layer = layers.get(layerIndex);
			// dont remove last neuron of a layer
			if (layer.getNeurons().size() == 1) {
				// dont remove last input / output neuron if hidden layers exist
				if (layer.equals(getInputLayer()) && getSizeOfHiddenLayers() > 0) {
					// return;
				} else if (layer.equals(getOutputLayer()) && getSizeOfHiddenLayers() > 0) {
					// return;
				} else if (layerIndex > 0 && layerIndex < layers.size() - 1) {
					// dont remove last neuron of hidden layer
					// return;
				}
			}

		} catch (IndexOutOfBoundsException ex) {
			System.out.println("ERROR: " + ex.getMessage() + ", neuron: " + neuron + ", layerIndex = " + layerIndex);
			return;
		}

		List<Neuron> neurons = layer.getNeurons();
		int size = neurons.size();
		for (int i = 0; i < size; i++) {
			if (neurons.get(i).getId() == neuron.getId()) {
				layer.getNeurons().remove(i);
				pcs.firePropertyChange(PropertyChanges.NEURONS.name(), size, size - 1);
				if (layer.equals(getInputLayer())) {
					pcs.firePropertyChange(PropertyChanges.INPUT_NEURON.name(), size, size - 1);
				}
				// Finish here cause a neuron is unique
				break;
			}
		}

		// Remove empty layers when possible (e.g. do not remove the input or output layer if there is at least one hidden layer)
		if (layer.getNeurons().size() == 0) {
			// Layer is empty!
			System.out.println(layer.getIndex() + " is empty");

			if (GraphController.getInstance().isLayerHidden(layer)) {
				// It's a hidden layer!
				System.out.println(layer.getIndex() + " is a hidden layer");

				if (layer.getIndex() == layers.size() - 2) {
					// It's the last hidden layer!
					System.out.println(layer.getIndex() + " is the last hidden layer");

					int oldValue = layers.size() - 2;
					layers.remove(layer);

					// shift the index attribute of the output layer
					Layer outputLayer = layers.get(layers.size() - 1);
					outputLayer.setIndex(layers.size() - 1);

					pcs.firePropertyChange(PropertyChanges.NEURONS.name(), oldValue, layers.size() - 2);
				}
			} else {
				// It's an input or output layer!
				if (layers.size() <= 2) {
					// There is no hidden layer!
					System.out.println("there is no hidden layer anymore");
					if (layer.getIndex() == 0) {
						// Input layer
						System.out.println(layer.getIndex() + " is the input layer");
					} else if (layer.getIndex() == 1) {
						// // Output layer
						System.out.println(layer.getIndex() + " is the output layer");
					} else {
						// WTF?
						System.out.println(layer.getIndex() + " WTF what kind of layer are you???");
					}
				}
			}
		}
	}

	public void addInputNeuron() {
		int layerSize = 0;
		if (getInputLayer() != null) {
			// input layer exists
			layerSize = getInputLayer().getNeurons().size();
		}
		setInputLayerSize(layerSize + 1);
	}

	// public void removeInputNeuron() {
	// int layerSize = 0;
	// if (getInputLayer() != null) {
	// // input layer exists
	// layerSize = getInputLayer().getNeurons().size();
	// }
	// setInputLayerSize(layerSize - 1);
	// }

	public void addOutputNeuron() {
		int layerSize = 0;
		if (getOutputLayer() != null) {
			// output layer exists
			layerSize = getOutputLayer().getNeurons().size();
		}
		setOuputLayerSize(layerSize + 1);
	}

	// public void removeOutputNeuron() {
	// int layerSize = 0;
	// if (getOutputLayer() != null) {
	// // output layer exists
	// layerSize = getOutputLayer().getNeurons().size();
	// }
	// setOuputLayerSize(layerSize - 1);
	// }

	public void addHiddenNeuron(final int layerIndex) {
		// add only, if the layer already exist
		int layerSize = 0;
		if (layers.size() - 1 >= layerIndex) {
			layerSize = layers.get(layerIndex).getNeurons().size();
			setHiddenLayerSize(layerIndex, layerSize + 1);
		}
	}

	public void removeHiddenNeuron(final int layerIndex) {
		// add only, if the layer already exist
		int layerSize = 0;
		if (layers.size() - 1 >= layerIndex) {
			layerSize = layers.get(layerIndex).getNeurons().size();
			setHiddenLayerSize(layerIndex, layerSize - 1);
		}
	}

	public void setInputSizeIgnoringBias(final int inputSize) {
		boolean hasBias = false;
		if (getInputLayer() != null) {
			hasBias = getInputLayer().hasBias();
		}
		setInputLayerSize(inputSize + (hasBias ? 1 : 0));
	}

	public void setInputLayerSize(final int inputSize) {
		if (inputSize < 0) {
			return;
		}
		if (inputSize == 0 && getSizeOfHiddenLayers() > 0) {
			return;
		}

		ActivationFunction function = getStandardFunction();
		if (layers.isEmpty()) {
			// add input layer
			Layer inputLayer = new Layer();
			inputLayer.setIndex(0);
			inputLayer.setNet(this);
			layers.add(inputLayer);
		}
		Layer inputLayer = layers.get(0);
		int oldValue = inputLayer.getNeurons().size();
		setLayerSize(inputSize, function, inputLayer);
		pcs.firePropertyChange(PropertyChanges.NEURONS.name(), oldValue, inputSize);
		pcs.firePropertyChange(PropertyChanges.INPUT_NEURON.name(), oldValue, inputSize);
	}

	/**
	 * Creates input with 1 neuron, if not already exist
	 * 
	 * @param outputSize
	 */
	public void setOuputLayerSize(final int outputSize) {
		if (outputSize < 0) {
			return;
		}
		if (outputSize == 0 && getSizeOfHiddenLayers() > 0) {
			return;
		}
		ActivationFunction function = getStandardFunction();
		if (layers.isEmpty()) {
			// add input layer
			setInputLayerSize(1);
		}
		if (layers.size() == 1) {
			// add output layer
			Layer outputLayer = new Layer();
			outputLayer.setIndex(1);
			outputLayer.setNet(this);
			layers.add(outputLayer);
		}
		Layer outputLayer = layers.get(layers.size() - 1);
		int oldValue = outputLayer.getNeurons().size();
		setLayerSize(outputSize, function, outputLayer);
		pcs.firePropertyChange(PropertyChanges.NEURONS.name(), oldValue, outputSize);
	}

	public int getSizeOfHiddenLayers() {
		if (layers.size() > 2) {
			return layers.size() - 2;
		}
		return 0;
	}

	/**
	 * Creates input and output with 1 neuron, if not already exist
	 * 
	 * @param hiddenLayerCount
	 */
	public void setSizeOfHiddenLayers(final int hiddenLayerCount) {
		if (hiddenLayerCount < 0) {
			return;
		}
		if (layers.isEmpty()) {
			// add input layer
			setInputLayerSize(1);
		}
		if (layers.size() == 1) {
			// add output layer
			setOuputLayerSize(1);
		}
		int oldValue = layers.size() - 2;
		int diff = hiddenLayerCount - oldValue;
		if (diff == 0) {
			return;
		} else if (diff > 0) {
			// add new layers
			for (int i = 0; i < diff; i++) {
				int index = layers.size() - 1; // old output index = new hidden index
				Layer hiddenLayer = new Layer();
				hiddenLayer.setNet(this);
				hiddenLayer.setIndex(index);
				layers.add(index, hiddenLayer); // shift the output layer in the list
				setHiddenLayerSize(index, 1); // initial size

				// Remove the edges between the previous last hidden layer and the output layer
				List<Neuron> outputNeurons = getOutputLayer().getNeurons();
				for (Neuron n : outputNeurons) {
					List<Synapse> incomingSynapses = n.getIncomingSynapses();
					for (Synapse synapse : incomingSynapses) {
						Integer fromId = synapse.getFromNeuron().getId();
						Integer toId = synapse.getToNeuron().getId();
					}
				}
			}
		} else {
			// remove layers
			for (int i = 0; i < -diff; i++) {
				layers.remove(layers.size() - 2); // -1 = output, -2 last hidden
			}
		}
		// shift the index attribute of the output layer
		Layer outputLayer = layers.get(layers.size() - 1);
		outputLayer.setIndex(layers.size() - 1);

		pcs.firePropertyChange(PropertyChanges.NEURONS.name(), oldValue, hiddenLayerCount);
	}

	public void setHiddenLayerSizeIgnoreingBias(final int layerIndex, final int layerSize) {
		boolean hasBias = false;
		if (getLayer(layerIndex) != null) {
			hasBias = getLayer(layerIndex).hasBias();
		}
		setHiddenLayerSize(layerIndex, layerSize + (hasBias ? 1 : 0));
	}

	public void setHiddenLayerSize(final int layerIndex, final int layerSize) {
		if (layerSize < 1) {
			return;
		}

		// For the case that we have no input and/or output neuron
		System.out.println("setHiddenLayerSize(" + layerIndex + ", " + layerSize + ")");

		ActivationFunction function = getStandardFunction();
		// add only, if the layer already exist
		// -1 because the the 2nd operand is index, not size
		Integer oldValue = null;
		if (layers.size() - 1 > layerIndex) {
			// for example layerIndex
			oldValue = layers.get(layerIndex).getNeurons().size();
			setLayerSize(layerSize, function, layers.get(layerIndex)); // initial size
			pcs.firePropertyChange(PropertyChanges.NEURONS.name(), oldValue, new Integer(layerSize));
		}
	}

	public void addSynapse(final Synapse synapse) {
		synapseSet.add(synapse);
	}

	public void removeSynapse(final Synapse synapse) {
		synapseSet.remove(synapse);
	}

	/**
	 * Change the value of a neuron.
	 * 
	 * @param neuron
	 * @param value
	 */
	public void changeNeuronValue(final Neuron neuron, final Double value) {
		Double oldValue = neuron.getValue();
		neuron.setValue(value);
		pcs.firePropertyChange(PropertyChanges.NEURONS.name(), oldValue, value);
	}

	/**
	 * Change the weight of a synapse.
	 * 
	 * @param synapse
	 * @param weight
	 */
	public void changeSynapseWeight(final Synapse synapse, final Double weight) {
		Double oldWeight = synapse.getWeight();
		synapse.setWeight(weight);
		pcs.firePropertyChange(PropertyChanges.SYNAPSES.name(), oldWeight, weight);
	}

	/**
	 * Change the activation function of a neuron.
	 * 
	 * @param neuron
	 * @param funcName
	 */
	public void changeNeuronActivationFunction(final Neuron neuron, final String funcName) {
		ActivationFunction oldValue = neuron.getActivationFunction();
		neuron.setActivationFunction(funcName);
		pcs.firePropertyChange(PropertyChanges.NEURONS.name(), oldValue, neuron.getActivationFunction());
	}

	private void setLayerSize(final int layerSize, final ActivationFunction function, final Layer layer) {
		if (layerSize < 0) {
			return;
		}
		int currentLayerSize = layer.getNeurons().size();
		// positive -> add
		// negative -> remove
		int diff = layerSize - currentLayerSize;
		if (diff == 0) {
			return;
		} else if (diff > 0) {
			// add neurons
			for (int i = 0; i < diff; i++) {
				Neuron neuron = new Neuron(function, false);
				neuron.setId(getNextNeuronId());
				layer.addNeuron(neuron);
			}
		} else {
			// delete neurons
			List<Neuron> inputNeurons = layer.getNeurons();
			for (int i = 0; i < -diff; i++) {
				inputNeurons.remove(inputNeurons.size() - 1);
			}
		}
	}

	/**
	 * Get the actual selected standard activation function from the sidebar.
	 * 
	 * @return
	 */
	public ActivationFunction getStandardFunction() {
		return Main.instance.sidebar.standardOptionsPanel.getStandardActivationFunction();
	}

	/**
	 * Creates the network with the given topology structure.<br>
	 * If the synapseBeanList is not null, the neurons will be connected, otherwise no connections / synapses will be created.<br>
	 * Sets the {@link #flatNet}, if given synapsesBanList is not null also the {@link #synapseSet} and the {@link #synapseMatrix}
	 * 
	 * @param topoBeanList
	 * @param synapsesBanList
	 */
	public void createTopology(final List<TopologyBean> topoBeanList, final List<SynapseBean> synapsesBanList) {

		int maxHiddenIndex = 0;
		for (TopologyBean b : topoBeanList) {
			maxHiddenIndex = Math.max(maxHiddenIndex, b.getLayer());
		}
		Layer[] hiddenLayer = new Layer[maxHiddenIndex + 1];
		// add layers to net
		Layer inputLayer = new Layer();
		addLayer(inputLayer);
		for (int i = 0; i < maxHiddenIndex + 1; i++) {
			hiddenLayer[i] = new Layer();
			addLayer(hiddenLayer[i]);
		}
		Layer outputLayer = new Layer();
		addLayer(outputLayer);

		// creating neurons and adding it to flatNet and layers
		for (TopologyBean b : topoBeanList) {
			Neuron n = new Neuron(b.getFunction(), b.getBias());
			n.setId(b.getId());
			flatNet.add(n);
			if (b.getLayer() == -1) {
				inputLayer.addNeuron(n);
			} else if (b.getLayer() == -2) {
				outputLayer.addNeuron(n);
			} else {
				hiddenLayer[b.getLayer()].addNeuron(n);
			}
		}

		synapseMatrix.setSize(flatNet.size(), flatNet.size());

		if (synapsesBanList != null) {
			setFlatSynapses(synapsesBanList);
		}
		// else: do not connect anything
	}

	/**
	 * Call this only if the flat net is empty (when exporting the network) Creates the synapses between all neurons. From layer x-1 to
	 * layer x, from x to x+1, ...<br>
	 * Sets the {@link #flatNet}
	 */
	public void initFlatNet() {
		if (flatNet.size() > 0) {
			flatNet = new ArrayList<Neuron>();
		}
		for (Layer l : layers) {
			// set flat net
			for (Neuron n : l.getNeurons()) {
				if (n.getId() == -1) {
					n.setId(flatNet.size());
				}
				flatNet.add(n);
			}
		}
		synapseMatrix.setSize(flatNet.size(), flatNet.size());
	}

	/**
	 * Creates the synapses between all neurons. From layer x-1 to layer x, from x to x+1, ...<br>
	 * Sets the {@link #flatNet}, the {@link #synapseSet} and the {@link #synapseMatrix}
	 */
	public void connectFeedForward() {

		initFlatNet();

		Layer previousLayer = null;
		// set synapses (strict forward feedback)
		for (Layer l : layers) {
			if (previousLayer != null) {
				for (Neuron fromNeuron : previousLayer.getNeurons()) {
					for (Neuron toNeuron : l.getNeurons()) {
						if (toNeuron.isBias() == false) {
							Synapse s = new Synapse(fromNeuron, toNeuron);
							synapseSet.add(s);
							// in this type of network use global ids for synapse matrix
							synapseMatrix.addOrUpdateSynapse(s, fromNeuron.getId(), toNeuron.getId());
						}
					}
				}
			}
			previousLayer = l;
		}
		pcs.firePropertyChange(PropertyChanges.SYNAPSES.name(), null, "feedForward");
	}

	/**
	 * NOTE: {@link #flatNet} have to be already created! Sets the the {@link #synapseSet} and the {@link #synapseMatrix}
	 * 
	 * @param synapsesBanList
	 */
	public void setFlatSynapses(final List<SynapseBean> synapsesBanList) {
		// connect neurons / create synapses
		for (SynapseBean b : synapsesBanList) {
			synapseRangeCheck(b);
			Neuron fromNeuron = flatNet.get(b.getFrom());
			Neuron toNeuron = flatNet.get(b.getTo());
			Synapse s = new Synapse(fromNeuron, toNeuron);
			if (b.getRandom() == false) {
				s.setWeight(b.getValue());
			}
			neuronRangeCheck(fromNeuron, toNeuron);
			synapseSet.add(s);
			// in this type of network use global ids for synapse matrix
			synapseMatrix.addOrUpdateSynapse(s, fromNeuron.getId(), toNeuron.getId());
		}
		pcs.firePropertyChange(PropertyChanges.SYNAPSES.name(), null, "setSynapses");

	}

	private void neuronRangeCheck(final Neuron fromNeuron, final Neuron toNeuron) {
		if (fromNeuron.getId() < 0) {
			throw new IllegalArgumentException("invalid neuron id, negative value not permitted\n" + fromNeuron);
		} else if (toNeuron.getId() >= synapseMatrix.getSynapses().length) {
			throw new IllegalArgumentException("invalid neuron id, don't fit into flat synapse array \n" + toNeuron);
		}

	}

	private void synapseRangeCheck(final SynapseBean b) {
		if (b.getFrom() < flatNet.get(0).getId()) {
			throw new IllegalArgumentException("synapse connections does not match with neuron ids, 1st id is too small\n" + b);
		} else if (b.getTo() > flatNet.get(flatNet.size() - 1).getId()) {
			throw new IllegalArgumentException("synapse connections does not match with neuron ids, 2nd id is too high\n" + b);
		}

	}

	/**
	 * Sets values for inputLayer.<br>
	 * Lenght of array must match to input layer neuron count!
	 * 
	 * @param input
	 *            for whole input layer
	 */
	public void setInputLayerValues(final Double[] input) {
		Layer layer = getInputLayer();
		List<Neuron> neuronList = layer.getNeurons();
		int biasOffset = layer.hasBias() ? 1 : 0;
		if (input.length != neuronList.size() - biasOffset) {
			throw new IllegalArgumentException("input layer count != input.lenght");
		}
		for (int i = biasOffset; i < neuronList.size(); i++) {
			neuronList.get(i).setValue(input[i - biasOffset]);
		}
	}

	public void setOutputToPair(final Double[] output) {
		Layer layer = getOutputLayer();
		List<Neuron> neuronList = layer.getNeurons();
		if (output.length != neuronList.size()) {
			throw new IllegalArgumentException("output layer count != ouput.length");
		}
		for (int i = 0; i < neuronList.size(); i++) {
			output[i] = neuronList.get(i).getValue();
		}
	}

	/**
	 * @return the network in reverse order for backpropagation
	 */
	public List<Layer> reverse() {
		ArrayList<Layer> reversedLayers = new ArrayList<Layer>(layers);
		Collections.reverse(reversedLayers);
		return reversedLayers;
	}

	public void setConfig(final NetConfig config) {
		this.config = config;
	}

	public NetConfig getConfig() {
		return config;
	}

	/**
	 * {@link #flatNet}
	 * 
	 * @return
	 */
	public List<Neuron> getFlatNet() {
		return flatNet;
	}

	/**
	 * {@link #synapseMatrix}
	 */
	@Override
	public SynapseMatrix getSynapseMatrix() {
		return synapseMatrix;
	}

	/**
	 * {@link #synapseSet}
	 * 
	 * @return
	 */
	public Set<Synapse> getSynapseSet() {
		return synapseSet;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append(", ");
		sb.append(layers.size());
		sb.append(" layers");
		return sb.toString();
	}

	/**
	 * 
	 */
	public void removeAllPropertyChangeListeners() {
		for (PropertyChangeListener l : listeners) {
			pcs.removePropertyChangeListener(l);
		}
		listeners.clear();
	}

	/**
	 * Returns true if at least one input and ouput layer exist
	 * 
	 * @return
	 */
	public boolean isTrainable() {
		return layers.size() >= 2;
	}

}
