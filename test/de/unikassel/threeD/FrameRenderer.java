package de.unikassel.threeD;

import java.awt.Graphics2D;

import de.unikassel.threeD.geo.Cube;
import de.unikassel.threeD.geo.Plane;
import de.unikassel.threeD.geo.Point3D;

public class FrameRenderer {
	
	final static double PERSPECTIVE_FACTOR = 1000;
	
	/**
	 * @param g2d graphics 
	 * @param cube cube with coordinates
	 * @param w world coordinate
	 */
	public static void paint(Graphics2D g2d, Cube cube, int[] offset2D) {
		int wx = offset2D[0];
		int wy = offset2D[1];
            
		for (Point3D[] pa : cube.lineList) {
			double fromZ = pa[0].z;
			double toZ = pa[1].z;
//			int fromX = (int) (wx + (pa[0].x));
//			int fromY = (int) (wy + (pa[0].y));
//			int toX = (int) (wx + (pa[1].x));
//			int toY = (int) (wy + (pa[1].y));
			int fromX = (int) (wx + (pa[0].x) * (1+pa[0].z/PERSPECTIVE_FACTOR));
			int fromY = (int) (wy + (pa[0].y) * (1+pa[0].z/PERSPECTIVE_FACTOR));
			int toX = (int) (wx + (pa[1].x) * (1+pa[1].z/PERSPECTIVE_FACTOR));
			int toY = (int) (wy + (pa[1].y) * (1+pa[1].z/PERSPECTIVE_FACTOR));
//			if (eye != null && eye.z != 0) {
//				fromX = (int) ((fromX - eye.x) * (eye.z / fromZ));
//				fromY = (int) ((fromY - eye.y) * (eye.z / fromZ));
//				toX = (int) ((toX - eye.x) * (eye.z / toZ));
//				toY = (int) ((toY - eye.y) * (eye.z / toZ));
//			}
			g2d.drawLine(fromX, fromY, toX, toY);
		}       
		// x = dx - ex * ez / dz
		// y = dy - ey * ez / dz
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
