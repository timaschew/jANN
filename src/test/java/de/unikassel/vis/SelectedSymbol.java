package de.unikassel.vis;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.border.TitledBorder;
import javax.swing.JLabel;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JSpinner;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.SpinnerNumberModel;


public class SelectedSymbol extends JPanel {

	private JPanel contentPane;
	private JTextField fieldAusgewaehlt;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SelectedSymbol panel = new SelectedSymbol();
					JFrame frame = new JFrame();
					frame.setContentPane(panel);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public SelectedSymbol() {
//		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		setBounds(100, 100, 294, 225);
//		contentPane = new JPanel();
//		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
//		contentPane.setLayout(new BorderLayout(0, 0));
//		setContentPane(contentPane);
//		
//		JPanel SelectedSymbolPanel = new JPanel();
		setBorder(new TitledBorder(null, "Ausgew\u00E4hltes Symbol", TitledBorder.LEADING, TitledBorder.TOP, null, null));
//		contentPane.add(SelectedSymbolPanel, BorderLayout.CENTER);
		
		JLabel lblSelected = new JLabel("Ausgew\u00E4hlt");
		
		JLabel lblSynapseWeight = new JLabel("Synapsengewicht");
		
		JLabel lblNeuroInput = new JLabel("Neuroneninput");
		
		JLabel lblActivationFunction = new JLabel("Aktivierungsfunktion");
		
		fieldAusgewaehlt = new JTextField();
		fieldAusgewaehlt.setEditable(false);
		fieldAusgewaehlt.setColumns(10);
		
		JComboBox activatedFunktioncomboBox = new JComboBox();
		activatedFunktioncomboBox.setModel(new DefaultComboBoxModel(new String[] {"Sigmoid", "Test"}));
		
		JSpinner neuroInputBySelectSpinner = new JSpinner();
		neuroInputBySelectSpinner.setModel(new SpinnerNumberModel(new Double(0), null, null, new Double(1)));
		
		JSpinner spinnerSynapsWeight = new JSpinner();
		GroupLayout gl_SelectedSymbolPanel = new GroupLayout(this);
		gl_SelectedSymbolPanel.setHorizontalGroup(
			gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_SelectedSymbolPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblActivationFunction)
						.addComponent(lblNeuroInput)
						.addComponent(lblSelected)
						.addComponent(lblSynapseWeight))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING, false)
						.addComponent(activatedFunktioncomboBox, 0, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
						.addComponent(neuroInputBySelectSpinner)
						.addComponent(spinnerSynapsWeight)
						.addComponent(fieldAusgewaehlt))
					.addContainerGap(187, Short.MAX_VALUE))
		);
		gl_SelectedSymbolPanel.setVerticalGroup(
			gl_SelectedSymbolPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_SelectedSymbolPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSelected)
						.addComponent(fieldAusgewaehlt, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblSynapseWeight)
						.addComponent(spinnerSynapsWeight, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblNeuroInput)
						.addComponent(neuroInputBySelectSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_SelectedSymbolPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblActivationFunction)
						.addComponent(activatedFunktioncomboBox, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(135, Short.MAX_VALUE))
		);
		setLayout(gl_SelectedSymbolPanel);
	}

}
