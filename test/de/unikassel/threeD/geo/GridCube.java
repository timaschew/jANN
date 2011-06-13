package de.unikassel.threeD.geo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import de.unikassel.mdda.MDDAPseudo;

/**
 * 3dim plane with 3d coordinates
 * <pre>
 * 
 *         a ___ b ____ c
 *        /     /     / |
 *       1- - -2 - - 3  f
 *       |     |     | /|
 *       4 - - 5 - - 6  i 
 *       |     |     | /
 *       7 - - 8 - - 9
 *       
 *          a ___ b ___ c
 *        / |   / |   / |
 *       * -d- * -e -* -f
 *       | /|  | /|  | /|
 *       | -g- * -h- * -i
 *       | /   | /   | /
 *       * - - * - - *
 *       
 *          a ___ b ___ c
 *        / |   / |   / |
 *       1 -d- 2 -e -3 -f
 *       | /|  | /|  | /|
 *       4 -g- 5 -h- 6 -i
 *       | /   | /   | /
 *       7 - - 8 - - 9
 * </pre>
 * 
 */
public class GridCube extends LineGeom {

	public Geom3D points;
	private int maxHeight;
	private int maxWidth;
	private int maxDepth;
	private int cols;
	private int rows;
	private int layers;

	public GridCube(int rows, int cols, int layers, int maxWidth, int maxHeight, int maxDepth) {
//		pointMatrix = new Point3D[layers][rows][cols];
		pointMatrix = new MDDAPseudo<Point3D>(rows, cols, layers);
		lineList = new ArrayList<Line>();
		points = new Geom3D();
		this.cols = cols;
		this.rows = rows;
		this.layers = layers;
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
		for (int l=0; l<layers; l++) {
			for (int r=0; r<rows; r++) {
				for (int c=0; c<cols; c++) {
//					pointMatrix[r][c] = new Point3D(rand.nextDouble()*maxWidth,
//							rand.nextDouble()*maxHeight, rand.nextDouble()*maxDepth);
					Point3D p = new Point3D(r*maxWidth/rows, c*maxHeight/cols, l*maxDepth/layers);
					pointMatrix.set(p, r,c,l);
					points.add(p);
				}
			}
		}
		
		
		// grid cube
		for (int l=0; l<layers; l++) {
			for (int r=0; r<rows; r++) {
				for (int c=0; c<cols; c++) {
					if (l>0) {
						Line deep = new Line(pointMatrix.get(r,c,l), pointMatrix.get(r,c,l-1));
						lineList.add(deep);
					}
					if (r>0) {
						Line up = new Line(pointMatrix.get(r,c,l), pointMatrix.get(r-1,c,l));
						lineList.add(up);
					}
					if (c>0) {
						Line left = new Line(pointMatrix.get(r,c,l), pointMatrix.get(r,c-1,l));
						lineList.add(left);
					}
				}
			}
		}
	}


	public Geom3D getPoints() {
		return points;
	}

}
