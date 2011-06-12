package de.unikassel.threeD.geo;

import java.util.List;

public abstract class LineGeom {
	
	public Geom3D points;
	
	public List<Line> lineList;
	
	public Geom3D getPoints() {
		return points;
	}
	
	abstract public void init();

}
