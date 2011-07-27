package wireframe;

import java.awt.*;
import java.net.*;
import java.io.*;
import java.util.*;
import java.awt.event.*;
import java.applet.*;
import javax.swing.*;
import java.awt.event.*;
import java.util.StringTokenizer;

public class ViewerApplet extends Applet implements KeyListener, ActionListener {

  private boolean isStandalone = false;

  //Get a parameter value
  public String getParameter(String key, String def) {
    return isStandalone ? System.getProperty(key, def) :
      (getParameter(key) != null ? getParameter(key) : def);
  }

  public static final int SIMPLE = 0;
  public static final int WIREFRAME = 1;
  public static final int FLAT = 2;
  public static final int GOURAUD = 3;
  public static final int PHONG = 4;

  private BufferedScreen screen;
  public static Camera cam;
  public static Scene currentScene;
  public static int viewWidth, viewHeight, renderType;


  public void render(){
    switch(renderType){
      case SIMPLE: screen.display(currentScene.simpleRender(viewWidth, viewHeight)); break;
      case FLAT: screen.display(currentScene.flatRender(viewWidth, viewHeight, cam)); break;
      case WIREFRAME: screen.display(currentScene.wireframeRender(viewWidth, viewHeight, cam)); break;
      case GOURAUD: screen.display(currentScene.gouraudRender(viewWidth, viewHeight, cam)); break;
      case PHONG: screen.display(currentScene.phongRender(viewWidth, viewHeight, cam)); break;
    }
  }

  public void actionPerformed(ActionEvent e) {
    renderType=Integer.parseInt(e.getActionCommand());
    render();
    this.requestFocus();
  }




  public void keyTyped(KeyEvent e) {}
  public void keyReleased(KeyEvent e) {}

  public void keyPressed(KeyEvent e) {
    int key = e.getKeyCode();
    int angleUnit = 2;

    switch (key) {
      case KeyEvent.VK_NUMPAD5: // twirl down
        cam.move(0, 1, -0.06f);
        break;
      case KeyEvent.VK_DIVIDE: // twirl up
        cam.move(0, -1, -0.06f);
        break;
      case KeyEvent.VK_NUMPAD7: // twirl right
        cam.move(-1, 0, -0.06f);
        break;
      case KeyEvent.VK_NUMPAD9: // twirl left
        cam.move(1, 0, -0.06f);
        break;
      case KeyEvent.VK_SUBTRACT: // slide up
        cam.slide(0, 1, 0);
        break;
      case KeyEvent.VK_ADD: // slide down
        cam.slide(0, -1, 0);
        break;
      case KeyEvent.VK_LEFT: // slide left
        cam.slide(-1, 0, 0);
        break;
      case KeyEvent.VK_RIGHT: // slide right
        cam.slide(1, 0, 0);
        break;
      case KeyEvent.VK_UP: // slide forward
        cam.slide(0, 0, -1);
        break;
      case KeyEvent.VK_DOWN: // slide backward
        cam.slide(0, 0, 1);
        break;
      case KeyEvent.VK_NUMPAD2: // pitch down
        cam.pitch(-angleUnit);
        break;
      case KeyEvent.VK_NUMPAD8: // pitch up
        cam.pitch(angleUnit);
        break;
      case KeyEvent.VK_NUMPAD1:// roll left
        cam.roll(angleUnit);
        break;
      case KeyEvent.VK_NUMPAD3: // roll right
        cam.roll(-angleUnit);
        break;
      case KeyEvent.VK_NUMPAD4: // yaw left
        cam.yaw(angleUnit);
        break;
      case KeyEvent.VK_NUMPAD6: // yaw right
        cam.yaw(-angleUnit);
        break;
    }
    render();
  }

  //Initialize the applet
  public void init() {
    try {
      jbInit();
    }
    catch(Exception e) {
      e.printStackTrace();
    }
  }



  //Component initialization
  @SuppressWarnings("deprecation")
private void jbInit() throws Exception {
    ArrayList options = new ArrayList();
    try{
      BufferedReader in = null;
      URL url = null;
      URLConnection urlConn = null;
      File f = new File("/Users/anton/Develop/Projekte/ANNtool/resources/listing.txt");
      url = f.toURL();
      urlConn = url.openConnection();
      urlConn.setDoInput(true);
      urlConn.setUseCaches(false);
      in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
      while (true) {
        String line = in.readLine();
        if (line == null) break; // done reading file
        options.add(line);
      }
    } catch (Exception e){JOptionPane.showInputDialog("Error Finding Scene Files");}

    Object[] choices = options.toArray();
    String fileName = (String)JOptionPane.showInputDialog(this, "Choose a Scene File","3D Viewer",JOptionPane.PLAIN_MESSAGE, null, choices, choices[0]);

    setFocusable(true);
    addKeyListener(this);
    int width=600,height=500;
    viewWidth=600;
    viewHeight=500;
    screen = new BufferedScreen(width, height+25);
    screen.setDoubleBuffered(true);
    int border = 20;
    this.setLayout(new GridLayout(0,1));
    this.add(screen);
    screen.setLayout(new BorderLayout());

    JRadioButton wireframeButton = new JRadioButton("Wireframe");
    wireframeButton.setMnemonic(KeyEvent.VK_W);
    wireframeButton.setActionCommand("" + WIREFRAME);
    wireframeButton.setSelected(true);

    JRadioButton flatButton = new JRadioButton("Flat Shading");
    flatButton.setMnemonic(KeyEvent.VK_F);
    flatButton.setActionCommand("" + FLAT);

    JRadioButton gouraudButton = new JRadioButton("Gouraud Shading");
    gouraudButton.setMnemonic(KeyEvent.VK_G);
    gouraudButton.setActionCommand("" + GOURAUD);

    JRadioButton phongButton = new JRadioButton("Phong Shading");
    phongButton.setMnemonic(KeyEvent.VK_P);
    phongButton.setActionCommand("" + PHONG);

    //Group the radio buttons.
    ButtonGroup group = new ButtonGroup();
    group.add(wireframeButton);
    group.add(flatButton);
    group.add(gouraudButton);
    group.add(phongButton);

    //Register a listener for the radio buttons.
    wireframeButton.addActionListener(this);
    flatButton.addActionListener(this);
    gouraudButton.addActionListener(this);
    phongButton.addActionListener(this);

    //Put the radio buttons in a row in a panel.
    JPanel radioPanel = new JPanel(new GridLayout(1, 0));
    radioPanel.add(wireframeButton);
    radioPanel.add(flatButton);
    radioPanel.add(gouraudButton);
    radioPanel.add(phongButton);
    screen.add(radioPanel, BorderLayout.SOUTH);
    radioPanel.addKeyListener(this);

    renderType=WIREFRAME;
    Graphics g = screen.getGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0,0,viewWidth,viewHeight);

    currentScene = new Scene(fileName, getCodeBase()+"/wireframe", true);
    cam = new Camera(new Vertex(0,0,20,1), (Vertex)(((Face)(((Mesh)(currentScene.meshes.get(0))).faces.get(0))).vertices.get(0)),40f,.5f,1000f);
    render();

  }

  //Get Applet information
  public String getAppletInfo() {
    return "3D Viewer";
  }
  //Get parameter info
  public String[][] getParameterInfo() {
    return null;
  }
}