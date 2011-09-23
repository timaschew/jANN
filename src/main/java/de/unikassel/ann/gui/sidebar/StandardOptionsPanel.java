package de.unikassel.ann.gui.sidebar;

import javax.swing.ButtonGroup;
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

import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.model.func.SigmoidFunction;
import de.unikassel.ann.model.func.TanHFunction;

public class StandardOptionsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public JComboBox funktionToActivateCombo;
	public JRadioButton rdbtnRandomInitialWeight;
	public JRadioButton rdbtnExactInitialWeight;
	public JSpinner RandomInitialWeightSpinner1;
	public JSpinner RandomInitialWeightSpinner2;
	public JSpinner exactInitialWeightSpinner;

	public final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the panel.
	 */
	public StandardOptionsPanel() {
		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.standardOptions"), TitledBorder.LEADING, TitledBorder.TOP, null,
				null));
		setSize(400, 160);

		JLabel lblFunktionToActivate = new JLabel(Settings.i18n.getString("sidebar.standardOptions.activatedFunction"));
		JLabel lblInitialWeight = new JLabel(Settings.i18n.getString("sidebar.standardOptions.initialWeigth"));

		funktionToActivateCombo = new JComboBox();
		funktionToActivateCombo.setModel(new DefaultComboBoxModel(new String[] { SigmoidFunction.class.getSimpleName(),
				TanHFunction.class.getSimpleName() }));

		rdbtnRandomInitialWeight = new JRadioButton(Settings.i18n.getString("sidebar.standardOptions.randomRB"));
		buttonGroup.add(rdbtnRandomInitialWeight);
		rdbtnExactInitialWeight = new JRadioButton(Settings.i18n.getString("sidebar.standardOptions.exactRB"));
		buttonGroup.add(rdbtnExactInitialWeight);

		RandomInitialWeightSpinner1 = new JSpinner();
		RandomInitialWeightSpinner1.setModel(new SpinnerNumberModel(new Double(0.0), null, null, new Double(0.1)));

		RandomInitialWeightSpinner2 = new JSpinner();
		RandomInitialWeightSpinner2.setModel(new SpinnerNumberModel(new Double(0.0), null, null, new Double(0.1)));

		exactInitialWeightSpinner = new JSpinner();
		exactInitialWeightSpinner.setModel(new SpinnerNumberModel(new Double(0.0), null, null, new Double(0.1)));
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
										.addComponent(funktionToActivateCombo, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addGroup(
												groupLayout
														.createSequentialGroup()
														.addComponent(rdbtnExactInitialWeight)
														.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE,
																Short.MAX_VALUE)
														.addComponent(exactInitialWeightSpinner, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE))
										.addGroup(
												groupLayout
														.createSequentialGroup()
														.addComponent(rdbtnRandomInitialWeight)
														.addGap(16)
														.addComponent(RandomInitialWeightSpinner1, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE))).addGap(18)
						.addComponent(RandomInitialWeightSpinner2, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(33, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
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
												GroupLayout.PREFERRED_SIZE)).addContainerGap(16, Short.MAX_VALUE)));
		setLayout(groupLayout);
	}
}
