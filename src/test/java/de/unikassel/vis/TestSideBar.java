package de.unikassel.vis;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JSplitPane;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;

import de.unikassel.ann.vis.Main;
import de.unikassel.ann.vis.TrainErrorPlot;

public class TestSideBar extends JFrame {

	private JPanel contentPane;
	private JFrame frame;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TestSideBar frame = new TestSideBar();
					frame.setVisible(true);
//					Main window = new Main();
//					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public TestSideBar() {
		JSplitPane mainSplitPane = new JSplitPane();
		frame.getContentPane().add(mainSplitPane, BorderLayout.CENTER);
		
		JPanel sideBar = new JPanel();
		mainSplitPane.setRightComponent(sideBar);
		GroupLayout gl_sideBar = new GroupLayout(sideBar);
		gl_sideBar.setHorizontalGroup(
			gl_sideBar.createParallelGroup(Alignment.LEADING)
				.addGap(0, 348, Short.MAX_VALUE)
		);
		gl_sideBar.setVerticalGroup(
			gl_sideBar.createParallelGroup(Alignment.LEADING)
				.addGap(0, 298, Short.MAX_VALUE)
		);
		sideBar.setLayout(gl_sideBar);
//		sideBar.add(prototypeButton);
		
		
		sideBar.setBorder(new TitledBorder(null, "Training", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JPanel strategienPanel = new JPanel();
		strategienPanel.setBorder(new TitledBorder(null, "Strategien", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		
		JLabel lblAlgorithmus = new JLabel("Algorithmus");
		
		JLabel lblLernrate = new JLabel("Lernrate");
		
		JLabel lblMomentum = new JLabel("Momentum");
		
		JComboBox comboBoxAlgorithm = new JComboBox();
		comboBoxAlgorithm.setModel(new DefaultComboBoxModel(new String[] {"Backpropagation", "Test"}));
		
		JSpinner spinnerLernRate = new JSpinner();
		
		JSpinner spinnerMomentum = new JSpinner();
		
		JLabel lblTrainingsmodus = new JLabel("Trainingsmodus");
		
		JRadioButton rdbtnOnline = new JRadioButton("Online");
		rdbtnOnline.setSelected(true);
		
		JRadioButton rdbtnOffline = new JRadioButton("Offline");
		GroupLayout groupLayout = new GroupLayout(sideBar);
		groupLayout.setHorizontalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addComponent(strategienPanel, GroupLayout.DEFAULT_SIZE, 287, Short.MAX_VALUE)
				.addGroup(groupLayout.createSequentialGroup()
					.addContainerGap()
					.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
						.addGroup(groupLayout.createParallelGroup(Alignment.TRAILING, false)
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblMomentum)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(spinnerMomentum, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblLernrate)
								.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addComponent(spinnerLernRate, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createSequentialGroup()
								.addComponent(lblAlgorithmus)
								.addGap(52)
								.addComponent(comboBoxAlgorithm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addComponent(rdbtnOnline)
								.addComponent(rdbtnOffline)))
						.addComponent(lblTrainingsmodus))
					.addContainerGap(44, Short.MAX_VALUE))
		);
		groupLayout.setVerticalGroup(
			groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblAlgorithmus)
						.addComponent(comboBoxAlgorithm, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblLernrate)
						.addComponent(spinnerLernRate, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMomentum)
						.addComponent(spinnerMomentum, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(groupLayout.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTrainingsmodus)
						.addComponent(rdbtnOnline))
					.addGroup(groupLayout.createSequentialGroup()
						.addPreferredGap(ComponentPlacement.UNRELATED)
						.addComponent(rdbtnOffline))
					.addGap(28)
					.addComponent(strategienPanel, GroupLayout.PREFERRED_SIZE, 79, Short.MAX_VALUE)
					.addContainerGap())
		);
		
		JLabel lblTyp = new JLabel("Typ");
		
		JLabel lblMaximalIteration = new JLabel("Maximal Iteration");
		
		JComboBox comboBoxTyp = new JComboBox();
		comboBoxTyp.setModel(new DefaultComboBoxModel(new String[] {"MaxIteration"}));
		
		JSpinner spinner = new JSpinner();
		
