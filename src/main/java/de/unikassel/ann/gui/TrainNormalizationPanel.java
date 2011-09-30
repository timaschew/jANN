package de.unikassel.ann.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SpinnerNumberModel;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.Main.Panel;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.normalize.NormUtil;

public class TrainNormalizationPanel extends JDialog {

	private static final long serialVersionUID = 1L;
	private static TrainNormalizationPanel trainDataPanelInstance;

	public ButtonGroup buttonGroup = new ButtonGroup();

	public JButton btnShowTrainingsdata;
	public JSpinner spinnerMin;
	public JSpinner spinnerMax;
	public JButton btnApplyData;
	public JButton btnCancel;

	/**
	 * Create the frame.
	 */
	public TrainNormalizationPanel() {
		setTitle(Settings.i18n.getString("menu.options.trainData"));
		setModal(true);
		setSize(295, 190);
		setResizable(false);
		setLocationRelativeTo(null);

		JPanel trainingsDatenNormalPanel = new JPanel();

		btnShowTrainingsdata = new JButton(Settings.i18n.getString("menu.options.trainData.btnShowTrainingsdata"));

		JLabel lblMin = new JLabel(Settings.i18n.getString("menu.options.trainData.lblMin"));

		JLabel lblMax = new JLabel(Settings.i18n.getString("menu.options.trainData.lblMax"));

		JLabel lblScale = new JLabel(Settings.i18n.getString("menu.options.trainData.lblScale"));
		spinnerMin = new JSpinner(new SpinnerNumberModel(new Double(-1.0), null, null, new Double(0.1)));

		spinnerMax = new JSpinner(new SpinnerNumberModel(new Double(1), null, null, new Double(0.1)));

		btnApplyData = new JButton(Settings.i18n.getString("menu.options.trainData.btnApplyData"));

		btnCancel = new JButton(Settings.i18n.getString("Schließen"));

		GroupLayout gl_trainingsDatenNormalPanel = new GroupLayout(trainingsDatenNormalPanel);
		gl_trainingsDatenNormalPanel
				.setHorizontalGroup(gl_trainingsDatenNormalPanel
						.createParallelGroup(Alignment.LEADING)
						.addGroup(
								gl_trainingsDatenNormalPanel
										.createSequentialGroup()
										.addContainerGap()
										.addGroup(
												gl_trainingsDatenNormalPanel
														.createParallelGroup(Alignment.LEADING)
														.addComponent(btnShowTrainingsdata)
														.addGroup(
																gl_trainingsDatenNormalPanel
																		.createSequentialGroup()
																		.addGroup(
																				gl_trainingsDatenNormalPanel
																						.createParallelGroup(Alignment.LEADING)
																						.addComponent(lblScale).addComponent(btnApplyData))
																		.addPreferredGap(ComponentPlacement.RELATED)
																		.addGroup(
																				gl_trainingsDatenNormalPanel
																						.createParallelGroup(Alignment.LEADING)
																						.addGroup(
																								gl_trainingsDatenNormalPanel
																										.createSequentialGroup()
																										.addGroup(
																												gl_trainingsDatenNormalPanel
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																spinnerMin,
																																GroupLayout.PREFERRED_SIZE,
																																60,
																																GroupLayout.PREFERRED_SIZE)
																														.addComponent(
																																lblMin))
																										.addPreferredGap(
																												ComponentPlacement.UNRELATED)
																										.addGroup(
																												gl_trainingsDatenNormalPanel
																														.createParallelGroup(
																																Alignment.LEADING)
																														.addComponent(
																																lblMax)
																														.addComponent(
																																spinnerMax,
																																GroupLayout.PREFERRED_SIZE,
																																60,
																																GroupLayout.PREFERRED_SIZE)))
																						.addGroup(
																								gl_trainingsDatenNormalPanel
																										.createSequentialGroup()
																										.addGap(100)
																										.addComponent(btnCancel)))))
										.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)));
		gl_trainingsDatenNormalPanel.setVerticalGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_trainingsDatenNormalPanel
						.createSequentialGroup()
						.addContainerGap()
						.addComponent(btnShowTrainingsdata)
						.addGap(6)
						.addGroup(
								gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.BASELINE).addComponent(lblMin)
										.addComponent(lblMax))
						.addPreferredGap(ComponentPlacement.RELATED, 6, Short.MAX_VALUE)
						.addGroup(
								gl_trainingsDatenNormalPanel
										.createParallelGroup(Alignment.BASELINE)
										.addComponent(lblScale)
										.addComponent(spinnerMin, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE)
										.addComponent(spinnerMax, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
												GroupLayout.PREFERRED_SIZE))
						.addGap(32)
						.addGroup(
								gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.BASELINE).addComponent(btnCancel)
										.addComponent(btnApplyData)).addGap(80)));
		trainingsDatenNormalPanel.setLayout(gl_trainingsDatenNormalPanel);
		getContentPane().add(trainingsDatenNormalPanel);

		initActions();
	}

	/**
	 * 
	 */
	private void initActions() {

		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent event) {
				dispose();
			}
		});

		btnShowTrainingsdata.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				Main.instance.switchBottomPanel(Panel.TRAIN_DATA_CHART);
			}
		});

		btnApplyData.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				NetConfig net = Settings.getInstance().getCurrentSession().getNetworkConfig();
				if (net.getTrainingData() == null) {
					return;
				}

				double spiMax = (Double) spinnerMax.getValue();
				double spiMin = (Double) spinnerMin.getValue();
				DataPairSet dataPairSet = NormUtil.normalize(net.getTrainingData(), spiMin, spiMax);
				net.setTrainingData(dataPairSet);
				Main.instance.trainingDataChartPanel.updateTrainingData();
				Main.instance.switchBottomPanel(Panel.TRAIN_DATA_CHART);
			}
		});

		btnShowTrainingsdata.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				Main.instance.switchBottomPanel(Panel.TRAIN_DATA_CHART);

			}
		});

	}
}
