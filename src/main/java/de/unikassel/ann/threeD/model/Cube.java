/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.threeD.model;

import java.util.ArrayList;
import java.util.List;

/**
 * <pre>
 * 
 *       8 - - - - - 7
 *     / |         / |
 *    5 - - - - - 6  |
 *    |  |        |  |
 *    |  4 - - - -|- 3
 *    | /         | /
 *    1 - - - - - 2
 * </pre>
 * 
 * @author anton
 * 
 */
public class Cube {

	/*
	 * 8 points
	 */
	private Point3D p1;
	private Point3D p2;
	private Point3D p3;
	private Point3D p4;
	private Point3D p5;
	private Point3D p6;
	private Point3D p7;
	private Point3D p8;

	/** half of the size */
	private double positiveRadius;

	/** half of the size */
	private double negativeRadius;

	/** flag if points are already initialized */
	private boolean init = false;

	/**
	 * list of all points / verticies
	 */
	public List<Point3D> points;

	/**
	 * list of the points which are connected
	 */
	public List<Line> lines;

	/**
	 * Cube with a size of 100
	 */
	public Cube() {
		this(100);
	}

	/**
	 * Cube with the given size
	 * 
	 * @param size
	 */
	public Cube(final int size) {
		points = new ArrayList<Point3D>();
		lines = new ArrayList<Line>();
		initAndConnectPoints(size);
	}

	/**
	 * @param size
	 */
	private void initAndConnectPoints(final int size) {
		setGeometrySize(size);
		points.add(p1);
		points.add(p2);
		points.add(p3);
		points.add(p4);
		points.add(p5);
		points.add(p6);
		points.add(p7);
		points.add(p8);

		// bottom lines
		lines.add(new Line(p1, p2));
		lines.add(new Line(p2, p3));
		lines.add(new Line(p3, p4));
		lines.add(new Line(p4, p1));
		// top lines
		lines.add(new Line(p5, p6));
		lines.add(new Line(p6, p7));
		lines.add(new Line(p7, p8));
		lines.add(new Line(p8, p5));
		// vertical lines
		lines.add(new Line(p5, p1));
		lines.add(new Line(p6, p2));
		lines.add(new Line(p7, p3));
		lines.add(new Line(p8, p4));
	}

	/**
	 * @param size
	 */
	public void setGeometrySize(final int size) {
		if (size == getGeometrySize()) {
			return;
		}

		negativeRadius = -(size / 2);
		positiveRadius = +(size / 2);

		double xDist = positiveRadius;
		double yDist = positiveRadius;
		double zDist = positiveRadius;

		// f/b = front/back
		// l/u = lower/upper
		// l/r = left/right
		// 8 Eckpunkte im lokalen Würfel-Koordinatensystem
		// Nullpunkt (0,0,0) = Mittelpunkt
		if (init) {
			p1.x = negativeRadius;
			p1.y = negativeRadius;
			p1.z = negativeRadius;
			p2.x = positiveRadius;
			p2.y = negativeRadius;
			p2.z = negativeRadius;
			p3.x = positiveRadius;
			p3.y = negativeRadius;
			p3.z = positiveRadius;
			p4.x = negativeRadius;
			p4.y = negativeRadius;
			p4.z = positiveRadius;
			p5.x = negativeRadius;
			p5.y = positiveRadius;
			p5.z = negativeRadius;
			p6.x = positiveRadius;
			p6.y = positiveRadius;
			p6.z = negativeRadius;
			p7.x = positiveRadius;
			p7.y = positiveRadius;
			p7.z = positiveRadius;
			p8.x = negativeRadius;
			p8.y = positiveRadius;
			p8.z = positiveRadius;
		} else {
			p1 = new Point3D(-xDist, -yDist, -zDist); // fll
			p2 = new Point3D(+xDist, -yDist, -zDist); // flr
			p3 = new Point3D(+xDist, -yDist, +zDist); // blr
			p4 = new Point3D(-xDist, -yDist, +zDist); // bll
			p5 = new Point3D(-xDist, +yDist, -zDist); // ful
			p6 = new Point3D(+xDist, +yDist, -zDist); // fur
			p7 = new Point3D(+xDist, +yDist, +zDist); // bur
			p8 = new Point3D(-xDist, +yDist, +zDist); // bul
			init = true;
		}

	}

	/**
	 * @return
	 */
	public double getGeometrySize() {
		return Math.abs(negativeRadius) + Math.abs(positiveRadius);
	}

}
