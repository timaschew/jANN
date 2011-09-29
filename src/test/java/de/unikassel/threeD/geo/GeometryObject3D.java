package de.unikassel.threeD.geo;

import java.util.ArrayList;
import java.util.List;

import de.unikassel.ann.threeD.model.Point3D;

public class GeometryObject3D {
	
	public List<Point3D> points;
	
	public GeometryObject3D() {
		points = new ArrayList<Point3D>();
	}
	
	public void add(Point3D p) {
		points.add(p);
	}
	
}
