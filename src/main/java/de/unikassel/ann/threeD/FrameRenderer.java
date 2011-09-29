/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.threeD;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.swing.JPanel;

import de.unikassel.ann.threeD.model.Line;
import de.unikassel.ann.threeD.model.Point3D;
import de.unikassel.ann.threeD.model.RenderGeometry;

/**
 * @author anton
 * 
 */
public class FrameRenderer extends JPanel implements MouseMotionListener {

	private static final double MOUSE_ROTATION_FACTOR = 100.0;;
	private SOMGui controller;
	private Point prevMove;
	private RenderGeometry cube;
	private Image buffer;
	private Graphics2D gBuffer;

	public FrameRenderer(final SOMGui controller) {
		this.controller = controller;
		setBackground(new Color(255, 255, 255));
		addMouseMotionListener(this);
	}

	public void setModel(final RenderGeometry cube) {
		this.cube = cube;
	}

	/**
	 * Rotates only if the rotaiton is activated in the controller<br>
	 * Uses angle values from the controllers spinners
	 */
	private void rotate() {
		if (controller.chckbxAutoRotation.isSelected()) {
			double angle_x = controller.xRotModel.getNumber().doubleValue();
			double angle_y = controller.yRotModel.getNumber().doubleValue();
			double angle_z = controller.zRotModel.getNumber().doubleValue();
			rotate(angle_x, angle_y, angle_z);
		}
	}

	/**
	 * @param angle_x
	 * @param angle_y
	 * @param angle_z
	 */
	private void rotate(final double angle_x, final double angle_y, final double angle_z) {
		double px, py, pz;
		for (Point3D p : cube.points) {
			px = p.x;
			py = p.y;
			pz = p.z;

			// Rotation um x-Achse
			p.y = py * Math.cos(angle_x) - pz * Math.sin(angle_x);
			p.z = py * Math.sin(angle_x) + pz * Math.cos(angle_x);

			py = p.y;
			pz = p.z;

			// Rotation um y-Achse
			p.x = px * Math.cos(angle_y) + pz * Math.sin(angle_y);
			p.z = -px * Math.sin(angle_y) + pz * Math.cos(angle_y);

			px = p.x;

			// Rotation um z-Achse
			p.x = px * Math.cos(angle_z) - py * Math.sin(angle_z);
			p.y = py * Math.cos(angle_z) + px * Math.sin(angle_z);
		}
	}

	@Override
	public void paint(final Graphics g) {

		// Double-Buffering
		if (buffer == null) {
			buffer = createImage(this.getSize().width, this.getSize().height);
			gBuffer = (Graphics2D) buffer.getGraphics();
		}
		gBuffer.clearRect(0, 0, this.getSize().width, this.getSize().height);

		// Antialiasing
		gBuffer.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		int worldXoffset = controller.worldXoffsetModel.getNumber().intValue();
		int worldYOffset = controller.worldYoffsetModel.getNumber().intValue();

		renderGeometry(worldXoffset, worldYOffset);

		g.drawImage(buffer, 0, 0, this);

		// Verzögerung / Delay
		try {
			Thread.sleep(controller.delayModel.getNumber().intValue());
		} catch (InterruptedException e) {
		}

		rotate();

		// repaint();
	}

	/**
	 * @param worldXoffset
	 * @param worldYOffset
	 */
	private void renderGeometry(final int worldXoffset, final int worldYOffset) {

		for (Line l : cube.lines) {
			Point3D a = l.a;
			Point3D b = l.b;
			gBuffer.drawLine(a.x.intValue() + worldXoffset, a.y.intValue() + worldYOffset, b.x.intValue() + worldXoffset, b.y.intValue()
					+ worldYOffset);
		}
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		// calc distance between last point
		int dx = prevMove.x - e.getX();
		int dy = prevMove.y - e.getY();
		// calc factor 100
		double rotY = dx / MOUSE_ROTATION_FACTOR;
		double rotX = dy / MOUSE_ROTATION_FACTOR;
		// do rotate x and y axis
		rotate(rotX, rotY, 0);
		prevMove = e.getPoint();
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
		// save point
		prevMove = e.getPoint();
	}

}
