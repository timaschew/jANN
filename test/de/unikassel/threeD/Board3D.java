package de.unikassel.threeD;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;

import de.unikassel.threeD.geo.Cube;
import de.unikassel.threeD.geo.Geom3D;
import de.unikassel.threeD.geo.Plane;
import de.unikassel.threeD.geo.Point3D;

// http://en.wikipedia.org/wiki/3D_projection

public class Board3D extends JPanel implements Runnable, ActionListener {
	
    private final int DELAY = 50;
	private Thread animator;
	private Cube quader;
	
	private long prevPaintTime;
	private Plane plane;
	private boolean threadActivated = true;
	private Cube cube;
	public static Board3D instance;
	
	public Board3D() {
		instance = this;
		quader = new Cube(100, 100, 200);
		cube = new Cube(100, 100, 100);
		plane = new Plane(10, 10, 200, 200, 20);
	}

	public void addNotify() {
		super.addNotify();
		animator = new Thread(this);
		animator.start();
	}
	
	@Override
	public void paint(Graphics g) {
		super.paint(g);

		long fps = System.currentTimeMillis() - prevPaintTime;

		Graphics2D g2d = (Graphics2D) g;
		// Antialiasing
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
            RenderingHints.VALUE_ANTIALIAS_ON);
		
		g2d.drawString("FPS: " + fps, getWidth() - 50, 20);
		FrameRenderer.paint(g2d, quader, new int[] {500, 300});
		SurfaceCubeRenderer.paint(g2d, quader, new int[] {500, 300});
		
		FrameRenderer.paint(g2d, cube, new int[] {200, 400});
		FrameRenderer.paint(g2d, plane, new int[] {50, 50});
		
		
		Toolkit.getDefaultToolkit().sync();
		g.dispose();

		prevPaintTime = System.currentTimeMillis();
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
	
	
	public void paint(Geom3D geo) {
		transform3DTo2D(geo, new Point3D(0, 0, 0), 0, 0, 0);
	}

	public void rotate(Geom3D geo, double x, double y, double z) {
		transform3DTo2D(geo, new Point3D(0, 0, 0), x, y, z);
	}

	public void changeCam(Geom3D geo, int x, int y, int z) {
		transform3DTo2D(geo, new Point3D(x, y, z), 0, 0, 0);
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
	 * @param ax
	 * @param ay
	 * @param az
	 */
	public void transform3DTo2D(Geom3D geo, Point3D cam, double ax, double ay,
			double az) {
		double px, py, pz; // temp point

		for (Point3D p : geo.points) {

			px = p.x;
			py = p.y;
			pz = p.z;

			double cx = cam.x;
			double cy = cam.y;
			double cz = cam.z;

			// 3D -> 2D transformation matrix calculation with rotation 
			// and camera coordinate parameters
			
            p.x =  Math.cos(ay) * 
        	( Math.sin(az) * (py-cy) + Math.cos(az) * (px-cy) )
        	- Math.sin(ay) * (pz-cz);
        
            p.y = Math.sin(ax) * 
	        ( Math.cos(ay) * (pz-cz) + Math.sin(ay) * 
	        		( Math.sin(az) * (py-cy) + Math.cos(az) * (px-cx) )
	        ) + Math.cos(ax) * ( Math.cos(az) * (py-cy) - Math.sin(az) * (px-cx) );
	        
        
            p.z = Math.cos(ax) * 
	        ( Math.cos(ay) * (pz-cz) + Math.sin(ay) * 
	        		( Math.sin(az) * (py-cy) + Math.cos(az) * (px-cx) )
	        ) - Math.sin(ax) * ( Math.cos(az) * (py-cy) - Math.sin(az) * (px-cx) );


		}
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		if (arg0.getActionCommand().equals(""+Action.START_STOP)) {
			synchronized (animator) {
				threadActivated = !threadActivated;
				System.out.println("aktivierung: "+threadActivated);
			   }
		}
	}

}
