package de.unikassel.ann.threeD.model;

/**
 * A Line from a 3D point <b>a</b> to another 3D point <b>b</b>
 * 
 * @author anton
 * 
 */
public class Line {

	public Point3D a;
	public Point3D b;

	public Line(final Point3D from, final Point3D to) {
		a = from;
		b = to;
	}

}
