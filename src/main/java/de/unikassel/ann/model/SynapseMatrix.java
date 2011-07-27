package de.unikassel.ann.model;

/**
 * Containts all synapses of a network<br>
 * Matrix is has a NxN size<br>
 * First index = fromNeuron, second index = toNeuron<br>
 * Its possible that each neuron have connection to every neuron<br>
 * Access for synapses or weights can used with index. 
 * null will be returned if synapse or weight does not exist.
 *
 */
public class SynapseMatrix {
	
	private Synapse[][] matrix;
	private BasicNetwork network;
	
	public SynapseMatrix(BasicNetwork network, Integer fromSize, Integer toSize) {
		this.network = network;
		if (fromSize != null && toSize != null) {
			setSize(fromSize, toSize);
		}
	}
	
	public void setSize(Integer fromSize, Integer toSize) {
		matrix = new Synapse[fromSize][toSize];
	}
	
	/**
	 * After adding synapse, you have to finalize the whole net<br>
	 * @param from
	 * @param to
	 * @param s
	 * @see Network#finalizeStructure()
	 * @see Network#finalizeFromFlatNet(java.util.List, java.util.List)
	 */
	public boolean addOrUpdateSynapse(Synapse s, Integer from, Integer to) {
		if (from == null || to == null) {
			throw new IllegalAccessError("neurons using synapse matrix, but have no id!");
		}
		boolean updated = false;
		if (matrix[from][to] != null) {
			updated = true;
		}
		matrix[from][to] = s;
		return updated;
	}
	
	public Synapse getSynapse(Integer from, Integer to) {
		try {
			return matrix[from][to];
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public boolean isNotEmpty() {
		return matrix != null && matrix.length > 0;
	}
	
	public Double getWeight(Integer from, Integer to) {
		try {
			return matrix[from][to].getWeight();
		} catch (ArrayIndexOutOfBoundsException e) {
			return null;
		} catch (NullPointerException e) {
			return null;
		}
	}
	
	public boolean setWeight(Integer from, Integer to, Double value) {
		try {
			matrix[from][to].setWeight(value);
			return true;
		} catch (ArrayIndexOutOfBoundsException e) {
			return false;
		} catch (NullPointerException e) {
			return false;
		}
	}
	
	public Synapse[][] getSynapses() {
		return matrix;
	}
	
	/**
	 * Updates the weights of the existing synapses<br>
	 * You can not pass weights for not existing synapses, 
	 * it will throw an IllegalArgumentException!
	 * @param x
	 */
	public void setWeightMatrix(Double[][] x) {
		if (matrix.length == x.length &&
				matrix[0].length == x[0].length) {
			for (int i=0; i<matrix.length; i++) {
				for (int j=0; j<matrix[0].length; j++) {
					Synapse s = getSynapse(i, j);
					Double weightToSet = x[i][j];
					if (s != null && weightToSet != null) {
						setWeight(i, j, weightToSet);
					} else if (s == null && weightToSet == null) {
						// nothing
					} else {
						throw new IllegalArgumentException("could not set matrix element at: ["+i+"]["+j+"]");
					}
				}
			}
		}
	}
	
	public Double[][][][] getBigWeightMatrix() {
		int layerSize = network.getLayers().size();
		int biggestSize = network.getBiggestLayer();
		
		Double[][][][] bigMatrix = new Double[layerSize][biggestSize][layerSize][biggestSize];
		for (int from=0; from<matrix.length; from++) {
			for (int to=0; to<matrix.length; to++) {
				Synapse s = getSynapse(from, to);
				if (s != null) {
					int fromLayer = s.getFromNeuron().getLayer().getIndex();
					int fromNeuron = s.getFromNeuron().getIndex();
					int toLayer = s.getToNeuron().getLayer().getIndex();
					int toNeuron = s.getToNeuron().getIndex();
					bigMatrix[fromLayer][fromNeuron][toLayer][toNeuron] = s.getWeight();
				}
			}
		}
		return bigMatrix;
	}
	
	/**
	 * Updates the weights of the existing synapses<br>
	 * You can not pass weights for not existing synapses, 
	 * it will throw an IllegalArgumentException!
	 * @param x
	 */
	public void setBigWeightMatrix(Double[][][][] m) {
		for (int fromLayer=0; fromLayer<m.length; fromLayer++) {
			if (fromLayer == network.getOutputLayer().getIndex()) {
				continue;
			}
			for (int fromNeuron=0; fromNeuron<m[fromLayer].length; fromNeuron++) {
				for (int toLayer=0; toLayer<m[fromLayer][fromNeuron].length; toLayer++) {
					for (int toNeuron=0; toNeuron<m[fromLayer][fromNeuron][toLayer].length; toNeuron++) {
						Neuron from = network.getLayer(fromLayer).getNeuron(fromNeuron);
						Neuron to = network.getLayer(toLayer).getNeuron(toNeuron);
						if (from != null && to != null) {
							Double weightToSet = m[fromLayer][fromNeuron][toLayer][toNeuron];
							Synapse s = getSynapse(from.getId(), to.getId());
							// old matrix and new matrix can both be null or both not null
							// otherwise throw exception
							if (s != null && weightToSet != null) {
								s.setWeight(weightToSet);
							} else if (s == null && weightToSet == null) {
								// nothing
							} else {
								throw new IllegalArgumentException("could not set matrix element at: ["+fromLayer+"]["+fromNeuron+"]["+toLayer+"]["+toNeuron+"]");
							}
						}
					}
				}
			}
		}
	}
}
