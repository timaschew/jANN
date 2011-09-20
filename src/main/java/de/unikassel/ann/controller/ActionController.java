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
import de.unikassel.ann.factory.NetworkFactory;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.graph.GraphLayoutViewer;
import de.unikassel.ann.gui.sidebar.Sidebar;
import de.unikassel.ann.model.Layer;
import de.unikassel.ann.model.SidebarModel;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

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

		SidebarModel sidebarModel = Settings.getInstance().getCurrentSession().sidebarModel;
		switch (a) {

		case UPDATE_SIDEBAR_CONFIG_INPUT_NEURON_MODEL:
			Integer newValue = (Integer) evt.getNewValue();

			sidebarModel.setInputNeurons(newValue);
			break;

		case UPDATE_SIDEBAR_CONFIG_HIDDEN_NEURON_MODEL:
			// TODO sidebarModel.setHiddenNeurons(newValue);
			break;

		case UPDATE_SIDEBAR_CONFIG_HIDDEN_LAYER_MODEL:
			newValue = (Integer) evt.getNewValue();
			sidebarModel.setHiddenLayers(newValue);
			break;

		case UPDATE_SIDEBAR_CONFIG_OUTPUT_NEURON_MODEL:
			newValue = (Integer) evt.getNewValue();
			sidebarModel.setOutputNeurons(newValue);
			break;

		case UPDATE_JUNG_GRAPH:
			newValue = (Integer) evt.getNewValue();
			Integer oldValue = (Integer) evt.getOldValue();

			Integer selectedHiddenLayer = sidebarModel.getSelectedGlobalHiddenLayerIndex();
			// sidebarConfig.
			Integer hiddenLayerSize = sidebarModel.getHiddenLayers();
			LayerController<Layer> layerController = LayerController.getInstance();

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
				else if (propertyName.equals(SidebarModel.P.hiddenNeurons.name())) {
					layerController.removeVertex(selectedHiddenLayer, newValue);
				}
				// HIDDEN LAYER
				else if (propertyName.equals(SidebarModel.P.hiddenLayers.name())) {
					for (int i = oldValue; i > newValue; i--) {
						layerController.removeLayer(i);
					}
				} // OUTPUT NEURON
				else if (propertyName.equals(SidebarModel.P.outputNeurons.name())) {
					layerController.removeVertex(hiddenLayerSize + 1, newValue);
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
				else if (propertyName.equals(SidebarModel.P.hiddenNeurons.name())) {
					layerController.addVertex(selectedHiddenLayer, newValue, true);
				}
				// HIDDEN LAYER
				else if (propertyName.equals(SidebarModel.P.hiddenLayers.name())) {
					for (int i = oldValue; i < newValue; i++) {
						// i is amount of hidden layers
						// layer controller need the global layer index
						layerController.addLayer(i + 1);
					}
				}

				// OUTPUT NEURON
				else if (propertyName.equals(SidebarModel.P.outputNeurons.name())) {
					layerController.addVertex(hiddenLayerSize + 1, newValue, true);
				}
			}

			break;
		case UPDATE_SIDEBAR_TOPOLOGY_VIEW:
			Sidebar sidebar = Main.instance.sideBar;
			// update full topology panel

			sidebar.topolgyPanel.inputNeuroSpinner.setValue(sidebarModel.getInputNeurons());
			System.out.println("updated the view");

			// get last selection from combobox
			Integer selectedItem = (Integer) sidebar.topolgyPanel.hiddenLayerDropDown.getSelectedItem();
			// refresh the comboboxen: remove all and add then again
			// remove all items
			sidebar.topolgyPanel.hiddenLayerComboModel.removeAllElements();
			// add the items
			for (int i = 1; i <= sidebarModel.getHiddenLayers(); i++) {
				// if getHiddenayer >= 1 then enable der Elements
				sidebar.topolgyPanel.hiddenLayerDropDown.setEnabled(true);
				sidebar.topolgyPanel.hiddenBiasCB.setEnabled(true);
				sidebar.topolgyPanel.hiddenNeuronSpinner.setEnabled(true);
				sidebar.topolgyPanel.comboBoxHiddenMausModus.setEnabled(true);

				sidebar.topolgyPanel.hiddenLayerComboModel.addElement(new Integer(i));
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

			break;

		case CREATE_NETWORK:
			NetworkFactory factory = new NetworkFactory();
			System.out.println("Create network");
			// factory.createSimpleNet(sidebarModel.getInputNeurons(),
			// sidebarModel.getHiddenLayers(),
			// sidebarModel.getOutputNeurons(), sidebarModel.getInputBias());

			break;

		case PLAY_TRAINING:

			break;

		case CHANGE_MOUSE_MODI:
			sidebar = Main.instance.sideBar;
			String selected = (String) sidebar.topolgyPanel.comboBoxMouseModis.getSelectedItem();
			if (selected == "Picking") {
				GraphLayoutViewer.getInstance().graphMouse.setMode(Mode.PICKING);
				System.out.println("picking");
			} else if (selected == "Editing") {
				GraphLayoutViewer.getInstance().graphMouse.setMode(Mode.EDITING);
				System.out.println("editing");
			} else if (selected == "Transforming") {
				GraphLayoutViewer.getInstance().graphMouse.setMode(Mode.TRANSFORMING);
				System.out.println("transforming");
			}

			break;

		case TEST_UPDATEMODEL:
			newValue = (Integer) evt.getNewValue();
			Integer value = newValue;
			MyModel dataModel = ActionControllerTest.instance.dataModel;
			dataModel.setLayerCount(value);
			break;

		case TEST_UPDATEVIEW:
			newValue = (Integer) evt.getNewValue();
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
