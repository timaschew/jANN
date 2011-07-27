package wireframe;

public class Vertex {
	public double x;
	public double y;
	public double z;
	public double w;
	public double worldx;
	public double worldy;
	public double worldz;
	public Vertex normal;
	public float r;
	public float g;
	public float b;

	public Vertex(double a, double b, double c, double d) {
		this.worldx = (this.x = a);
		this.worldy = (this.y = b);
		this.worldz = (this.z = c);
		this.w = d;
	}

	public Vertex normalize() {
		this.r = 0.0F;
		this.g = 0.0F;
		this.b = 0.0F;
		double normalizer = Math.sqrt(this.x * this.x + this.y * this.y	+ this.z * this.z);
		this.x /= normalizer;
		this.y /= normalizer;
		this.z /= normalizer;
		return this;
	}

	public double length() {
		return Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
	}

	public Vertex scalarMultiply(double s) {
		this.x *= s;
		this.y *= s;
		this.z *= s;
		this.w *= s;
		return this;
	}

	public void perspectiveDivide() {
		x = x / w;
		y = y / w;
		z = z / w;
		w = 1.0D;
		// this.x /= this.w;
		// this.y /= this.w;
		// this.z /= this.w;
		// this.w = 1.0D;
	}

	public static double dotProduct(Vertex a, Vertex b) {
		return a.x * b.x + a.y * b.y + a.z * b.z;
	}

	public static Vertex crossProduct(Vertex a, Vertex b) {
		return new Vertex(a.y * b.z - b.y * a.z, 
				-(a.x * b.z - b.x * a.z), 
				a.x	* b.y - b.x * a.y, 
				0.0D);
	}

	public static Vertex subtract(Vertex a, Vertex b) {
		return new Vertex(a.x - b.x, a.y - b.y, a.z - b.z, a.w - b.w);
	}

	public static Vertex add(Vertex a, Vertex b) {
		return new Vertex(a.x + b.x, a.y + b.y, a.z + b.z, a.w + b.w);
	}

	public static Vertex interpolate(Vertex a, Vertex b, double t) {
		Vertex v = new Vertex(a.x + (b.x - a.x) * t, 
				a.y + (b.y - a.y) * t, 
				a.z + (b.z - a.z) * t, 
				a.w + (b.w - a.w) * t);
		if ((a.normal != null) && (b.normal != null))
			v.normal = new Vertex(a.normal.x + (b.normal.x - a.normal.x) * t,
					a.normal.y + (b.normal.y - a.normal.y) * t, 
					a.normal.z + (b.normal.z - a.normal.z) * t, 
					a.normal.w + (b.normal.w - a.normal.w) * t);
		a.r += (b.r - a.r) * (float) t;
		a.g += (b.g - a.g) * (float) t;
		a.b += (b.b - a.b) * (float) t;
		a.worldx += (b.worldx - a.worldx) * t;
		a.worldy += (b.worldy - a.worldy) * t;
		a.worldz += (b.worldz - a.worldz) * t;
		return v;
	}
}