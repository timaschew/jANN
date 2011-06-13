package de.unikassel.threeD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferInt;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.SomNetwork;
import de.unikassel.ann.model.Synapse;
import de.unikassel.threeD.geo.Cube;
import de.unikassel.threeD.geo.Geom3D;
import de.unikassel.threeD.geo.GridCube;
import de.unikassel.threeD.geo.GridHyperCube;
import de.unikassel.threeD.geo.LineGeom;
import de.unikassel.threeD.geo.Plane;
import de.unikassel.threeD.geo.Point3D;
import de.unikassel.threeD.geo.SimpleLine;

public class Board3D extends JPanel implements Runnable, ActionListener, ChangeListener {
	
    private final int DELAY = 50;
	private Thread animator;
	private Cube quader;
	
	private boolean anaglyph = false;
	
	private long prevPaintTime;
	private LineGeom somVisualisation;
	private boolean threadActivated = true;
	private Cube cube;
	private double rotX = 0;
	private double rotY = 0;
	private double rotZ = 0;
	private double camX = 0;
	private double camY = 0;
	private double camZ = 0;
	private double eyeX = 10;
	private double eyeY = 1;
	private double eyeZ = 1000;
	private BufferedImage bufferImgLeft;
	private BufferedImage bufferImgRight;
	private SomNetwork som;
	private TrainingThread thread;
	
	private class TrainingThread extends Thread {
		TrainingThread(Board3D board) {
			start();
		}
		public void run() {
			som.train();
		}
	}
	
	public static Board3D instance;
	
	public int dim1 = 3;
	public int dim2 = 3;
	public int dim3 = 3;
	public int dim4 = 2;
	public int inputSize = 3;
	
	public Board3D() {
		instance = this;
		
		som = new SomNetwork(inputSize, dim1, dim2, dim3);
		som.addChangeListener(this);
		
		quader = new Cube(100, 100, 200);
		cube = new Cube(100, 100, 100);
//		somVisualisation = new GridHyperCube(dim1, dim2, dim3, dim4, 100, 100, 100);
		somVisualisation = new GridCube(dim1, dim2, dim3, 200, 200, 200);
//		somVisualisation = new Plane(dim1, dim2, 100, 100, 100);
//		somVisualisation = new SimpleLine(dim1, 50, 50, 50);

		updatePoints();
		
		prevPaintTime = System.currentTimeMillis();
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        
	}

