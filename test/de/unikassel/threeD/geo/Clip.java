package de.unikassel.threeD.geo;

public class Clip {
	
	private int x;
	private int y;
	private int width;
	private int height;
	
	public Clip(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width - x;
		this.height = height - y;
	}
	
	public void update(Clip c) {
		x = Math.min(x, c.getX());
		y = Math.min(y, c.getY());
		width = Math.max(this.width, c.getWidth());
		height = Math.max(this.height, c.getHeight());
	}
	
	/**
	 * @return the x
	 */
	public int getX() {
		return x;
	}

	/**
	 * @param x the x to set
	 */
	public void setX(int x) {
		this.x = x;
	}

	/**
	 * @return the y
	 */
	public int getY() {
		return y;
	}

	/**
	 * @param y the y to set
	 */
	public void setY(int y) {
		this.y = y;
	}

	/**
	 * @return the width
	 */
	public int getWidth() {
		return width;
	}

	/**
	 * @param width the width to set
	 */
	public void setWidth(int width) {
		this.width = width;
	}

	/**
	 * @return the height
	 */
	public int getHeight() {
		return height;
	}

	/**
	 * @param height the height to set
	 */
	public void setHeight(int height) {
		this.height = height;
	}

}
