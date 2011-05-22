package de.unikassel.threeD.geo;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * 2dim plane with 3d coordinates
 * <pre>
 *       1- - -2 - - 3
 *       |     |     |
 *       4 - - 5 - - 6
 *       |     |     |
 *       7 - - 8 - - 3
 * </pre>
 * 
 */
public class Plane {

	public Geom3D points;
	public Point3D[][] pointMatrix;
	public List<Point3D[]> lineList;
	private int maxHeight;
	private int maxWidth;
	private int maxDepth;
	private int cols;
	private int rows;

	public Plane(int rows, int cols, int maxWidth, int maxHeight, int maxDepth) {
		pointMatrix = new Point3D[rows][cols];
		lineList = new ArrayList<Point3D[]>();
		points = new Geom3D();
		this.cols = cols;
		this.rows = rows;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.maxDepth = maxDepth;
		init();
	}

	
	public void init() {		
		lineList.clear();
		points.points.clear();
		Random rand = new Random();
		
		for (int r=0; r<rows; r++) {
			for (int c=0; c<cols; c++) {
//				pointMatrix[r][c] = new Point3D(rand.nextDouble()*maxWidth,
//						rand.nextDouble()*maxHeight, rand.nextDouble()*maxDepth);
				pointMatrix[r][c] = new Point3D(r*maxWidth/rows,
						c*maxHeight/cols, rand.nextDouble()*maxDepth);
				points.add(pointMatrix[r][c]);
			}
		}
		
		// center
		for (int r=1; r<rows; r++) {
			for (int c=1; c<cols; c++) {
				Point3D[] up = new Point3D[]{pointMatrix[r][c], pointMatrix[r-1][c]};
				Point3D[] left = new Point3D[]{pointMatrix[r][c], pointMatrix[r][c-1]};
				lineList.add(up);
				lineList.add(left);
			}
		}
		// top side
		for (int c=1; c<cols; c++) {
			Point3D[] left = new Point3D[]{pointMatrix[0][c], pointMatrix[0][c-1]};
			lineList.add(left);
		}
		
		// left side
		for (int r=1; r<rows; r++) {
			Point3D[] up = new Point3D[]{pointMatrix[r][0], pointMatrix[r-1][0]};
			lineList.add(up);
		}

	}


	public Geom3D getPoints() {
		return points;
	}

}
