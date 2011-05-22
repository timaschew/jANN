package de.unikassel.ann.io.beans;

public class SynapseBean {
	
	/* CSV */
	/* "from";"to";"value";"random" */
	
	private int to;
	private double value;
	private boolean random;
	
	private int from;
	/**
	 * @return the from
	 */
	public int getFrom() {
		return from;
	}
	/**
	 * @param from the from to set
	 */
	public void setFrom(int from) {
		this.from = from;
	}
	/**
	 * @return the to
	 */
	public int getTo() {
		return to;
	}
	/**
	 * @param to the to to set
	 */
	public void setTo(int to) {
		this.to = to;
	}
	/**
	 * @return the value
	 */
	public double getValue() {
		return value;
	}
	/**
	 * @param value the value to set
	 */
	public void setValue(double value) {
		this.value = value;
	}
	/**
	 * @return the random
	 */
	public boolean isRandom() {
		return random;
	}
	/**
	 * @param random the random to set
	 */
	public void setRandom(boolean random) {
		this.random = random;
	}

}
