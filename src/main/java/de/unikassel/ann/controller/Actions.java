/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.controller;

/**
 * @author anton
 * 
 */
public enum Actions {
	// Test Actions
	TEST_UPDATEVIEW, TEST_UPDATEMODEL, TEST_NETWORK,

	// Menu Actions
	NONE, NEW, OPEN, SAVE, EXIT, VIEW_DATA, VIEW_TRAINING, ABOUT,
	
	// ActionController Update-Events
	UPDATE_SIDEBAR_TOPOLOGY_VIEW,
	UPDATE_SIDEBAR_CONFIG_INPUT_NEURON_MODEL,
	UPDATE_SIDEBAR_CONFIG_HIDDEN_NEURON_MODEL,
	UPDATE_SIDEBAR_CONFIG_HIDDEN_LAYER_MODEL,
	UPDATE_SIDEBAR_CONFIG_OUTPUT_NEURON_MODEL,
	UPDATE_JUNG_GRAPH,
	
	//Actions
	CREATE_NETWORK, 
	PLAY_TRAINING,
	CHANGE_MOUSE_MODI;
}
