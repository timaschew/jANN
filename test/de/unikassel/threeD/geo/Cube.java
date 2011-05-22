package de.unikassel.threeD.geo;

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
 */
public class Cube {

	public Point3D p1;
	public Point3D p2;
	public Point3D p3;
	public Point3D p4;
	public Point3D p5;
	public Point3D p6;
	public Point3D p7;
	public Point3D p8;

	public int xDist = 100;
	public int yDist = 100;
	public int zDist = 100;

	public Geom3D pointList;
	public List<Point3D[]> lineList;

	public Cube() {
		pointList = new Geom3D();
		lineList = new ArrayList<Point3D[]>();
	}

	/**
	 * @param fll
	 *            front lower left
	 * @param flr
	 *            front lower right
	 * @param blr
	 *            back lower right
	 * @param bll
	 *            back lower left
	 * @param ful
	 *            front upper left
	 * @param fur
	 *            front upper right
	 * @param bur
	 *            back upper right
	 * @param bul
	 *            back upper left
	 */
	public void set(Point3D fll, Point3D flr, Point3D blr, Point3D bll,
			Point3D ful, Point3D fur, Point3D bur, Point3D bul) {
		p1 = fll;
		p2 = flr;
		p3 = blr;
		p4 = bll;
		p5 = ful;
		p6 = fur;
		p7 = bur;
		p8 = bul;
		
		pointList.points.clear();
		lineList.clear();
		
		pointList.add(p1);
		pointList.add(p2);
		pointList.add(p3);
		pointList.add(p4);
		pointList.add(p5);
		pointList.add(p6);
		pointList.add(p7);
		pointList.add(p8);
		
		// bottom lines
		lineList.add(new Point3D[] {p1,p2});
		lineList.add(new Point3D[] {p2,p3});
		lineList.add(new Point3D[] {p3,p4});
		lineList.add(new Point3D[] {p4,p1});
		// top lines
		lineList.add(new Point3D[] {p5,p6});
		lineList.add(new Point3D[] {p6,p7});
		lineList.add(new Point3D[] {p7,p8});
		lineList.add(new Point3D[] {p8,p5});
		// vertical lines
		lineList.add(new Point3D[] {p5,p1});
		lineList.add(new Point3D[] {p6,p2});
		lineList.add(new Point3D[] {p7,p3});
		lineList.add(new Point3D[] {p8,p4});
		
		flipYAxis();

	}

	/**
	 * craeats a cube with 0,0,0 as center
	 * and the given distance between the corners
	 * @param xDistance
	 * @param yDistance
	 * @param zDistance
	 */
	public Cube(int xDistance, int yDistance, int zDistance) {
		this();
		xDist = xDistance;
		yDist = yDistance;
		zDist = zDistance;
		setPointsWithDistance();
	}
	
	public void setPointsWithDistance() {
		set(new Point3D(-xDist, -yDist, -zDist), 
				new Point3D(+xDist, -yDist,	-zDist), 
				new Point3D(+xDist, -yDist, +zDist), 
				new Point3D(-xDist, -yDist, +zDist), 
				new Point3D(-xDist, +yDist, -zDist),
				new Point3D(+xDist, +yDist, -zDist), 
				new Point3D(+xDist,	+yDist, +zDist), 
				new Point3D(-xDist, +yDist, +zDist));
	}

	public void flipYAxis() {
		// y-Werte spiegeln
		for (Point3D p : pointList.points) {
			p.y *= -1;
        }
	}
	
	public Geom3D getPoints() {
		return pointList;
	}

}
