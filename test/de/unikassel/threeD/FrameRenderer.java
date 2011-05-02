package de.unikassel.threeD;

import java.awt.Graphics2D;

import de.unikassel.threeD.geo.Cube;
import de.unikassel.threeD.geo.Plane;
import de.unikassel.threeD.geo.Point3D;

public class FrameRenderer {
	
	/**
	 * @param g2d graphics 
	 * @param cube cube with coordinates
	 * @param w world coordinate
	 */
	public static void paint(Graphics2D g2d, Cube cube, int[] offset2D) {
		int wx = offset2D[0];
		int wy = offset2D[1];
		for (Point3D[] pa : cube.lineList) {
			g2d.drawLine((int)pa[0].x + wx, (int)pa[0].y + wy,
					(int)pa[1].x + wx, (int)pa[1].y + wy);
		}       
	}
	
	public static void paint(Graphics2D g2d, Plane plane, int[] offset2D) {
		int wx = offset2D[0];
		int wy = offset2D[1];
		for (Point3D[] pa : plane.lineList) {
			g2d.drawLine((int)pa[0].x + wx, (int)pa[0].y + wy,
					(int)pa[1].x + wx, (int)pa[1].y + wy);
		}       
	}
}
