package wireframe;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Scene
{
  public ArrayList meshes;
  public Color background;
  public Shader shader;

  public Scene(String fileName, String directory, boolean isApplet)
  {
    this.meshes = new ArrayList();
    this.shader = new Shader();
    Color emissiveColor = Color.BLACK;
    this.background = Color.WHITE;
    float ambientR = 0.0F; float ambientG = 0.0F; float ambientB = 0.0F;
    float diffuseR = 0.0F; float diffuseG = 0.0F; float diffuseB = 0.0F;
    float specularR = 0.0F; float specularG = 0.0F; float specularB = 0.0F; float specularExp = 0.0F;
    TransformStack transforms = new TransformStack();
    try {
      BufferedReader in = null;
      URL url = null;
      URLConnection urlConn = null;
      if (isApplet) {
    	  directory = ("/Users/anton/Develop/Projekte/ANNtool/resources/");
    	  File f = new File(directory + "/meshes/" + fileName);
        url = f.toURL();
        urlConn = url.openConnection();
        urlConn.setDoInput(true);
        urlConn.setUseCaches(false);
        in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
      } else {
        in = new BufferedReader(new FileReader(fileName));
      }
      while (true) {
        String line = in.readLine();
        if (line == null) break;
        StringTokenizer st = new StringTokenizer(line);
        while (st.hasMoreTokens()) {
          String token = st.nextToken();
          if (token.startsWith("!")) break;
          if (token.equals("mesh"))
          {
            String slash;
            if (isApplet) slash = "/meshes/"; else slash = File.separator;
            Mesh m = new Mesh(directory + slash + st.nextToken(), emissiveColor, ambientR, ambientG, ambientB, diffuseR, diffuseG, diffuseB, specularR, specularG, specularB, specularExp, isApplet);
            m.transform(transforms.CurrentTransform());
            this.meshes.add(m);
          }
          else if (token.equals("background")) {
            float r = Float.parseFloat(st.nextToken());
            float g = Float.parseFloat(st.nextToken());
            float b = Float.parseFloat(st.nextToken());
            this.background = new Color(r, g, b);
          }
          else if (token.equals("light")) {
            double x = Double.parseDouble(st.nextToken());
            double y = Double.parseDouble(st.nextToken());
            double z = Double.parseDouble(st.nextToken());
            float r = Float.parseFloat(st.nextToken());
            float g = Float.parseFloat(st.nextToken());
            float b = Float.parseFloat(st.nextToken());
            this.shader.lights.add(new Light(x, y, x, r, g, b));
          }
          else if (token.equals("ambient")) {
            ambientR = Float.parseFloat(st.nextToken());
            ambientG = Float.parseFloat(st.nextToken());
            ambientB = Float.parseFloat(st.nextToken());
          }
          else if (token.equals("diffuse")) {
            diffuseR = Float.parseFloat(st.nextToken());
            diffuseG = Float.parseFloat(st.nextToken());
            diffuseB = Float.parseFloat(st.nextToken());
          }
          else if (token.equals("specular")) {
            specularR = Float.parseFloat(st.nextToken());
            specularG = Float.parseFloat(st.nextToken());
            specularB = Float.parseFloat(st.nextToken());
          }
          else if (token.equals("specularExponent")) {
            specularExp = Float.parseFloat(st.nextToken());
          }
          else if (token.equals("emissive")) {
            float r = Float.parseFloat(st.nextToken());
            float g = Float.parseFloat(st.nextToken());
            float b = Float.parseFloat(st.nextToken());
            emissiveColor = new Color(r, g, b);
          }
          else if (token.equals("push")) {
            transforms.push();
          } else if (token.equals("pop")) {
            transforms.pop();
          } else if (token.equals("translate")) {
            double dx = Double.parseDouble(st.nextToken());
            double dy = Double.parseDouble(st.nextToken());
            double dz = Double.parseDouble(st.nextToken());
            transforms.translate(dx, dy, dz);
          }
          else if (token.equals("scale")) {
            double sx = Double.parseDouble(st.nextToken());
            double sy = Double.parseDouble(st.nextToken());
            double sz = Double.parseDouble(st.nextToken());
            transforms.scale(sx, sy, sz);
          }
          else if (token.equals("rotate")) {
            double angle = Double.parseDouble(st.nextToken());
            double vx = Double.parseDouble(st.nextToken());
            double vy = Double.parseDouble(st.nextToken());
            double vz = Double.parseDouble(st.nextToken());
            transforms.rotate(angle, vx, vy, vz);
          }
        }
      }
      in.close();
    } catch (Exception e) {
      System.out.println("Scene exception!");
      e.printStackTrace();
    }
  }

  public BufferedImage simpleRender(int viewWidth, int viewHeight)
  {
    BufferedImage rendering = new BufferedImage(viewWidth, viewHeight, 1);
    Graphics g = rendering.getGraphics();
    g.setColor(this.background);
    g.fillRect(0, 0, viewWidth, viewHeight);

    for (int i = 0; i < this.meshes.size(); i++) {
      for (int j = 0; j < ((Mesh)this.meshes.get(i)).faces.size(); j++) {
        g.setColor(((Mesh)this.meshes.get(i)).emissive);
        int x1 = 0; int x2 = 0; int y1 = 0; int y2 = 0;
        for (int k = 1; k < ((Face)((Mesh)this.meshes.get(i)).faces.get(j)).vertices.size(); k++) {
          x1 = (int)((Vertex)((Face)((Mesh)this.meshes.get(i)).faces.get(j)).vertices.get(k - 1)).x;
          y1 = (int)((Vertex)((Face)((Mesh)this.meshes.get(i)).faces.get(j)).vertices.get(k - 1)).y;
          x2 = (int)((Vertex)((Face)((Mesh)this.meshes.get(i)).faces.get(j)).vertices.get(k)).x;
          y2 = (int)((Vertex)((Face)((Mesh)this.meshes.get(i)).faces.get(j)).vertices.get(k)).y;
          g.drawLine(x1, y1, x2, y2);
        }
        x1 = (int)((Vertex)((Face)((Mesh)this.meshes.get(i)).faces.get(j)).vertices.get(0)).x;
        y1 = (int)((Vertex)((Face)((Mesh)this.meshes.get(i)).faces.get(j)).vertices.get(0)).y;
        g.drawLine(x2, y2, x1, y1);
      }
    }
    return rendering;
  }

  public BufferedImage wireframeRender(int viewWidth, int viewHeight, Camera cam) {
    BufferedImage rendering = new BufferedImage(viewWidth, viewHeight, 1);
    Rasterizer drawer = new Rasterizer();
    Graphics g = rendering.getGraphics();
    g.setColor(Color.WHITE);
    g.fillRect(0, 0, viewWidth, viewHeight);

    ArrayList copyFaces = new ArrayList();
    for (int i = 0; i < this.meshes.size(); i++) {
      Mesh m = (Mesh)this.meshes.get(i);
      for (int j = 0; j < m.faces.size(); j++) {
        Face f = (Face)m.faces.get(j);
        Face c = f.copy();
        c.color = m.emissive;
        copyFaces.add(c);
      }

    }

    FourByFour eyeTransform = cam.eyeTransform();
    for (int i = 0; i < copyFaces.size(); i++) {
    	((Face)copyFaces.get(i)).transformNoNormals(eyeTransform);
    }

    // perspective pre calculatings
    double top = cam.near * Math.tan(Math.toRadians(cam.angle / 2.0D));
    double bottom = -top;
    double right = top * (viewWidth / viewHeight);
    double left = -right;

    // perspective transform
    double[] data = new double[16];
    data[0] = (2.0D * cam.near / (right - left));
    data[1] = 0.0D;
    data[2] = ((right + left) / (right - left));
    data[3] = 0.0D;
    data[4] = 0.0D;
    data[5] = (2.0D * cam.near / (top - bottom));
    data[6] = ((top + bottom) / (top - bottom));
    data[7] = 0.0D;
    data[8] = 0.0D;
    data[9] = 0.0D;
    data[10] = (-1.0D * (cam.far + cam.near) / (cam.far - cam.near));
    data[11] = (-2.0D * cam.far * cam.near / (cam.far - cam.near));
    data[12] = 0.0D;
    data[13] = 0.0D;
    data[14] = -1.0D;
    data[15] = 0.0D;
    FourByFour perspectiveTransform = new FourByFour(data);
    for (int i = 0; i < copyFaces.size(); i++) {
    	((Face)copyFaces.get(i)).transformNoNormals(perspectiveTransform);
    }

    for (int i = 0; i < copyFaces.size(); i++) {
    	((Face)copyFaces.get(i)).clipCanonical();
    }

    for (int i = 0; i < copyFaces.size(); i++) {
      ((Face)copyFaces.get(i)).perspectivDivide();
    }

    FourByFour viewportTransform = FourByFour.identity();
    viewportTransform.data[0] = ((viewWidth - 1.0D) / 2.0D);
    viewportTransform.data[3] = ((viewWidth - 1.0D) / 2.0D);
    viewportTransform.data[5] = ((viewHeight - 1.0D) / 2.0D);
    viewportTransform.data[7] = ((viewHeight + 2.0D) / 2.0D);
    for (int i = 0; i < copyFaces.size(); i++) {
    	((Face)copyFaces.get(i)).transformNoNormals(viewportTransform);
    }

    g.setColor(Color.BLACK);

    for (int i = 0; i < copyFaces.size(); i++) {
      drawer.setColor(((Face)copyFaces.get(i)).color);
      int x1 = 0; 
      int x2 = 0; 
      int y1 = 0; 
      int y2 = 0;
      Face f = (Face)copyFaces.get(i);
      for (int k = 1; k < ((Face)copyFaces.get(i)).vertices.size(); k++) {
        x1 = (int)((Vertex)f.vertices.get(k - 1)).x;
        y1 = viewHeight - (int)((Vertex)f.vertices.get(k - 1)).y;
        x2 = (int)((Vertex)f.vertices.get(k)).x;
        y2 = viewHeight - (int)((Vertex)f.vertices.get(k)).y;
        g.drawLine(x1, y1, x2, y2);
      }

      if (f.vertices.size() > 0) {
        x1 = (int)((Vertex)f.vertices.get(0)).x;
        y1 = viewHeight - (int)((Vertex)f.vertices.get(0)).y;
        g.drawLine(x1, y1, x2, y2);
      }

    }

    return rendering;
  }

  public BufferedImage flatRender(int viewWidth, int viewHeight, Camera cam) {
    BufferedImage rendering = new BufferedImage(viewWidth, viewHeight, 1);
    Graphics g = rendering.getGraphics();
    Rasterizer drawer = new Rasterizer(rendering);
    g.setColor(this.background);
    g.fillRect(0, 0, viewWidth, viewHeight);

    ArrayList copyFaces = new ArrayList();
    for (int i = 0; i < this.meshes.size(); i++) {
      Mesh m = (Mesh)this.meshes.get(i);
      for (int j = 0; j < m.faces.size(); j++) {
        Face f = (Face)m.faces.get(j);
        Face c = f.copy();
        c.color = m.emissive;
        copyFaces.add(c);
      }

    }

    for (int i = 0; i < copyFaces.size(); i++) {
      Face f = (Face)copyFaces.get(i);
      f.color = this.shader.shade(f, f.faceNormal(), (Vertex)f.vertices.get(0));
    }

    FourByFour eyeTransform = cam.eyeTransform();
    for (int i = 0; i < copyFaces.size(); i++) 
    	((Face)copyFaces.get(i)).transformNoNormals(eyeTransform);

    double top = cam.near * Math.tan(Math.toRadians(cam.angle / 2.0D));
    double bottom = -top;
    double right = top * (viewWidth / viewHeight);
    double left = -right;

    double[] data = new double[16];
    data[0] = (2.0D * cam.near / (right - left));
    data[2] = ((right + left) / (right - left));
    data[5] = (2.0D * cam.near / (top - bottom));
    data[6] = ((top + bottom) / (top - bottom));
    data[10] = (-1.0D * (cam.far + cam.near) / (cam.far - cam.near));
    data[11] = (-2.0D * cam.far * cam.near / (cam.far - cam.near));
    data[14] = -1.0D;
    double tmp482_481 = (data[4] = data[7] = data[8] = data[9] = data[12] = data[13] = data[15] = 0.0D); data[3] = tmp482_481; data[1] = tmp482_481;
    FourByFour perspectiveTransform = new FourByFour(data);
    for (int i = 0; i < copyFaces.size(); i++) ((Face)copyFaces.get(i)).transformNoNormals(perspectiveTransform);

    for (int i = 0; i < copyFaces.size(); i++) ((Face)copyFaces.get(i)).clipCanonical();

    for (int i = 0; i < copyFaces.size(); i++) {
      ((Face)copyFaces.get(i)).perspectivDivide();
    }

    FourByFour viewportTransform = FourByFour.identity();
    viewportTransform.data[0] = ((viewWidth - 1.0D) / 2.0D);
    viewportTransform.data[3] = ((viewWidth - 1.0D) / 2.0D);
    viewportTransform.data[5] = ((viewHeight - 1.0D) / 2.0D);
    viewportTransform.data[7] = ((viewHeight + 2.0D) / 2.0D);
    for (int i = 0; i < copyFaces.size(); i++) ((Face)copyFaces.get(i)).transformNoNormals(viewportTransform);

    for (int i = 0; i < copyFaces.size(); i++) {
      Face f = (Face)copyFaces.get(i);
      for (int j = 0; j < f.vertices.size(); j++) {
        ((Vertex)f.vertices.get(j)).y = (viewHeight - ((Vertex)f.vertices.get(j)).y);
      }

    }

    for (int i = 0; i < copyFaces.size(); i++) {
      drawer.setColor(((Face)copyFaces.get(i)).color);
      Face f = (Face)copyFaces.get(i);
      drawer.flatPolygon(f.vertices, rendering);
    }
    return rendering;
  }

  public BufferedImage gouraudRender(int viewWidth, int viewHeight, Camera cam) {
    BufferedImage rendering = new BufferedImage(viewWidth, viewHeight, 1);
    Graphics g = rendering.getGraphics();
    Rasterizer drawer = new Rasterizer(rendering);
    g.setColor(this.background);
    g.fillRect(0, 0, viewWidth, viewHeight);

    ArrayList copyFaces = new ArrayList();
    for (int i = 0; i < this.meshes.size(); i++) {
      Mesh m = (Mesh)this.meshes.get(i);
      for (int j = 0; j < m.faces.size(); j++) {
        Face f = (Face)m.faces.get(j);
        Face c = f.copy();
        c.color = m.emissive;
        copyFaces.add(c);
      }

    }

    for (int i = 0; i < copyFaces.size(); i++) {
      Face f = (Face)copyFaces.get(i);
      for (int j = 0; j < f.vertices.size(); j++) {
        Vertex v = (Vertex)f.vertices.get(j);
        Color c = this.shader.shade(f, (Vertex)f.normals.get(j), v);
        v.r = c.getRed();
        v.g = c.getGreen();
        v.b = c.getBlue();
      }

    }

    FourByFour eyeTransform = cam.eyeTransform();
    for (int i = 0; i < copyFaces.size(); i++) ((Face)copyFaces.get(i)).transformNoNormals(eyeTransform);

    double top = cam.near * Math.tan(Math.toRadians(cam.angle / 2.0D));
    double bottom = -top;
    double right = top * (viewWidth / viewHeight);
    double left = -right;

    double[] data = new double[16];
    data[0] = (2.0D * cam.near / (right - left));
    data[2] = ((right + left) / (right - left));
    data[5] = (2.0D * cam.near / (top - bottom));
    data[6] = ((top + bottom) / (top - bottom));
    data[10] = (-1.0D * (cam.far + cam.near) / (cam.far - cam.near));
    data[11] = (-2.0D * cam.far * cam.near / (cam.far - cam.near));
    data[14] = -1.0D;
    double tmp547_546 = (data[4] = data[7] = data[8] = data[9] = data[12] = data[13] = data[15] = 0.0D); data[3] = tmp547_546; data[1] = tmp547_546;
    FourByFour perspectiveTransform = new FourByFour(data);
    for (int i = 0; i < copyFaces.size(); i++) ((Face)copyFaces.get(i)).transformNoNormals(perspectiveTransform);

    for (int i = 0; i < copyFaces.size(); i++) ((Face)copyFaces.get(i)).clipCanonical();

    for (int i = 0; i < copyFaces.size(); i++) {
      ((Face)copyFaces.get(i)).perspectivDivide();
    }

    FourByFour viewportTransform = FourByFour.identity();
    viewportTransform.data[0] = ((viewWidth - 1.0D) / 2.0D);
    viewportTransform.data[3] = ((viewWidth - 1.0D) / 2.0D);
    viewportTransform.data[5] = ((viewHeight - 1.0D) / 2.0D);
    viewportTransform.data[7] = ((viewHeight + 2.0D) / 2.0D);
    for (int i = 0; i < copyFaces.size(); i++) ((Face)copyFaces.get(i)).transformNoNormals(viewportTransform);

    for (int i = 0; i < copyFaces.size(); i++) {
      Face f = (Face)copyFaces.get(i);
      for (int j = 0; j < f.vertices.size(); j++) {
        ((Vertex)f.vertices.get(j)).y = (viewHeight - ((Vertex)f.vertices.get(j)).y);
      }

    }

    for (int i = 0; i < copyFaces.size(); i++) {
      Face f = (Face)copyFaces.get(i);
      drawer.gouraudPolygon(f.vertices, rendering, this.shader, f);
    }
    return rendering;
  }

  public BufferedImage phongRender(int viewWidth, int viewHeight, Camera cam) {
    BufferedImage rendering = new BufferedImage(viewWidth, viewHeight, 1);
    Graphics g = rendering.getGraphics();
    Rasterizer drawer = new Rasterizer(rendering);
    g.setColor(this.background);
    g.fillRect(0, 0, viewWidth, viewHeight);

    ArrayList copyFaces = new ArrayList();
    for (int i = 0; i < this.meshes.size(); i++) {
      Mesh m = (Mesh)this.meshes.get(i);
      for (int j = 0; j < m.faces.size(); j++) {
        Face f = (Face)m.faces.get(j);
        Face c = f.copy();
        c.color = m.emissive;
        copyFaces.add(c);
      }

    }

    for (int i = 0; i < copyFaces.size(); i++) {
      Face f = (Face)copyFaces.get(i);
      f.fn = f.faceNormal();
      f.fn.x *= -1.0D;
      for (int j = 0; j < f.vertices.size(); j++) {
        Vertex v = (Vertex)f.vertices.get(j);
        Vertex n = (Vertex)f.normals.get(j);
        v.normal = new Vertex(n.x, n.y, n.z, 0.0D);
        v.worldx = v.x; v.worldy = v.y; v.worldz = v.z;
      }

    }

    FourByFour eyeTransform = cam.eyeTransform();
    for (int i = 0; i < copyFaces.size(); i++) ((Face)copyFaces.get(i)).transformNoNormals(eyeTransform);

    double top = cam.near * Math.tan(Math.toRadians(cam.angle / 2.0D));
    double bottom = -top;
    double right = top * (viewWidth / viewHeight);
    double left = -right;

    double[] data = new double[16];
    data[0] = (2.0D * cam.near / (right - left));
    data[2] = ((right + left) / (right - left));
    data[5] = (2.0D * cam.near / (top - bottom));
    data[6] = ((top + bottom) / (top - bottom));
    data[10] = (-1.0D * (cam.far + cam.near) / (cam.far - cam.near));
    data[11] = (-2.0D * cam.far * cam.near / (cam.far - cam.near));
    data[14] = -1.0D;
    double tmp587_586 = (data[4] = data[7] = data[8] = data[9] = data[12] = data[13] = data[15] = 0.0D); data[3] = tmp587_586; data[1] = tmp587_586;
    FourByFour perspectiveTransform = new FourByFour(data);
    for (int i = 0; i < copyFaces.size(); i++) ((Face)copyFaces.get(i)).transformNoNormals(perspectiveTransform);

    for (int i = 0; i < copyFaces.size(); i++) ((Face)copyFaces.get(i)).clipCanonical();

    for (int i = 0; i < copyFaces.size(); i++) {
      ((Face)copyFaces.get(i)).perspectivDivide();
    }

    FourByFour viewportTransform = FourByFour.identity();
    viewportTransform.data[0] = ((viewWidth - 1.0D) / 2.0D);
    viewportTransform.data[3] = ((viewWidth - 1.0D) / 2.0D);
    viewportTransform.data[5] = ((viewHeight - 1.0D) / 2.0D);
    viewportTransform.data[7] = ((viewHeight + 2.0D) / 2.0D);
    for (int i = 0; i < copyFaces.size(); i++) ((Face)copyFaces.get(i)).transformNoNormals(viewportTransform);

    for (int i = 0; i < copyFaces.size(); i++) {
      Face f = (Face)copyFaces.get(i);
      for (int j = 0; j < f.vertices.size(); j++) {
        ((Vertex)f.vertices.get(j)).y = (viewHeight - ((Vertex)f.vertices.get(j)).y);
      }

    }

    for (int i = 0; i < copyFaces.size(); i++) {
      Face f = (Face)copyFaces.get(i);
      drawer.phongPolygon(f.vertices, rendering, this.shader, f, cam);
    }
    return rendering;
  }
}