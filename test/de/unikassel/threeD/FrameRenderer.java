package de.unikassel.threeD;

import java.awt.Graphics2D;

import de.unikassel.threeD.geo.Cube;
import de.unikassel.threeD.geo.Plane;
import de.unikassel.threeD.geo.Point3D;

public class FrameRenderer {
	
	final static double PERSPECTIVE_FACTOR = 1000;
	
	/*
	 * x = x * f
	 * f = 1+(z/d)
	 */
	
	/**
	 * @param g2d graphics 
	 * @param cube cube with coordinates
	 * @param w world coordinate
	 */
	public static void paint(Graphics2D g2d, Cube cube, int[] offset2D, Point3D eye) {
		int wx = offset2D[0];
		int wy = offset2D[1];
            
		for (Point3D[] pa : cube.lineList) {
			double fromZ = pa[0].z;
			double toZ = pa[1].z;
			
			int fromX = (int) (pa[0].x);
			int fromY = (int) (pa[0].y);
			int toX = (int) (pa[1].x);
			int toY = (int) (pa[1].y);
			
			// b_x = (d_x - e_x) (e_z / d_z)
			// b_y = (d_y - e_y) (e_z / d_z)
			if (eye != null && eye.z != 0) {
				fromX = (int) ((fromX - eye.x) * (eye.z / fromZ));
				fromY = (int) ((fromY - eye.y) * (eye.z / fromZ));
				toX = (int) ((toX - eye.x) * (eye.z / toZ));
				toY = (int) ((toY - eye.y) * (eye.z / toZ));
			} else {
				fromX = (int) (fromX * (1+fromZ/PERSPECTIVE_FACTOR));
				fromY = (int) (fromY * (1+fromZ/PERSPECTIVE_FACTOR));
				toX = (int) (toX * (1+toZ/PERSPECTIVE_FACTOR));
				toY = (int) (toY * (1+toZ/PERSPECTIVE_FACTOR));
			}
			
			fromX = (int) (wx + fromX);
			fromY = (int) (wy + fromY);
			toX = (int) (wx + toX);
			toY = (int) (wy + toY);
			g2d.drawLine(fromX, fromY, toX, toY);
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
