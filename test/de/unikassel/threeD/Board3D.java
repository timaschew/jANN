package de.unikassel.threeD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.awt.image.DataBufferInt;

import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import de.unikassel.threeD.geo.Clip;
import de.unikassel.threeD.geo.Cube;
import de.unikassel.threeD.geo.Geom3D;
import de.unikassel.threeD.geo.Plane;
import de.unikassel.threeD.geo.Point3D;

// http://en.wikipedia.org/wiki/3D_projection

public class Board3D extends JPanel implements Runnable, ActionListener, ChangeListener {
	
    private final int DELAY = 50;
	private Thread animator;
	private Cube quader;
	
	private boolean anaglyph = false;
	
	private long prevPaintTime;
	private Plane plane;
	private boolean threadActivated = true;
	private Cube cube;
	private double camX = 0;
	private double camY = 0;
	private double camZ = 0;
	private BufferedImage bufferImgLeft;
	private BufferedImage bufferImgRight;
	
	public static Board3D instance;
	
	public Board3D() {
		instance = this;
		quader = new Cube(100, 100, 100);
		cube = new Cube(100, 100, 100);
		plane = new Plane(10, 10, 200, 200, 20);
		
		prevPaintTime = System.currentTimeMillis();
        setBackground(Color.WHITE);
        setDoubleBuffered(true);
        
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
		
		int[] offset = new int[]{200, 200};
		

		if (anaglyph) {

			
			Rectangle clipRect = g2d.getClipBounds();
			clipRect.toString();
			
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
			SurfaceCubeRenderer.paint(g2ld, quader, offset);
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

			SurfaceCubeRenderer.paint(g2rd, quader, offset);

			g2rd.dispose();
			// bufferImgRight ready

			// calculate anaglpyh
			calcFaster(bufferImgLeft, bufferImgRight, width, height, null);

			g2d.drawImage(bufferImgRight, 0, 0, null);

			camX *= -1;
			updateViewPort(quader.getPoints());

			
		} else {
			SurfaceCubeRenderer.paint(g2d, quader, offset);
			g2d.setColor(Color.BLACK);
//			 FrameRenderer.paint(g2d, quader, offset);
//			 FrameRenderer.paint(g2d, cube, offset);
//			 FrameRenderer.paint(g2d, plane, offset);
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
	
	private Clip checkClip(Geom3D points, int xOffset, int yOffset) {
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxX = Integer.MIN_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		for (Point3D p : points.points) {
			minX = (int) Math.min(minX, p.x);
			minY = (int) Math.min(minY, p.y);
			maxX = (int) Math.max(maxX, p.x);
			maxY = (int) Math.max(maxY, p.y);
		}
		return new Clip(minX+xOffset, minY+yOffset, maxX+xOffset, maxY+yOffset);
	}

	private void calcFaster(BufferedImage l, BufferedImage r, int w, int h, Clip clip) {
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
	
	private void calcAnaglyph(BufferedImage l, BufferedImage r, int w, int h, Clip clip) {
		
//		int x = (int) clip.getX();
//		int y = (int) clip.getY();
//		int width = (int) clip.getWidth();
//		int height = (int) clip.getHeight();
		

		int fullSize = w*h;
		int[] rgbLeft = new int[fullSize];
		l.getRGB(0, 0, w, h, rgbLeft, 0, w);

		int[] rgbRight = new int[fullSize];
		r.getRGB(0, 0, w, h, rgbRight, 0, w);
		
		for (int i=0; i<fullSize; i++) {
			Color cLeft = new Color(rgbLeft[i]);
			Color cRight = new Color(rgbRight[i]);
			
			// gray
			//	0.299	0.587	0.114	  r1	0		0		0		r2
			//	0		0		0		* g1 + 	0.299	0.587	0.114 *	g2
			//	0		0		0		  b1	0.299	0.587	0.114	b2
			
			// color
//        	[r]	  [0	0.7	0.3]	[r_left]	[0	0	0]		[r_right]
//        	[g] = [0	0	0  ] *  [g_left] +  [0	1	0] *	[g_right]
//        	[b]	  [0	0	0  ]	[b_left]	[0	0	1]		[b_right]
			
			Color gray = new Color(
					(int) (cLeft.getRed()*0.299+cLeft.getGreen()*0.587+cLeft.getBlue()*0.114), 
					(int) (cRight.getRed()*0.299+cRight.getGreen()*0.587+cRight.getBlue()*0.114), 
					(int) (cRight.getRed()*0.299+cRight.getGreen()*0.587+cRight.getBlue()*0.114));
			
			Color color = new Color((int) (cLeft.getGreen()*0.7+cLeft.getBlue()*0.3), 
					cRight.getGreen(), cRight.getBlue());
			rgbRight[i] = gray.getRGB();
		}
//		r.setRGB(0, 0, w, h, rgbRight, 0, w);
//		r.flush();
		
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

					rotate(cube.getPoints(), angle_x, angle_y, angle_z);
					rotate(quader.getPoints(), angle_x, angle_y, angle_z);
					rotate(plane.getPoints(), angle_x, angle_y, angle_z);
					
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

	
	/**
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
	public void transform3DTo2D(Geom3D geo, double thetaX, double thetaY,
			double thetaZ, double cX, double cY, double cZ) {
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
            p.y = (aY-cY)*Math.cos(thetaX)-(aZ-cZ)*Math.sin(thetaX);
            p.z = (aY-cY)*Math.sin(thetaX)+(aZ-cZ)*Math.cos(thetaX);

            aX = p.x;
            aZ = p.z;
            
            // Rotation um y-Achse
            p.x = (aX-cX)*Math.cos(thetaY)+(aZ-cZ)*Math.sin(thetaY);
            //p[i][y]= py;
            p.z =-(aX-cX)*Math.sin(thetaY)+(aZ-cZ)*Math.cos(thetaY);

            aY = p.y;
            aX = p.x;

            // Rotation um z-Achse
            p.x = (aX-cX)*Math.cos(thetaZ)-(aY-cY)*Math.sin(thetaZ);
            p.y = (aY-cY)*Math.cos(thetaZ)+(aX-cX)*Math.sin(thetaZ);
          //p[i][z]= pz;
			
//            p.x =  Math.cos(thetaY) * 
//        	( Math.sin(thetaZ) * (aY-cY) + Math.cos(thetaZ) * (aX-cX) )
//        	- Math.sin(thetaY) * (aZ-cZ);
//        
//            p.y = Math.sin(thetaX) * 
//	        ( Math.cos(thetaY) * (aZ-cZ) + Math.sin(thetaY) * 
//	        		( Math.sin(thetaZ) * (aY-cY) + Math.cos(thetaZ) * (aX-cX) )
//	        ) + Math.cos(thetaX) * ( Math.cos(thetaZ) * (aY-cY) - Math.sin(thetaZ) * (aX-cX) );
//	        
//        
//            p.z = Math.cos(thetaX) * 
//	        ( Math.cos(thetaY) * (aZ-cZ) + Math.sin(thetaY) * 
//	        		( Math.sin(thetaZ) * (aY-cY) + Math.cos(thetaZ) * (aX-cX) )
//	        ) - Math.sin(thetaX) * ( Math.cos(thetaZ) * (aY-cY) - Math.sin(thetaZ) * (aX-cX) );


		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals("start-stop")) {
			synchronized (animator) {
				threadActivated = !threadActivated;
			}
		} else if (arg0.getActionCommand().equals("reset")) {

			camY = 0;
			camZ = 0;
			quader.setPointsWithDistance();
			cube.setPointsWithDistance();
			plane.init();
			rotate(cube.getPoints(), 0, 0, 0);
			rotate(quader.getPoints(), 0, 0, 0);
			rotate(plane.getPoints(), 0, 0, 0);
			repaint();
		} else if (arg0.getActionCommand().equals("refresh")) {
			rotate(cube.getPoints(), 0, 0, 0);
			rotate(quader.getPoints(), 0, 0, 0);
			rotate(plane.getPoints(), 0, 0, 0);
			repaint();
		} else if (arg0.getActionCommand().equals("eyeSwitch")) {
			camX *= -1;
			updateViewPort(cube.getPoints());
			updateViewPort(quader.getPoints());
			updateViewPort(plane.getPoints());
			repaint();
		} else if (arg0.getActionCommand().equals("anaglyph")) {
			anaglyph = !anaglyph;
			repaint();
			System.out.println("anaglpyh: "+anaglyph);
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
			if (name.equals("camXspinner")) {
				int val = (Integer) s.getValue();
				if (camX < 0) {
					camX = -val;
				} else {
					camX = val;
				}
				
				repaint();
			}
		} else {
			System.err.println("ooops "+arg0.getSource());
		}
	}

}
