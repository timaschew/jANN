/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.gui;

import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;

import org.junit.Test;

/**
 * @author anton
 * 
 */
public class LookAndFeelTest {

	@Test
	public void test() {
		for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			System.out.println(info.getClassName());
		}
	}

}
