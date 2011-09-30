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

	// Menu Actions
	NONE, IMPORT, CLOSE_CURRENT_SESSION, IMPORT_RECENT, EXPORT, NEW, SAVE, EXIT, ABOUT, CREATE_TRAIN_DATA, jANN_FUNCTION_OVERVIEW,

	// View Actions
	VIEW_DATA, VIEW_TRAINING, BACKPROPAGATION_VIEW, SOM_VIEW, NORMALIZE_TRAIN_DATA, SWITCH_CONSOLE, SWITCH_TRAINERROR, SWITCH_TRAINDATA,

	// SOM Actions
	UPDATE_SIDEBAR_TOPOLOGYSOM_VIEW, UPDATE_SIDEBAR_TRAINSTRATEGY_LERNRATE_MODEL,

	// Load Example-Networks
	LOAD_OR_NETWORK, LOAD_XOR_NETWORK, LOAD_AND_NETWORK, LOAD_2_BIT_ADDIERER_NETWORK, CHANGE_BETWEEN_SESSIONS;
}
