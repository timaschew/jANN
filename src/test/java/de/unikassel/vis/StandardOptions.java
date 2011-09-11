package de.unikassel.vis;

import java.awt.Color;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import javax.swing.SpinnerNumberModel;

public class StandardOptions extends JPanel {

	/**
	 * Create the panel.
	 */
	public StandardOptions() {
		setBorder(new TitledBorder(null, "Standard Options", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setSize(400, 120);
		
		JLabel lblFunktionToActivate = new JLabel("Aktivierungsfunktion");
		
		JLabel lblInitialWeight = new JLabel("Initialgewicht");
		
		JComboBox funktionToActivateCombo = new JComboBox();
		funktionToActivateCombo.setModel(new DefaultComboBoxModel(new String[] {"Sigmoid", "Test"}));
		
		JRadioButton rdbtnRandomInitialWeight = new JRadioButton("Zufällig");
		
		JRadioButton rdbtnExactInitialWeight = new JRadioButton("Exakt");
		
		JSpinner RandomInitialWeightSpinner1 = new JSpinner();
		RandomInitialWeightSpinner1.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		
		JSpinner RandomInitialWeightSpinner2 = new JSpinner();
		RandomInitialWeightSpinner2.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		
		JSpinner exactInitialWeightSpinner = new JSpinner();
		exactInitialWeightSpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, groupLayout.createSequentialGroup()
							.addComponent(lblFunktionToActivate)
							.addGap(18))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblInitialWeight)
							.addGap(53)))
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnExactInitialWeight)
							.addGap(18)
							.addComponent(exactInitialWeightSpinner, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
						.addComponent(funktionToActivateCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(rdbtnRandomInitialWeight)
							.addGap(6)
							.addComponent(RandomInitialWeightSpinner1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(RandomInitialWeightSpinner2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(122, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblFunktionToActivate)
						.addComponent(funktionToActivateCombo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInitialWeight)
						.addComponent(rdbtnRandomInitialWeight)
						.addComponent(RandomInitialWeightSpinner1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(RandomInitialWeightSpinner2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(rdbtnExactInitialWeight)
						.addComponent(exactInitialWeightSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(60, Short.MAX_VALUE))
		);
		setLayout(groupLayout);
	}
}
