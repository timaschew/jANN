package de.unikassel.vis;

import javax.swing.JPanel;
import javax.swing.border.TitledBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.JRadioButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JSpinner;
import javax.swing.DefaultComboBoxModel;

public class StandardOptions extends JPanel {

	/**
	 * Create the panel.
	 */
	public StandardOptions() {
		setBorder(new TitledBorder(null, "Standard-Einstellung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblActivationFunctionByOptions = new JLabel("Aktivierungsfunktion");
		
		JLabel lblInitialWeight = new JLabel("Initialgewicht");
		
		JRadioButton rdbtnRandom = new JRadioButton("Zufällig");
		
		JRadioButton rdbtnExact = new JRadioButton("Exakt");
		
		JSpinner spinnerRandom1 = new JSpinner();
		
		JSpinner spinnerRandom2 = new JSpinner();
		
		JSpinner spinnerExact = new JSpinner();
		
		JComboBox comboBoxActivFunktionByOptions = new JComboBox();
		comboBoxActivFunktionByOptions.setModel(new DefaultComboBoxModel(new String[] {"Sigmoid", "Test"}));
		GroupLayout groupLayout2 = new GroupLayout(this);
		groupLayout2.setHorizontalGroup(
			groupLayout2.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout2.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout2.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout2.createSequentialGroup()
							.addComponent(lblActivationFunctionByOptions)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(comboBoxActivFunktionByOptions, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout2.createSequentialGroup()
							.addComponent(lblInitialWeight)
							.addGap(18)
							.addGroup(groupLayout2.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout2.createSequentialGroup()
									.addComponent(rdbtnRandom)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(spinnerRandom1, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout2.createSequentialGroup()
									.addComponent(rdbtnExact)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(spinnerExact, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinnerRandom2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(193, Short.MAX_VALUE))
		);
		groupLayout2.setVerticalGroup(
			groupLayout2.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout2.createSequentialGroup()
					.addGroup(groupLayout2.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout2.createSequentialGroup()
							.addGap(7)
							.addComponent(comboBoxActivFunktionByOptions, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout2.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnRandom)
								.addComponent(spinnerRandom1, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinnerRandom2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout2.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnExact)
								.addComponent(spinnerExact, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout2.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblActivationFunctionByOptions)
							.addGap(18)
							.addComponent(lblInitialWeight)))
					.addContainerGap(185, Short.MAX_VALUE))
		);
		setLayout(groupLayout2);

	}

}
