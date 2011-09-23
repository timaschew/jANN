/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.controller;

import java.beans.PropertyChangeEvent;

import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.sidebar.SidebarSOM;

/**
 * @author Sofia
 * 
 */
public class ActionControllerSOM {

	private static ActionControllerSOM instance;

	private ActionControllerSOM() {
	}

	public static ActionControllerSOM get() {
		if (instance == null) {
			instance = new ActionControllerSOM();
		}
		return instance;
	}

	public void doActionSOM(final Actions a, final PropertyChangeEvent evt) {
		SidebarSOM sidebarSom = Main.instance.sidebarSom;
		//
	}
}
