/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.normalize;

import junit.framework.Assert;

import org.junit.Test;

import de.unikassel.ann.model.DataPair;
import de.unikassel.ann.model.DataPairSet;

/**
 * @author anton
 * 
 */
public class NormUtilTest {

	@Test
	public void testBrainFuck() {
		// positive
		double max = Math.max(6.0, Double.MIN_VALUE);
		double min = Math.min(6.0, Double.MAX_VALUE);
		System.out.println(max + " / " + min);

		// negative
		max = Math.max(-11.0, -Double.MAX_VALUE);
		min = Math.min(-1.0, Double.MAX_VALUE);
		System.out.println(max + " / " + min);
	}

	@Test
	public void testNegative() {
		NormUtil norm = new NormUtil(-11, -1, -1, +1);
		Assert.assertEquals(-1.0, norm.normalize(-11.0));
		Assert.assertEquals(0.0, norm.normalize(-6.0));
		Assert.assertEquals(1.0, norm.normalize(-1.0));
	}

	@Test
	public void test() {
		NormUtil norm = new NormUtil(0, 1, -1, +1);

		Assert.assertEquals(-1.0, norm.normalize(0.0));
		Assert.assertEquals(+1.0, norm.normalize(1.0));

		Assert.assertEquals(0.0, norm.denormalize(-1.0), 0.000001);
		Assert.assertEquals(1.0, norm.denormalize(+1.0));
	}

	@Test
	public void testEnercast() {

		NormUtil norm = new NormUtil(0, 2000, -1, +1);

		double low = 0.0;
		double quarter = 500.0;
		double middle = 1000.0;
		double high = 2000.0;

		Assert.assertEquals(-1.0, norm.normalize(low));
		Assert.assertEquals(-0.5, norm.normalize(quarter));
		Assert.assertEquals(0.0, norm.normalize(middle));
		Assert.assertEquals(+1.0, norm.normalize(high));

		Assert.assertEquals(low, norm.denormalize(norm.normalize(low)), 0.000001);
		Assert.assertEquals(quarter, norm.denormalize(norm.normalize(quarter)), 0.000001);
		Assert.assertEquals(middle, norm.denormalize(norm.normalize(middle)), 0.000001);
		Assert.assertEquals(high, norm.denormalize(norm.normalize(high)), 0.000001);
	}

	@Test
	public void testDataSet() {
		DataPairSet trainSet = new DataPairSet();
		trainSet.addPair(new DataPair(new Double[] { -1.0, 0.0 }, new Double[] { 5.0 }));
		trainSet.addPair(new DataPair(new Double[] { -1.0, 10.0 }, new Double[] { 6.0 }));
		trainSet.addPair(new DataPair(new Double[] { 0.0, 0.0 }, new Double[] { 6.0 }));
		trainSet.addPair(new DataPair(new Double[] { 0.0, 10.0 }, new Double[] { 5.0 }));

		/**
		 * <pre>
		 * should be xor 
		 * 0 0 -> 0 
		 * 0 1 -> 1 
		 * 1 0 -> 1 
		 * 1 1 -> 0
		 * </pre>
		 */
		DataPairSet actual = NormUtil.normalize(trainSet, 0, +1);
		System.out.println(actual);

		DataPairSet expected = new DataPairSet();
		expected.addPair(new DataPair(new Double[] { 0.0, 0.0 }, new Double[] { 0.0 }));
		expected.addPair(new DataPair(new Double[] { 0.0, 1.0 }, new Double[] { 1.0 }));
		expected.addPair(new DataPair(new Double[] { 1.0, 0.0 }, new Double[] { 1.0 }));
		expected.addPair(new DataPair(new Double[] { 1.0, 1.0 }, new Double[] { 0.0 }));

		Assert.assertEquals(expected, actual);
	}

}
