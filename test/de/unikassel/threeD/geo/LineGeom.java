package de.unikassel.threeD.geo;

import java.util.List;

import de.unikassel.mdda.MDDAPseudo;

public abstract class LineGeom {
	
	public MDDAPseudo<Point3D> pointMatrix;
	
	public Geom3D points;
	
	public List<Line> lineList;
	
	public Geom3D getPoints() {
		return points;
	}
	
	abstract public void init();

}
