package de.unikassel.ann.threeD.model;

import java.util.ArrayList;

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
		pointMatrix = new MDDA<Point3D>(xGrids, yGrids, zGrids);
		lines = new ArrayList<Line>();
		points = new ArrayList<Point3D>();
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
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

		for (int z = zNegative; z <= zPositive; z += zChunk) {
			xCounter = 0;
			for (int x = xNegative; x <= xPositive; x += xChunk) {
				yCounter = 0;
				for (int y = yNegative; y <= yPositive; y += yChunk) {
					System.out.println("                     " + xCounter + " " + yCounter + " " + zCounter);
					System.out.println(x + " " + y + " " + z);
					Point3D p = new Point3D(x, y, z);
					pointMatrix.set(p, xCounter, yCounter, zCounter);
					points.add(p);
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
						Line deep = new Line(pointMatrix.get(x, y, z), pointMatrix.get(x, y, z - 1));
						lines.add(deep);
					}
					if (x > 0) {
						// connection in x axis
						Line left2 = new Line(pointMatrix.get(x, y, z), pointMatrix.get(x - 1, y, z));
						lines.add(left2);
					}
					if (y > 0) {
						// connection in y axis
						Line down = new Line(pointMatrix.get(x, y, z), pointMatrix.get(x, y - 1, z));
						lines.add(down);
					}
				}
			}
		}
	}

	@Override
	public double getGeometrySize() {
		return xSize;
	}
}
