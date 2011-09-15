/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.controller;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

import junit.framework.Assert;

import org.junit.Test;

/**
 * @author anton
 * 
 */
public class SettingsTest {

	@Test
	public void testAutomaticSingleton() {
		ResourceBundle i18n = Settings.i18n;
		Assert.assertNotNull(i18n);
	}

	@Test(expected = MissingResourceException.class)
	public void testDefautltI18nWithException() {
		ResourceBundle i18n = Settings.i18n;
		i18n.getString("bladsbalsudb");
	}

	@Test()
	public void testWrapperWihtoutException() {
		ResourceBundle i18n = Settings.i18n;
		String str = Settings.getI18n("bladsbalsudb");
		Assert.assertNull(str);
	}
}
