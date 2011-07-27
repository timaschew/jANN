package de.unikassel.threeD.geo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.unikassel.mdda.MDDA;

/**
 * a line with points in 3d space
 * 
 * <pre>
 * 1 - 2 - 3 - 4 - 5
 * </pre>
 * 
 */
public class SimpleLine extends LineGeom {

	private int maxHeight;
	private int maxWidth;
	private int maxDepth;
	private int length;

	public SimpleLine(int length, int maxWidth, int maxHeight, int maxDepth) {

		pointMatrix = new MDDA<Point3D>(length);
		lineList = new ArrayList<Line>();
		points = new Geom3D();
		this.length = length;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.maxDepth = maxDepth;
		init();
	}

	public void init() {
		lineList.clear();
		points.points.clear();
		Random rand = new Random();

		// points
		for (int c = 0; c < length; c++) {
			// pointMatrix[r][c] = new Point3D(rand.nextDouble()*maxWidth,
			// rand.nextDouble()*maxHeight, rand.nextDouble()*maxDepth);
			Point3D p = new Point3D(c * maxWidth / length, rand.nextDouble()
					* maxHeight, rand.nextDouble() * maxDepth);
			pointMatrix.set(p, c);
			points.add(p);
		}

		for (int c = 1; c < length; c++) {
			Line left = new Line(pointMatrix.get(c), pointMatrix.get(c - 1));
			lineList.add(left);
		}
	}

}
