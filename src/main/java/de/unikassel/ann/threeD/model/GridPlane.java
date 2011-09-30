package de.unikassel.ann.threeD.model;

import java.util.ArrayList;
import java.util.Random;

import de.unikassel.mdda.MDDA;

/**
 * 2D plane with a equi gridded structure of 2
 * 
 * <pre>
 * 
 *       1- - -2 - - 3 
 *       |     |     | 
 *       4 - - 5 - - 6 
 *       |     |     | 
 *       7 - - 8 - - 9
 * 
 * </pre>
 * 
 */
public class GridPlane extends RenderGeometry {

	private int xSize;
	private int ySize;

	private int yGrids;
	private int xGrids;

	/**
	 * grid count = 3<br>
	 * size = 100
	 */
	public GridPlane() {
		this(100);
	}

	/**
	 * grid count = 3
	 * 
	 * @param xSize
	 * @param ySize
	 * @param zSize
	 */
	public GridPlane(final int xSize, final int ySize) {
		this(3, 3, xSize, ySize);
	}

	public GridPlane(final int xGrids, final int yGrids, final int size) {
		this(xGrids, yGrids, size, size);
	}

	/**
	 * grid count = 3
	 * 
	 * @param size
	 */
	public GridPlane(final int size) {
		this(3, 3, size, size);
	}

	public GridPlane(final int xGrids, final int yGrids, final int xSize, final int ySize) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.xGrids = xGrids;
		this.yGrids = yGrids;

		setGeometrySize();
	}

	@Override
	public void setGeometrySize(final Integer size, final Integer grids) {
		// lock.lock();
		if (grids != null) {
			xGrids = grids;
			yGrids = grids;
		}
		if (size != null) {
			if (xGrids == 2 && size % 2 != 0) {
				xSize = size + 1;
				ySize = size + 1;
			} else {
				xSize = size;
				ySize = size;
			}
		}
		if (size != null || grids != null) {
			setGeometrySize();
		}
		// lock.unlock();
	}

	/**
	 * @param zGrids
	 * @param yGrids
	 * @param xGrids
	 * 
	 */
	private void setGeometrySize() {

		int yPositive = ySize / 2;
		int yNegative = -yPositive;
		int yChunk = ySize / (yGrids - 1);
		int yCounter = 0;

		int xPositive = xSize / 2;
		int xNegative = -xPositive;
		int xChunk = xSize / (xGrids - 1);
		int xCounter = 0;

		MDDA<Point3D> pointMatrixT = new MDDA<Point3D>(xGrids, yGrids);
		ArrayList<Line> linesT = new ArrayList<Line>();
		ArrayList<Point3D> pointsT = new ArrayList<Point3D>();

		for (int x = xNegative; x <= xPositive; x += xChunk) {
			yCounter = 0;
			for (int y = yNegative; y <= yPositive; y += yChunk) {
				Point3D p = new Point3D(x, y, 1);
				try {
					pointMatrixT.set(p, xCounter, yCounter);
				} catch (Exception e) {
					// System.err.println("try to set " + xCounter + ", " + yCounter + ", " + zCounter);
					// System.err.print("but array was: ");
					// for (int i : pointMatrix.getSize()) {
					// System.err.print(" " + i);
					// }
					// System.err.println("");
					// e.printStackTrace();
					return;
				}

				pointsT.add(p);
				yCounter++;
			}
			xCounter++;
		}

		// grid cube
		for (int x = 0; x < xGrids; x++) {
			for (int y = 0; y < yGrids; y++) {
				if (x > 0) {
					// connection in x axis
					Line left2 = new Line(pointMatrixT.get(x, y), pointMatrixT.get(x - 1, y));
					linesT.add(left2);
				}
				if (y > 0) {
					// connection in y axis
					Line down = new Line(pointMatrixT.get(x, y), pointMatrixT.get(x, y - 1));
					linesT.add(down);
				}
			}
		}

		pointMatrix = pointMatrixT;
		lines = linesT;
		points = pointsT;

	}

	@Override
	public double getGeometrySize() {
		return xSize;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unikassel.ann.threeD.model.RenderGeometry#getGridSize()
	 */
	@Override
	public int getGridSize() {
		return xGrids;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.unikassel.ann.threeD.model.RenderGeometry#random()
	 */
	@Override
	public void random() {
		Random r = new Random();
		for (Point3D p : points) {
			p.x = r.nextDouble() * xSize - xSize / 2;
			p.y = r.nextDouble() * ySize - ySize / 2;
			p.z = 1.0;
		}

	}
}
