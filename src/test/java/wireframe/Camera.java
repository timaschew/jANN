package wireframe;

public class Camera
{
  public Vertex eye;
  public Vertex look;
  public Vertex up;
  public Vertex u;
  public Vertex v;
  public Vertex n;
  double angle;
  double near;
  double far;

  public Camera()
  {
  }

  public Camera(Vertex eyePosition, Vertex lookPoint, double viewAngle, double nearPlane, double farPlane)
  {
    this.near = nearPlane;
    this.far = farPlane;
    this.angle = viewAngle;
    this.eye = eyePosition;
    this.look = lookPoint;
    this.up = new Vertex(0.0D, 1.0D, 0.0D, 0.0D);
    this.n = Vertex.subtract(this.eye, this.look).normalize();
    this.u = Vertex.crossProduct(this.up, this.n).normalize();
    this.v = Vertex.crossProduct(this.n, this.u).normalize();
  }

	public FourByFour eyeTransform() {
		Vertex v1 = new Vertex(0.0D, 0.0D, 0.0D, 0.0D);
		Vertex v2 = new Vertex(this.eye.x, this.eye.y, this.eye.z, 0.0D);
    Vertex negativeEye = Vertex.subtract(v1, v2);
    double[] data = new double[16];
    data[0] = this.u.x; 
    data[1] = this.u.y; 
    data[2] = this.u.z; 
    data[3] = Vertex.dotProduct(negativeEye, this.u);
    data[4] = this.v.x; 
    data[5] = this.v.y; 
    data[6] = this.v.z; 
    data[7] = Vertex.dotProduct(negativeEye, this.v);
    data[8] = this.n.x; 
    data[9] = this.n.y; 
    data[10] = this.n.z; 
    data[11] = Vertex.dotProduct(negativeEye, this.n);
    data[12] = 0.0D; 
    data[13] = 0.0D; 
    data[14] = 0.0D; 
    data[15] = 0.0D;
    return new FourByFour(data);
  }
  public void slide(double deltaU, double deltaV, double deltaN) {
    this.eye.x += deltaU * this.u.x + deltaV * this.v.x + deltaN * this.n.x;
    this.eye.y += deltaU * this.u.y + deltaV * this.v.y + deltaN * this.n.y;
    this.eye.z += deltaU * this.u.z + deltaV * this.v.z + deltaN * this.n.z;
  }
  public void move(double deltaU, double deltaV, double deltaN) {
    this.eye.x += deltaU * this.u.x + deltaV * this.v.x + deltaN * this.n.x;
    this.eye.y += deltaU * this.u.y + deltaV * this.v.y + deltaN * this.n.y;
    this.eye.z += deltaU * this.u.z + deltaV * this.v.z + deltaN * this.n.z;
    this.up = this.v.normalize();
    this.n = Vertex.subtract(this.eye, this.look).normalize();
    this.u = Vertex.crossProduct(this.up, this.n).normalize();
    this.v = Vertex.crossProduct(this.n, this.u).normalize();
  }
  public void pitch(double theta) {
    double cos = Math.cos(Math.toRadians(theta));
    double sin = Math.sin(Math.toRadians(theta));
    Vertex t = new Vertex(this.n.x, this.n.y, this.n.z, this.n.w);
    this.n.x = (cos * t.x - sin * this.v.x);
    this.n.y = (cos * t.y - sin * this.v.y);
    this.n.z = (cos * t.z - sin * this.v.z);
    this.v.x = (sin * t.x + cos * this.v.x);
    this.v.y = (sin * t.y + cos * this.v.y);
    this.v.z = (sin * t.z + cos * this.v.z);
  }
  public void roll(double theta) {
    double cos = Math.cos(Math.toRadians(theta));
    double sin = Math.sin(Math.toRadians(theta));
    Vertex t = new Vertex(this.u.x, this.u.y, this.u.z, this.u.w);
    this.u.x = (cos * t.x - sin * this.v.x);
    this.u.y = (cos * t.y - sin * this.v.y);
    this.u.z = (cos * t.z - sin * this.v.z);
    this.v.x = (sin * t.x + cos * this.v.x);
    this.v.y = (sin * t.y + cos * this.v.y);
    this.v.z = (sin * t.z + cos * this.v.z);
  }

  public void yaw(double theta) {
    double cos = Math.cos(Math.toRadians(theta));
    double sin = Math.sin(Math.toRadians(theta));
    Vertex t = new Vertex(this.u.x, this.u.y, this.u.z, this.u.w);
    this.u.x = (cos * t.x - sin * this.n.x);
    this.u.y = (cos * t.y - sin * this.n.y);
    this.u.z = (cos * t.z - sin * this.n.z);
    this.n.x = (sin * t.x + cos * this.n.x);
    this.n.y = (sin * t.y + cos * this.n.y);
    this.n.z = (sin * t.z + cos * this.n.z);
  }
}