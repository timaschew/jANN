package de.unikassel.ann.io.beans;

public class TopologyBean implements Comparable<TopologyBean> {
	
	/* CSV */
	/* "id";"layer";"bias";"function" */
	
	private int layer;
	private boolean bias;
	private String function;
	
	private int id;
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the layer
	 */
	public int getLayer() {
		return layer;
	}
	/**
	 * @param layer the layer to set
	 */
	public void setLayer(int layer) {
		this.layer = layer;
	}
	/**
	 * @return the bias
	 */
	public boolean isBias() {
		return bias;
	}
	/**
	 * @param bias the bias to set
	 */
	public void setBias(boolean bias) {
		this.bias = bias;
	}
	/**
	 * @return the function
	 */
	public String getFunction() {
		return function;
	}
	/**
	 * @param function the function to set
	 */
	public void setFunction(String function) {
		this.function = function;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append('"');
		sb.append(id);
		sb.append('"');
		sb.append(';');
		sb.append('"');
		sb.append(layer);
		sb.append('"');
		sb.append(';');
		sb.append('"');
		sb.append(bias);
		sb.append('"');
		sb.append(';');
		sb.append('"');
		sb.append(function);
		sb.append('"');
		sb.append('\n');
		return sb.toString();
	}
	
	@Override
	public int compareTo(TopologyBean o) {
		return getId() - o.getId();
	}


}
