package de.unikassel.ann.factory;

import org.apache.commons.collections15.Factory;

/**
 * Edge Factory used by GraphMouse (jung) in order to create edges in the graph
 * viewer.
 * 
 * @author Way
 * 
 */
public class EdgeFactory implements Factory<Number> {

	int index = 0;

	@Override
	public Number create() {

		// TODO create edge model

		return index++;
	}
}
