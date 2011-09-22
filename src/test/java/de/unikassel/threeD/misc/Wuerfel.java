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
	public double p[][] = new double[9][4];

	int x = 1, y = 2, z = 3;

	public double minCorner = -100;
	public double maxCorner = +100;
	public int worldWidthOffset = 200;
	public int worldHeightOffset = 200;

	// Rotationswinkel in rad
	public double angle_x = 0.01;
	public double angle_y = 0.0075;
	public double angle_z = 0.005;

	Image buffer;
	Graphics2D gBuffer;

	public Wuerfel() {

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

		// Lokale Würfel-Koordinaten
		// in Welt-Koordinaten: +200
		gBuffer.drawLine((int) p[1][x] + worldWidthOffset, (int) p[1][y] + worldHeightOffset, (int) p[2][x] + worldWidthOffset,
				(int) p[2][y] + worldHeightOffset);
		gBuffer.drawLine((int) p[2][x] + worldWidthOffset, (int) p[2][y] + worldHeightOffset, (int) p[3][x] + worldWidthOffset,
				(int) p[3][y] + worldHeightOffset);
		gBuffer.drawLine((int) p[3][x] + worldWidthOffset, (int) p[3][y] + worldHeightOffset, (int) p[4][x] + worldWidthOffset,
				(int) p[4][y] + worldHeightOffset);
		gBuffer.drawLine((int) p[4][x] + worldWidthOffset, (int) p[4][y] + worldHeightOffset, (int) p[1][x] + worldWidthOffset,
				(int) p[1][y] + worldHeightOffset);
		gBuffer.drawLine((int) p[5][x] + worldWidthOffset, (int) p[5][y] + worldHeightOffset, (int) p[6][x] + worldWidthOffset,
				(int) p[6][y] + worldHeightOffset);
		gBuffer.drawLine((int) p[6][x] + worldWidthOffset, (int) p[6][y] + worldHeightOffset, (int) p[7][x] + worldWidthOffset,
				(int) p[7][y] + worldHeightOffset);
		gBuffer.drawLine((int) p[7][x] + worldWidthOffset, (int) p[7][y] + worldHeightOffset, (int) p[8][x] + worldWidthOffset,
				(int) p[8][y] + worldHeightOffset);
		gBuffer.drawLine((int) p[8][x] + worldWidthOffset, (int) p[8][y] + worldHeightOffset, (int) p[5][x] + worldWidthOffset,
				(int) p[5][y] + worldHeightOffset);
		gBuffer.drawLine((int) p[1][x] + worldWidthOffset, (int) p[1][y] + worldHeightOffset, (int) p[5][x] + worldWidthOffset,
				(int) p[5][y] + worldHeightOffset);
		gBuffer.drawLine((int) p[2][x] + worldWidthOffset, (int) p[2][y] + worldHeightOffset, (int) p[6][x] + worldWidthOffset,
				(int) p[6][y] + worldHeightOffset);
		gBuffer.drawLine((int) p[3][x] + worldWidthOffset, (int) p[3][y] + worldHeightOffset, (int) p[7][x] + worldWidthOffset,
				(int) p[7][y] + worldHeightOffset);
		gBuffer.drawLine((int) p[4][x] + worldWidthOffset, (int) p[4][y] + worldHeightOffset, (int) p[8][x] + worldWidthOffset,
				(int) p[8][y] + worldHeightOffset);

		g.drawImage(buffer, 0, 0, this);

		// Verzögerung
		try {
			Thread.sleep(10);
		} catch (InterruptedException e) {
		}

		double px, py, pz;

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
