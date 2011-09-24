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
	public void test() {
		NormUtil norm = new NormUtil(0, 1, -1, +1);

		Assert.assertEquals(-1.0, norm.normalize(0.0));
		Assert.assertEquals(+1.0, norm.normalize(1.0));

		Assert.assertEquals(0.0, norm.denormalize(-1.0));
		Assert.assertEquals(1.0, norm.denormalize(+1.0));
	}

	@Test
	public void testDataSet() {
		DataPairSet trainSet = new DataPairSet();
		trainSet.addPair(new DataPair(new Double[] { -1.0, 0.0 }, new Double[] { 5.0 }));
		trainSet.addPair(new DataPair(new Double[] { -1.0, 10.0 }, new Double[] { 6.0 }));
		trainSet.addPair(new DataPair(new Double[] { 0.0, 0.0 }, new Double[] { 6.0 }));
		trainSet.addPair(new DataPair(new Double[] { 0.0, 10.0 }, new Double[] { 5.0 }));

		DataPairSet normalizedDataSet = NormUtil.normalize(trainSet, 0, +1);
		System.out.println(normalizedDataSet);
	}

}
