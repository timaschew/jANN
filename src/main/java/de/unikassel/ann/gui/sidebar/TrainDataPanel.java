package de.unikassel.ann.gui.sidebar;

import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


public class TrainDataPanel extends JDialog {

	private static final long serialVersionUID = 1L;
	private static TrainDataPanel trainDataPanelInstance;
	

	public static TrainDataPanel getTrainDataPanelInstance() {
		if (trainDataPanelInstance == null) {
			trainDataPanelInstance = new TrainDataPanel();
		}
		return trainDataPanelInstance;
	}


	private JButton btnShowTrainingsdata;
	private JCheckBox checkBoxInput;
	private JCheckBox checkBoxOutput;
	private JSpinner spinnerInputFrom;
	private JSpinner spinnerInputTo;
	private JSpinner spinnerOutputFrom;
	private JSpinner spinnerOutputTo;
	private JRadioButton rdbtnInvertOutputYes;
	private JRadioButton rdbtnInvertOutputNo;
	private JButton btnPreviewData;
	private JButton btnApplyData;
	private JButton btnCancel;
	

	/**
	 * Create the frame.
	 */
	private TrainDataPanel() {
		setTitle("Trainingsdaten normalisieren");
		setModal(true);
		setSize(350, 260);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		JPanel trainingsDatenNormalPanel = new JPanel();
		
		JLabel lblWhat = new JLabel("Was");
		
		btnShowTrainingsdata = new JButton("Trainingsdaten anzeigen");
		
		JLabel lblNormalize = new JLabel("Normalisieren");
		
		JLabel lblfrom = new JLabel("von");
		
		JLabel lblUp = new JLabel("bis");
		
		JLabel lblInput = new JLabel("Input");
		
		JLabel lblOutput = new JLabel("Output");
		
		checkBoxInput = new JCheckBox("");
		
		checkBoxOutput = new JCheckBox("");
		
		spinnerInputFrom = new JSpinner();
		
		spinnerInputTo = new JSpinner();
		
		spinnerOutputFrom = new JSpinner();
		
		spinnerOutputTo = new JSpinner();
		
		JLabel lblInvertOutput = new JLabel("Ausgabe wieder umkehren");
		
		rdbtnInvertOutputYes = new JRadioButton("Ja");
		rdbtnInvertOutputYes.setSelected(true);
		
		rdbtnInvertOutputNo = new JRadioButton("Nein");
		
		btnPreviewData = new JButton("Vorschau");
		
		btnApplyData = new JButton("Anwenden");
		
		btnCancel = new JButton("Abbrechen");
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});
		
		
		GroupLayout gl_trainingsDatenNormalPanel = new GroupLayout(trainingsDatenNormalPanel);
		gl_trainingsDatenNormalPanel.setHorizontalGroup(
			gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_trainingsDatenNormalPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnShowTrainingsdata)
						.addGroup(gl_trainingsDatenNormalPanel.createSequentialGroup()
							.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_trainingsDatenNormalPanel.createSequentialGroup()
									.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(lblInput)
										.addComponent(lblOutput))
									.addGap(47)
									.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(checkBoxOutput)
										.addComponent(checkBoxInput)))
								.addGroup(gl_trainingsDatenNormalPanel.createSequentialGroup()
									.addComponent(lblWhat)
									.addGap(41)
									.addComponent(lblNormalize))
								.addComponent(lblInvertOutput))
							.addGap(13)
							.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnInvertOutputYes, GroupLayout.PREFERRED_SIZE, 46, GroupLayout.PREFERRED_SIZE)
								.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.TRAILING)
									.addComponent(spinnerOutputFrom, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addComponent(spinnerInputFrom, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblfrom)))
							.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_trainingsDatenNormalPanel.createSequentialGroup()
									.addGap(14)
									.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(spinnerInputTo, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblUp)
										.addComponent(spinnerOutputTo, GroupLayout.PREFERRED_SIZE, 45, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_trainingsDatenNormalPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(rdbtnInvertOutputNo))))
						.addGroup(gl_trainingsDatenNormalPanel.createSequentialGroup()
							.addComponent(btnPreviewData)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnApplyData)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnCancel)))
					.addContainerGap(69, Short.MAX_VALUE))
		);
		gl_trainingsDatenNormalPanel.setVerticalGroup(
			gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_trainingsDatenNormalPanel.createSequentialGroup()
					.addGap(20)
					.addComponent(btnShowTrainingsdata)
					.addGap(18)
					.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblWhat)
						.addComponent(lblNormalize)
						.addComponent(lblfrom)
						.addComponent(lblUp))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(lblInput)
						.addComponent(checkBoxInput)
						.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(spinnerInputFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(spinnerInputTo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblOutput)
							.addComponent(checkBoxOutput))
						.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(spinnerOutputFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(spinnerOutputTo, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInvertOutput)
						.addComponent(rdbtnInvertOutputYes)
						.addComponent(rdbtnInvertOutputNo))
					.addGap(18)
					.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPreviewData)
						.addComponent(btnApplyData)
						.addComponent(btnCancel))
					.addContainerGap(22, Short.MAX_VALUE))
		);
		trainingsDatenNormalPanel.setLayout(gl_trainingsDatenNormalPanel);
		getContentPane().add(trainingsDatenNormalPanel);
	}

}
