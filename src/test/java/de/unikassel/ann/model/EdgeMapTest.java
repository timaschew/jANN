/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.model;

import java.util.Map;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author anton
 * 
 */
public class EdgeMapTest {

	@Test
	public void test() {
		EdgeMap<Double> edgeMap = new EdgeMap<Double>();

		edgeMap.put(new FromTo(0, 10), 1.23);
		edgeMap.put(new FromTo(1, 11), 4.56);
		edgeMap.put(new FromTo(2, 10), 7.89);
		edgeMap.put(new FromTo(0, 12), 0.01);

		Set<Integer> fromSet = edgeMap.getFromForTo(10);
		Assert.assertTrue(fromSet.contains(new Integer(0)));
		Assert.assertTrue(fromSet.contains(new Integer(2)));

		Set<Integer> toSet = edgeMap.getToForFrom(0);
		Assert.assertTrue(toSet.contains(new Integer(10)));
		Assert.assertTrue(toSet.contains(new Integer(12)));

		Map<FromTo, Double> fromToMap1 = edgeMap.getSynapsesFromToForFrom(0);
		Assert.assertTrue(fromToMap1.containsKey(new FromTo(0, 10)));
		Assert.assertTrue(fromToMap1.containsKey(new FromTo(0, 12)));
		Assert.assertEquals(fromToMap1.get(new FromTo(0, 10)), 1.23);
		Assert.assertEquals(fromToMap1.get(new FromTo(0, 12)), 0.01);

		Map<FromTo, Double> fromToMap2 = edgeMap.getSynapsesFromToForTo(10);
		Assert.assertTrue(fromToMap2.containsKey(new FromTo(0, 10)));
		Assert.assertTrue(fromToMap2.containsKey(new FromTo(2, 10)));
		Assert.assertEquals(fromToMap2.get(new FromTo(0, 10)), 1.23);
		Assert.assertEquals(fromToMap2.get(new FromTo(2, 10)), 7.89);

	}

}