		JCheckBox chckbxAktivieren = new JCheckBox("Strategie Aktivieren");
		chckbxAktivieren.setSelected(true);
		GroupLayout gl_strategienPanel = new GroupLayout(strategienPanel);
		gl_strategienPanel.setHorizontalGroup(
			gl_strategienPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_strategienPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_strategienPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTyp)
						.addComponent(lblMaximalIteration))
					.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
					.addGroup(gl_strategienPanel.createParallelGroup(Alignment.TRAILING)
						.addComponent(comboBoxTyp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addComponent(chckbxAktivieren)
					.addGap(23))
		);
		gl_strategienPanel.setVerticalGroup(
			gl_strategienPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_strategienPanel.createSequentialGroup()
					.addGroup(gl_strategienPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblTyp)
						.addComponent(comboBoxTyp, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addComponent(chckbxAktivieren))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_strategienPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblMaximalIteration)
						.addComponent(spinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
		);
		strategienPanel.setLayout(gl_strategienPanel);
		sideBar.setLayout(groupLayout);
		
		
	JPanel standardOptions = new JPanel();	
	standardOptions.setBorder(new TitledBorder(null, "Standard-Einstellung", TitledBorder.LEADING, TitledBorder.TOP, null, null));
	
	JLabel lblAktivierungsfunktion = new JLabel("Aktivierungsfunktion");
	
	JLabel lblInitialgewicht = new JLabel("Initialgewicht");
	
	JRadioButton rdbtnZufllig = new JRadioButton("Zufällig");
	
	JRadioButton rdbtnExakt = new JRadioButton("Exakt");
	
	JSpinner spinnerRandom = new JSpinner();
	
	JSpinner spinnerRandom2 = new JSpinner();
	
	JSpinner spinnerExact = new JSpinner();
	
	JComboBox comboBoxAktivFunktion = new JComboBox();
	comboBoxAktivFunktion.setModel(new DefaultComboBoxModel(new String[] {"Sigmoid", "Test"}));
	GroupLayout groupLayout2 = new GroupLayout(standardOptions);
	groupLayout2.setHorizontalGroup(
		groupLayout2.createParallelGroup(Alignment.LEADING)
			.addGroup(groupLayout2.createSequentialGroup()
				.addContainerGap()
				.addGroup(groupLayout2.createParallelGroup(Alignment.LEADING, false)
					.addGroup(groupLayout2.createSequentialGroup()
						.addComponent(lblAktivierungsfunktion)
						.addPreferredGap(ComponentPlacement.RELATED, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(comboBoxAktivFunktion, GroupLayout.PREFERRED_SIZE, 87, GroupLayout.PREFERRED_SIZE))
					.addGroup(groupLayout2.createSequentialGroup()
						.addComponent(lblInitialgewicht)
						.addGap(18)
						.addGroup(groupLayout2.createParallelGroup(Alignment.LEADING, false)
							.addGroup(groupLayout2.createSequentialGroup()
								.addComponent(rdbtnZufllig)
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(spinnerRandom, GroupLayout.PREFERRED_SIZE, 40, GroupLayout.PREFERRED_SIZE))
							.addGroup(groupLayout2.createSequentialGroup()
								.addComponent(rdbtnExakt)
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
						.addComponent(comboBoxAktivFunktion, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout2.createParallelGroup(Alignment.BASELINE)
							.addComponent(rdbtnZufllig)
							.addComponent(spinnerRandom, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)
							.addComponent(spinnerRandom2, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
						.addPreferredGap(ComponentPlacement.RELATED)
						.addGroup(groupLayout2.createParallelGroup(Alignment.BASELINE)
							.addComponent(rdbtnExakt)
							.addComponent(spinnerExact, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE)))
					.addGroup(groupLayout2.createSequentialGroup()
						.addContainerGap()
						.addComponent(lblAktivierungsfunktion)
						.addGap(18)
						.addComponent(lblInitialgewicht)))
				.addContainerGap(185, Short.MAX_VALUE))
	);
	standardOptions.setLayout(groupLayout2);

	sideBar.add(standardOptions);

		
		
		
		JSplitPane jungConsoleSplitPane = new JSplitPane();
		jungConsoleSplitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		mainSplitPane.setLeftComponent(jungConsoleSplitPane);
		
		JPanel jungPanel = new JPanel(new BorderLayout());
		jungConsoleSplitPane.setLeftComponent(jungPanel);
		
		JButton prototypeButton = new JButton("start");
		final TrainErrorPlot tep = new TrainErrorPlot();
		tep.init();
		JFrame f = new JFrame();
		Component plot = tep.getPlotPanel();
		f.getContentPane().add(plot);
		f.setVisible(true);
		f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		f.setSize(tep.getPlotPanel().getPreferredSize());
	}
	
}
