package org.generation5;

import com.sun.org.apache.xpath.internal.compiler.PsuedoNames;

import de.unikassel.mdda.MDDA;

class Kohonen {
	private int width = -1; // output n (n x m)
	private int height = -1; // output m (n x m)
	 public int inputSize = -1; // input
//	private double[][][] weights;
	private MDDA<Double> weights;

	public void setDimensions(int dimensionSize, int width, int height) {
		 this.inputSize = dimensionSize;
		this.width = width;
		this.height = height;

//		this.weights = new double[dimensionSize][width][height];
		weights = new MDDA<Double>(dimensionSize,width,height);
	}

	public double getWeight(int input, int width, int height) {
//		return this.weights[input][width][height];
		return weights.get(input, width, height);
	}

	public boolean init() {
		if ((this.width == -1) || (this.height == -1))
			return false;

		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
				for (int inp=0; inp<inputSize; inp++) {
					weights.set((Math.random() - 0.5), inp, i, j);
				}
				
//				this.weights[0][i][j] = (Math.random() - 0.5D); // -0.5 / +0.5
//				this.weights[1][i][j] = (Math.random() - 0.5D); // -0.5 / +0.5
			}
		}

		return true;
	}

	public void run(double factor, int neighborRadius, double... input) {
		if (neighborRadius > 1) {
			System.out.println(neighborRadius);
		}
		if (neighborRadius < 1) {
			neighborRadius = 1;
		}

		int x = 0;
		int y = 0;
		double min = 0.0D;
		double max = 9999999.0D;

		for (int w = 0; w < this.width; w++) {
			for (int h = 0; h < this.height; h++) {
				for (int inp=0; inp<inputSize; inp++) {
					min += Math.pow(input[inp] - weights.get(inp,w,h), 2.0D);
				}
				
//				min += Math.pow(inp1 - this.weights[0][w][h], 2.0D);
//				min += Math.pow(inp2 - this.weights[1][w][h], 2.0D);

				// für jedes output neuron, ermittele gewinner neuron
				// und speichere indizes (x,y)
				if (min < max) {
					x = w;
					y = h;
					max = min;
				}
				min = 0.0D;
			}

		}

		// gewichte updaten mit factor
		// offset gibt nachbarschafts radius an
		for (int m = x - neighborRadius; m <= x + neighborRadius; m++)
			for (int n = y - neighborRadius; n <= y + neighborRadius; n++) {
				if ((m < 0) || (m >= this.width) || (n < 0)
						|| (n >= this.height)) {
					continue;
				}

				for (int inp=0; inp<inputSize; inp++) {
					Double tmp = weights.get(inp,m,n);
					weights.set(tmp+(factor * (input[inp]-tmp)), inp,m,n);
				}
//				this.weights[0][m][n] += factor	* (inp1 - this.weights[0][m][n]);
//				this.weights[1][m][n] += factor	* (inp2 - this.weights[1][m][n]);
			}
	}
}