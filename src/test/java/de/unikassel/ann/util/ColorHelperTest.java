/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.util;

import java.awt.Color;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author anton
 * 
 */
public class ColorHelperTest {

	@Test
	public void test() {
		Assert.assertNotNull(ColorHelper.numberToColor(0));
		Assert.assertNotNull(ColorHelper.numberToColor(50));
		Assert.assertNotNull(ColorHelper.numberToColor(100));

		Assert.assertNull(ColorHelper.numberToColor(-1));
		Assert.assertNull(ColorHelper.numberToColor(101));

		Assert.assertNotNull(ColorHelper.numberToColorPercentage(0.0));
		Assert.assertNotNull(ColorHelper.numberToColorPercentage(0.5));
		Assert.assertNotNull(ColorHelper.numberToColorPercentage(1.0));

		Assert.assertNull(ColorHelper.numberToColorPercentage(-0.01));
		Assert.assertNull(ColorHelper.numberToColorPercentage(1.01));
	}

	@Test
	public void creatHTML() {
		StringBuilder sb = new StringBuilder();
		sb.append("<table>");
		sb.append("<tr>");

		for (int i = 0; i <= 100; i++) {
			Color c = ColorHelper.numberToColor(i);
			if (c == null) {
				System.err.println("color null for " + i);
			}
			// rgb(0,0,0)
			sb.append("<td style=\"background-color:rgb(");
			sb.append(c.getRed()).append(",");
			sb.append(c.getGreen()).append(",");
			sb.append(c.getBlue());
			sb.append(")\">");
			sb.append("&nbsp;</td>");
		}
		sb.append("</tr>");
		sb.append("</table>");
		System.out.println(sb.toString());

	}
}
