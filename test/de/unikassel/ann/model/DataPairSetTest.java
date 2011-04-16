package de.unikassel.ann.model;

import java.util.ArrayList;
import java.util.List;

import junit.framework.Assert;

import org.junit.Test;

public class DataPairSetTest {

	Double[][] input = new Double[][]{{1d,2d,3d},{4d,5d,6d},{7d,8d,9d}};
	Double[][] output = new Double[][]{{11d,22d},{33d,44d},{55d,66d}};
	
	Double[][] inputAdd = new Double[][]{{123d,234d,345d},{456d,567d,678d},{789d,890d,999d}};
	Double[][] outputAdd = new Double[][]{{111d,222d},{333d,444d},{555d,666d}};
	
	@Test
	public void testAddingRows() {
		DataPairSet dataSet = new DataPairSet(input, output);
		Assert.assertEquals(new Integer(3), dataSet.getRows());
		
		dataSet.addRows(inputAdd, outputAdd);
		Assert.assertEquals(new Integer(6), dataSet.getRows());
		
		dataSet.addRow(new Double[]{12d,34d,45d}, new Double[]{787d,898d});
		Assert.assertEquals(new Integer(7), dataSet.getRows());
		
		List<Double> inList = new ArrayList<Double>();
		inList.add(66d);
		inList.add(77d);
		inList.add(88d);
		List<Double> outList = new ArrayList<Double>();
		outList.add(100d);
		outList.add(900d);
		dataSet.addRow(inList, outList);
		Assert.assertEquals(new Integer(8), dataSet.getRows());
		
	}
}
