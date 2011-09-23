/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.threeD.misc;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * @author anton
 * 
 */
public class Wuerfel extends JPanel {

	// 8 Eckpunkte 1-8
	// mit je 3 Koordinaten 1,2,3

	/**
	 * <pre>
	 *       8 - - - - - 7
	 *      / |         / |
	 *     5 - - - - - 6  |
	 *     |  |        |  |
	 *     |  4 - - - -|- 3
	 *     | /         | /
	 *     1 - - - - - 2
	 * </pre>
	 */
	public Double p[][] = new Double[9][4];

	int x = 1, y = 2, z = 3;

	public Double minCorner = -100.0;
	public Double maxCorner = +100.0;

	Image buffer;
	Graphics2D gBuffer;

	private WuerfelGUI controller;

	public Wuerfel(final WuerfelGUI controller) {
		this.controller = controller;
		setBackground(new Color(255, 255, 255));
		add(new JLabel("Test"));

		// 8 Eckpunkte im lokalen Würfel-Koordinatensystem
		// Nullpunkt = Mittelpunkt
		p[1][x] = minCorner;
		p[1][y] = minCorner;
		p[1][z] = minCorner;
		p[2][x] = maxCorner;
		p[2][y] = minCorner;
		p[2][z] = minCorner;
		p[3][x] = maxCorner;
		p[3][y] = minCorner;
		p[3][z] = maxCorner;
		p[4][x] = minCorner;
		p[4][y] = minCorner;
		p[4][z] = maxCorner;
		p[5][x] = minCorner;
		p[5][y] = maxCorner;
		p[5][z] = minCorner;
		p[6][x] = maxCorner;
		p[6][y] = maxCorner;
		p[6][z] = minCorner;
		p[7][x] = maxCorner;
		p[7][y] = maxCorner;
		p[7][z] = maxCorner;
		p[8][x] = minCorner;
		p[8][y] = maxCorner;
		p[8][z] = maxCorner;

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

		// Lokale Würfel-Koordinaten
		// in Welt-Koordinaten: +200
		gBuffer.drawLine(p[1][x].intValue() + worldXoffset, p[1][y].intValue() + worldYOffset, p[2][x].intValue() + worldXoffset,
				p[2][y].intValue() + worldYOffset);
		gBuffer.drawLine(p[2][x].intValue() + worldXoffset, p[2][y].intValue() + worldYOffset, p[3][x].intValue() + worldXoffset,
				p[3][y].intValue() + worldYOffset);
		gBuffer.drawLine(p[3][x].intValue() + worldXoffset, p[3][y].intValue() + worldYOffset, p[4][x].intValue() + worldXoffset,
				p[4][y].intValue() + worldYOffset);
		gBuffer.drawLine(p[4][x].intValue() + worldXoffset, p[4][y].intValue() + worldYOffset, p[1][x].intValue() + worldXoffset,
				p[1][y].intValue() + worldYOffset);
		gBuffer.drawLine(p[5][x].intValue() + worldXoffset, p[5][y].intValue() + worldYOffset, p[6][x].intValue() + worldXoffset,
				p[6][y].intValue() + worldYOffset);
		gBuffer.drawLine(p[6][x].intValue() + worldXoffset, p[6][y].intValue() + worldYOffset, p[7][x].intValue() + worldXoffset,
				p[7][y].intValue() + worldYOffset);
		gBuffer.drawLine(p[7][x].intValue() + worldXoffset, p[7][y].intValue() + worldYOffset, p[8][x].intValue() + worldXoffset,
				p[8][y].intValue() + worldYOffset);
		gBuffer.drawLine(p[8][x].intValue() + worldXoffset, p[8][y].intValue() + worldYOffset, p[5][x].intValue() + worldXoffset,
				p[5][y].intValue() + worldYOffset);
		gBuffer.drawLine(p[1][x].intValue() + worldXoffset, p[1][y].intValue() + worldYOffset, p[5][x].intValue() + worldXoffset,
				p[5][y].intValue() + worldYOffset);
		gBuffer.drawLine(p[2][x].intValue() + worldXoffset, p[2][y].intValue() + worldYOffset, p[6][x].intValue() + worldXoffset,
				p[6][y].intValue() + worldYOffset);
		gBuffer.drawLine(p[3][x].intValue() + worldXoffset, p[3][y].intValue() + worldYOffset, p[7][x].intValue() + worldXoffset,
				p[7][y].intValue() + worldYOffset);
		gBuffer.drawLine(p[4][x].intValue() + worldXoffset, p[4][y].intValue() + worldYOffset, p[8][x].intValue() + worldXoffset,
				p[8][y].intValue() + worldYOffset);

		g.drawImage(buffer, 0, 0, this);

		// Verzögerung
		try {
			Thread.sleep(controller.delayModel.getNumber().intValue());
		} catch (InterruptedException e) {
		}

		double px, py, pz;

		double angle_x = controller.xRotModel.getNumber().doubleValue();
		double angle_y = controller.yRotModel.getNumber().doubleValue();
		double angle_z = controller.zRotModel.getNumber().doubleValue();
		for (int i = 1; i < 9; i++) {

			px = p[i][x];
			py = p[i][y];
			pz = p[i][z];

			// Rotation um x-Achse
			p[i][y] = py * Math.cos(angle_x) - pz * Math.sin(angle_x);
			p[i][z] = py * Math.sin(angle_x) + pz * Math.cos(angle_x);

			py = p[i][y];
			pz = p[i][z];

			// Rotation um y-Achse
			p[i][x] = px * Math.cos(angle_y) + pz * Math.sin(angle_y);
			p[i][z] = -px * Math.sin(angle_y) + pz * Math.cos(angle_y);

			px = p[i][x];

			// Rotation um z-Achse
			p[i][x] = px * Math.cos(angle_z) - py * Math.sin(angle_z);
			p[i][y] = py * Math.cos(angle_z) + px * Math.sin(angle_z);
		}

		repaint();
	}

	@Override
	public void update(final Graphics g) {
		paint(g);
	}
}
