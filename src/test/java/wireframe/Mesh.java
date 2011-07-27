package wireframe;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.io.StringWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.StringTokenizer;

public class Mesh
{
  public Color emissive;
  public ArrayList faces;
  public float ambientR;
  public float ambientG;
  public float ambientB;
  public float diffuseR;
  public float diffuseG;
  public float diffuseB;
  public float specularR;
  public float specularG;
  public float specularB;
  public float specularExp;

  public Mesh()
  {
  }

  public Mesh(String fileName, Color emissiveColor, float ambientRed, float ambientGreen, float ambientBlue, float diffuseRed, float diffuseGreen, float diffuseBlue, float specularRed, float specularGreen, float specularBlue, float specularExponent, boolean isApplet)
  {
    this.emissive = emissiveColor;
    this.ambientR = ambientRed; this.ambientG = ambientGreen; this.ambientB = ambientBlue;
    this.diffuseR = diffuseRed; this.diffuseG = diffuseGreen; this.diffuseB = diffuseBlue;
    this.specularR = specularRed; this.specularG = specularGreen; this.specularB = specularBlue; this.specularExp = specularExponent;
    this.faces = new ArrayList();
    System.out.println("Reading " + fileName);
    try
    {
      BufferedReader in = null;
      URL url = null;
      URLConnection urlConn = null;
      if (isApplet) {
    	  File f = new File(fileName);
        url = f.toURL();
        urlConn = url.openConnection();
        urlConn.setDoInput(true);
        urlConn.setUseCaches(false);
        in = new BufferedReader(new InputStreamReader(urlConn.getInputStream()));
      } else {
        in = new BufferedReader(new FileReader(fileName));
      }
      StringWriter sw = new StringWriter();
      int c = in.read();

      for (; c >= 0; c = in.read()) sw.write(c);
      in.close();
      StringTokenizer st = new StringTokenizer(sw.toString());
      int numVertices = Integer.parseInt(st.nextToken());
      int numNormals = Integer.parseInt(st.nextToken());
      int numFaces = Integer.parseInt(st.nextToken());
      Vertex[] vertices = new Vertex[numVertices];
      Vertex[] normals = new Vertex[numNormals];
      for (int i = 0; i < numVertices; i++) {
        vertices[i] = new Vertex(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()), 1.0D);
      }
      for (int i = 0; i < numNormals; i++) {
        normals[i] = new Vertex(Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()), Double.parseDouble(st.nextToken()), 0.0D);
      }
      for (int i = 0; i < numFaces; i++) {
        Face currentFace = new Face(this);
        int numCorners = Integer.parseInt(st.nextToken());
        for (int j = 0; j < numCorners; j++) {
          int vertexNumber = Integer.parseInt(st.nextToken());
          double x = vertices[vertexNumber].x;
          double y = vertices[vertexNumber].y;
          double z = vertices[vertexNumber].z;
          currentFace.addVertex(x, y, z, 1.0D);
        }
        for (int j = 0; j < numCorners; j++) {
          int normalNumber = Integer.parseInt(st.nextToken());
          double x = normals[normalNumber].x;
          double y = normals[normalNumber].y;
          double z = normals[normalNumber].z;
          currentFace.addNormal(x, y, z, 0.0D);
        }
        this.faces.add(currentFace);
      }
    } catch (Exception e) {
      System.out.println("Mesh exception!");
      System.out.println(e.getMessage());
      e.printStackTrace();
    }
  }

  public void transform(FourByFour transformation) {
    for (int i = 0; i < this.faces.size(); i++) ((Face)this.faces.get(i)).transform(transformation);
  }
}