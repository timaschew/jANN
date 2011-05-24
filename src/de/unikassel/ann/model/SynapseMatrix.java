package de.unikassel.ann.model;

public class SynapseMatrix {
	
	private Synapse[][] matrix;
	private Network network;
	
	public SynapseMatrix(Network network, Integer fromSize, Integer toSize) {
		this.network = network;
		if (fromSize != null && toSize != null) {
			setSize(fromSize, toSize);
		}
	}
	
	public void setSize(Integer fromSize, Integer toSize) {
		matrix = new Synapse[fromSize][toSize];
	}
	
	public void addOrUpdateSynapse(Integer from, Integer to, Synapse s) {
		matrix[from][to] = s;
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
