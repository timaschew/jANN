package de.unikassel.threeD.geo;

public class Point3D {
	public double x;
	public double y;
	public double z;

	public Point3D(double xPos, double yPos, double zPos) {
		this.x = xPos;
		this.y = yPos;
		this.z = zPos;
	}

	public Point3D(Point3D p) {
		this.x = p.x;
		this.y = p.y;
		this.z = p.z;
	}

	public boolean equals(Point3D p) {
		if (this.x != p.x)
			return false;
		if (this.y != p.y)
			return false;
		return this.z == p.z;
	}

	public String toString() {
		return this.x + "/" + this.y + "/" + this.z;
	}
}
