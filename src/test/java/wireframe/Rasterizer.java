package wireframe;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Vector;

public class Rasterizer {
	class Edge implements Comparable {
		public double t;
		public double yMax, inverseSlope, xIntersect;
		public double zValue, zInc, tInc;
		public Vertex startNormal, normalInc;

		Edge(double _yMax, double _inverseSlope, double _xIntersect,
				double _zValue, double _zInc, Vertex _startNormal,
				Vertex _normalInc, double _tInc) {
			t = 0;
			yMax = _yMax;
			inverseSlope = _inverseSlope;
			xIntersect = _xIntersect;
			zValue = _zValue;
			zInc = _zInc;
			startNormal = _startNormal;
			normalInc = _normalInc;
			tInc = _tInc;
		}

		public int compareTo(Object o) {
			return (int) (xIntersect - ((Edge) (o)).xIntersect);
		}
	}

	class EdgeTable {
		public ArrayList[] scanlines;

		EdgeTable(int height) {
			scanlines = new ArrayList[height];
			for (int i = 0; i < height; i++)
				scanlines[i] = new ArrayList();
		}

		public void addEdge(int scanline, double yMax, double inverseSlope,
				double xIntersect, double zValue, double zInc,
				Vertex startNormal, Vertex normalInc, double tInc) {
			scanlines[scanline].add(new Edge(yMax, inverseSlope, xIntersect,
					zValue, zInc, startNormal, normalInc, tInc));
		}
	}

	public double[][] zbuffer;
	public Color c;

	Rasterizer(BufferedImage offImage) {
		this.c = Color.BLACK;
		int width = offImage.getWidth();
		int height = offImage.getHeight();

		this.zbuffer = new double[width][];
		for (int i = 0; i < width; i++) {
			this.zbuffer[i] = new double[height];
			for (int j = 0; j < height; j++)
				this.zbuffer[i][j] = 1.0D;
		}
	}

	Rasterizer() {
		this.c = Color.BLACK;
	}

	public void setColor(Color color) {
		this.c = color;
	}

	public void fuckYou_drawLine(double startX, double startY, double endX,
			double endY, BufferedImage offImage) {
		double swap;
		if ((startX == endX) && (startY > endY)) {
			swap = startY;
			startY = endY;
			endY = swap;
		}

		double slope = (endY - startY) / (endX - startX);

		if (((slope >= 0.0D) && (startX > endX))
				|| ((slope < 0.0D) && (startX < endX))) {
			swap = startX;
			startX = endX;
			endX = swap;
			swap = startY;
			startY = endY;
			endY = swap;
		}

		slope = (endY - startY) / (endX - startX);

		if (Math.abs(slope) > 1.0D) {
			swap = startX;
			startX = startY;
			startY = swap;
			swap = endX;
			endX = endY;
			endY = swap;
		}

		if (slope < -1.0D) {
			swap = startX;
			startX = endX;
			endX = swap;
			swap = startY;
			startY = endY;
			endY = swap;
		}

		int offset = 10000;
		int deltaX = (int) (offset * (endX - startX));
		int deltaY = (int) (offset * (endY - startY));
		int epsilon = deltaY - Math.abs(deltaX);
		int x = (int) startX;
		int y = (int) startY;
		if ((Math.abs(deltaX) < 1) && (Math.abs(deltaY) < 1))
			setPixel(offImage, x, y, this.c.getRGB());
		if (slope >= 0.0D) {
			for (; x <= (int) endX; x++) {
				if (Math.abs(slope) <= 1.0D)
					setPixel(offImage, x, y, this.c.getRGB());
				if (Math.abs(slope) > 1.0D)
					setPixel(offImage, y, x, this.c.getRGB());
				if (epsilon >= 0) {
					y++;
					epsilon -= deltaX;
				}
				epsilon += deltaY;
			}
		}

		if (slope < 0.0D)
			for (; x >= (int) endX; x--) {
				if (Math.abs(slope) <= 1.0D)
					offImage.setRGB(x, y, this.c.getRGB());
				if (Math.abs(slope) > 1.0D)
					offImage.setRGB(y, x, this.c.getRGB());
				if (epsilon >= 0) {
					y++;
					epsilon += deltaX;
				}
				epsilon += deltaY;
			}
	}

	private void setPixel(BufferedImage img, int x, int y, int RGB) {
		if (((x >= 0 ? 1 : 0) & (x < img.getWidth() ? 1 : 0) & (y >= 0 ? 1 : 0) & (y < img
				.getHeight() ? 1 : 0)) != 0)
			img.setRGB(x, y, RGB);
	}

