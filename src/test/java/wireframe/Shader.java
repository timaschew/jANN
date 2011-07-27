package wireframe;

import java.awt.Color;
import java.util.ArrayList;

public class Shader
{
  public ArrayList lights = new ArrayList();

  public Color shade(Face f, Vertex normal, Vertex worldCoords) { normal.normalize();
    if (this.lights.isEmpty()) return Color.BLACK;
    Mesh m = f.owner;
    float iR = 0.0F; float iG = 0.0F; float iB = 0.0F;
    for (int j = 0; j < this.lights.size(); j++) {
      Light l = (Light)this.lights.get(j);
      Vertex lightVector = Vertex.subtract(l.location, worldCoords).normalize();
      double lightDotNormal = Vertex.dotProduct(lightVector, normal);
      iR += l.r * m.diffuseR * Math.max((float)(lightDotNormal / lightVector.length()), 0.0F);
      iR += l.r * m.ambientR;
      iG += l.g * m.diffuseG * Math.max((float)(lightDotNormal / lightVector.length()), 0.0F);
      iG += l.g * m.ambientG;
      iB += l.b * m.diffuseB * Math.max((float)(lightDotNormal / lightVector.length()), 0.0F);
      iB += l.b * m.ambientB;
    }
    return new Color(Math.min(iR, 1.0F), Math.min(iG, 1.0F), Math.min(iB, 1.0F)); }

  public Color shade(Face f, Vertex normal, Camera cam, Vertex worldCoords)
  {
    normal.normalize();
    if (this.lights.isEmpty()) return Color.BLACK;
    Mesh m = f.owner;
    float iR = 0.0F; float iG = 0.0F; float iB = 0.0F;
    for (int j = 0; j < this.lights.size(); j++) {
      Light l = (Light)this.lights.get(j);
      Vertex lightVector = Vertex.subtract(l.location, worldCoords).normalize();
      double lightDotNormal = Vertex.dotProduct(lightVector, normal);
      Vertex h = Vertex.add(lightVector, Vertex.subtract(cam.eye, worldCoords)).normalize();
      double ph = Math.pow(Vertex.dotProduct(h, normal), m.specularExp);
      if (Double.isNaN(ph)) ph = 0.0D;
      iR += l.r * m.diffuseR * Math.max((float)lightDotNormal, 0.0F);
      iR += l.r * m.ambientR;
      iR += l.r * m.specularR * (float)Math.max(ph, 0.0D);
      iG += l.g * m.diffuseG * Math.max((float)lightDotNormal, 0.0F);
      iG += l.g * m.ambientG;
      iG += Math.max(0.0F, l.g * m.specularG * (float)Math.max(ph, 0.0D));
      iB += l.b * m.diffuseB * Math.max((float)lightDotNormal, 0.0F);
      iB += l.b * m.ambientB;
      iB += Math.max(0.0F, l.b * m.specularB * (float)Math.max(ph, 0.0D));
    }
    return new Color(Math.min(iR, 1.0F), Math.min(iG, 1.0F), Math.min(iB, 1.0F));
  }
}