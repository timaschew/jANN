/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * Sofia
 */
package de.unikassel.ann.gui.sidebar;

import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.DefaultComboBoxModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JSpinner.DefaultEditor;
import javax.swing.JTextField;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.SOMGui;

/**
 * @author Sofia
 * 
 */
public class SOMTopologyPanel extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected static final String DIM_TEXT_2D = "Quadrat (2D)";
	protected static final String DIM_TEXT_3D = "Würfel (3D)";
	protected static final String DIM_TEXT_4D = "Tesserakt (4D)";
	public SpinnerNumberModel inputModel;
	public SpinnerNumberModel outputModel;
	public SpinnerNumberModel dimensionModel;
	public SpinnerNumberModel visualSize;

	public JSpinner inputSpinner;
	public JSpinner outputDimensionSpinner;
	public JComboBox selectedOutputDimensionCombo;
	public JSpinner outputSpinner;
	public JComboBox somPatternCombo;
	private JLabel lblGre;
	private JTextField dimensionAsStringField;
	private JSpinner visualisationSizeSpinner;
	private SOMGui parent;

	/**
	 * Create the panel.
	 * 
	 * @param somGui
	 */
	public SOMTopologyPanel(final SOMGui parent) {

		this.parent = parent;

		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.topologySOM"), TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setPreferredSize(new Dimension(400, 278));

		JLabel lblInputNeuronSOM = new JLabel(Settings.i18n.getString("sidebar.topologySOM.lblInputNeuronSOM"));

		inputModel = new SpinnerNumberModel(3, 2, 3, 1);
		inputSpinner = new JSpinner(inputModel);

		JLabel lblOutputDimesionSOM = new JLabel(Settings.i18n.getString("sidebar.topologySOM.lblOutputDimesionSOM"));

		dimensionModel = new SpinnerNumberModel(3, 2, 3, 1);
		outputDimensionSpinner = new JSpinner(dimensionModel);

		selectedOutputDimensionCombo = new JComboBox();
		selectedOutputDimensionCombo.setEnabled(false);

		JLabel lblOutputNeuronSOM = new JLabel(Settings.i18n.getString("sidebar.topologySOM.lblOutputNeuronSOM"));

		outputModel = new SpinnerNumberModel(2, 2, 100, 1);
		outputSpinner = new JSpinner(outputModel);

		JLabel lblNeighborhoodTopologySOM = new JLabel(Settings.i18n.getString("sidebar.topologySOM.lblNeighborhoodTopologySOM"));

		JLabel lblPatternSOM = new JLabel(Settings.i18n.getString("sidebar.topologySOM.lblPatternSOM"));

		somPatternCombo = new JComboBox();
		somPatternCombo.setModel(new DefaultComboBoxModel(new String[] { "Eckig" }));

		lblGre = new JLabel("Größe");

		visualSize = new SpinnerNumberModel(300, 10, 500, 10);
		visualisationSizeSpinner = new JSpinner(visualSize);

		dimensionAsStringField = new JTextField();
		dimensionAsStringField.setColumns(10);
		dimensionAsStringField.setText(DIM_TEXT_3D);

		GroupLayout groupLayout = new GroupLayout(this);
		groupLayout.setHorizontalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addGroup(
								groupLayout.createParallelGroup(Alignment.LEADING).addComponent(lblOutputNeuronSOM)
										.addComponent(lblInputNeuronSOM).addComponent(lblOutputDimesionSOM)
										.addComponent(lblNeighborhoodTopologySOM).addComponent(lblPatternSOM)
										.addComponent(lblGre, GroupLayout.PREFERRED_SIZE, 41, GroupLayout.PREFERRED_SIZE))
						.addGap(18)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.LEADING, false)
										.addComponent(visualisationSizeSpinner, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
										.addComponent(inputSpinner, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
										.addComponent(outputDimensionSpinner, GroupLayout.PREFERRED_SIZE, 60, GroupLayout.PREFERRED_SIZE)
										.addGroup(
												groupLayout
														.createSequentialGroup()
														.addComponent(outputSpinner, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE)
														.addGap(18)
														.addComponent(selectedOutputDimensionCombo, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE))
										.addComponent(somPatternCombo, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
										.addComponent(dimensionAsStringField)).addContainerGap(72, Short.MAX_VALUE)));
		groupLayout.setVerticalGroup(groupLayout.createParallelGroup(Alignment.LEADING).addGroup(
				groupLayout
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblInputNeuronSOM)
										.addComponent(inputSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblOutputDimesionSOM)
										.addComponent(outputDimensionSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblOutputNeuronSOM)
										.addComponent(outputSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(selectedOutputDimensionCombo, GroupLayout.PREFERRED_SIZE, 29,
												GroupLayout.PREFERRED_SIZE))
						.addGap(11)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblNeighborhoodTopologySOM)
										.addComponent(dimensionAsStringField, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addGap(11)
						.addGroup(
								groupLayout.createParallelGroup(Alignment.BASELINE)
										.addComponent(somPatternCombo, GroupLayout.PREFERRED_SIZE, 29, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblPatternSOM))
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addGroup(
								groupLayout
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(visualisationSizeSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE).addComponent(lblGre)).addContainerGap()));
		setLayout(groupLayout);

		initListeners();
	}

	private void initListeners() {

		DefaultEditor editor = (JSpinner.DefaultEditor) outputDimensionSpinner.getEditor();
		editor.getTextField().addPropertyChangeListener("value", new PropertyChangeListener() {
			@Override
			public void propertyChange(final PropertyChangeEvent evt) {
				Integer value = (Integer) evt.getNewValue();
				int v = value;
				switch (v) {
				case 2:
					dimensionAsStringField.setText(DIM_TEXT_2D);
					break;
				case 3:
					dimensionAsStringField.setText(DIM_TEXT_3D);
					break;
				case 4:
					dimensionAsStringField.setText(DIM_TEXT_4D);
					break;
				default:
					dimensionAsStringField.setText("");
				}
			}
		});
	}
}
