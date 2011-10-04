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
import de.unikassel.ann.model.Neuron;
import de.unikassel.ann.model.func.ActivationFunction;
import de.unikassel.ann.model.func.SigmoidFunction;
import de.unikassel.ann.model.func.TanHFunction;
import de.unikassel.ann.util.FormatHelper;
import de.unikassel.ann.util.Logger;

public class StandardOptionsPanel extends JPanel {

	private static final long serialVersionUID = 1L;

	public JComboBox funktionToActivateCombo;
	public JRadioButton rdbtnRandomInitialWeight;
	public JRadioButton rdbtnExactInitialWeight;
	public JSpinner randomInitialWeightSpinnerMin;
	public JSpinner randomInitialWeightSpinnerMax;

	public final ButtonGroup buttonGroup = new ButtonGroup();

	/**
	 * Create the panel.
	 */
	public StandardOptionsPanel() {
		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.standardOptions"), TitledBorder.LEADING, TitledBorder.TOP, null,
				null));
		setSize(400, 100);

		JLabel lblFunktionToActivate = new JLabel(Settings.i18n.getString("sidebar.standardOptions.activatedFunction"));
		JLabel lblInitialWeight = new JLabel(Settings.i18n.getString("sidebar.standardOptions.initialWeigth"));

		funktionToActivateCombo = new JComboBox();
		funktionToActivateCombo.setModel(new DefaultComboBoxModel(new String[] { SigmoidFunction.class.getSimpleName(),
				TanHFunction.class.getSimpleName() }));

		rdbtnRandomInitialWeight = new JRadioButton(Settings.i18n.getString("sidebar.standardOptions.randomRB"));
		buttonGroup.add(rdbtnRandomInitialWeight);
		rdbtnRandomInitialWeight.setSelected(true);

		randomInitialWeightSpinnerMin = new JSpinner();
		randomInitialWeightSpinnerMin.setModel(new SpinnerNumberModel(new Double(-2.0), null, null, new Double(0.1)));
		randomInitialWeightSpinnerMin.setEnabled(true);
		randomInitialWeightSpinnerMax = new JSpinner();
		randomInitialWeightSpinnerMax.setModel(new SpinnerNumberModel(new Double(2.0), null, null, new Double(0.1)));
		randomInitialWeightSpinnerMax.setEnabled(true);

		JLabel lblBis = new JLabel(Settings.i18n.getString("sidebar.standardOptions.until"));

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
														.addComponent(randomInitialWeightSpinnerMin, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE)
														.addGap(18)
														.addComponent(lblBis)
														.addGap(18)
														.addComponent(randomInitialWeightSpinnerMax, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE))
										.addComponent(funktionToActivateCombo, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)).addGap(51)));
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
										.addComponent(randomInitialWeightSpinnerMin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(lblBis)
										.addComponent(randomInitialWeightSpinnerMax, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)).addContainerGap(16, Short.MAX_VALUE)));
		setLayout(groupLayout);
	}

	public ActivationFunction getStandardActivationFunction() {
		String activationFunctionName = (String) funktionToActivateCombo.getSelectedItem();
		ActivationFunction activation = new SigmoidFunction();
		Class<?> clazz;
		try {
			clazz = Class.forName(Neuron.functionPackage + "." + activationFunctionName);
			activation = (ActivationFunction) clazz.newInstance();
		} catch (Exception e) {
			Logger.error(this.getClass(),
					"could not instantiate function with name: " + activationFunctionName + "reason{} " + e.getMessage());
		}
		return activation;
	}

	public double getInitialWeightMin() {
		Object minValue = randomInitialWeightSpinnerMin.getValue();
		double min = FormatHelper.parse2Double(minValue);
		return min;
	}

	public double getInitialWeightMax() {
		Object maxValue = randomInitialWeightSpinnerMax.getValue();
		double max = FormatHelper.parse2Double(maxValue);
		return max;
	}

	public double getRandomInitialWeight() {
		double min = getInitialWeightMin();
		double max = getInitialWeightMax();
		if (min > max) {
			double temp = min;
			min = max;
			max = temp;
		}
		double weight = new Double(Math.random() * (Math.abs(min) + Math.abs(max))) - Math.abs(min);
		return weight;
	}

}
