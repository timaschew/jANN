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
			double fromZ = pa[0].z;
			double toZ = pa[1].z;
			int fromX = (int) (pa[0].x + wx * (1+pa[0].z/1000));
			int fromY = (int) (pa[0].y + wy* (1+pa[0].z/1000));
			int toX = (int) (pa[1].x + wx * (1+pa[1].z/1000));
			int toY = (int) (pa[1].y + wy * (1+pa[1].z/1000));
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
