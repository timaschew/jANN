/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.threeD.model;

import java.util.List;

import de.unikassel.mdda.MDDA;

/**
 * Abstract class to use for the FrameRender.<br>
 * Access to all points of a render model and the connectet lines.<br>
 * Furthermore there is an n dimensional array which describes the connected points in the n dimensional space.
 * 
 * @author anton
 * 
 */
abstract public class RenderGeometry {

	/**
	 * 
	 * A plane have 2 dimensions, get the point of the corner <code>Point3D corner = pointMatrix.get(0,0);</code><br>
	 * Next points to the corner with a maximal distacne of two: <code>neighborsOfCorner = pointMatrix.getNeighborForAllDims(2, 0,0);</code><br>
	 * This field can have n dimensions.
	 */
	public MDDA<Point3D> pointMatrix;

	/**
	 * list of all points / verticies
	 */
	public List<Point3D> points;

	/**
	 * list of the points which are connected
	 */
	public List<Line> lines;

	/**
	 * Returns the size
	 * 
	 * @return
	 */
	abstract public double getGeometrySize();

	/**
	 * @param size
	 * @param grids
	 */
	abstract public void setGeometrySize(Integer size, Integer grids);

	/**
	 * @return
	 */
	abstract public int getGridSize();

}
