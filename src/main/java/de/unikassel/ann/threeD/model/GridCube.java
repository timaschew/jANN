package de.unikassel.ann.threeD.model;

import java.util.ArrayList;
import java.util.Random;

import de.unikassel.mdda.MDDA;

/**
 * 3D plane with a equi gridded structure of 3
 * 
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
public class GridCube extends RenderGeometry {

	private int xSize;
	private int zSize;
	private int ySize;
	private int zGrids;
	private int yGrids;
	private int xGrids;

	/**
	 * grid count = 3<br>
	 * size = 100
	 */
	public GridCube() {
		this(100);
	}

	/**
	 * grid count = 3
	 * 
	 * @param xSize
	 * @param ySize
	 * @param zSize
	 */
	public GridCube(final int xSize, final int ySize, final int zSize) {
		this(3, 3, 3, xSize, ySize, zSize);
	}

	public GridCube(final int xGrids, final int yGrids, final int zGrids, final int size) {
		this(xGrids, yGrids, zGrids, size, size, size);
	}

	/**
	 * grid count = 3
	 * 
	 * @param size
	 */
	public GridCube(final int size) {
		this(3, 3, 3, size, size, size);
	}

	public GridCube(final int xGrids, final int yGrids, final int zGrids, final int xSize, final int ySize, final int zSize) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.xGrids = xGrids;
		this.yGrids = yGrids;
		this.zGrids = zGrids;

		setGeometrySize();
	}

	@Override
	public void setGeometrySize(final Integer size, final Integer grids) {
		// lock.lock();
		if (grids != null) {
			xGrids = grids;
			yGrids = grids;
			zGrids = grids;
		}
		if (size != null) {
			if (xGrids == 2 && size % 2 != 0) {
				xSize = size + 1;
				ySize = size + 1;
				zSize = size + 1;
			} else {
				xSize = size;
				ySize = size;
				zSize = size;
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

		MDDA<Point3D> pointMatrixT = new MDDA<Point3D>(xGrids, yGrids, zGrids);
		ArrayList<Line> linesT = new ArrayList<Line>();
		ArrayList<Point3D> pointsT = new ArrayList<Point3D>();

		Random r = new Random();
		Object[] ar = pointMatrixT.getArray();
		for (int i = 0; i < ar.length; i++) {
			Point3D p = new Point3D(-100, 100, r);
			pointMatrixT.set1D(p, i);
			pointsT.add(p);
		}

		// grid cube
		for (int z = 0; z < zGrids; z++) {
			for (int x = 0; x < xGrids; x++) {
				for (int y = 0; y < yGrids; y++) {
					if (z > 0) {
						// connection in z axis
						Line deep = new Line(pointMatrixT.get(x, y, z), pointMatrixT.get(x, y, z - 1));
						linesT.add(deep);
					}
					if (x > 0) {
						// connection in x axis
						Line left2 = new Line(pointMatrixT.get(x, y, z), pointMatrixT.get(x - 1, y, z));
						linesT.add(left2);
					}
					if (y > 0) {
						// connection in y axis
						Line down = new Line(pointMatrixT.get(x, y, z), pointMatrixT.get(x, y - 1, z));
						linesT.add(down);
					}
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
			p.z = r.nextDouble() * zSize - zSize / 2;
		}

	}
}
