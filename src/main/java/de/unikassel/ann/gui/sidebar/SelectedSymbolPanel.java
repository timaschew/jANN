package de.unikassel.ann.gui.sidebar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.controller.Settings;


public class SelectedSymbolPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JTextField fieldSelected;
	public JComboBox activatedFunktioncomboBox;
	public JSpinner neuroInputBySelectSpinner;
	public JSpinner spinnerSynapsWeight;
	

	/**
	 * Create the frame.
	 */
	public SelectedSymbolPanel() {
		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.selectedSymbol"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setSize(400, 180);

		JLabel lblSelected = new JLabel(Settings.i18n.getString("sidebar.selectedSymbol.selected"));

		JLabel lblSynapseWeight = new JLabel(Settings.i18n.getString("sidebar.selectedSymbol.synapseWeigth"));

		JLabel lblNeuroInput = new JLabel(Settings.i18n.getString("sidebar.selectedSymbol.neuronsInput"));

		JLabel lblActivationFunction = new JLabel(Settings.i18n.getString("sidebar.selectedSymbol.activatedFunction"));

		fieldSelected = new JTextField();
		fieldSelected.setEditable(false);
		fieldSelected.setColumns(10);

		activatedFunktioncomboBox = new JComboBox();
		activatedFunktioncomboBox.setModel(new DefaultComboBoxModel(new String[] { "Sigmoid", "Test" }));

		neuroInputBySelectSpinner = new JSpinner();
		neuroInputBySelectSpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));

		spinnerSynapsWeight = new JSpinner();
		GroupLayout gl_SelectedSymbolPanel = new GroupLayout(this);
		gl_SelectedSymbolPanel.setHorizontalGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_SelectedSymbolPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING).addComponent(lblActivationFunction)
										.addComponent(lblNeuroInput).addComponent(lblSelected).addComponent(lblSynapseWeight))
						.addGap(41)
						.addGroup(
								gl_SelectedSymbolPanel.createParallelGroup(Alignment.TRAILING, false)
										.addComponent(neuroInputBySelectSpinner, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(spinnerSynapsWeight, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(fieldSelected).addComponent(activatedFunktioncomboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addGap(62)));
		gl_SelectedSymbolPanel.setVerticalGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_SelectedSymbolPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_SelectedSymbolPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblSelected)
										.addComponent(fieldSelected, GroupLayout.PREFERRED_SIZE, 20, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								gl_SelectedSymbolPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblSynapseWeight)
										.addComponent(spinnerSynapsWeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
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
										.addComponent(activatedFunktioncomboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)).addContainerGap(29, Short.MAX_VALUE)));
		setLayout(gl_SelectedSymbolPanel);
	}

}
