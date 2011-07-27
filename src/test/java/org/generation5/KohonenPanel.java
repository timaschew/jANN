package org.generation5;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

class KohonenPanel extends JPanel {
	private Kohonen kohonen;
	private int m_iDimenX;
	private int m_iDimenY;
	private int m_iPhase;
	private int m_iIteration;

	public void setKohonen(Kohonen paramKohonen, int paramInt1, int paramInt2) {
		this.kohonen = paramKohonen;
		this.m_iDimenX = paramInt1;
		this.m_iDimenY = paramInt2;
	}

	public void setPhaseInfo(int phase, int iteration) {
		this.m_iPhase = phase;
		this.m_iIteration = iteration;
	}

	public void paintComponent(Graphics paramGraphics) {
		super.paintComponent(paramGraphics);

		// init values (200, 161)
		int width = getWidth();
		int height = getHeight();

		paramGraphics.setColor(new Color(245, 245, 245));
		paramGraphics.fillRect(0, 0, width, height);

		paramGraphics.setColor(Color.gray);
		if (this.m_iPhase == 0)
			paramGraphics.drawString("Press Start to initiate training...", 5,
					height - 5);
		else {
			paramGraphics.drawString("Phase " + this.m_iPhase + ": "
					+ this.m_iIteration, 5, height - 5);
		}

		// koordinaten system
		paramGraphics.drawLine(0, height / 2, width, height / 2);
		paramGraphics.drawLine(width / 2, 0, width / 2, height);

		double scaleX = 2.0D / width;
		double scaleY = 2.0D / height;

		double centerOffset = 1.0D; // for centering on the grid
		
		paramGraphics.setColor(Color.black);
//		paintVersion1(paramGraphics, height, scaleX, scaleY, centerOffset);
		
		paintVersion2D(paramGraphics, height, scaleX, scaleY, centerOffset);
		
		
	}


	
	
	private void paintVersion2D(Graphics paramGraphics, int height,
			double scaleX, double scaleY, double centerOffset) {
		
		int startX = 0;
		int startY = 0;
		int endX = 0;
		int endY = 0;
		
//		for (int input=0; input<kohonen.inputSize; input++) {
			for (int w = 0; w<m_iDimenX; w++) {
				for (int h = 0; h<m_iDimenY; h++) {
					startX = (int) ((this.kohonen.getWeight(0, w, h) + centerOffset) / scaleX);
					startY = height - (int) ((this.kohonen.getWeight(1, w, h) + centerOffset) / scaleY);
					
					// horizontal
					if (w + 1 < this.m_iDimenX) {
						endX = (int) ((this.kohonen.getWeight(0, w + 1, h) + centerOffset) / scaleX);
						endY = height	- (int) ((this.kohonen.getWeight(1, w + 1, h) + centerOffset) / scaleY);
						paramGraphics.drawLine(startX, startY, endX, endY);
					}
					
					// vertikal
					if (h + 1 < this.m_iDimenY) {
						endX = (int) ((this.kohonen.getWeight(0, w, h + 1) + centerOffset) / scaleX);
						endY = height - (int) ((this.kohonen.getWeight(1, w, h + 1) + centerOffset) / scaleY);
						paramGraphics.drawLine(startX, startY, endX, endY);
					}
					
				}
			}
//		}
		
		
	}

	private void paintVersion1(Graphics paramGraphics, int height,
			double scaleX, double scaleY, double centerOffset) {
		int x1 = 0;
		int y1 = 0;
		int x2 = 0;
		int y2 = 0;
		
		
		
		
		// zeichne grid
		for (int w = 0; w < this.m_iDimenX; w++) {
			for (int h = 0; h < this.m_iDimenY; h++) {

				// start position
				x1 = (int) ((this.kohonen.getWeight(0, w, h) + centerOffset) / scaleX);
				y1 = height
						- (int) ((this.kohonen.getWeight(1, w, h) + centerOffset) / scaleY);

				// horizontale linien
				// solange nicht außerhalb des wertebereichs
				// w+1 horizontaler nachbar knoten
				if (w + 1 < this.m_iDimenX) {
					x2 = (int) ((this.kohonen.getWeight(0, w + 1, h) + centerOffset) / scaleX);
					y2 = height
							- (int) ((this.kohonen.getWeight(1, w + 1, h) + centerOffset) / scaleY);
					paramGraphics.drawLine(x1, y1, x2, y2);
				}

				// vertikale linien
				// solange nicht außerhalb des wertebereichs
				// h+1 vertikaler nachbar knoten
				if (h + 1 < this.m_iDimenY) {
					x2 = (int) ((this.kohonen.getWeight(0, w, h + 1) + centerOffset) / scaleX);
					y2 = height
							- (int) ((this.kohonen.getWeight(1, w, h + 1) + centerOffset) / scaleY);
					paramGraphics.drawLine(x1, y1, x2, y2);
				}
			}
		}
	}
}