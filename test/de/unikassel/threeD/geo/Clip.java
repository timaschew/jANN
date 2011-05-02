package de.unikassel.threeD.geo;

public class Clip {
	
	private double x;
	private double y;
	private double width;
	private double height;
	
	public Clip(double x, double y, double width, double height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}
	
	public void update(Clip c) {
		x = Math.min(x, c.getX());
		y = Math.min(y, c.getY());
		width = Math.max(this.width, c.getWidth());
		height = Math.max(this.height, c.getHeight());
	}
	
	public void update(double x, double y, double width, double height) {
		x = Math.min(this.x, x);
		y = Math.min(this.y, y);
		width = Math.max(this.width, width);
		height = Math.max(this.height, height);
	}
	
	/**
	 * @return the x
	 */
	public double getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(double x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public double getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(double y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public double getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(double height) {
		this.height = height;
	}

}
