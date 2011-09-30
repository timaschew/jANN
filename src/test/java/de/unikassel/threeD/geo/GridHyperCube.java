package de.unikassel.threeD.geo;

import java.util.ArrayList;
import java.util.Random;

import de.unikassel.ann.threeD.model.Line;
import de.unikassel.ann.threeD.model.Point3D;
import de.unikassel.mdda.MDDA;

/**
 * 4dim plane with 3d coordinates
 * <pre>
 * 
 * </pre>
 * 
 */
public class GridHyperCube extends WireframeGeometry {

	public GeometryObject3D points;
	private int maxHeight;
	private int maxWidth;
	private int maxDepth;
	private int cols;
	private int rows;
	private int layers;
	private int hyperLayer;

	public GridHyperCube(int rows, int cols, int layers, int hyperLayers, int maxWidth, int maxHeight, int maxDepth) {
//		pointMatrix = new Point3D[layers][rows][cols];
		pointMatrix = new MDDA<Point3D>(rows, cols, layers, hyperLayers);
		lineList = new ArrayList<Line>();
		points = new GeometryObject3D();
		this.cols = cols;
		this.rows = rows;
		this.layers = layers;
		this.hyperLayer = hyperLayers;
		this.maxWidth = maxWidth;
		this.maxHeight = maxHeight;
		this.maxDepth = maxDepth;
		init();
	}

	
	public void init() {		
		lineList.clear();
		points.points.clear();
		Random rand = new Random();
		
		// points
		for (int h=0; h<hyperLayer; h++) {
			for (int l=0; l<layers; l++) {
				for (int r=0; r<rows; r++) {
					for (int c=0; c<cols; c++) {
//						pointMatrix[r][c] = new Point3D(rand.nextDouble()*maxWidth,
//								rand.nextDouble()*maxHeight, rand.nextDouble()*maxDepth);
						Point3D p = new Point3D(r*maxWidth/rows, c*maxHeight/cols, l*maxDepth/layers);
						pointMatrix.set(p, r,c,l,h);
						points.add(p);
					}
				}
			}
		}
		
		
		
		// grid cube
		for (int h=0; h<hyperLayer; h++) {
			for (int l=0; l<layers; l++) {
				for (int r=0; r<rows; r++) {
					for (int c=0; c<cols; c++) {
						if (h>0) {
							Line hyper = new Line(pointMatrix.get(r,c,l,h), pointMatrix.get(r,c,l,h-1));
							lineList.add(hyper);
						}
						if (l>0) {
							Line deep = new Line(pointMatrix.get(r,c,l,h), pointMatrix.get(r,c,l-1,h));
							lineList.add(deep);
						}
						if (c>0) {
							Line left = new Line(pointMatrix.get(r,c,l,h), pointMatrix.get(r,c-1,l,h));
							lineList.add(left);
						}
						if (r>0) {
							Line up = new Line(pointMatrix.get(r,c,l,h), pointMatrix.get(r-1,c,l,h));
							lineList.add(up);
						}
					}
				}
			}
		}
	}


	public GeometryObject3D getPoints() {
		return points;
	}

}
