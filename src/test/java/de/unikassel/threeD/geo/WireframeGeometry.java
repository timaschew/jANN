package de.unikassel.threeD.geo;

import java.util.List;

import de.unikassel.ann.threeD.model.Line;
import de.unikassel.ann.threeD.model.Point3D;
import de.unikassel.mdda.MDDA;

public abstract class WireframeGeometry {
	
	public MDDA<Point3D> pointMatrix;
	
	public GeometryObject3D points;
	
	public List<Line> lineList;
	
	public GeometryObject3D getPoints() {
		return points;
	}
	
	abstract public void init();

}
