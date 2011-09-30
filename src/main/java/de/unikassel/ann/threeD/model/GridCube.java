package de.unikassel.ann.threeD.model;

import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

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
	private ReentrantLock lock = new ReentrantLock();

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
		// points
		int zPositive = zSize / 2;
		int zNegative = -zPositive;
		int zChunk = zSize / (zGrids - 1);
		int zCounter = 0;

		int yPositive = ySize / 2;
		int yNegative = -yPositive;
		int yChunk = ySize / (yGrids - 1);
		int yCounter = 0;

		int xPositive = xSize / 2;
		int xNegative = -xPositive;
		int xChunk = xSize / (xGrids - 1);
		int xCounter = 0;

		MDDA<Point3D> pointMatrixT = new MDDA<Point3D>(xGrids, yGrids, zGrids);
		ArrayList<Line> linesT = new ArrayList<Line>();
		ArrayList<Point3D> pointsT = new ArrayList<Point3D>();

		for (int z = zNegative; z <= zPositive; z += zChunk) {
			xCounter = 0;
			for (int x = xNegative; x <= xPositive; x += xChunk) {
				yCounter = 0;
				for (int y = yNegative; y <= yPositive; y += yChunk) {
					Point3D p = new Point3D(x, y, z);
					try {
						pointMatrixT.set(p, xCounter, yCounter, zCounter);
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
			zCounter++;
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
}
