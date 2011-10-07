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
	}

	/**
	 * @param zGrids
	 * @param yGrids
	 * @param xGrids
	 * 
	 */
	private void setGeometrySize() {

		MDDA<Point3D> pointMatrixT = new MDDA<Point3D>(xGrids, yGrids);
		ArrayList<Line> linesT = new ArrayList<Line>();
		ArrayList<Point3D> pointsT = new ArrayList<Point3D>();

		// Point3D p = new Point3D(x, y, 1);
		Random r = new Random();
		Object[] ar = pointMatrixT.getArray();
		for (int i = 0; i < ar.length; i++) {
			Point3D p = new Point3D(-1, 1, r);
			pointMatrixT.set1D(p, i);
			pointsT.add(p);
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
