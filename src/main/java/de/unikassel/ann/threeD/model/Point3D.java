package de.unikassel.ann.threeD.model;

public class Point3D {
	public Double x;
	public Double y;
	public Double z;

	public Point3D(final double xPos, final double yPos, final double zPos) {
		x = xPos;
		y = yPos;
		z = zPos;
	}

	public Point3D(final Point3D p) {
		x = p.x;
		y = p.y;
		z = p.z;
	}

	public boolean equals(final Point3D p) {
		if (x != p.x) {
			return false;
		}
		if (y != p.y) {
			return false;
		}
		return z == p.z;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("(").append(x).append(";");
		sb.append(y).append(";").append(z).append(")");
		return sb.toString();
	}
}
