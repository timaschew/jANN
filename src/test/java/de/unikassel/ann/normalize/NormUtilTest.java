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

}
