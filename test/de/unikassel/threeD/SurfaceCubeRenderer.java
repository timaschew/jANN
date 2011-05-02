package de.unikassel.threeD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Polygon;

import de.unikassel.threeD.geo.Cube;
import de.unikassel.threeD.geo.Point3D;

public class SurfaceCubeRenderer {
	
	final static int x = 1;
	final static int y = 2;
	final static int z = 3;
	
	static Color bottomColor = Color.CYAN;
	static Color topColor = Color.YELLOW;
	static Color frontColor = Color.GREEN;
	static Color backColor = Color.GRAY;
	static Color leftColor = Color.BLUE;
	static Color rightColor = Color.RED;
	
	
	/**
	 * Convert into a 2dim array, starting with index 1!!!
	 * @param g
	 * @param cube
	 * @param c
	 * @param w
	 */
	public static void paint(Graphics g, Cube cube, int[] offset2D) {
		double[][]points = new double[9][4];
		int i=1;
		for (Point3D point : cube.getPoints().points) {
			points[i] = convert(point);
			i++;
		}
		paint(g, points, offset2D);
	}
	
	/**
	 * converts a point to an array, startin with index 1!<br>
	 * index 0 is NaN
	 * @param p
	 * @return
	 */
	private static double[] convert(Point3D p) {
		return new double[]{Double.NaN, p.x, p.y, p.z};
	}
	
	/**
	 * @param p cube points and coordinates
	 * @param w world coordinate
	 */
	public static void paint(Graphics g, double[][] p, int[] offset2D) {
		
		int wx = offset2D[0];
		int wy = offset2D[1];
		
		// Perspektive: *1+z/1000
		double c[] = new double[9];
		for (int i=1;i<9;i++) {
            c[i] = 1+p[i][z]/1000;
//        	c[i] = 1; // parallel projection
        }


        // Kreuzprodukt der eine Fläche aufspannenden Vektoren bilden
        // Wenn Betrag der z-Koordinate positiv: Fläche anzeigen
		
		// ((p1.x - p2.x) * (p3.y - p2.y)) - ((p1.y - p2.y)*(p3.x - p2.x)) > 0

		// bottom
        if((p[1][x]*c[1]-p[2][x]*c[2])*(p[3][y]*c[3]-p[2][y]*c[2])
          -(p[1][y]*c[1]-p[2][y]*c[2])*(p[3][x]*c[3]-p[2][x]*c[2]) > 0) {
            // |2->1 x 2->3| > 0

            int xCoords1234[] = {(int)(p[1][x]*c[1])+wx,(int)(p[2][x]*c[2])+wx,
                                 (int)(p[3][x]*c[3])+wx,(int)(p[4][x]*c[4])+wx};
            int yCoords1234[] = {(int)(p[1][y]*c[1])+wy,(int)(p[2][y]*c[2])+wy,
                                 (int)(p[3][y]*c[3])+wy,(int)(p[4][y]*c[4])+wy};

            g.setColor(bottomColor);
            g.fillPolygon(new Polygon(xCoords1234,yCoords1234,4));
        }
        // top
        else if((p[7][x]*c[7]-p[6][x]*c[6])*(p[5][y]*c[5]-p[6][y]*c[6])
               -(p[7][y]*c[7]-p[6][y]*c[6])*(p[5][x]*c[5]-p[6][x]*c[6]) > 0) {
            // |6->7 x 6->5| > 0

            int xCoords5678[] = {(int)(p[5][x]*c[5])+wx,(int)(p[6][x]*c[6])+wx,
                                 (int)(p[7][x]*c[7])+wx,(int)(p[8][x]*c[8])+wx};
            int yCoords5678[] = {(int)(p[5][y]*c[5])+wy,(int)(p[6][y]*c[6])+wy,
                                 (int)(p[7][y]*c[7])+wy,(int)(p[8][y]*c[8])+wy};

            g.setColor(topColor);
            g.fillPolygon(new Polygon(xCoords5678,yCoords5678,4));
        }
        // front
        if((p[6][x]*c[6]-p[2][x]*c[2])*(p[1][y]*c[1]-p[2][y]*c[2])
          -(p[6][y]*c[6]-p[2][y]*c[2])*(p[1][x]*c[1]-p[2][x]*c[2]) > 0) {
            // |2->6 x 2->1| > 0

            int xCoords1265[] = {(int)(p[1][x]*c[1])+wx,(int)(p[2][x]*c[2])+wx,
                                 (int)(p[6][x]*c[6])+wx,(int)(p[5][x]*c[5])+wx};
            int yCoords1265[] = {(int)(p[1][y]*c[1])+wy,(int)(p[2][y]*c[2])+wy,
                                 (int)(p[6][y]*c[6])+wy,(int)(p[5][y]*c[5])+wy};

            g.setColor(frontColor);
            g.fillPolygon(new Polygon(xCoords1265,yCoords1265,4));
        }
        // back
        else if((p[4][x]*c[4]-p[3][x]*c[3])*(p[7][y]*c[7]-p[3][y]*c[3])
               -(p[4][y]*c[4]-p[3][y]*c[3])*(p[7][x]*c[7]-p[3][x]*c[3]) > 0) {
            // |3->4 x 3->7| > 0

            int xCoords4378[] = {(int)(p[4][x]*c[4])+wx,(int)(p[3][x]*c[3])+wx,
                                 (int)(p[7][x]*c[7])+wx,(int)(p[8][x]*c[8])+wx};
            int yCoords4378[] = {(int)(p[4][y]*c[4])+wy,(int)(p[3][y]*c[3])+wy,
                                 (int)(p[7][y]*c[7])+wy,(int)(p[8][y]*c[8])+wy};

            g.setColor(backColor);
            g.fillPolygon(new Polygon(xCoords4378,yCoords4378,4));
        }
        // right
        if((p[3][x]*c[3]-p[2][x]*c[2])*(p[6][y]*c[6]-p[2][y]*c[2])-(p[3][y]*c[3]
           -p[2][y]*c[2])*(p[6][x]*c[6]-p[2][x]*c[2]) > 0) {
            // |2->3 x 2->6| > 0

            int xCoords2376[] = {(int)(p[2][x]*c[2])+wx,(int)(p[3][x]*c[3])+wx,
                                 (int)(p[7][x]*c[7])+wx,(int)(p[6][x]*c[6])+wx};
            int yCoords2376[] = {(int)(p[2][y]*c[2])+wy,(int)(p[3][y]*c[3])+wy,
                                 (int)(p[7][y]*c[7])+wy,(int)(p[6][y]*c[6])+wy};

            g.setColor(rightColor);
            g.fillPolygon(new Polygon(xCoords2376,yCoords2376,4));
        }
        // left
        else if((p[5][x]*c[5]-p[1][x]*c[1])*(p[4][y]*c[4]-p[1][y]*c[1])
               -(p[5][y]*c[5]-p[1][y]*c[1])*(p[4][x]*c[4]-p[1][x]*c[1]) > 0) {
            // |1->5 x 1->4| > 0

            int xCoords1485[] = {(int)(p[1][x]*c[1])+wx,(int)(p[4][x]*c[4])+wx,
                                 (int)(p[8][x]*c[8])+wx,(int)(p[5][x]*c[5])+wx};
            int yCoords1485[] = {(int)(p[1][y]*c[1])+wy,(int)(p[4][y]*c[4])+wy,
                                 (int)(p[8][y]*c[8])+wy,(int)(p[5][y]*c[5])+wy};
            
            g.setColor(leftColor);
            g.fillPolygon(new Polygon(xCoords1485,yCoords1485,4));
        }

	}
}