	public void flatPolygon(ArrayList polygon, BufferedImage offImage) {
		if (polygon.size() == 0)
			return;

		for (int i = 0; i < polygon.size(); i++) {
			Vertex v = (Vertex) polygon.get(i);
			v.x = (int) (v.x + 0.5D);
			v.y = ((int) (v.y + 0.5D) * 2);
		}
		polygon.add(new Vertex(((Vertex) polygon.get(0)).x, ((Vertex) polygon
				.get(0)).y, ((Vertex) polygon.get(0)).z, ((Vertex) polygon
				.get(0)).w));

		double ftop = 10000000.0D;
		double fbottom = 0.0D;
		Vertex a;
		for (int i = 0; i < polygon.size(); i++) {
			a = (Vertex) polygon.get(i);
			if (a.y < ftop)
				ftop = a.y;
			if (a.y <= fbottom)
				continue;
			fbottom = a.y;
		}
		int top = (int) ftop;
		int bottom = (int) fbottom;

		EdgeTable edges = new EdgeTable(bottom - top + 1);
		for (int i = 1; i < polygon.size(); i++) {
			Vertex b = (Vertex) polygon.get(i - 1);
			a = (Vertex) polygon.get(i);
			if ((int) b.y != (int) a.y) {
				if (b.y < a.y) {
					Vertex swap = a;
					a = b;
					b = swap;
				}
				double xInc = (b.x - a.x) / (b.y - a.y);
				double zInc = (b.z - a.z) / (b.y - a.y);
				edges.addEdge((int) a.y - top, (int) b.y - top, xInc, a.x, a.z,
						zInc, null, null, 0.0D);
			}

		}

		Vector activeEdges = new Vector();

		for (int i = 0; i < bottom - top; i++) {
			Collections.sort(activeEdges);

			for (int j = 0; j < activeEdges.size() - 1; j += 2) {
				Rasterizer.Edge e1 = (Rasterizer.Edge) activeEdges.elementAt(j);
				Rasterizer.Edge e2 = (Rasterizer.Edge) activeEdges
						.elementAt(j + 1);

				int xleft = (int) e1.xIntersect;
				if (xleft < e1.xIntersect)
					xleft++;
				int xright = (int) e2.xIntersect;
				if (xright >= e2.xIntersect)
					xright--;
				if (xright < xleft) {
					xright = xleft + 1;
				}

				double zValue = e1.zValue;
				double zInc = (e2.zValue - e1.zValue) / (xright - xleft);

				for (int x = xleft; x <= xright; x++) {
					int py = (i + top) / 2;
					if ((x > 0) && (py > 0) && (x < this.zbuffer.length)
							&& (py < this.zbuffer[x].length)
							&& (this.zbuffer[x][py] > zValue)) {
						this.zbuffer[x][py] = zValue;
						setPixel(offImage, x, py, this.c.getRGB());
					}
					zValue += zInc;
				}
			}

			for (int j = 0; j < edges.scanlines[i].size(); j++)
				activeEdges.add(edges.scanlines[i].get(j));
			for (int j = 0; j < activeEdges.size(); j++) {
				Edge e = (Edge) activeEdges.elementAt(j);
				if ((int) e.yMax == i) {
					activeEdges.remove(j);
					j--;
				} else {
					e.xIntersect += e.inverseSlope;
					e.zValue += e.zInc;
				}
			}
		}
	}

