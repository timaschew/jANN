package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Logger;

import de.unikassel.ann.gui.Main;
import de.unikassel.ann.model.func.ActivationFunction;

public class Layer {

	Logger log = Logger.getAnonymousLogger();

	private int index = -1;

	private List<Neuron> neurons;

	private BasicNetwork network;

	// private boolean haveBias();

	public Layer() {
		neurons = new ArrayList<Neuron>();
	}

	public void addNeuron(final Neuron n) {
		if (hasBias() && n.isBias()) {
			throw new IllegalArgumentException("second bias neuron not allowed");
		}
		// set local index for layer scope
		n.setLayerIndex(neurons.size());

		if (n.isBias()) {
			// add bias neuron at first position an shift all others
			neurons.add(0, n);
		} else {
			// add to the end
			neurons.add(n);
		}
		if ((n.getLayer() != null && n.getLayer().equals(this)) == false) {
			n.setLayer(this);
		}
	}

	public List<Neuron> getNeurons() {
		return neurons;
	}

	public Neuron getNeuron(final Integer i) {
		try {
			return neurons.get(i);
		} catch (IndexOutOfBoundsException e) {
			log.fine("no neuron at [" + getIndex() + "][" + i + "]");
			return null;
		}
	}

	public void setIndex(final int size) {
		index = size;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("[");
		sb.append(index);
		sb.append("]");
		sb.append(hasBias() ? " (B)" : "");
		sb.append(" ");
		sb.append(neurons.size());
		sb.append(" neurons\n");

		return sb.toString();
	}

	public int getIndex() {
		return index;
	}

	public Layer getNextLayer() {
		return network.getLayers().get(index + 1);
	}

	public Layer getPrevLayer() {
		if (index > 0) {
			return network.getLayers().get(index - 1);
		}
		return null;
	}

	public void setNet(final BasicNetwork net) {
		network = net;
	}

	public BasicNetwork getNet() {
		return network;
	}

	public boolean hasBias() {
		for (Neuron n : neurons) {
			if (n.isBias()) {
				return true;
			}
		}
		return false;
	}

	public void setBias(final boolean bias) {
		if (hasBias() == bias) {
			// already contains a bias or not
			return;
		}
		boolean oldValue = hasBias();
		if (bias) {
			Neuron n = new Neuron(getStandardFunction(), true);
			n.setId(network.getNextNeuronId());
			addNeuron(n);
		} else {
			for (Neuron n : new CopyOnWriteArrayList<Neuron>(neurons)) {
				if (n.isBias()) {
					neurons.remove(n);
				}
			}
		}
		if (network instanceof Network) {
			((Network) network).getPCS().firePropertyChange(Network.PropertyChanges.NEURONS.name(), oldValue, bias);
		}
	}

	/**
	 * @return
	 */
	private ActivationFunction getStandardFunction() {
		return Main.instance.sidebar.standardOptionsPanel.getStandardActivationFunction();
	}

}