	private void updatePoints() {
		
		double scaleX = 2.0D / getWidth();
		double scaleY = 2.0D / getHeight();

		double centerOffset = 0.5; // for centering on the grid
		
		Object[] multiDimArray = (Object[]) som.getMultiArray().getArray();
		for (int i=0; i<multiDimArray.length; i++) {
			
			double[] coord = new double[inputSize];
			for (int c=0; c<inputSize; c++) {
				Synapse synapse = som.getSynapseMatrix().getSynapse(c,i);
				coord[c] = synapse.getWeight();
			}
//			double x = som.getSynapseMatrix().getSynapse(0,i).getWeight();
//			double y = som.getSynapseMatrix().getSynapse(1,i).getWeight();
//			double z = som.getSynapseMatrix().getSynapse(2,i).getWeight();
			
//			int scaleFactor = 200;
//			m[dim1][dim2].x = x / scaleX + centerOffset;
//			m[dim1][dim2].y = y / scaleY + centerOffset;
//			m[dim1][dim2].z = z / scaleFactor + centerOffset;
			
			//TODO: wrong scaling factor
			
			// disable network 2 plane mapping
//			m[d1][d2].x = (x + centerOffset) / scaleX;
//			m[d1][d2].y = (y + centerOffset) / scaleY;
//			m[d2][d2].z = (z + centerOffset) / scaleX;
			
			int[] indices = som.getMultiArray().getMultiDimIndices(i);
			Point3D point = somVisualisation.pointMatrix.get(indices);
			
			for (int c=0; c<coord.length; c++) {
				switch(c) {
				case 0:
					point.x = (coord[0] + centerOffset) / scaleX;
					break;
				case 1:
					point.y = (coord[1] + centerOffset) / scaleY;
					break;
				case 2:
					point.z = (coord[2] + centerOffset) / scaleX;
					break;
				default:
					System.err.println("could not visualize fourth dimension");
					break;
				}
			}
			
//			point.x = (x + centerOffset) / scaleX;
//			point.y = (y + centerOffset) / scaleY;
//			point.z = (z + centerOffset) / scaleX;

				
			
		}
		
	}

	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
		animator.start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);
		boolean prevThreadState;
		synchronized (animator) {
			prevThreadState = threadActivated;
			threadActivated = false;
		}
		long fps = System.currentTimeMillis() - prevPaintTime;

		Graphics2D g2d = (Graphics2D) g;
		// Antialiasing
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);

		int height = getHeight();
		int width = getWidth();
		
		int[] quaderOffset = new int[]{200, 300};
		int[] cubeOffset = new int[]{600, 200};
		int[] planeOffset = new int[]{200, 200};

		if (anaglyph) {

			drawAnaglyph(g2d, height, width, quaderOffset);
			
		} else {
			g2d.setColor(Color.BLACK);
//			 FrameRenderer.paint(g2d, quader, quaderOffset, null);
//			 FrameRenderer.paint(g2d, cube, cubeOffset, null);
			 FrameRenderer.paint(g2d, somVisualisation, planeOffset);
		}

		
		g2d.setColor(Color.BLACK);
		g2d.drawString("FPS: " + fps, getWidth() - 50, 20);

		Toolkit.getDefaultToolkit().sync();
		g.dispose();

		prevPaintTime = System.currentTimeMillis();
		synchronized (animator) {
			threadActivated = prevThreadState;
		}
	}

	/**
	 * @param g2d
	 * @param height
	 * @param width
	 * @param offset
	 */
	private void drawAnaglyph(Graphics2D g2d, int height, int width,
			int[] offset) {
				
		if (bufferImgLeft == null || bufferImgLeft.getWidth() != width
				|| bufferImgLeft.getHeight() != height) {
			bufferImgLeft = (BufferedImage) createImage(width, height);
		}
		if (bufferImgRight == null || bufferImgRight.getWidth() != width
				|| bufferImgRight.getHeight() != height) {
			bufferImgRight = (BufferedImage) createImage(width, height);
		}
		

		Graphics2D g2ld = bufferImgLeft.createGraphics();
		g2ld.setBackground(getBackground());
		g2ld.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		super.paint(g2ld);
		FrameRenderer.paint(g2ld, quader, offset, new Point3D(eyeX, eyeY, eyeZ));
		g2ld.dispose();
		// bufferImgLeft ready

		// switch cam and recalculate
		camX *= -1;
		updateViewPort(quader.getPoints());

		Graphics2D g2rd = bufferImgRight.createGraphics();
		g2rd.setBackground(getBackground());
		g2rd.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		super.paint(g2rd);

		FrameRenderer.paint(g2rd, quader, offset, new Point3D(eyeX, eyeY, eyeZ));

		g2rd.dispose();
		// bufferImgRight ready

		// calculate anaglpyh
		calcFaster(bufferImgLeft, bufferImgRight, width, height);

		g2d.drawImage(bufferImgRight, 0, 0, null);

		camX *= -1;
		updateViewPort(quader.getPoints());
	}


// gray
//	0.299	0.587	0.114	  r1	0		0		0		r2
//	0		0		0		* g1 + 	0.299	0.587	0.114 *	g2
//	0		0		0		  b1	0.299	0.587	0.114	b2
	
