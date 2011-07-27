package de.unikassel.threeD.misc;

import java.applet.*;
import java.awt.*;
import java.lang.Math;

class Point3D {
	public int x, y, z;

	public Point3D(int X, int Y, int Z) {
		x = X;
		y = Y;
		z = Z;
	}
}

class Edge {
	public int a, b;

	public Edge(int A, int B) {
		a = A;
		b = B;
	}
}

public class Example3D extends Applet {
	private static final long serialVersionUID = 1L;
	int width, height;
//	int mx, my;
	Image imag;
	Graphics graph;
	int azimuth = 20, elevation = 30;
	Point3D[] vertices;
	Edge[] edges;

	public void init() {
		setSize(640, 480);
		width = getSize().width;
		height = getSize().height;
		vertices = new Point3D[8];
		vertices[0] = new Point3D(-1, -1, -1);
		vertices[1] = new Point3D(-1, -1, 8);
		vertices[2] = new Point3D(-1, 1, -1);
		vertices[3] = new Point3D(-1, 1, 8);
		vertices[4] = new Point3D(1, -1, -1);
		vertices[5] = new Point3D(1, -1, 8);
		vertices[6] = new Point3D(1, 1, -1);
		vertices[7] = new Point3D(1, 1, 8);
		edges = new Edge[12];
		edges[0] = new Edge(0, 1);
		edges[1] = new Edge(0, 2);
		edges[2] = new Edge(0, 4);
		edges[3] = new Edge(1, 3);
		edges[4] = new Edge(1, 5);
		edges[5] = new Edge(2, 3);
		edges[6] = new Edge(2, 6);
		edges[7] = new Edge(3, 7);
		edges[8] = new Edge(4, 5);
		edges[9] = new Edge(4, 6);
		edges[10] = new Edge(5, 7);
		edges[11] = new Edge(6, 7);
		imag = createImage(width, height);
		graph = imag.getGraphics();
		drawWireFrame(graph);
	}

	void drawWireFrame(Graphics g) {
		double theta = Math.PI * azimuth / 180.0; // 0,34906585
		double phi = Math.PI * elevation / 180.0; // 0,523598776
		float cosT = (float) Math.cos(theta);
		float sinT = (float) Math.sin(theta);
		float cosP = (float) Math.cos(phi); 
		float sinP = (float) Math.sin(phi);
		float cosTcosP = cosT * cosP;
		float cosTsinP = cosT * sinP;
		float sinTcosP = sinT * cosP;
		float sinTsinP = sinT * sinP;
		Point[] points;
		points = new Point[vertices.length];
		int scaleFactor = width / 4;
		float near = 3;
		int i;
		float nearToObj = 1.5f;
		for (i = 0; i < vertices.length; i++) {
			int x0 = vertices[i].x;
			int y0 = vertices[i].y;
			int z0 = vertices[i].z;
			float x1 = cosT * x0 + sinT * z0;
			float y1 = -sinTsinP * x0 + cosP * y0 + cosTsinP * z0;
			float z1 = cosTcosP * z0 - sinTcosP * x0 - sinP * y0;
			x1 = x1 * near / (z1 + near + nearToObj);
			y1 = y1 * near / (z1 + near + nearToObj);
			points[i] = new Point((int) (width / 2 + scaleFactor * x1 + 0.5),
					(int) (height / 2 - scaleFactor * y1 + 0.5));
		}
		g.fillRect(0, 0, width, height);
		g.setColor(Color.red);
		for (i = 0; i < edges.length; ++i) {
			g.drawLine(points[edges[i].a].x, points[edges[i].a].y,
					points[edges[i].b].x, points[edges[i].b].y);
		}
	}

	public void update(Graphics g) {
		g.drawImage(imag, 0, 0, this);
	}

	public void paint(Graphics g) {
		update(g);
	}
}