package de.unikassel.ann.gui.sidebar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.algo.BackPropagation;
import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.io.tasks.TrainWorker;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.util.Logger;

public class TrainControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JSpinner delaySpinner;
	public JButton btnPlay;

	/**
	 * Create the frame.
	 */
	public TrainControlPanel() {

		setBorder(new TitledBorder(null, Settings.i18n.getString("sidebar.trainControl"), TitledBorder.LEADING, TitledBorder.TOP, null,
				null));
		setSize(400, 138);

		JLabel lblVerzoegerung = new JLabel(Settings.i18n.getString("sidebar.trainControl.lblVerzoegerung"));
		delaySpinner = new JSpinner();
		delaySpinner.setEnabled(false);

		JLabel lblMs = new JLabel(Settings.i18n.getString("sidebar.trainControl.lblMs"));
		btnPlay = new JButton("Training starten");
		GroupLayout gl_trainingSteuerungPanel = new GroupLayout(this);
		gl_trainingSteuerungPanel.setHorizontalGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_trainingSteuerungPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_trainingSteuerungPanel
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_trainingSteuerungPanel
														.createSequentialGroup()
														.addComponent(lblVerzoegerung)
														.addPreferredGap(ComponentPlacement.RELATED, 138, Short.MAX_VALUE)
														.addComponent(delaySpinner, GroupLayout.PREFERRED_SIZE, 60,
																GroupLayout.PREFERRED_SIZE).addPreferredGap(ComponentPlacement.RELATED)
														.addComponent(lblMs).addGap(71))
										.addGroup(
												gl_trainingSteuerungPanel.createSequentialGroup().addComponent(btnPlay)
														.addContainerGap(315, Short.MAX_VALUE)))));
		gl_trainingSteuerungPanel.setVerticalGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING).addGroup(
				gl_trainingSteuerungPanel
						.createSequentialGroup()
						.addContainerGap()
						.addGroup(
								gl_trainingSteuerungPanel
										.createParallelGroup(Alignment.LEADING)
										.addGroup(
												gl_trainingSteuerungPanel
														.createParallelGroup(Alignment.BASELINE)
														.addComponent(delaySpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE,
																GroupLayout.PREFERRED_SIZE).addComponent(lblMs))
										.addComponent(lblVerzoegerung)).addGap(18).addComponent(btnPlay)
						.addContainerGap(126, Short.MAX_VALUE)));
		setLayout(gl_trainingSteuerungPanel);

		initListeners();
	}

	/**
	 * 
	 */
	private void initListeners() {
		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				NetConfig net = Settings.getInstance().getCurrentSession().getNetworkConfig();

				if (net.getTrainingData() == null || net.getTrainingData().getRows() == 0) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Es existieren keine Trainingsdaten ", "Warnung", JOptionPane.WARNING_MESSAGE);
				} else {
					// copy of train data
					DataPairSet testData = new DataPairSet(net.getTrainingData());

					int trainInLenght = testData.getPairs().get(0).getInput().length;
					int trainOutLenght = testData.getPairs().get(0).getIdeal().length;
					int netInLenght = net.getNetwork().getInputSizeIgnoringBias();
					int netOutLength = net.getNetwork().getOutputSize();
					if (trainInLenght != netInLenght || trainOutLenght != netOutLength) {
						Logger.warn(this.getClass(), "Trainingsdaten {} Input {} Output passten nicht zur Topology {} Input und {} Output",
								trainInLenght, trainOutLenght, netInLenght, netOutLength);
						return;
					}

					BackPropagation train = (BackPropagation) net.getTrainingModule();
					Double learnRate = (Double) Main.instance.sidebar.trainStrategyPanel.spinnerLearnRate.getValue();
					Double momentum = (Double) Main.instance.sidebar.trainStrategyPanel.spinnerMomentum.getValue();
					Boolean batchMode = Main.instance.sidebar.trainStrategyPanel.chbBatchMode.isSelected();
					train.setBatchMode(batchMode);
					train.setLearnRate(learnRate);
					train.setMomentum(momentum);

					// Disable button
					btnPlay.setEnabled(false);

					// Start worker
					TrainWorker worker = new TrainWorker(net, train, testData);
					worker.execute();
				}
			}
		});
	}
}
