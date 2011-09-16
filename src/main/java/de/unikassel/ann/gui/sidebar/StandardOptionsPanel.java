package de.unikassel.ann.gui.sidebar;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

public class StandardOptionsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	private JComboBox funktionToActivateCombo;
	private JRadioButton rdbtnRandomInitialWeight;
	private JRadioButton rdbtnExactInitialWeight;
	private JSpinner RandomInitialWeightSpinner1;
	private JSpinner RandomInitialWeightSpinner2;
	private JSpinner exactInitialWeightSpinner;

	/**
	 * Create the panel.
	 */
	public StandardOptionsPanel() {
		setBorder(new TitledBorder(null, "Standard Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setSize(400, 132);

		JLabel lblFunktionToActivate = new JLabel("Aktivierungsfunktion");
		JLabel lblInitialWeight = new JLabel("Initialgewicht");

		funktionToActivateCombo = new JComboBox();
		funktionToActivateCombo.setModel(new DefaultComboBoxModel(new String[] { "Sigmoid", "TanH" }));

		rdbtnRandomInitialWeight = new JRadioButton("Zufällig");
		rdbtnExactInitialWeight = new JRadioButton("Exakt");

		RandomInitialWeightSpinner1 = new JSpinner();
		RandomInitialWeightSpinner1.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));

		RandomInitialWeightSpinner2 = new JSpinner();
		RandomInitialWeightSpinner2.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));

		exactInitialWeightSpinner = new JSpinner();
		exactInitialWeightSpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout.createParallelGroup(Alignment.TRAILING)
										.addGroup(groupLayout.createSequentialGroup().addComponent(lblFunktionToActivate).addGap(18))
										.addGroup(groupLayout.createSequentialGroup().addComponent(lblInitialWeight).addGap(53)))
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING, false)
										.addGroup(
												groupLayout
														.createSequentialGroup()
														.addGroup(
																groupLayout
																		.createParallelGroup(Alignment.LEADING, false)
																		.addGroup(
																				groupLayout
																						.createSequentialGroup()
																						.addComponent(rdbtnExactInitialWeight)
																						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
																						.addComponent(exactInitialWeightSpinner, GroupLayout.PREFERRED_SIZE, 45,
																								GroupLayout.PREFERRED_SIZE))
																		.addGroup(
																				groupLayout
																						.createSequentialGroup()
																						.addComponent(rdbtnRandomInitialWeight)
																						.addGap(16)
																						.addComponent(RandomInitialWeightSpinner1, GroupLayout.PREFERRED_SIZE, 45,
																								GroupLayout.PREFERRED_SIZE))).addGap(18)
														.addComponent(RandomInitialWeightSpinner2, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE))
										.addComponent(funktionToActivateCombo, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
						.addContainerGap(26, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(
						groupLayout
								.createSequentialGroup()
								.addGroup(
										groupLayout
												.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblFunktionToActivate)
												.addComponent(funktionToActivateCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.UNRELATED)
								.addGroup(
										groupLayout
												.createParallelGroup(Alignment.BASELINE)
												.addComponent(lblInitialWeight)
												.addComponent(rdbtnRandomInitialWeight)
												.addComponent(RandomInitialWeightSpinner2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)
												.addComponent(RandomInitialWeightSpinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addGroup(
										groupLayout
												.createParallelGroup(Alignment.BASELINE)
												.addComponent(rdbtnExactInitialWeight)
												.addComponent(exactInitialWeightSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
														GroupLayout.PREFERRED_SIZE)).addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		setLayout(groupLayout);
	}
}
