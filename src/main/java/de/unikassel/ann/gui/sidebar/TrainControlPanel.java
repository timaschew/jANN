package de.unikassel.ann.gui.sidebar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.SwingWorker;
import javax.swing.border.TitledBorder;

import de.unikassel.ann.algo.BackPropagation;
import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.chart.ChartTrainingErrorPanel;
import de.unikassel.ann.io.tasks.TrainWorker;
import de.unikassel.ann.model.DataPairSet;
import de.unikassel.ann.util.Logger;

public class TrainControlPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	public JSpinner delaySpinner;
	public JButton btnPlay;
	private JButton btnReset;

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

		btnReset = new JButton("Gewichte-Reset");
		GroupLayout gl_trainingSteuerungPanel = new GroupLayout(this);
		gl_trainingSteuerungPanel
				.setHorizontalGroup(gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING).addGroup(
						gl_trainingSteuerungPanel
								.createSequentialGroup()
								.addContainerGap()
								.addGroup(
										gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING).addComponent(lblVerzoegerung)
												.addComponent(btnPlay))
								.addPreferredGap(ComponentPlacement.RELATED, 93, Short.MAX_VALUE)
								.addGroup(
										gl_trainingSteuerungPanel
												.createParallelGroup(Alignment.LEADING, false)
												.addGroup(
														gl_trainingSteuerungPanel
																.createSequentialGroup()
																.addComponent(delaySpinner, GroupLayout.PREFERRED_SIZE, 60,
																		GroupLayout.PREFERRED_SIZE)
																.addPreferredGap(ComponentPlacement.RELATED).addComponent(lblMs))
												.addComponent(btnReset)).addContainerGap(60, Short.MAX_VALUE)));
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
										.addComponent(lblVerzoegerung))
						.addGap(18)
						.addGroup(
								gl_trainingSteuerungPanel.createParallelGroup(Alignment.LEADING).addComponent(btnPlay)
										.addComponent(btnReset)).addContainerGap(18, Short.MAX_VALUE)));
		setLayout(gl_trainingSteuerungPanel);

		initListeners();
	}

	/**
	 * 
	 */
	private void initListeners() {

		btnReset.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				NetConfig net = Settings.getInstance().getCurrentSession().getNetworkConfig();
				StandardOptionsPanel panel = Main.instance.sidebar.standardOptionsPanel;
				double min = panel.getInitialWeightMin();
				double max = panel.getInitialWeightMax();
				net.setInitMaxWeight(max);
				net.setInitMinWeight(min);
				net.initWeights();
				GraphController.getInstance().repaint();
			}
		});

		btnPlay.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				NetConfig net = Settings.getInstance().getCurrentSession().getNetworkConfig();

				if (net.getTrainingData() == null || net.getTrainingData().getRows() == 0) {
					JFrame frame = new JFrame();
					JOptionPane.showMessageDialog(frame, "Es existieren keine Trainingsdaten ", "Warnung", JOptionPane.WARNING_MESSAGE);
				} else {
					// copy of train data
					DataPairSet trainingDat = new DataPairSet(net.getTrainingData());

					int trainInLenght = trainingDat.getPairs().get(0).getInput().length;
					int trainOutLenght = trainingDat.getPairs().get(0).getIdeal().length;
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

					// Start worker
					TrainWorker worker = new TrainWorker(net, train, trainingDat);
					JungUpdateWorker jungUpdateWorker = new JungUpdateWorker(worker);
					jungUpdateWorker.execute();
					worker.execute();
					worker.addPropertyChangeListener(new PropertyChangeListener() {
						@Override
						public void propertyChange(final PropertyChangeEvent evt) {
							String newValue = evt.getNewValue().toString();
							if (newValue.equalsIgnoreCase("STARTED")) {
								// Disable button
								btnPlay.setEnabled(false);
							} else if (newValue.equalsIgnoreCase("DONE")) {
								// Enable button
								btnPlay.setEnabled(true);
							}
						}
					});
				}
			}
		});
	}

	class JungUpdateWorker extends SwingWorker<Void, Void> {
		private TrainWorker trainWorker;

		/**
		 * @param worker
		 */
		public JungUpdateWorker(final TrainWorker worker) {
			trainWorker = worker;
		}

		@Override
		protected Void doInBackground() throws Exception {
			NetConfig netCfg = Settings.getInstance().getCurrentSession().getNetworkConfig();
			BackPropagation bp = (BackPropagation) netCfg.getTrainingModule();
			ChartTrainingErrorPanel chart = Main.instance.trainingErrorChartPanel;
			while (trainWorker.isDone() == false) {
				GraphController.getInstance().repaint();
				Integer iteration = bp.getCurrentIteration();
				Double error = bp.getCurrentError();
				if (iteration != Double.NaN || error != Double.NaN) {
					chart.addToCurrentSeries(iteration, error);
				}

			}
			return null;
		}

	}
}
