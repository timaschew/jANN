package de.unikassel.vis;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JDesktopPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.JSpinner;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;
import java.awt.Dialog.ModalExclusionType;


public class Trainingsdaten extends JDialog {


	/**
	 * Create the frame.
	 */
	public Trainingsdaten() {
		setTitle("Trainingsdaten normalisieren");
		setModal(true);
		setSize(330, 260);
		setResizable(false);
		setLocationRelativeTo(null);
		
		
		JPanel trainingsDatenNormalPanel = new JPanel();
		
		JLabel lblWhat = new JLabel("Was");
		
		JButton btnShowTrainingsdata = new JButton("Trainingsdaten anzeigen");
		
		JLabel lblNormalize = new JLabel("Normalisieren");
		
		JLabel lblfrom = new JLabel("von");
		
		JLabel lblUp = new JLabel("bis");
		
		JLabel lblInput = new JLabel("Input");
		
		JLabel lblOutput = new JLabel("Output");
		
		JCheckBox checkBoxInput = new JCheckBox("");
		
		JCheckBox checkBoxOutput = new JCheckBox("");
		
		JSpinner spinnerInputFrom = new JSpinner();
		
		JSpinner spinnerInputUp = new JSpinner();
		
		JSpinner spinnerOutputFrom = new JSpinner();
		
		JSpinner spinnerOutputUp = new JSpinner();
		
		JLabel lblInvertOutput = new JLabel("Ausgabe wieder umkehren");
		
		JRadioButton rdbtnInvertOutputYes = new JRadioButton("Ja");
		rdbtnInvertOutputYes.setSelected(true);
		
		JRadioButton rdbtnInvertOutputNo = new JRadioButton("Nein");
		
		JButton btnPreviewData = new JButton("Vorschau");
		
		JButton btnApplyData = new JButton("Anwenden");
		
		JButton btnAbbrechen = new JButton("Abbrechen");
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
									.addComponent(spinnerOutputFrom, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
									.addComponent(spinnerInputFrom, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
									.addComponent(lblfrom)))
							.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_trainingsDatenNormalPanel.createSequentialGroup()
									.addGap(14)
									.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING)
										.addComponent(spinnerInputUp, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)
										.addComponent(lblUp)
										.addComponent(spinnerOutputUp, GroupLayout.PREFERRED_SIZE, 35, GroupLayout.PREFERRED_SIZE)))
								.addGroup(gl_trainingsDatenNormalPanel.createSequentialGroup()
									.addPreferredGap(ComponentPlacement.UNRELATED)
									.addComponent(rdbtnInvertOutputNo))))
						.addGroup(gl_trainingsDatenNormalPanel.createSequentialGroup()
							.addComponent(btnPreviewData)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnApplyData)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(btnAbbrechen)))
					.addContainerGap(15, Short.MAX_VALUE))
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
							.addComponent(spinnerInputUp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(lblOutput)
							.addComponent(checkBoxOutput))
						.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.BASELINE)
							.addComponent(spinnerOutputFrom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(spinnerOutputUp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGap(18)
					.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInvertOutput)
						.addComponent(rdbtnInvertOutputYes)
						.addComponent(rdbtnInvertOutputNo))
					.addGap(18)
					.addGroup(gl_trainingsDatenNormalPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnPreviewData)
						.addComponent(btnApplyData)
						.addComponent(btnAbbrechen))
					.addContainerGap(23, Short.MAX_VALUE))
		);
		trainingsDatenNormalPanel.setLayout(gl_trainingsDatenNormalPanel);
		getContentPane().add(trainingsDatenNormalPanel);
	}

}
