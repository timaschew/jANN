/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Way
 */
package de.unikassel.ann.gui.model;

import java.util.ArrayList;

/**
 * @author Way
 * 
 */
public class JungLayer implements Comparable<JungLayer> {
	private int index = -1;

	// List contains all vertices of the layer
	private ArrayList<Vertex> vertices;

	public JungLayer(final int index) {
		this.index = index;
		vertices = new ArrayList<Vertex>();
	}

	public void setIndex(final int index) {
		this.index = index;
	}

	public int getIndex() {
		return index;
	}

	public ArrayList<Vertex> getVertices() {
		return vertices;
	}

	public void addVertex(final Vertex vertex) {
		// Prevent duplicate vertices
		if (contains(vertex)) {
			return;
		}
		vertices.add(vertex);
	}

	public void removeVertex() {
		// Try to remove the last vertex of the layer
		try {
			vertices.remove(vertices.size() - 1);
		} catch (IndexOutOfBoundsException ex) {
			// ex.printStackTrace();
		}
	}

	@Override
	public int compareTo(final JungLayer layer) {
		return getIndex() - layer.getIndex();
	}

	private boolean contains(final Vertex vertex) {
		for (Vertex v : vertices) {
			if (v.getIndex() == vertex.getIndex()) {
				// Vertex found by its index!
				return true;
			}
		}
		// Vertex not found!
		return false;
	}
}
