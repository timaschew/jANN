/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.controller;

import java.beans.PropertyChangeEvent;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.SpinnerModel;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.ActionControllerTest.MyModel;
import de.unikassel.ann.factory.NetworkFactory;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.sidebar.Sidebar;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.SidebarModel;

/**
 * @author anton
 * 
 */
public class ActionController {

	private static ActionController instance;

	private ActionController() {
	}

	public static ActionController get() {
		if (instance == null) {
			instance = new ActionController();
		}
		return instance;
	}

	public void doAction(final Actions a, final PropertyChangeEvent evt) {

		System.out.println(a);
		System.out.println(evt.getOldValue());
		System.out.println(evt.getNewValue());

		Integer newValue = (Integer) evt.getNewValue();
		switch (a) {

		case UPDATE_SIDEBAR_CONFIG_INPUT_NEURON_MODEL:
			SidebarModel sidebarModel = Settings.getInstance().getCurrentSession().sidebarModel;
			sidebarModel.setInputNeurons(newValue);
			break;

		case UPDATE_SIDEBAR_CONFIG_HIDDEN_LAYER_MODEL:
			sidebarModel = Settings.getInstance().getCurrentSession().sidebarModel;
			sidebarModel.setHiddenLayers(newValue);
			break;

		case UPDATE_JUNG_GRAPH:
			sidebarModel = Settings.getInstance().getCurrentSession().sidebarModel;
			Integer selectedHiddenLayer = 1; // TODO get selected hidden layer
			// sidebarConfig.
			Integer maxHiddenLayer = sidebarModel.getHiddenLayers();
			LayerController<Layer> layerController = LayerController.getInstance();

			if ((Integer) evt.getOldValue() > newValue) {
				// remove last
				if (evt.getPropertyName().equals(SidebarModel.P.hiddenNeurons.name())) {
					layerController.removeVertex(selectedHiddenLayer, newValue);
				} else if (evt.getPropertyName().equals(SidebarModel.P.inputNeurons.name())) {
					layerController.removeVertex(0, newValue);
				} else if (evt.getPropertyName().equals(SidebarModel.P.hiddenLayers.name())) {
					// layerController.removeLayer(maxHiddenLayer, newValue);
				} else if (evt.getPropertyName().equals(SidebarModel.P.outputNeurons.name())) {
					layerController.removeVertex(maxHiddenLayer + 1, newValue);
				}
			} else if ((Integer) evt.getOldValue() < newValue) {
				// add new
				if (evt.getPropertyName().equals(SidebarModel.P.hiddenNeurons.name())) {
					layerController.addVertex(selectedHiddenLayer, newValue);
				} else if (evt.getPropertyName().equals(SidebarModel.P.inputNeurons.name())) {
					layerController.addVertex(0, newValue);
				} else if (evt.getPropertyName().equals(SidebarModel.P.hiddenLayers.name())) {
					// layerController.addLayer(maxHiddenLayer + 1, newValue);
				} else if (evt.getPropertyName().equals(SidebarModel.P.outputNeurons.name())) {
					layerController.addVertex(maxHiddenLayer + 1, newValue);
				}
			}

			break;

		case UPDATE_SIDEBAR_TOPOLOGY_VIEW:
			sidebarModel = Settings.getInstance().getCurrentSession().sidebarModel;
			Sidebar sidebar = Main.instance.sideBar;
			// update full topology panel

			sidebar.topolgyPanel.inputNeuroSpinner.setValue(sidebarModel.getInputNeurons());
			System.out.println("updated the view");
			
			// get last selection from combobox
			Integer selectedItem = (Integer) sidebar.topolgyPanel.hiddenLayerDropDown.getSelectedItem();
			// refresh the comboboxen: remove all and add then again
			// remove all items
			sidebar.topolgyPanel.hiddenLayerDropDown.removeAllItems();
			sidebar.topolgyPanel.comboBoxHiddenMausModus.removeAllItems();
			// add the items
			for (int i = 1; i <= sidebarModel.getHiddenLayers(); i++) {
				//if getHiddenayer >= 1 then enable der Elements 
				sidebar.topolgyPanel.hiddenLayerDropDown.setEnabled(true);
				sidebar.topolgyPanel.hiddenBiasCB.setEnabled(true);
				sidebar.topolgyPanel.hiddenNeuronSpinner.setEnabled(true);
				sidebar.topolgyPanel.comboBoxHiddenMausModus.setEnabled(true);
				
				sidebar.topolgyPanel.hiddenLayerDropDown.addItem(new Integer(i));
				sidebar.topolgyPanel.comboBoxHiddenMausModus.addItem(new Integer(i));
			}
			// if previous selection was not null, set to old value
			if (selectedItem != null) {
				// if old value don't exist anymore set to current hidden layer amount, 
				// otherwise use the previous selected item
				if (selectedItem > sidebarModel.getHiddenLayers()) {
					sidebar.topolgyPanel.hiddenLayerDropDown.setSelectedItem(sidebarModel.getHiddenLayers());
					sidebar.topolgyPanel.comboBoxHiddenMausModus.setSelectedItem(sidebarModel.getHiddenLayers());
					
					sidebar.topolgyPanel.hiddenLayerDropDown.setEnabled(false);
					sidebar.topolgyPanel.hiddenBiasCB.setEnabled(false);
					sidebar.topolgyPanel.hiddenNeuronSpinner.setEnabled(false);
					sidebar.topolgyPanel.comboBoxHiddenMausModus.setEnabled(false);
				} else {
					sidebar.topolgyPanel.hiddenLayerDropDown.setSelectedItem(selectedItem);
					sidebar.topolgyPanel.comboBoxHiddenMausModus.setSelectedItem(selectedItem);
				}
			}

//
//			if (sidebarModel.getHiddenLayers() > 0) {
//				// enable combox box and checkbox
//				sidebar.topolgyPanel.hiddenLayerDropDown.setEnabled(true);
//				sidebar.topolgyPanel.hiddenBiasCB.setEnabled(true);
//				sidebar.topolgyPanel.hiddenNeuronSpinner.setEnabled(true);
//				sidebar.topolgyPanel.comboBoxHiddenMausModus.setEnabled(true);
//
//				Vector<Integer> comboBoxEntries = new Vector<Integer>();
//				for (int i = 1; i <= sidebarModel.getHiddenLayers(); i++) {
//					comboBoxEntries.add(new Integer(i));
//				}
//				DefaultComboBoxModel newComboBoxModel = new DefaultComboBoxModel(comboBoxEntries);
//				sidebar.topolgyPanel.hiddenLayerDropDown.setModel(newComboBoxModel);
//				sidebar.topolgyPanel.hiddenLayerDropDown.setSelectedItem(sidebarModel.getHiddenLayers());
//				DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(comboBoxEntries);
//				sidebar.topolgyPanel.comboBoxHiddenMausModus.setModel(comboBoxModel);
//				sidebar.topolgyPanel.comboBoxHiddenMausModus.setSelectedItem(sidebarModel.getHiddenLayers());
//			} else {
//				sidebar.topolgyPanel.hiddenLayerDropDown.setEnabled(false);
//				sidebar.topolgyPanel.hiddenBiasCB.setEnabled(false);
//				sidebar.topolgyPanel.hiddenNeuronSpinner.setEnabled(false);
//				sidebar.topolgyPanel.comboBoxHiddenMausModus.setEnabled(false);
//			}

			break;
			
		case CREATE_NETWORK:
			NetConfig config = new NetConfig();
			sidebarModel = Settings.getInstance().getCurrentSession().sidebarModel;
			NetworkFactory factory = new NetworkFactory();
//			factory.createSimpleNet(sidebarModel.getInputNeurons(), sidebarModel.getHiddenNeurons(), 
//					sidebarModel.getOutputNeurons(), sidebarModel.getBias());
			break;
			
		case TEST_UPDATEMODEL:
			Integer value = newValue;
			MyModel dataModel = ActionControllerTest.instance.dataModel;
			dataModel.setLayerCount(value);
			break;

		case TEST_UPDATEVIEW:
			System.out.println("TEST ACTION: " + evt.getNewValue());
			// model was changed, upadte everything
			// property in TEST case is MyModel.layerCount from type Integer
			Integer layerCount = newValue;

			// update combox box
			JComboBox cb = ActionControllerTest.instance.comboBox;
			// create a list with entries for the comboboxModel
			Vector<Integer> comboBoxEntries = new Vector<Integer>();
			for (int i = 0; i <= layerCount; i++) {
				comboBoxEntries.add(i);
			}
			// set overwrite the old combobox model
			cb.setModel(new DefaultComboBoxModel(comboBoxEntries));
			cb.setSelectedItem(layerCount); // select the last one

			// update Spinner
			SpinnerModel sm = ActionControllerTest.instance.spinnerModel;
			sm.setValue(layerCount);

			// update Label
			JLabel label = ActionControllerTest.instance.label;
			label.setText("Aktueller Wert: " + layerCount);

			break;
		}

	}
}
