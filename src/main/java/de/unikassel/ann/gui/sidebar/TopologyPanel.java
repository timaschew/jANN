package de.unikassel.ann.gui.sidebar;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.controller.ActionController;
import de.unikassel.ann.controller.Actions;
import de.unikassel.ann.controller.Settings;

public class TopologyPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private static final int MAX_NEURONS = 99;

	private static final int MAX_HIDDEN_LAYER = 10;

	public JSpinner inputNeuroSpinner;
	public SpinnerNumberModel inputSpinnerModel = new SpinnerNumberModel(0, 0, MAX_NEURONS, 1);
	public JSpinner outputNeuroSpinner;
	public JSpinner hiddenLayerCountSpinner;
	public JSpinner hiddenNeuronSpinner;
	public JComboBox hiddenLayerDropDown;
	public JComboBox comboBoxHiddenMausModus;
	public JCheckBox inputBiasCB;
	public JCheckBox hiddenBiasCB;

	private final ButtonGroup buttonGroup = new ButtonGroup();

	private ActionController ac = ActionController.get();

	public JRadioButton mouseInputRB;
	public JRadioButton mouseOutputRB;
	public JRadioButton mouseHiddenRB;

	/**
	 * Create the frame.
	 */
	public TopologyPanel() {
		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.topology"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		// setSize(400, 240);
		setPreferredSize(new Dimension(400, 250));

		JLabel lblInputNeuronen = new JLabel(Settings.i18n.getString("sidebar.topology.inputNeurons"));
		inputNeuroSpinner = new JSpinner(inputSpinnerModel);

		JLabel lblOutputNeuronen = new JLabel(Settings.i18n.getString("sidebar.topology.outputNeurons"));
		outputNeuroSpinner = new JSpinner(new SpinnerNumberModel(0, 0, MAX_NEURONS, 1));

		JLabel lblHiddenLayer = new JLabel(Settings.i18n.getString("sidebar.topology.hiddenlayers"));
		hiddenLayerCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, MAX_HIDDEN_LAYER, 1));

		JLabel lblHiddenNeuronen = new JLabel(Settings.i18n.getString("sidebar.topology.hiddenNeurons"));
		hiddenNeuronSpinner = new JSpinner(new SpinnerNumberModel(0, 0, MAX_NEURONS, 1));

		// is with the hiddenLayerCountSpinner associated

		hiddenLayerDropDown = new JComboBox(new DefaultComboBoxModel());
		hiddenLayerDropDown.setEnabled(false);
		hiddenNeuronSpinner.setEnabled(false);

		JPanel mouseModusPanel = new JPanel();
		mouseModusPanel.setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.topology.mouse"), TitledBorder.LEADING, TitledBorder.TOP, null, null));

		inputBiasCB = new JCheckBox(Settings.i18n.getString("sidebar.topology.biasInputCB"));
		hiddenBiasCB = new JCheckBox(Settings.i18n.getString("sidebar.topology.biasHiddenCB"));
		hiddenBiasCB.setEnabled(false);

		GroupLayout gl_topologiePanel = new GroupLayout(this);
		gl_topologiePanel.setHorizontalGroup(
			gl_topologiePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topologiePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(mouseModusPanel, GroupLayout.DEFAULT_SIZE, 360, Short.MAX_VALUE)
						.addGroup(gl_topologiePanel.createSequentialGroup()
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblInputNeuronen)
								.addComponent(lblOutputNeuronen)
								.addComponent(lblHiddenLayer)
								.addComponent(lblHiddenNeuronen))
							.addGap(78)
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING, false)
								.addComponent(outputNeuroSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addComponent(hiddenLayerCountSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addComponent(hiddenNeuronSpinner, Alignment.TRAILING, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
								.addComponent(inputNeuroSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_topologiePanel.createSequentialGroup()
									.addComponent(hiddenLayerDropDown, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(hiddenBiasCB))
								.addComponent(inputBiasCB))))
					.addContainerGap())
		);
		gl_topologiePanel.setVerticalGroup(
			gl_topologiePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topologiePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblInputNeuronen)
						.addGroup(gl_topologiePanel.createSequentialGroup()
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(inputBiasCB)
								.addComponent(inputNeuroSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGap(9)
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(outputNeuroSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblOutputNeuronen))
							.addGap(11)
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(hiddenLayerCountSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblHiddenLayer))
							.addGap(11)
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.BASELINE)
								.addComponent(hiddenNeuronSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(lblHiddenNeuronen)
								.addComponent(hiddenLayerDropDown, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(hiddenBiasCB))))
					.addGap(11)
					.addComponent(mouseModusPanel, GroupLayout.PREFERRED_SIZE, 70, Short.MAX_VALUE)
					.addContainerGap())
		);
		/**
		 * internal MausModus-Panel Elements
		 */
		JRadioButton mouseInputRB = new JRadioButton(Settings.i18n.getString("sidebar.topology.mouse.input"));
		mouseInputRB.setSelected(true);
		buttonGroup.add(mouseInputRB);
		JRadioButton mouseOutputRB = new JRadioButton(Settings.i18n.getString("sidebar.topology.mouse.output"));
		buttonGroup.add(mouseOutputRB);
		JRadioButton mouseHiddenRB = new JRadioButton(Settings.i18n.getString("sidebar.topology.mouse.hidden"));
		buttonGroup.add(mouseHiddenRB);
		comboBoxHiddenMausModus = new JComboBox();
		comboBoxHiddenMausModus.setEnabled(false);

		GroupLayout gl_mouseModusPanel = new GroupLayout(mouseModusPanel);
		gl_mouseModusPanel.setHorizontalGroup(gl_mouseModusPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_mouseModusPanel.createSequentialGroup().addContainerGap().addComponent(mouseInputRB)
						.addPreferredGap(ComponentPlacement.UNRELATED).addComponent(mouseOutputRB).addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(mouseHiddenRB).addGap(18)
						.addComponent(comboBoxHiddenMausModus, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(115, Short.MAX_VALUE)));
		gl_mouseModusPanel.setVerticalGroup(gl_mouseModusPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(
						gl_mouseModusPanel
								.createSequentialGroup()
								.addGroup(
										gl_mouseModusPanel
												.createParallelGroup(Alignment.BASELINE)
												.addComponent(mouseInputRB)
												.addComponent(mouseOutputRB)
												.addComponent(mouseHiddenRB)
												.addComponent(comboBoxHiddenMausModus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		mouseModusPanel.setLayout(gl_mouseModusPanel);
		setLayout(gl_topologiePanel);

		initActions();
	}

	/**
	 * 
	 */
	private void initActions() {
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) inputNeuroSpinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				ac.doAction(Actions.UPDATE_SIDEBAR_CONFIG_INPUT_NEURON_MODEL, evt);
			}
		});

		editor = (JSpinner.DefaultEditor) hiddenLayerCountSpinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				ac.doAction(Actions.UPDATE_SIDEBAR_CONFIG_HIDDEN_LAYER_MODEL, evt);
			}
		});

	}
}
