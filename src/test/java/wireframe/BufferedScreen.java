package wireframe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

class BufferedScreen extends JPanel
{
  public BufferedImage offImage;

  public BufferedScreen(int width, int height)
  {
    super(false);
    setSize(width, height);
    this.offImage = new BufferedImage(width, height, 1);
    this.offImage.getGraphics().setColor(Color.WHITE);
    this.offImage.getGraphics().fillRect(0, 0, width, height);
  }
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);
    g.drawImage(this.offImage, 0, 0, this);
  }
  public void display(BufferedImage newImage) {
    this.offImage = newImage;
    paint(getGraphics());
  }
}