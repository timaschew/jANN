package de.unikassel.ann.gui.sidebar;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ActionMap;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListCellRenderer;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.model.Edge;
import de.unikassel.ann.gui.model.Vertex;
import de.unikassel.ann.model.Network;
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.Synapse;
import de.unikassel.ann.model.func.ActivationFunction;
import de.unikassel.ann.util.FormatHelper;

public class SelectedSymbolPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private JTextField fieldSelected;
	private CustomComboBox activatedFunctionComboBox;
	private JSpinner neuroInputBySelectSpinner;
	private JSpinner spinnerSynapsWeight;
	private JButton btnApplyChanges;
	private JButton btnDiscardChanges;

	private enum SelectedType {
		VERTEX, EDGE
	};

	// Set by the update method of their type in order to call it again on discard
	private SelectedType lastSelectedType;

	private boolean ignoreChanges;

	/**
	 * Create the frame.
	 */
	public SelectedSymbolPanel() {
		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.selectedSymbol"), TitledBorder.LEADING, TitledBorder.TOP, null,
				null));
		setSize(400, 198);

		JLabel lblSelected = new JLabel(Settings.i18n.getString("sidebar.selectedSymbol.selected"));

		JLabel lblSynapseWeight = new JLabel(Settings.i18n.getString("sidebar.selectedSymbol.synapseWeigth"));

		JLabel lblNeuroInput = new JLabel(Settings.i18n.getString("sidebar.selectedSymbol.neuronsInput"));

		JLabel lblActivationFunction = new JLabel(Settings.i18n.getString("sidebar.selectedSymbol.activatedFunction"));

		fieldSelected = new JTextField();
		fieldSelected.setEditable(false);
		fieldSelected.setColumns(10);

		activatedFunctionComboBox = new CustomComboBox();
		DefaultComboBoxModel model = new DefaultComboBoxModel();
		model.addElement("");
		model.addElement("Sigmoid");
		model.addElement("TanH");
		activatedFunctionComboBox.setModel(model);
		HashSet<Integer> disableIndex = new HashSet<Integer>();
		disableIndex.add(0);
		activatedFunctionComboBox.setDisableIndex(disableIndex);

		neuroInputBySelectSpinner = new JSpinner();
		neuroInputBySelectSpinner.setModel(new SpinnerNumberModel(new Double(0.0), null, null, new Double(0.1)));

		spinnerSynapsWeight = new JSpinner();
		spinnerSynapsWeight.setModel(new SpinnerNumberModel(new Double(0.0), null, null, new Double(0.1)));

		btnApplyChanges = new JButton(Settings.i18n.getString("sidebar.selectedSymbol.applyChanges"));
		btnDiscardChanges = new JButton(Settings.i18n.getString("sidebar.selectedSymbol.discardChanges"));

		GroupLayout gl_SelectedSymbolPanel = new GroupLayout(this);
		gl_SelectedSymbolPanel.setHorizontalGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_SelectedSymbolPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING).addComponent(lblActivationFunction)
										.addComponent(lblNeuroInput).addComponent(lblSelected).addComponent(lblSynapseWeight)
										.addComponent(btnApplyChanges))
						.addGap(41)
						.addGroup(
								gl_SelectedSymbolPanel.createParallelGroup(Alignment.TRAILING, false).addComponent(fieldSelected)
										.addComponent(activatedFunctionComboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(spinnerSynapsWeight).addComponent(neuroInputBySelectSpinner)
										.addComponent(btnDiscardChanges))));

		gl_SelectedSymbolPanel.setVerticalGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_SelectedSymbolPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_SelectedSymbolPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblSelected)
										.addComponent(fieldSelected, GroupLayout.PREFERRED_SIZE, 25, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								gl_SelectedSymbolPanel
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblSynapseWeight)
										.addComponent(spinnerSynapsWeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								gl_SelectedSymbolPanel
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNeuroInput)
										.addComponent(neuroInputBySelectSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								gl_SelectedSymbolPanel
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblActivationFunction)
										.addComponent(activatedFunctionComboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addGroup(
								gl_SelectedSymbolPanel
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(btnApplyChanges)
										.addComponent(btnDiscardChanges, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		setLayout(gl_SelectedSymbolPanel);

		reset();
		initActions();
	}

	/**
	 * Bind action listener to the elements of this panel.
	 */
	private void initActions() {

		btnApplyChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				apply();
			}
		});

		btnDiscardChanges.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				discard();

			}
		});
	}

	private void changeNeuronValue(final Double value) {
		// Update all selected vertices
		Set<Vertex> pickedVertices = GraphController.getInstance().getPickedVertices();
		if (pickedVertices.size() > 0) {
			for (Vertex vertex : pickedVertices) {
				if (vertex.getModel().isBias()) {
					continue;
				}
				Network.getNetwork().changeNeuronValue(vertex.getModel(), value);
			}
		}
	}

	private void changeSynapseWeight(final Double value) {
		// Update all selected egdes
		Set<Edge> pickedEdges = GraphController.getInstance().getPickedEdges();
		if (pickedEdges.size() > 0) {
			for (Edge edge : pickedEdges) {
				Network.getNetwork().changeSynapseWeight(edge.getModel(), value);
			}
		}
	}

	private void changeNeuronActivationFunction(final String value) {
		// Add "Function"-suffix to the activation function name if it is missing
		String activationFunctionName = value;
		if (activationFunctionName.endsWith(Neuron.functionSuffix) == false) {
			activationFunctionName += Neuron.functionSuffix;
		}

		// Update all selected vertices
		Set<Vertex> pickedVertices = GraphController.getInstance().getPickedVertices();
		if (pickedVertices.size() > 0) {
			for (Vertex vertex : pickedVertices) {
				// Update only if the selected value differs from the current value
				ActivationFunction activationFunction = vertex.getModel().getActivationFunction();
				String curFunc = activationFunction != null ? activationFunction.getClass().getSimpleName() : null;
				if (curFunc.equalsIgnoreCase(activationFunctionName) == false) {
					Network.getNetwork().changeNeuronActivationFunction(vertex.getModel(), activationFunctionName);
				}
			}
		}
	}

	/**
	 * Apply the changes made in the input elements to the currently picked symbols.
	 */
	private void apply() {
		// neuroInputBySelectSpinner
		if (neuroInputBySelectSpinner.isEnabled()) {
			Double value = FormatHelper.parse2Double(neuroInputBySelectSpinner.getValue());
			changeNeuronValue(value);
		}

		// spinnerSynapsWeight
		if (spinnerSynapsWeight.isEnabled()) {
			Double value = FormatHelper.parse2Double(spinnerSynapsWeight.getValue());
			changeSynapseWeight(value);
		}

		// activatedFunctionComboBox
		if (activatedFunctionComboBox.isEditable()) {
			Object selectedItem = activatedFunctionComboBox.getSelectedItem();
			String value = selectedItem != null ? selectedItem.toString() : null;
			if (value != null && value.isEmpty() == false) {
				changeNeuronActivationFunction(value);
			}
		}
	}

	/**
	 * Discard the changes made in the input elements and (re)set the values of the currently picked symbols.
	 */
	private void discard() {
		GraphController graphController = GraphController.getInstance();
		switch (lastSelectedType) {
		case VERTEX:
			updateVertex(graphController.getPickedVertices());
			break;
		case EDGE:
			updateEdge(graphController.getPickedEdges());
			break;
		default:
			break;
		}
	}

	/**
	 * Reset the panel and disable all elements.
	 */
	public void reset() {
		ignoreChanges = true;
		fieldSelected.setText(null);

		HashSet<Integer> disableIndex = new HashSet<Integer>();
		activatedFunctionComboBox.setDisableIndex(disableIndex);
		activatedFunctionComboBox.setSelectedIndex(0);
		disableIndex.add(0);
		activatedFunctionComboBox.setDisableIndex(disableIndex);
		activatedFunctionComboBox.setEnabled(false);

		spinnerSynapsWeight.setValue(0);
		spinnerSynapsWeight.setEnabled(false);

		neuroInputBySelectSpinner.setValue(0);
		neuroInputBySelectSpinner.setEnabled(false);
	}

	/**
	 * Update the panel with the picked vertex (or vertices)
	 * 
	 * @param picked
	 */
	public void updateVertex(final Set<Vertex> picked) {
		reset();

		// List of picked values to show
		List<String> activationFunctions = new ArrayList<String>();
		List<Double> values = new ArrayList<Double>();

		// Collect all values of the picked set
		for (Vertex vertex : picked) {
			Neuron model = vertex.getModel();
			activationFunctions.add(model.getActivationFunction().getClass().getSimpleName());
			values.add(model.getValue());
		}

		// Field select
		String neurons = Settings.i18n.getString(picked.size() != 1 ? "sidebar.standardOptions.neurons" : "sidebar.standardOptions.neuron");
		String selectText = String.format("%d " + neurons, picked.size());
		fieldSelected.setText(selectText);

		// Activation function
		boolean showFunc = false;
		String refFunc = null;
		for (String func : activationFunctions) {
			if (refFunc == null) {
				// There's at least one function so set the reference function and set the show flag to true
				refFunc = func;
				showFunc = true;
			}
			// Show function only when all functions are the same
			showFunc &= refFunc.equals(func);
		}
		activatedFunctionComboBox.setEnabled(true);
		if (showFunc && refFunc != null) {
			refFunc = refFunc.replaceAll("Function", "");
			activatedFunctionComboBox.setSelectedItem(refFunc);
		} else {
			activatedFunctionComboBox.setSelectedItem(null);
		}

		// Neuron value
		boolean showValue = false;
		Double refValue = null;
		for (Double value : values) {
			if (refValue == null) {
				// There's at least one value so set the reference value and set the show flag to true
				refValue = value;
				showValue = true;
			}
			// Show value only when all values are the same
			showValue &= refValue.equals(value);
		}
		neuroInputBySelectSpinner.setEnabled(true);
		if (showValue && refValue != null) {
			neuroInputBySelectSpinner.setValue(refValue);
		} else {
			neuroInputBySelectSpinner.setValue(0);
		}

		// Store in order to know on discard which update to call
		lastSelectedType = SelectedType.VERTEX;
		ignoreChanges = false;
	}

	/**
	 * 
	 * @param picked
	 */
	public void updateEdge(final Set<Edge> picked) {
		reset();

		// List of picked values to show
		List<Double> values = new ArrayList<Double>();

		// Collect all values of the picked set
		for (Edge edge : picked) {
			Synapse model = edge.getModel();
			values.add(model.getWeight());
		}

		// Field select
		String synapses = Settings.i18n.getString(picked.size() != 1 ? "sidebar.standardOptions.synapses"
				: "sidebar.standardOptions.synapse");
		String selectText = String.format("%d " + synapses, picked.size());
		fieldSelected.setText(selectText);

		// Synapse weight
		boolean showValue = true;
		Double refValue = values.get(0);
		for (Double value : values) {
			showValue &= refValue.equals(value);
		}
		spinnerSynapsWeight.setEnabled(true);
		if (showValue) {
			spinnerSynapsWeight.setValue(refValue);
		} else {
			spinnerSynapsWeight.setValue(0);
		}

		// Store in order to know on discard which update to call
		lastSelectedType = SelectedType.EDGE;
		ignoreChanges = false;
	}

	/*
	 * Custom Combox Box which has the ability to disable elements.
	 */
	private class CustomComboBox extends JComboBox {
		public CustomComboBox() {
			super();
			setRenderer(new DefaultListCellRenderer() {
				@Override
				public Component getListCellRendererComponent(final JList list, final Object value, final int index,
						final boolean isSelected, final boolean cellHasFocus) {
					Component c;
					if (disableIndexSet.contains(index)) {
						c = super.getListCellRendererComponent(list, value, index, false, false);
						c.setEnabled(false);
					} else {
						c = super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
						c.setEnabled(true);
					}
					return c;
				}
			});
			Action up = new AbstractAction() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					int si = getSelectedIndex();
					for (int i = si - 1; i >= 0; i--) {
						if (!disableIndexSet.contains(i)) {
							setSelectedIndex(i);
							break;
						}
					}
				}
			};
			Action down = new AbstractAction() {
				@Override
				public void actionPerformed(final ActionEvent e) {
					int si = getSelectedIndex();
					for (int i = si + 1; i < getModel().getSize(); i++) {
						if (!disableIndexSet.contains(i)) {
							setSelectedIndex(i);
							break;
						}
					}
				}
			};
			ActionMap am = getActionMap();
			am.put("selectPrevious3", up);
			am.put("selectNext3", down);
			InputMap im = getInputMap();
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0), "selectPrevious3");
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_UP, 0), "selectPrevious3");
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0), "selectNext3");
			im.put(KeyStroke.getKeyStroke(KeyEvent.VK_KP_DOWN, 0), "selectNext3");
		}

		private final HashSet<Integer> disableIndexSet = new HashSet<Integer>();
		private boolean isDisableIndex = false;

		public void setDisableIndex(final HashSet<Integer> set) {
			disableIndexSet.clear();
			for (Integer i : set) {
				disableIndexSet.add(i);
			}
		}

		@Override
		public void setPopupVisible(final boolean v) {
			if (!v && isDisableIndex) {
				isDisableIndex = false;
			} else {
				super.setPopupVisible(v);
			}
		}

		@Override
		public void setSelectedIndex(final int index) {
			if (disableIndexSet.contains(index)) {
				isDisableIndex = true;
			} else {
				// isDisableIndex = false;
				super.setSelectedIndex(index);
			}
		}
	}

}
