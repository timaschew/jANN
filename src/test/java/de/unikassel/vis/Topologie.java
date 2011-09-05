package de.unikassel.vis;
import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.CardLayout;
import javax.swing.JLabel;
import javax.swing.JComboBox;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JTextField;
import javax.swing.JCheckBox;
import javax.swing.JRadioButton;
import javax.swing.JToggleButton;
import javax.swing.JSeparator;
import java.awt.Color;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.JFormattedTextField;
import javax.swing.JTextArea;
import javax.swing.JButton;
import javax.swing.JTree;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.JTabbedPane;
import java.awt.FlowLayout;
import javax.swing.SpringLayout;
import javax.swing.JLayeredPane;
import javax.swing.JSplitPane;
import javax.swing.BoxLayout;
import javax.swing.border.TitledBorder;
import javax.swing.UIManager;
import java.awt.Component;
import javax.swing.JSpinner;


public class Topologie extends JFrame {

	private JPanel contentPane;
	private Box topologyBox;
	private JPanel topologiePanel;
	private JLabel lblInputNeuronen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Topologie frame = new Topologie();
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
	public Topologie() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 192, 211);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel panel = new JPanel();
		contentPane.add(panel);
		panel.setLayout(new BoxLayout(panel, BoxLayout.X_AXIS));
		
		topologyBox = Box.createVerticalBox();
		contentPane.add(topologyBox);
		topologyBox.setAlignmentX(Component.CENTER_ALIGNMENT);
		topologyBox.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), "Topologie", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
		
		topologiePanel = new JPanel();
		topologyBox.add(topologiePanel);
		
		lblInputNeuronen = new JLabel("Input Neuronen");
		
		JSpinner inputNeuroSpinner = new JSpinner();
		
		JLabel lblOutputNeuronen = new JLabel("Output Neuronen");
		
		JSpinner outputNeuroSpinner = new JSpinner();
		
		JLabel lblHiddenLayer = new JLabel("Hidden Layer");
		
		JSpinner hiddenLayerSpinner = new JSpinner();
		
		JLabel lblHiddenNeuronen = new JLabel("Hidden Neuronen");
		
		JSpinner hiddenNeuroSpinner = new JSpinner();
		GroupLayout gl_topologiePanel = new GroupLayout(topologiePanel);
		gl_topologiePanel.setHorizontalGroup(
			gl_topologiePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topologiePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING, false)
						.addGroup(gl_topologiePanel.createSequentialGroup()
							.addComponent(lblInputNeuronen)
							.addGap(18)
							.addComponent(inputNeuroSpinner, GroupLayout.DEFAULT_SIZE, 33, Short.MAX_VALUE))
						.addGroup(gl_topologiePanel.createSequentialGroup()
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING)
								.addComponent(lblOutputNeuronen)
								.addComponent(lblHiddenLayer))
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addGroup(gl_topologiePanel.createParallelGroup(Alignment.LEADING)
								.addComponent(hiddenLayerSpinner, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)
								.addComponent(outputNeuroSpinner, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
						.addGroup(gl_topologiePanel.createSequentialGroup()
							.addComponent(lblHiddenNeuronen)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(hiddenNeuroSpinner, GroupLayout.PREFERRED_SIZE, 33, GroupLayout.PREFERRED_SIZE)))
					.addContainerGap(275, Short.MAX_VALUE))
		);
		gl_topologiePanel.setVerticalGroup(
			gl_topologiePanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_topologiePanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_topologiePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblInputNeuronen)
						.addComponent(inputNeuroSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_topologiePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblOutputNeuronen)
						.addComponent(outputNeuroSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_topologiePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHiddenLayer)
						.addComponent(hiddenLayerSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_topologiePanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblHiddenNeuronen)
						.addComponent(hiddenNeuroSpinner, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
					.addContainerGap(101, Short.MAX_VALUE))
		);
		topologiePanel.setLayout(gl_topologiePanel);
	}
}
