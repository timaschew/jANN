package de.unikassel.threeD;

import java.awt.Graphics2D;

import de.unikassel.ann.threeD.model.Line;
import de.unikassel.ann.threeD.model.Point3D;
import de.unikassel.threeD.geo.Cube;
import de.unikassel.threeD.geo.WireframeGeometry;

public class FrameRenderer {

	final static double PERSPECTIVE_FACTOR = 1000;

	/*
	 * x = x * f f = 1+(z/d)
	 */

	/**
	 * @param g2d
	 *            graphics
	 * @param cube
	 *            cube with coordinates
	 * @param w
	 *            world coordinate
	 */
	public static void paint(final Graphics2D g2d, final Cube cube, final int[] offset2D, final Point3D eye) {
		int wx = offset2D[0];
		int wy = offset2D[1];

		for (Line line : cube.lineList) {
			double fromZ = line.a.z;
			double toZ = line.b.z;

			int fromX = line.a.x.intValue();
			int fromY = line.a.y.intValue();
			int toX = line.b.x.intValue();
			int toY = line.b.y.intValue();

			// b_x = (d_x - e_x) (e_z / d_z)
			// b_y = (d_y - e_y) (e_z / d_z)
			// if (eye != null && eye.z != 0) {
			// fromX = (int) ((fromX - eye.x) * (eye.z / fromZ));
			// fromY = (int) ((fromY - eye.y) * (eye.z / fromZ));
			// toX = (int) ((toX - eye.x) * (eye.z / toZ));
			// toY = (int) ((toY - eye.y) * (eye.z / toZ));
			// } else {
			// fromX = (int) (fromX * (1+fromZ/PERSPECTIVE_FACTOR));
			// fromY = (int) (fromY * (1+fromZ/PERSPECTIVE_FACTOR));
			// toX = (int) (toX * (1+toZ/PERSPECTIVE_FACTOR));
			// toY = (int) (toY * (1+toZ/PERSPECTIVE_FACTOR));
			// }
			fromX = (int) (fromX * (1 + fromZ / PERSPECTIVE_FACTOR));
			fromY = (int) (fromY * (1 + fromZ / PERSPECTIVE_FACTOR));
			toX = (int) (toX * (1 + toZ / PERSPECTIVE_FACTOR));
			toY = (int) (toY * (1 + toZ / PERSPECTIVE_FACTOR));

			//
			fromX = wx + fromX;
			fromY = wy + fromY;
			toX = wx + toX;
			toY = wy + toY;
			g2d.drawLine(fromX, fromY, toX, toY);
		}
	}

	/*
	 * float Z = distance + z; p = new Point((int) (distance * this.x / Z), (int) (distance * this.y / Z));
	 */

	private static double DISTANCE = 10;

	public static void paint(final Graphics2D g2d, final WireframeGeometry plane, final int[] offset2D) {
		int wx = offset2D[0];
		int wy = offset2D[1];
		for (Line line : plane.lineList) {
			// double zFrom = line.from.z;
			// double zTo = line.to.z;
			//
			// int x1 = (int)((DISTANCE * line.from.x / -zFrom));
			// int y1 = (int)((DISTANCE * line.from.y / -zFrom));
			// int x2 = (int)((DISTANCE * line.to.x / -zTo));
			// int y2 = (int)((DISTANCE * line.to.y / -zTo));
			//
			// g2d.drawLine(x1+wx, y1+wy, x2+wx, y2+wy);
			g2d.drawLine(line.a.x.intValue() + wx, line.a.y.intValue() + wy, line.b.x.intValue() + wx, line.b.y.intValue() + wy);
		}
	}
}