// color
//	[r]	  [0	0.7	0.3]	[r_left]	[0	0	0]		[r_right]
//	[g] = [0	0	0  ] *  [g_left] +  [0	1	0] *	[g_right]
//	[b]	  [0	0	0  ]	[b_left]	[0	0	1]		[b_right]
	
	private void calcFaster(BufferedImage l, BufferedImage r, int w, int h) {
		int[] left = ((DataBufferInt)l.getRaster().getDataBuffer()).getData();
		int[] right = ((DataBufferInt)r.getRaster().getDataBuffer()).getData();

		int size = left.length;
		 
		for (int i=0; i<size; i++) {
			Color cLeft = new Color(left[i]);
			Color cRight = new Color(right[i]);
			
			Color gray = new Color(
					(int) (cLeft.getRed()*0.299+cLeft.getGreen()*0.587+cLeft.getBlue()*0.114), 
					(int) (cRight.getRed()*0.299+cRight.getGreen()*0.587+cRight.getBlue()*0.114), 
					(int) (cRight.getRed()*0.299+cRight.getGreen()*0.587+cRight.getBlue()*0.114));

			
			Color color = new Color((int) (cLeft.getGreen()*0.7+cLeft.getBlue()*0.3), 
					cRight.getGreen(), cRight.getBlue());
//			right[i] = (byte) c.getRGB();
			((DataBufferInt)r.getRaster().getDataBuffer()).setElemDouble(i, gray.getRGB());
		}
		
		
	}
	

	@Override
	public void run() {

		long beforeTime, timeDiff, sleep;
		beforeTime = System.currentTimeMillis();

		while (true) {
			synchronized (this) {
				while (threadActivated) {

					double angle_x = 0.01;
					double angle_y = 0.0075;
					double angle_z = 0.005;
					
//					double angle_x = 0.0;
//					double angle_y = 0.0;
//					double angle_z = 0.0;
					
					rotate(cube.getPoints(), angle_x, angle_y, angle_z);
					rotate(quader.getPoints(), angle_x, angle_y, angle_z);
					rotate(somVisualisation.getPoints(), angle_x, angle_y, angle_z);
					
					repaint();

					timeDiff = System.currentTimeMillis() - beforeTime;

					sleep = DELAY - timeDiff;

					if (sleep < 0)
						sleep = 2;
					try {
						Thread.sleep(sleep);
					} catch (InterruptedException e) {
						System.out.println("interrupted");
					}

					beforeTime = System.currentTimeMillis();
				}
			}
		}

	}
	
	public void rotate(Geom3D geo, double x, double y, double z) {
		transform3DTo2D(geo, x, y, z, 0, 0, 0);
	}
	public void updateViewPort(Geom3D geo) {
		transform3DTo2D(geo,0, 0, 0, camX, camY, camZ);
	}
	
	/*
	 * Calculates the 3D to 2D transformation with an rotation
	 * <pre>
	 * 
	 * [ 1      0         0    ]   [ x ]   [          x          ]
	 * [ 0   cos(ax)  -sin(ax) ] . [ y ] = [ y*cos(ax)-z*sin(ax) ]
	 * [ 0   sin(ax)   cos(ax) ]   [ z ]   [ y*sin(ax)+z*cos(ax) ]
	 * 
	 * [ cos(ay)   0   sin(ay) ]   [ x ]   [ x*cos(ay)+z*sin(ay) ]
	 * [    0      1      0    ] . [ y ] = [          y          ]
	 * [-sin(ay)   0   cos(ay) ]   [ z ]   [-x*sin(ay)+z*cos(ay) ]
	 * 
	 * [ cos(az)  -sin(az)  0  ]   [ x ]   [ x*cos(az)-y*sin(az) ]
	 * [ sin(az)   cos(az)  0  ] . [ y ] = [ y*cos(az)+x*sin(az) ]
	 * [    0         0     1  ]   [ z ]   [          z          ]
	 * </pre>
	 * @param geo
	 * @param cam
	 * @param thetaX
	 * @param thetaY
	 * @param thetaZ
	 */
	
	/**
	 * @param geo list of points / coordinates to transform / project
	 * @param thetaX orientation x axis
	 * @param thetaY orientation y axis
	 * @param thetaZ orientation z axis
	 * @param camX x position of camera
	 * @param camY y position of camera
	 * @param camZ z position of camera
	 */
	public void transform3DTo2D(Geom3D geo, double thetaX, double thetaY,
			double thetaZ, double camX, double camY, double camZ) {
		double aX, aY, aZ; // temp point

		for (Point3D p : geo.points) {
			aX = p.x;
			aY = p.y;
			aZ = p.z;

			// 3D -> 2D transformation matrix calculation with rotation 
			// and camera coordinate parameters
			aY = p.y;
			aZ = p.z;
            // Rotation um x-Achse
            //p[i][x] = px;
            p.y = (aY-camY)*Math.cos(thetaX)-(aZ-camZ)*Math.sin(thetaX);
            p.z = (aY-camY)*Math.sin(thetaX)+(aZ-camZ)*Math.cos(thetaX);

            aX = p.x;
            aZ = p.z;
            // Rotation um y-Achse
            p.x = (aX-camX)*Math.cos(thetaY)+(aZ-camZ)*Math.sin(thetaY);
            //p[i][y]= py;
            p.z =-(aX-camX)*Math.sin(thetaY)+(aZ-camZ)*Math.cos(thetaY);

            aY = p.y;
            aX = p.x;
            // Rotation um z-Achse
            p.x = (aX-camX)*Math.cos(thetaZ)-(aY-camY)*Math.sin(thetaZ);
            p.y = (aY-camY)*Math.cos(thetaZ)+(aX-camX)*Math.sin(thetaZ);
		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("start-stop")) {
			synchronized (animator) {
				threadActivated = !threadActivated;
			}
		} else if (arg0.getActionCommand().equals("restart")) {
			camY = 0;
			camZ = 0;
			quader.setPointsWithDistance();
			cube.setPointsWithDistance();
			rotate(cube.getPoints(), 0, 0, 0);
			rotate(quader.getPoints(), 0, 0, 0);
			rotate(somVisualisation.getPoints(), 0, 0, 0);
			repaint();
		} else if (arg0.getActionCommand().equals("resetview")) {
			quader.setPointsWithDistance();
			rotate(cube.getPoints(), 0, 0, 0);
			rotate(quader.getPoints(), 0, 0, 0);
			rotate(somVisualisation.getPoints(), 0, 0, 0);
			updatePoints();
			repaint();
		} else if (arg0.getActionCommand().equals("eyeSwitch")) {
			camX *= -1;
			updateViewPort(cube.getPoints());
			updateViewPort(quader.getPoints());
			updateViewPort(somVisualisation.getPoints());
			repaint();
		} else if (arg0.getActionCommand().equals("anaglyph")) {
			anaglyph = !anaglyph;
			repaint();
			System.out.println("anaglpyh: "+anaglyph);
		} else if (arg0.getActionCommand().equals("train")) {
			thread = new TrainingThread(this);
		} else if(arg0.getActionCommand().equals("setrot")) {
			rotate(somVisualisation.getPoints(), rotX, rotY, rotZ);
//			updatePoints();
			repaint();
			System.out.println("updated rotation: "+rotX+", "+rotY+", "+rotZ);
		}
		else {
			System.err.println("oops: "+arg0.getSource());
		}
	}

	@Override
	public void stateChanged(ChangeEvent arg0) {
		if (arg0.getSource() instanceof JSlider) {
			JSlider s = (JSlider)arg0.getSource();
			String name = s.getName();
			if (name.equals("camX")) {
				camX = s.getValue();
			} else if (name.equals("camY")) {
				camY = s.getValue();
			} else if (name.equals("camZ")) {
				camZ = s.getValue();
			}
		} else if (arg0.getSource() instanceof JSpinner) {
			JSpinner s = (JSpinner)arg0.getSource();
			String name = s.getName();
			double val = (Double) (s.getValue());
			if (name.equals("camXspinner")) {
				
				if (camX < 0) {
					camX = -val;
				} else {
					camX = val;
				}
				
			} else if (name.equals("eyeX")) {
				eyeX = val;
			} else if (name.equals("eyeY")) {
				eyeY = val;
			} else if (name.equals("eyeZ")) {
				eyeZ = val;
			} else if (name.equals("rotX")) {
				rotX = val;
				System.out.println("rotX = "+rotX);
			} else if (name.equals("rotY")) {
				rotY = val;
				System.out.println("rotY = "+rotY);
			} else if (name.equals("rotZ")) {
				rotZ = val;
				System.out.println("rotZ = "+rotZ);
			}
			repaint();
		} else {
			System.err.println("ooops "+arg0.getSource());
		}
	}

	public void update() {
		updatePoints();
	}

}
