package de.unikassel.threeD.geo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 3dim plane with 3d coordinates
 * <pre>
 * 
 *         10 __ 11 ___ 12
 *         /     /     / |
 *        /     /     /  |
 *       1- - -2 - - 3  /|
 *       |     |     | / |
 *       4 - - 5 - - 6  /
 *       |     |     | /
 *       7 - - 8 - - 9
 *       
 *          a ___ b ____ c
 *         /|    /|    / |
 *        / |   / |   /  |
 *       * -d- * -e -* - f
 *       | /|  | /|  | / |
 *       | -g- * -h- * - i
 *       | /   | /   | /
 *       * - - * - - *
 *       
 *          a ___ b ____ c
 *         /|    /|    / |
 *        / |   / |   /  |
 *       1 -d- 2 -e -3 - f
 *       | /|  | /|  | / |
 *       4 -g- 5 -h- 6 - i
 *       | /   | /   | /
 *       7 - - 8 - - 9
 * </pre>
 * 
 */
public class GridCube extends LineGeom {

	public Geom3D points;
	public Point3D[][][] pointMatrix;
	private int maxHeight;
	private int maxWidth;
	private int maxDepth;
	private int cols;
	private int rows;
	private int layers;

	public GridCube(int layers, int rows, int cols, int maxWidth, int maxHeight, int maxDepth) {
		pointMatrix = new Point3D[layers][rows][cols];
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
					pointMatrix[l][r][c] = new Point3D(l*maxDepth/layers, r*maxWidth/rows,
							c*maxHeight/cols);
					points.add(pointMatrix[l][r][c]);
				}
			}
		}
		
		
		// inner cube
		for (int l=0; l<layers; l++) {
			for (int r=0; r<rows; r++) {
				for (int c=0; c<cols; c++) {
					if (l>0) {
						Line deep = new Line(pointMatrix[l][r][c], pointMatrix[l-1][r][c]);
						lineList.add(deep);
					}
					if (r>0) {
						Line up = new Line(pointMatrix[l][r][c], pointMatrix[l][r-1][c]);
						lineList.add(up);
					}
					if (c>0) {
						Line left = new Line(pointMatrix[l][r][c], pointMatrix[l][r][c-1]);
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
