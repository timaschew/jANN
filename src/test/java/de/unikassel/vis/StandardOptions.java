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
		
		JLabel lblAktivierungsfunktion = new JLabel("Aktivierungsfunktion");
		
		JLabel lblInitialgewicht = new JLabel("Initialgewicht");
		
		JRadioButton rdbtnZufllig = new JRadioButton("Zufällig");
		
		JRadioButton rdbtnExakt = new JRadioButton("Exakt");
		
		JSpinner spinnerRandom = new JSpinner();
		
		JSpinner spinnerRandom2 = new JSpinner();
		
		JSpinner spinnerExact = new JSpinner();
		
		JComboBox comboBoxAktivFunktion = new JComboBox();
		comboBoxAktivFunktion.setModel(new DefaultComboBoxModel(new String[] {"Sigmoid", "Test"}));
		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblAktivierungsfunktion)
							.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
							.addComponent(comboBoxAktivFunktion, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
						.addGroup(groupLayout.createSequentialGroup()
							.addComponent(lblInitialgewicht)
							.addGap(18)
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(rdbtnZufllig)
									.addPreferredGap(ComponentPlacement.RELATED)
									.addComponent(spinnerRandom, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
								.addGroup(groupLayout.createSequentialGroup()
									.addComponent(rdbtnExakt)
									.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
									.addComponent(spinnerExact, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addComponent(spinnerRandom2, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(193, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createSequentialGroup()
							.addGap(7)
							.addComponent(comboBoxAktivFunktion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnZufllig)
								.addComponent(spinnerRandom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
								.addComponent(spinnerRandom2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
								.addComponent(rdbtnExakt)
								.addComponent(spinnerExact, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
						.addGroup(groupLayout.createSequentialGroup()
							.addContainerGap()
							.addComponent(lblAktivierungsfunktion)
							.addGap(18)
							.addComponent(lblInitialgewicht)))
					.addContainerGap(185, Short.MAX_VALUE))
		);
		setLayout(groupLayout);

	}

}