	public void phongPolygon(ArrayList polygon, BufferedImage offImage,
			Shader shader, Face f, Camera cam) {
		if (polygon.size() == 0)
			return;

		for (int i = 0; i < polygon.size(); i++) {
			Vertex v = (Vertex) polygon.get(i);
			v.x = (int) (v.x + 0.5D);
			v.y = ((int) (v.y + 0.5D) * 2);
		}
		Vertex last = (Vertex) polygon.get(0);
		Vertex m = new Vertex(last.x, last.y, last.z, last.w);
		m.normal = last.normal;
		m.worldx = last.worldx;
		m.worldy = last.worldy;
		m.worldz = last.worldz;
		polygon.add(m);

		Vertex a = null;
		double ftop = 10000000.0D;
		double fbottom = 0.0D;
		for (int i = 0; i < polygon.size(); i++) {
			a = (Vertex) polygon.get(i);
			if (a.y < ftop)
				ftop = a.y;
			if (a.y <= fbottom)
				continue;
			fbottom = a.y;
		}
		int top = (int) ftop;
		int bottom = (int) fbottom;
		EdgeTable edges = new EdgeTable(bottom - top + 1);

		for (int i = 1; i < polygon.size(); i++) {
			Vertex b = (Vertex) polygon.get(i - 1);
			a = (Vertex) polygon.get(i);
			if ((int) b.y != (int) a.y) {
				if (b.y < a.y) {
					Vertex swap = a;
					a = b;
					b = swap;
				}
				double xInc = (b.x - a.x) / (b.y - a.y);
				double zInc = (b.z - a.z) / (b.y - a.y);
				Vertex nStart = new Vertex(a.normal.x, a.normal.y, a.normal.z,
						0.0D);
				nStart.worldx = a.worldx;
				nStart.worldy = a.worldy;
				nStart.worldz = a.worldz;
				Vertex nIncs = new Vertex((b.normal.x - a.normal.x)
						/ (b.y - a.y), (b.normal.y - a.normal.y) / (b.y - a.y),
						(b.normal.z - a.normal.z) / (b.y - a.y), 0.0D);
				nIncs.worldx = ((b.worldx - a.worldx) / (b.y - a.y));
				nIncs.worldy = ((b.worldy - a.worldy) / (b.y - a.y));
				nIncs.worldz = ((b.worldz - a.worldz) / (b.y - a.y));
				edges.addEdge((int) a.y - top, (int) b.y - top, xInc, a.x, a.z,
						zInc, nStart, nIncs, 0.0D);
			}
		}

		Vector activeEdges = new Vector();

		for (int i = 0; i < bottom - top; i++) {
			Collections.sort(activeEdges);

			for (int j = 0; j < activeEdges.size() - 1; j += 2) {
				Rasterizer.Edge e1 = (Rasterizer.Edge) activeEdges.elementAt(j);
				Rasterizer.Edge e2 = (Rasterizer.Edge) activeEdges
						.elementAt(j + 1);

				int xleft = (int) e1.xIntersect;
				if (xleft < e1.xIntersect)
					xleft++;
				int xright = (int) e2.xIntersect;
				if (xright >= e2.xIntersect)
					xright--;
				if (xright < xleft) {
					xright = xleft + 1;
				}

				double t = 0.0D;
				double zValue = e1.zValue;
				double zInc = (e2.zValue - e1.zValue) / (xright - xleft);
				Vertex vPoint = new Vertex(e1.startNormal.x, e1.startNormal.y,
						e1.startNormal.z, 0.0D);
				vPoint.worldx = e1.startNormal.worldx;
				vPoint.worldy = e1.startNormal.worldy;
				vPoint.worldz = e1.startNormal.worldz;
				Vertex nIncs = new Vertex((e2.startNormal.x - e1.startNormal.x)
						/ (xright - xleft),
						(e2.startNormal.y - e1.startNormal.y)
								/ (xright - xleft),
						(e2.startNormal.z - e1.startNormal.z)
								/ (xright - xleft), 0.0D);
				nIncs.worldx = ((e2.startNormal.worldx - e1.startNormal.worldx) / (xright - xleft));
				nIncs.worldy = ((e2.startNormal.worldy - e1.startNormal.worldy) / (xright - xleft));
				nIncs.worldz = ((e2.startNormal.worldz - e1.startNormal.worldz) / (xright - xleft));
				for (int x = xleft; x <= xright; x++) {
					int py = (i + top) / 2;
					if ((x > 0) && (py > 0) && (x < this.zbuffer.length)
							&& (py < this.zbuffer[x].length)
							&& (this.zbuffer[x][py] > zValue)) {
						this.zbuffer[x][py] = zValue;
						Vertex world = new Vertex(vPoint.worldx, vPoint.worldy,
								vPoint.worldz, 1.0D);
						setPixel(offImage, x, py,
								shader.shade(f, vPoint, cam, world).getRGB());
					}

					zValue += zInc;
					vPoint.x += nIncs.x;
					vPoint.y += nIncs.y;
					vPoint.z += nIncs.z;
					vPoint.worldx += nIncs.worldx;
					vPoint.worldy += nIncs.worldy;
					vPoint.worldz += nIncs.worldz;
				}
			}

			for (int j = 0; j < edges.scanlines[i].size(); j++)
				activeEdges.add(edges.scanlines[i].get(j));
			for (int j = 0; j < activeEdges.size(); j++) {
				Rasterizer.Edge e = (Rasterizer.Edge) activeEdges.elementAt(j);
				if ((int) e.yMax == i) {
					activeEdges.remove(j);
					j--;
				} else {
					e.xIntersect += e.inverseSlope;
					e.zValue += e.zInc;

					e.startNormal.x += e.normalInc.x;
					e.startNormal.y += e.normalInc.y;
					e.startNormal.z += e.normalInc.z;

					e.startNormal.worldx += e.normalInc.worldx;
					e.startNormal.worldy += e.normalInc.worldy;
					e.startNormal.worldz += e.normalInc.worldz;
				}
			}
		}
	}

