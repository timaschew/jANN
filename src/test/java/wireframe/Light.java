package wireframe;

public class Light {
	public Vertex location;
	public float r;
	public float g;
	public float b;

	Light(double x, double y, double z, float red, float green, float blue) {
		this.location = new Vertex(x, y, z, 0.0D);
		this.r = red;
		this.g = green;
		this.b = blue;
	}
}