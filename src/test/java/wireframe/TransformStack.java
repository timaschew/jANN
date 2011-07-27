package wireframe;

import java.util.Stack;

public class TransformStack
{
  private Stack transforms;

  public TransformStack()
  {
    this.transforms = new Stack();
    this.transforms.push(FourByFour.identity());
  }
  public void pop() { this.transforms.pop(); } 
  public FourByFour CurrentTransform() { return (FourByFour)this.transforms.peek(); } 
  public void push() {
    double[] a = (double[])CurrentTransform().data.clone();
    this.transforms.push(new FourByFour(a));
  }
  public void scale(double x, double y, double z) {
    FourByFour scaling = FourByFour.identity();
    scaling.data[0] = x;
    scaling.data[5] = y;
    scaling.data[10] = z;
    transform(scaling);
  }
  public void translate(double x, double y, double z) {
    FourByFour translation = FourByFour.identity();
    translation.data[3] = x;
    translation.data[7] = y;
    translation.data[11] = z;
    transform(translation);
  }
  public void rotate(double theta, double x, double y, double z) {
    Vertex u = new Vertex(x, y, z, 0.0D).normalize();
    x = u.x; y = u.y; z = u.z;
    FourByFour rotation = FourByFour.identity();
    double s = Math.sin(Math.toRadians(theta));
    double c = Math.cos(Math.toRadians(theta));
    rotation.data[0] = (c + (1.0D - c) * x * x);
    rotation.data[1] = ((1.0D - c) * y * x - s * z);
    rotation.data[2] = ((1.0D - c) * z * s * y);
    rotation.data[4] = ((1.0D - c) * x * y + s * z);
    rotation.data[5] = (c + (1.0D - c) * y * y);
    rotation.data[6] = ((1.0D - c) * z * y - s * x);
    rotation.data[8] = ((1.0D - c) * x * z - s * y);
    rotation.data[9] = ((1.0D - c) * y * z + s * x);
    rotation.data[10] = (c + (1.0D - c) * z * z);
    transform(rotation);
  }
  public void transform(FourByFour transformation) {
    FourByFour current = (FourByFour)this.transforms.pop();
    this.transforms.push(FourByFour.multiply(current, transformation));
  }
}