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

import de.unikassel.ann.controller.ActionControllerTest.MyModel;
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

		System.out.println(a + " ( " + evt.getOldValue() + ", "
				+ evt.getNewValue() + " )");

		SidebarModel sidebarModel = Settings.getInstance().getCurrentSession().sidebarModel;

		Integer newValue = (Integer) evt.getNewValue();
		switch (a) {
		case UPDATE_SIDEBAR_CONFIG_INPUT_NEURON_MODEL:
			sidebarModel.setInputNeurons(newValue);
			break;

		case UPDATE_SIDEBAR_CONFIG_HIDDEN_NEURON_MODEL:
			// TODO sidebarModel.setHiddenNeurons(newValue);
			break;

		case UPDATE_SIDEBAR_CONFIG_HIDDEN_LAYER_MODEL:
			sidebarModel.setHiddenLayers(newValue);
			break;

		case UPDATE_SIDEBAR_CONFIG_OUTPUT_NEURON_MODEL:
			sidebarModel.setOutputNeurons(newValue);
			break;

		case UPDATE_JUNG_GRAPH:
			Integer selectedHiddenLayer = 1; // TODO get selected hidden layer
			// sidebarConfig.
			Integer maxHiddenLayer = sidebarModel.getHiddenLayers();
			LayerController<Layer> layerController = LayerController
					.getInstance();

			String propertyName = evt.getPropertyName();
			//
			// Remove
			//
			if ((Integer) evt.getOldValue() > newValue) {
				// INPUT NEURON
				if (propertyName.equals(SidebarModel.P.inputNeurons.name())) {
					layerController.removeVertex(0, newValue);
				}
				// HIDDEN NEURON
				else if (propertyName.equals(SidebarModel.P.hiddenNeurons
						.name())) {
					layerController.removeVertex(selectedHiddenLayer, newValue);
				}
				// HIDDEN LAYER
				else if (propertyName
						.equals(SidebarModel.P.hiddenLayers.name())) {
					// TODO layerController.removeLayer(maxHiddenLayer,
					// newValue);
				} // OUTPUT NEURON
				else if (propertyName.equals(SidebarModel.P.outputNeurons
						.name())) {
					layerController.removeVertex(maxHiddenLayer + 1, newValue);
				}
			}
			//
			// Add
			//
			else if ((Integer) evt.getOldValue() < newValue) {
				// INPUT NEURON
				if (propertyName.equals(SidebarModel.P.inputNeurons.name())) {
					layerController.addVertex(0, newValue, true);
				}
				// HIDDEN NEURON
				else if (propertyName.equals(SidebarModel.P.hiddenNeurons
						.name())) {
					layerController.addVertex(selectedHiddenLayer, newValue,
							true);
				}
				// HIDDEN LAYER
				else if (propertyName
						.equals(SidebarModel.P.hiddenLayers.name())) {
					// TODO layerController.addLayer(maxHiddenLayer + 1,
					// newValue);
				}
				// OUTPUT NEURON
				else if (propertyName.equals(SidebarModel.P.outputNeurons
						.name())) {
					layerController.addVertex(maxHiddenLayer + 1, newValue,
							true);
				}
			}

			break;

		case UPDATE_SIDEBAR_TOPOLOGY_VIEW:
			Sidebar sidebar = Main.instance.sideBar;
			// update full topology panel

			sidebar.topolgyPanel.inputNeuroSpinner.setValue(sidebarModel
					.getInputNeurons());

			// System.out.println("updated the view");

			if (sidebarModel.getHiddenLayers() > 0) {
				// enable combox box and checkbox
				sidebar.topolgyPanel.hiddenLayerDropDown.setEnabled(true);
				sidebar.topolgyPanel.hiddenBiasCB.setEnabled(true);
				sidebar.topolgyPanel.hiddenNeuronSpinner.setEnabled(true);
				sidebar.topolgyPanel.comboBoxHiddenMausModus.setEnabled(true);

				Vector<Integer> comboBoxEntries = new Vector<Integer>();
				for (int i = 1; i <= sidebarModel.getHiddenLayers(); i++) {
					comboBoxEntries.add(new Integer(i));
				}
				DefaultComboBoxModel newComboBoxModel = new DefaultComboBoxModel(
						comboBoxEntries);
				sidebar.topolgyPanel.hiddenLayerDropDown
						.setModel(newComboBoxModel);
				sidebar.topolgyPanel.hiddenLayerDropDown
						.setSelectedItem(sidebarModel.getHiddenLayers());
				DefaultComboBoxModel comboBoxModel = new DefaultComboBoxModel(
						comboBoxEntries);
				sidebar.topolgyPanel.comboBoxHiddenMausModus
						.setModel(comboBoxModel);
				sidebar.topolgyPanel.comboBoxHiddenMausModus
						.setSelectedItem(sidebarModel.getHiddenLayers());
			} else {
				sidebar.topolgyPanel.hiddenLayerDropDown.setEnabled(false);
				sidebar.topolgyPanel.hiddenBiasCB.setEnabled(false);
				sidebar.topolgyPanel.hiddenNeuronSpinner.setEnabled(false);
				sidebar.topolgyPanel.comboBoxHiddenMausModus.setEnabled(false);
			}

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
