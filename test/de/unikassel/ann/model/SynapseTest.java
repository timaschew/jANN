package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.factory.NetworkFactory;
import de.unikassel.ann.model.func.SigmoidFunction;

public class SynapseTest {

	@Test
	public void randomTest() {
		Neuron n = new Neuron(null, false);
		List<Double> list = new ArrayList<Double>();
		for (int i = 0; i < 20; i++) {
			Synapse s = new Synapse(n, n);
			list.add(s.getWeight());
		}

		Collections.sort(list);
		for (Double d : list) {
			System.out.println(d);
		}
	}

	@Test
	public void synapseAsMatrixTest() {

		NetConfig netConfig = NetworkFactory.createSimpleNet(2,
				new int[] { 2 }, 1, true, new SigmoidFunction());
		Network net = netConfig.getNetwork();

		System.out.println("synapse matrix before (random)");
		System.out.println(buildMatrix(net.getSynapseBigMatrix()));

		// new matrix
		Double[][][][] x = new Double[3][3][3][3];
		x[0][0][1][1] = 1.0;
		x[0][0][1][2] = 2.0;
		x[0][1][1][1] = 3.0;
		x[0][1][1][2] = 4.0;
		x[0][2][1][1] = 5.0;
		x[0][2][1][2] = 6.0;
		x[1][0][2][0] = 7.0;
		x[1][1][2][0] = 8.0;
		x[1][2][2][0] = 9.0;

		net.setSynapseBigMatrix(x);

		System.out.println("synapse matrix after");
		System.out.println(buildMatrix(net.getSynapseBigMatrix()));

	}

	private String buildMatrix(Double[][][][] m) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < m.length; i++) {
			for (int j = 0; j < m[i].length; j++) {
				for (int k = 0; k < m[i][j].length; k++) {
					for (int l = 0; l < m[i][j][k].length; l++) {
						if (m[i][j][k][l] != null) {
							sb.append("[");
							sb.append(i);
							sb.append("]");
							sb.append("[");
							sb.append(j);
							sb.append("]");
							sb.append("[");
							sb.append(k);
							sb.append("]");
							sb.append("[");
							sb.append(l);
							sb.append("]");
							sb.append(" = ");
							sb.append(m[i][j][k][l]);
							sb.append("\n");
						}
					}
				}
			}
		}
		return sb.toString();
	}

}