	public void gouraudPolygon(ArrayList polygon, BufferedImage offImage,
			Shader shader, Face f) {
		if (polygon.size() == 0)
			return;

		for (int i = 0; i < polygon.size(); i++) {
			Vertex v = (Vertex) polygon.get(i);
			v.x = (int) (v.x + 0.5D);
			v.y = ((int) (v.y + 0.5D) * 2);
		}
		Vertex o = (Vertex) polygon.get(0);
		Vertex m = new Vertex(o.x, o.y, o.z, o.w);
		m.r = o.r;
		m.g = o.g;
		m.b = o.b;
		polygon.add(o);
		Vertex a = null;
		double ftop = 10000000.0D;
		double fbottom = 0.0D;
		for (int i = 0; i < polygon.size(); i++) {
			a = (Vertex) polygon.get(i);
			if (a.y < ftop)
				ftop = a.y;
			if (a.y <= fbottom)
				continue;
			fbottom = a.y;
		}
		int top = (int) ftop;
		int bottom = (int) fbottom;
		EdgeTable edges = new EdgeTable(bottom - top + 1);
		for (int i = 1; i < polygon.size(); i++) {
			Vertex b = (Vertex) polygon.get(i - 1);
			a = (Vertex) polygon.get(i);
			if ((int) b.y != (int) a.y) {
				if (b.y < a.y) {
					Vertex swap = a;
					a = b;
					b = swap;
				}
				double xInc = (b.x - a.x) / (b.y - a.y);
				double zInc = (b.z - a.z) / (b.y - a.y);
				Vertex colorStarts = new Vertex(a.r, a.g, a.b, 0.0D);
				Vertex colorIncs = new Vertex((b.r - a.r) / (b.y - a.y),
						(b.g - a.g) / (b.y - a.y), (b.b - a.b) / (b.y - a.y),
						0.0D);
				edges.addEdge((int) a.y - top, (int) b.y - top, xInc, a.x, a.z,
						zInc, colorStarts, colorIncs, 0.0D);
			}
		}

		Vector activeEdges = new Vector();

		for (int i = 0; i < bottom - top; i++) {
			Collections.sort(activeEdges);

			for (int j = 0; j < activeEdges.size() - 1; j += 2) {
				Rasterizer.Edge e1 = (Rasterizer.Edge) activeEdges.elementAt(j);
				Rasterizer.Edge e2 = (Rasterizer.Edge) activeEdges
						.elementAt(j + 1);

				int xleft = (int) e1.xIntersect;
				if (xleft < e1.xIntersect)
					xleft++;
				int xright = (int) e2.xIntersect;
				if (xright >= e2.xIntersect)
					xright--;
				if (xright < xleft) {
					xright = xleft + 1;
				}

				double zValue = e1.zValue;
				float pr = (float) e1.startNormal.x;
				float pg = (float) e1.startNormal.y;
				float pb = (float) e1.startNormal.z;
				float rInc = (float) (e2.startNormal.x - e1.startNormal.x)
						/ (xright - xleft);
				float gInc = (float) (e2.startNormal.y - e1.startNormal.y)
						/ (xright - xleft);
				float bInc = (float) (e2.startNormal.z - e1.startNormal.z)
						/ (xright - xleft);
				double zInc = (e2.zValue - e1.zValue) / (xright - xleft);

				for (int x = xleft; x <= xright; x++) {
					int py = (i + top) / 2;
					if ((x > 0) && (py > 0) && (x < this.zbuffer.length)
							&& (py < this.zbuffer[x].length)
							&& (this.zbuffer[x][py] > zValue)) {
						this.zbuffer[x][py] = zValue;
						setPixel(
								offImage,
								x,
								py,
								new Color((int) Math.max(Math.min(pr, 255.0F),
										0.0F), (int) Math.max(
										Math.min(pg, 255.0F), 0.0F), (int) Math
										.max(Math.min(pb, 255.0F), 0.0F))
										.getRGB());
					}
					pr += rInc;
					pg += gInc;
					pb += bInc;
					zValue += zInc;
				}

			}

			for (int j = 0; j < edges.scanlines[i].size(); j++)
				activeEdges.add(edges.scanlines[i].get(j));
			for (int j = 0; j < activeEdges.size(); j++) {
				Rasterizer.Edge e = (Rasterizer.Edge) activeEdges.elementAt(j);
				if ((int) e.yMax == i) {
					activeEdges.remove(j);
					j--;
				} else {
					e.xIntersect += e.inverseSlope;
					e.zValue += e.zInc;

					e.startNormal.x += e.normalInc.x;
					e.startNormal.y += e.normalInc.y;
					e.startNormal.z += e.normalInc.z;
				}
			}
		}
	}
}