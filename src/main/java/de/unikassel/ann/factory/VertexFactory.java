package de.unikassel.ann.factory;

import org.apache.commons.collections15.Factory;

/**
 * Vertex Factory used by GraphMouse (jung) in order to create vertexes in the
 * graph viewer.
 * 
 * @author Way
 * 
 */
public class VertexFactory implements Factory<Number> {

	int index = 0;

	@Override
	public Number create() {
		
		// TODO create vertex model
		
		return index++;
	}
}
