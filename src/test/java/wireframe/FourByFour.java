package wireframe;

import java.io.PrintStream;

public class FourByFour {
	public double[] data;

	public FourByFour() {
		this.data = new double[16];
	}

	public FourByFour(double[] matrix) {
		this.data = matrix;
	}

	public static FourByFour identity() {
		double[] a = new double[16];
		for (int i = 0; i < 16; i++)
			a[i] = 0.0D;
		double tmp38_37 = (a[10] = a[15] = 1.0D);
		a[5] = tmp38_37;
		a[0] = tmp38_37;
		return new FourByFour(a);
	}

	public void print() {
		for (int i = 0; i < 16; i++) {
			System.out.print(this.data[i] + "\t");
			if ((i + 1) % 4 != 0)
				continue;
			System.out.println();
		}
	}

	public FourByFour inverse() {
		double[][] old = new double[4][];
		double[][] inv = new double[4][];
		for (int i = 0; i < 4; i++) {
			old[i] = new double[4];
			inv[i] = new double[4];
			for (int j = 0; j < 4; j++) {
				old[i][j] = this.data[(i * 4 + j)];
				inv[i][j] = 0.0D;
				if (i != j)
					continue;
				inv[i][j] = 1.0D;
			}
		}
		for (int i = 0; i < 4; i++) {
			double scale = old[i][i];
			for (int j = 0; j < 4; j++) {
				old[i][j] /= scale;
				inv[i][j] /= scale;
			}

			for (int j = 0; j < 4; j++)
				if (i != j) {
					scale = old[j][i];
					for (int k = 0; k < 4; k++) {
						old[j][k] -= scale * old[i][k];
						inv[j][k] -= scale * inv[i][k];
					}
				}
		}
		double[] d = new double[16];
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				d[(i * 4 + j)] = inv[i][j];
		return new FourByFour(d);
	}

	public FourByFour transpose() {
		double[] trans = new double[16];
		trans[0] = this.data[0];
		trans[1] = this.data[4];
		trans[2] = this.data[8];
		trans[3] = this.data[12];
		trans[4] = this.data[1];
		trans[5] = this.data[5];
		trans[6] = this.data[9];
		trans[7] = this.data[13];
		trans[8] = this.data[2];
		trans[9] = this.data[6];
		trans[10] = this.data[10];
		trans[11] = this.data[14];
		trans[12] = this.data[3];
		trans[13] = this.data[7];
		trans[14] = this.data[11];
		trans[15] = this.data[15];
		return new FourByFour(trans);
	}

	public static Vertex multiply(FourByFour left, Vertex right) {
		double[] a = left.data;
		double x = a[0] * right.x + a[1] * right.y + a[2] * right.z + a[3] * right.w;
		double y = a[4] * right.x + a[5] * right.y + a[6] * right.z + a[7] * right.w;
		double z = a[8] * right.x + a[9] * right.y + a[10] * right.z + a[11] * right.w;
		double w = a[12] * right.x + a[13] * right.y + a[14] * right.z + a[15] * right.w;
		Vertex v = new Vertex(x, y, z, w);
		v.r = right.r;
		v.g = right.g;
		v.b = right.b;
		v.normal = right.normal;
		return v;
	}

	public static FourByFour multiply(FourByFour left, FourByFour right) {
		double[] a = left.data;
		double[] b = right.data;
		double[] c = new double[16];
		for (int i = 0; i < 16; i++) {
			int astart = i / 4 * 4;
			int bstart = i % 4;
			c[i] = 0.0D;
			for (int j = 0; j < 4; j++)
				c[i] += a[(astart + j)] * b[(bstart + j * 4)];
		}
		return new FourByFour(c);
	}
}