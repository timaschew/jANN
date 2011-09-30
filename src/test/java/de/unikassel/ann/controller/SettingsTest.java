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

import de.unikassel.ann.model.UserSession;

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
		String str = Settings.getI18n("bladsbalsudb");
		Assert.assertEquals("<i18n>", str);
	}

	@Test
	public void testSession() {
		UserSession session1 = Settings.getInstance().createNewSession(null);
		UserSession session2 = Settings.getInstance().createNewSession(null);
		UserSession session3 = Settings.getInstance().createNewSession(null);

		System.out.println(session1);
		System.out.println(session2);
		System.out.println(session3);

	}
}
