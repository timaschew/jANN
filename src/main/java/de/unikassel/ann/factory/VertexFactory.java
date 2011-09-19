package de.unikassel.ann.factory;

import org.apache.commons.collections15.Factory;

import de.unikassel.ann.gui.graph.Vertex;

/**
 * Vertex Factory used by GraphMouse (jung) in order to create vertexes in the
 * graph viewer.
 * 
 * @author Way
 * 
 */
public class VertexFactory implements Factory<Vertex> {

	int index = 0;

	@Override
	public Vertex create() {
		// Raise number of created vertexes
		index++;

		Vertex v = new Vertex();
		v.setIndex(index);

		System.out.println("VertexFactory.create() :: " + index);

		return v;
	}

	/**
	 * Reset factory
	 */
	public void reset() {
		index = 0;
	}

}
