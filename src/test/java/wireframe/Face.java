package wireframe;

import java.awt.Color;
import java.io.PrintStream;
import java.util.ArrayList;

public class Face {
	public Vertex fn;
	public Mesh owner;
	public ArrayList vertices;
	public ArrayList normals;
	public Color color;

	public Face(Mesh m) {
		this.vertices = new ArrayList();
		this.normals = new ArrayList();
		this.owner = m;
	}

	public void addVertex(double x, double y, double z, double w) {
		this.vertices.add(new Vertex(x, y, z, w));
	}

	public void addNormal(double x, double y, double z, double w) {
		this.normals.add(new Vertex(x, y, z, w));
	}

	public Face copy() {
		Face f = new Face(this.owner);
		for (int i = 0; i < this.vertices.size(); i++) {
			Vertex v = (Vertex) this.vertices.get(i);
			Vertex n = (Vertex) this.normals.get(i);
			f.addVertex(v.x, v.y, v.z, v.w);
			f.addNormal(n.x, n.y, n.z, n.w);
		}
		return f;
	}

	public void perspectivDivide() {
		for (int i = 0; i < this.vertices.size(); i++)
			((Vertex) this.vertices.get(i)).perspectiveDivide();
	}

	public void transform(FourByFour transformation) {
		FourByFour transposedInverse = transformation.inverse().transpose();
		for (int i = 0; i < this.vertices.size(); i++) {
			this.vertices.set(
					i,
					FourByFour.multiply(transformation,
							(Vertex) this.vertices.get(i)));
			this.normals.set(i, FourByFour.multiply(transposedInverse,
					(Vertex) this.normals.get(i)));
		}
	}

	public void transformNoNormals(FourByFour transformation) {
		Vertex r;
		for (int i = 0; i < this.vertices.size(); i++) {
			r = FourByFour.multiply(transformation, (Vertex) vertices.get(i));
			vertices.set(i, r);
		}
			 
	}

	private double code(int plane, Vertex v) {
		switch (plane) {
		case 0:
			return v.w + v.x;
		case 1:
			return v.w - v.x;
		case 2:
			return v.w + v.y;
		case 3:
			return v.w - v.y;
		case 4:
			return v.w + v.z;
		case 5:
			return v.w - v.z;
		}
		return 0.0D;
	}

	private double hitParameter(Vertex a, Vertex c, int plane) {
		switch (plane) {
		case 0:
			return (a.x + a.w) / (-c.w + a.w - c.x + a.x);
		case 1:
			return (a.w - a.x) / (a.w - a.x - c.w + c.x);
		case 2:
			return (a.y + a.w) / (-c.w + a.w - c.y + a.y);
		case 3:
			return (a.w - a.y) / (a.w - a.y - c.w + c.y);
		case 4:
			return (a.z + a.w) / (-c.w + a.w - c.z + a.z);
		case 5:
			return (a.w - a.z + 2.0D) / (a.w - a.z - c.w + c.z);
		}
		System.out.println("Error in parameterization");
		return -1.0D;
	}

	void clipCanonical() {
		for (int j = 0; j < this.vertices.size(); j++)
			((Vertex) this.vertices.get(j)).w += 2.0D;
		for (int plane = 0; plane < 6; plane++) {
			ArrayList outputVertices = new ArrayList();
			ArrayList outputNormals = new ArrayList();
			for (int i = 0; i < this.vertices.size(); i++) {
				int p = (i + 1) % this.vertices.size();

				Vertex vS = (Vertex) this.vertices.get(i);
				Vertex nS = (Vertex) this.normals.get(i);

				Vertex vP = (Vertex) this.vertices.get(p);
				Vertex nP = (Vertex) this.normals.get(p);
				double codeS = code(plane, vS);
				double codeP = code(plane, vP);

				if ((codeS <= 0.0D) && (codeP > 0.0D)) {
					double t = hitParameter(vS, vP, plane);
					Vertex vMid = Vertex.interpolate(vS, vP, t);
					Vertex nMid = Vertex.interpolate(nS, nP, t);
					outputVertices.add(vMid);
					outputNormals.add(nMid);
					outputVertices.add(vP);
					outputNormals.add(nP);
				}
				if ((codeS > 0.0D) && (codeP <= 0.0D)) {
					double t = hitParameter(vS, vP, plane);
					Vertex vMid = Vertex.interpolate(vS, vP, t);
					Vertex nMid = Vertex.interpolate(nS, nP, t);
					outputVertices.add(vMid);
					outputNormals.add(nMid);
				}
				if ((codeS > 0.0D) && (codeP > 0.0D)) {
					outputVertices.add(vP);
					outputNormals.add(nP);
				}
			}
			this.vertices = outputVertices;
			this.normals = outputNormals;
		}
		for (int i = 0; i < this.vertices.size(); i++)
			((Vertex) this.vertices.get(i)).normal = ((Vertex) this.normals
					.get(i));
	}

	public Vertex faceNormal() {
		if (this.vertices.size() < 3)
			return new Vertex(1.0D, 0.0D, 0.0D, 0.0D);
		Vertex a = (Vertex) this.vertices.get(0);
		Vertex b = (Vertex) this.vertices.get(1);
		Vertex c = (Vertex) this.vertices.get(2);
		return Vertex
				.crossProduct(Vertex.subtract(b, a), Vertex.subtract(c, b))
				.normalize();
	}
}