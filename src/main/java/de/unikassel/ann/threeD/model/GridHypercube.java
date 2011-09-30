package de.unikassel.ann.threeD.model;

import java.util.ArrayList;
import java.util.Random;

import de.unikassel.mdda.MDDA;

/**
 * 4D plane with a equi gridded structure of 4
 * 
 * 
 */
public class GridHypercube extends RenderGeometry {

	private int xSize;
	private int zSize;
	private int ySize;
	private int wSize;

	private int xGrids;
	private int yGrids;
	private int zGrids;
	private int wGrids;

	public GridHypercube(final int xGrids, final int yGrids, final int zGrids, final int wGrids, final int xSize, final int ySize,
			final int zSize, final int wSize) {
		this.xSize = xSize;
		this.ySize = ySize;
		this.zSize = zSize;
		this.wSize = wSize;
		this.xGrids = xGrids;
		this.yGrids = yGrids;
		this.zGrids = zGrids;
		this.wGrids = wGrids;
		setGeometrySize();
	}

	@Override
	public void setGeometrySize(final Integer size, final Integer grids) {
		// lock.lock();
		if (grids != null) {
			xGrids = grids;
			yGrids = grids;
			zGrids = grids;
			wGrids = grids;
		}
		if (size != null) {
			if (xGrids == 2 && size % 2 != 0) {
				xSize = size + 1;
				ySize = size + 1;
				zSize = size + 1;
				wSize = size + 1;
			} else {
				xSize = size;
				ySize = size;
				zSize = size;
				wSize = size;
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

		int wPositive = wSize / 2;
		int wNegative = -wPositive;
		int wChunk = wSize / (wGrids - 1);
		int wCounter = 0;

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

		MDDA<Point3D> pointMatrixT = new MDDA<Point3D>(xGrids, yGrids, zGrids, wGrids);
		ArrayList<Line> linesT = new ArrayList<Line>();
		ArrayList<Point3D> pointsT = new ArrayList<Point3D>();

		// points
		for (int h = 0; h < wGrids; h++) {
			for (int l = 0; l < zGrids; l++) {
				for (int r = 0; r < yGrids; r++) {
					for (int c = 0; c < xGrids; c++) {
						// pointMatrix[r][c] = new Point3D(rand.nextDouble()*maxWidth,
						// rand.nextDouble()*maxHeight, rand.nextDouble()*maxDepth);
						Point3D p = new Point3D(r * ySize / yGrids, c * xSize / xGrids, l * zGrids / zGrids);
						try {
							pointMatrix.set(p, r, c, l, h);
						} catch (Exception e) {
							// TODO: handle exception
						}
						points.add(p);
					}
				}
			}
		}

		// for (int w = wNegative; w <= wPositive; w += wChunk) {
		// wCounter = 0;
		// for (int z = zNegative; z <= zPositive; z += zChunk) {
		// xCounter = 0;
		// for (int x = xNegative; x <= xPositive; x += xChunk) {
		// yCounter = 0;
		// for (int y = yNegative; y <= yPositive; y += yChunk) {
		// Point3D p = new Point3D(x, y, z);
		// try {
		// pointMatrixT.set(p, xCounter, yCounter, zCounter, wCounter);
		// } catch (Exception e) {
		// System.out.println(xCounter + "/" + yCounter + "/" + zCounter + "/" + wCounter);
		// e.printStackTrace();
		// return;
		// }
		// pointsT.add(p);
		// yCounter++;
		// }
		// xCounter++;
		// }
		// zCounter++;
		// }
		// wCounter++;
		// }

		// grid cube
		for (int w = 0; w < zGrids; w++) {
			for (int z = 0; z < zGrids; z++) {
				for (int x = 0; x < xGrids; x++) {
					for (int y = 0; y < yGrids; y++) {
						if (w > 0) {
							// connection in z axis
							Line wDim = new Line(pointMatrixT.get(x, y, z, w), pointMatrixT.get(x, y, z, w - 1));
							linesT.add(wDim);
						}
						if (z > 0) {
							// connection in z axis
							Line deep = new Line(pointMatrixT.get(x, y, z, w), pointMatrixT.get(x, y, z - 1, w));
							linesT.add(deep);
						}
						if (x > 0) {
							// connection in x axis
							Line left = new Line(pointMatrixT.get(x, y, z, w), pointMatrixT.get(x - 1, y, z, w));
							linesT.add(left);
						}
						if (y > 0) {
							// connection in y axis
							Line down = new Line(pointMatrixT.get(x, y, z, w), pointMatrixT.get(x, y - 1, z, w));
							linesT.add(down);
						}
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
