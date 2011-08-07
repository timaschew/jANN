package de.unikassel.threeD;

import java.awt.Graphics2D;

import de.unikassel.threeD.geo.Cube;
import de.unikassel.threeD.geo.Line;
import de.unikassel.threeD.geo.WireframeGeometry;
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
            
		for (Line line : cube.lineList) {
			double fromZ = line.from.z;
			double toZ = line.to.z;
			
			int fromX = (int) (line.from.x);
			int fromY = (int) (line.from.y);
			int toX = (int) (line.to.x);
			int toY = (int) (line.to.y);
			
			// b_x = (d_x - e_x) (e_z / d_z)
			// b_y = (d_y - e_y) (e_z / d_z)
//			if (eye != null && eye.z != 0) {
//				fromX = (int) ((fromX - eye.x) * (eye.z / fromZ));
//				fromY = (int) ((fromY - eye.y) * (eye.z / fromZ));
//				toX = (int) ((toX - eye.x) * (eye.z / toZ));
//				toY = (int) ((toY - eye.y) * (eye.z / toZ));
//			} else {
//				fromX = (int) (fromX * (1+fromZ/PERSPECTIVE_FACTOR));
//				fromY = (int) (fromY * (1+fromZ/PERSPECTIVE_FACTOR));
//				toX = (int) (toX * (1+toZ/PERSPECTIVE_FACTOR));
//				toY = (int) (toY * (1+toZ/PERSPECTIVE_FACTOR));
//			}
			fromX = (int) (fromX * (1+fromZ/PERSPECTIVE_FACTOR));
			fromY = (int) (fromY * (1+fromZ/PERSPECTIVE_FACTOR));
			toX = (int) (toX * (1+toZ/PERSPECTIVE_FACTOR));
			toY = (int) (toY * (1+toZ/PERSPECTIVE_FACTOR));
			
//			
			fromX = (int) (wx + fromX);
			fromY = (int) (wy + fromY);
			toX = (int) (wx + toX);
			toY = (int) (wy + toY);
			g2d.drawLine(fromX, fromY, toX, toY);
		}       
	}
	
	/*
	 *  float Z = distance + z;
            p = new Point((int) (distance * this.x / Z), (int) (distance * this.y / Z));
	 */
	
	private static double DISTANCE = 10;
	
	public static void paint(Graphics2D g2d, WireframeGeometry plane, int[] offset2D) {
		int wx = offset2D[0];
		int wy = offset2D[1];
		for (Line line : plane.lineList) {
			double zFrom = line.from.z;
			double zTo = line.to.z;
			
			int x1 = (int)((DISTANCE * line.from.x / -zFrom));
			int y1 = (int)((DISTANCE * line.from.y / -zFrom));
			int x2 = (int)((DISTANCE * line.to.x / -zTo));
			int y2 = (int)((DISTANCE * line.to.y / -zTo));
			
			g2d.drawLine(x1+wx, y1+wy, x2+wx, y2+wy);
//			g2d.drawLine((int)line.from.x + wx, (int)line.from.y + wy,
//					(int)line.to.x + wx, (int)line.to.y + wy);
		}       
	}
}
