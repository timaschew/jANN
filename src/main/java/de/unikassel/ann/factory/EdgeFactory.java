package de.unikassel.ann.factory;

import org.apache.commons.collections15.Factory;

import de.unikassel.ann.gui.graph.Edge;

/**
 * Edge Factory used by GraphMouse (jung) in order to create edges in the graph
 * viewer.
 * 
 * @author Way
 * 
 */
public class EdgeFactory implements Factory<Edge> {

	int index = 0;

	@Override
	public Edge create() {
		// Raise number of created edges
		index++;

		Edge e = new Edge();
		e.setIndex(index);

		return e;
	}

	/**
	 * Reset factory
	 */
	public void reset() {
		index = 0;
	}
}
