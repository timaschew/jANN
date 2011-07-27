package de.unikassel.threeD.geo;

import java.util.List;

import de.unikassel.mdda.MDDA;

public abstract class LineGeom {
	
	public MDDA<Point3D> pointMatrix;
	
	public Geom3D points;
	
	public List<Line> lineList;
	
	public Geom3D getPoints() {
		return points;
	}
	
	abstract public void init();

}
