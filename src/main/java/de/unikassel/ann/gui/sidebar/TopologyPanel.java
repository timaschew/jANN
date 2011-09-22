package de.unikassel.ann.gui.sidebar;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.ButtonGroup;
import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
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
	public JComboBox comboBoxMouseModis;
	/**
	 * Model for hiddenLayerDropDown and comboBoxHiddenMausModus
	 */
	public DefaultComboBoxModel hiddenLayerComboModel = new DefaultComboBoxModel();

	private final ButtonGroup buttonGroup = new ButtonGroup();

	private ActionController ac = ActionController.get();

	public JRadioButton mouseInputRB;
	public JRadioButton mouseOutputRB;
	public JRadioButton mouseHiddenRB;

	public JCheckBox chckbxAllNeuronsBind;

	public JButton btnCreateNetwork;
	private JLabel lblJungModis;

	/**
	 * Create the frame.
	 */
	public TopologyPanel() {
		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.topology"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		// setSize(400, 240);
		setPreferredSize(new Dimension(400, 342));

		JLabel lblInputNeuronen = new JLabel(Settings.i18n.getString("sidebar.topology.inputNeurons"));
		inputNeuroSpinner = new JSpinner(inputSpinnerModel);

		JLabel lblOutputNeuronen = new JLabel(Settings.i18n.getString("sidebar.topology.outputNeurons"));
		outputNeuroSpinner = new JSpinner(new SpinnerNumberModel(0, 0, MAX_NEURONS, 1));

		JLabel lblHiddenLayer = new JLabel(Settings.i18n.getString("sidebar.topology.hiddenlayers"));
		hiddenLayerCountSpinner = new JSpinner(new SpinnerNumberModel(0, 0, MAX_HIDDEN_LAYER, 1));

		JLabel lblHiddenNeuronen = new JLabel(Settings.i18n.getString("sidebar.topology.hiddenNeurons"));
		hiddenNeuronSpinner = new JSpinner(new SpinnerNumberModel(0, 0, MAX_NEURONS, 1));

		// is with the hiddenLayerCountSpinner associated

		hiddenLayerDropDown = new JComboBox(hiddenLayerComboModel);
		hiddenLayerDropDown.setEnabled(false);
		hiddenNeuronSpinner.setEnabled(false);

		JPanel mouseModusPanel = new JPanel();
		mouseModusPanel.setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.topology.mouse"), TitledBorder.LEADING,
				TitledBorder.TOP, null, null));

		inputBiasCB = new JCheckBox(Settings.i18n.getString("sidebar.topology.biasInputCB"));
		hiddenBiasCB = new JCheckBox(Settings.i18n.getString("sidebar.topology.biasHiddenCB"));
		hiddenBiasCB.setEnabled(false);

		chckbxAllNeuronsBind = new JCheckBox(Settings.i18n.getString("sidebar.topology.chckbxAllNeuronsBind"));

		btnCreateNetwork = new JButton(Settings.i18n.getString("sidebar.topology.btnCreateNetwork"));

		GroupLayout gl_topologiePanel = new GroupLayout(this);
		gl_topologiePanel.setHorizontalGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_topologiePanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_topologiePanel
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_topologiePanel
														.createSequentialGroup()
														.addComponent(mouseModusPanel, GroupLayout.PREFERRED_SIZE, 360,
																GroupLayout.PREFERRED_SIZE).addContainerGap())
										.addGroup(
												gl_topologiePanel
														.createSequentialGroup()
														.addGroup(
																gl_topologiePanel.createParallelGroup(Alignment.LEADING)
																		.addComponent(lblInputNeuronen).addComponent(lblOutputNeuronen)
																		.addComponent(lblHiddenLayer).addComponent(lblHiddenNeuronen))
														.addGap(78)
														.addGroup(
																gl_topologiePanel
																		.createParallelGroup(Alignment.LEADING, false)
																		.addComponent(outputNeuroSpinner, GroupLayout.PREFERRED_SIZE, 45,
																				GroupLayout.PREFERRED_SIZE)
																		.addComponent(hiddenLayerCountSpinner, GroupLayout.PREFERRED_SIZE,
																				45, GroupLayout.PREFERRED_SIZE)
																		.addComponent(hiddenNeuronSpinner, Alignment.TRAILING,
																				GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
																		.addComponent(inputNeuroSpinner, GroupLayout.PREFERRED_SIZE, 45,
																				GroupLayout.PREFERRED_SIZE))
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addGroup(
																gl_topologiePanel
																		.createParallelGroup(Alignment.LEADING)
																		.addGroup(
																				gl_topologiePanel
																						.createSequentialGroup()
																						.addComponent(hiddenLayerDropDown,
																								GroupLayout.PREFERRED_SIZE, 45,
																								GroupLayout.PREFERRED_SIZE)
																						.addPreferredGap(ComponentPlacement.RELATED)
																						.addComponent(hiddenBiasCB))
																		.addComponent(inputBiasCB)).addContainerGap(35, Short.MAX_VALUE))
										.addGroup(
												gl_topologiePanel.createSequentialGroup().addGap(6).addComponent(chckbxAllNeuronsBind)
														.addPreferredGap(ComponentPlacement.RELATED, 59, Short.MAX_VALUE)
														.addComponent(btnCreateNetwork).addGap(15)))));
		gl_topologiePanel.setVerticalGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_topologiePanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_topologiePanel
										.createParallelGroup(Alignment.LEADING)
										.addComponent(lblInputNeuronen)
										.addGroup(
												gl_topologiePanel
														.createSequentialGroup()
														.addGroup(
																gl_topologiePanel
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(inputBiasCB)
																		.addComponent(inputNeuroSpinner, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
														.addGap(9)
														.addGroup(
																gl_topologiePanel
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(outputNeuroSpinner, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																		.addComponent(lblOutputNeuronen))
														.addGap(11)
														.addGroup(
																gl_topologiePanel
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(hiddenLayerCountSpinner, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																		.addComponent(lblHiddenLayer))
														.addGap(11)
														.addGroup(
																gl_topologiePanel
																		.createParallelGroup(Alignment.BASELINE)
																		.addComponent(hiddenNeuronSpinner, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																		.addComponent(lblHiddenNeuronen)
																		.addComponent(hiddenLayerDropDown, GroupLayout.PREFERRED_SIZE,
																				GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
																		.addComponent(hiddenBiasCB))))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(mouseModusPanel, GroupLayout.PREFERRED_SIZE, 103, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_topologiePanel.createParallelGroup(Alignment.BASELINE).addComponent(chckbxAllNeuronsBind)
										.addComponent(btnCreateNetwork)).addGap(24)));
		/**
		 * internal MausModus-Panel Elements
		 */
		mouseInputRB = new JRadioButton(Settings.i18n.getString("sidebar.topology.mouse.input"));
		mouseInputRB.setSelected(true);
		mouseInputRB.setEnabled(false);
		buttonGroup.add(mouseInputRB);
		mouseOutputRB = new JRadioButton(Settings.i18n.getString("sidebar.topology.mouse.output"));
		buttonGroup.add(mouseOutputRB);
		mouseOutputRB.setEnabled(false);
		mouseHiddenRB = new JRadioButton(Settings.i18n.getString("sidebar.topology.mouse.hidden"));
		buttonGroup.add(mouseHiddenRB);
		mouseHiddenRB.setEnabled(false);
		comboBoxHiddenMausModus = new JComboBox(hiddenLayerComboModel);
		comboBoxHiddenMausModus.setEnabled(false);

		lblJungModis = new JLabel(Settings.i18n.getString("sidebar.topology.mouse.lblJungModis"));
		comboBoxMouseModis = new JComboBox();
		comboBoxMouseModis.setModel(new DefaultComboBoxModel(new String[] { "Picking", "Editing", "Transforming" }));

		GroupLayout gl_mouseModusPanel = new GroupLayout(mouseModusPanel);
		gl_mouseModusPanel.setHorizontalGroup(gl_mouseModusPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_mouseModusPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_mouseModusPanel
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_mouseModusPanel
														.createSequentialGroup()
														.addComponent(lblJungModis)
														.addGap(18)
														.addComponent(comboBoxMouseModis, GroupLayout.PREFERRED_SIZE,
																GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
										.addGroup(
												gl_mouseModusPanel
														.createSequentialGroup()
														.addComponent(mouseInputRB)
														.addGap(26)
														.addComponent(mouseOutputRB)
														.addPreferredGap(ComponentPlacement.UNRELATED)
														.addComponent(mouseHiddenRB)
														.addGap(18)
														.addComponent(comboBoxHiddenMausModus, GroupLayout.PREFERRED_SIZE, 45,
																GroupLayout.PREFERRED_SIZE))).addContainerGap(53, Short.MAX_VALUE)));
		gl_mouseModusPanel.setVerticalGroup(gl_mouseModusPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_mouseModusPanel
						.createSequentialGroup()
						.addGroup(
								gl_mouseModusPanel
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblJungModis)
										.addComponent(comboBoxMouseModis, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(
								gl_mouseModusPanel
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(mouseInputRB)
										.addComponent(mouseOutputRB)
										.addComponent(mouseHiddenRB)
										.addComponent(comboBoxHiddenMausModus, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)).addContainerGap(30, Short.MAX_VALUE)));
		mouseModusPanel.setLayout(gl_mouseModusPanel);
		setLayout(gl_topologiePanel);

		initActions();
	}

	/**
	 * 
	 */
	private void initActions() {

		btnCreateNetwork.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent evt) {
				ac.doAction(Actions.CREATE_NETWORK, new PropertyChangeEvent(btnCreateNetwork, "button", 0, 1));
			}
		});

		comboBoxMouseModis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				ac.doAction(Actions.CHANGE_MOUSE_MODI,
						new PropertyChangeEvent(comboBoxMouseModis, "item", "", comboBoxMouseModis.getSelectedIndex()));
			}
		});
		// input bias
		inputBiasCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				boolean newVal = inputBiasCB.isSelected();
				ac.doAction(Actions.UPDATE_SIDEBAR_CONFIG_INPUT_BIAS_MODEL, new PropertyChangeEvent(inputBiasCB, "checkbox", !newVal,
						newVal));
			}
		});

		// hidden bias
		hiddenBiasCB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				boolean newVal = hiddenBiasCB.isSelected();
				ac.doAction(Actions.UPDATE_SIDEBAR_CONFIG_HIDDEN_BIAS_MODEL, new PropertyChangeEvent(hiddenBiasCB, "checkbox", !newVal,
						newVal));
			}
		});

		hiddenLayerDropDown.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				Integer item = (Integer) hiddenLayerDropDown.getSelectedItem();
				ac.doAction(Actions.UPDATE_SIDEBAR_TOPOLOGY_VIEW, new PropertyChangeEvent(hiddenLayerDropDown, "", null, item));
			}
		});

		// Input Neuronen
		JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) inputNeuroSpinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				ac.doAction(Actions.UPDATE_SIDEBAR_CONFIG_INPUT_NEURON_MODEL, evt);
			}
		});

		// Hidden Neuronen
		editor = (JSpinner.DefaultEditor) hiddenNeuronSpinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				ac.doAction(Actions.UPDATE_SIDEBAR_CONFIG_HIDDEN_NEURON_MODEL, evt);
			}
		});

		// Hidden Layer
		editor = (JSpinner.DefaultEditor) hiddenLayerCountSpinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				ac.doAction(Actions.UPDATE_SIDEBAR_CONFIG_HIDDEN_LAYER_MODEL, evt);
			}
		});

		// Output Neuronen
		editor = (JSpinner.DefaultEditor) outputNeuroSpinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				ac.doAction(Actions.UPDATE_SIDEBAR_CONFIG_OUTPUT_NEURON_MODEL, evt);
			}
		});

	}
}
