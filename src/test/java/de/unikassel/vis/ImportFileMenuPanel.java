package de.unikassel.vis;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;

public class ImportFileMenuPanel extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
	 * Create the panel.
	 */
	public ImportFileMenuPanel() {
		
		setTitle("Import");
		setSize(270, 210);
        setLocationRelativeTo(null);
        setModal(true);
        setResizable(false);
		
		JPanel importDialogPanel = new JPanel();
		
		JLabel lblImportFile = new JLabel("File Importieren");
		
		JButton btnSuchen = new JButton("Suchen");
		btnSuchen.setIcon(new ImageIcon(ImportFileMenuPanel.class.getResource("/de/test/sofia/search-icon.png")));
		
		btnSuchen.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
			}
		});
		
		JLabel lblTopology = new JLabel("Topologie");
		
		JLabel lblSynapse = new JLabel("Synapse");
		
		JLabel lblTrainingData = new JLabel("Training Data");
		
		JCheckBox topologieCB = new JCheckBox("");
		
		JCheckBox synapseCB = new JCheckBox("");
		
		JCheckBox trainigDataCB = new JCheckBox("");
		
		JButton btnCancel = new JButton("Abbrechen");
		
		JButton btnImport = new JButton("Import");
		GroupLayout gl_importDialogPanel = new GroupLayout(importDialogPanel);
		gl_importDialogPanel.setHorizontalGroup(
			gl_importDialogPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_importDialogPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTopology)
						.addComponent(btnCancel)
						.addComponent(lblImportFile)
						.addComponent(lblTrainingData)
						.addComponent(lblSynapse))
					.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.TRAILING)
						.addGroup(gl_importDialogPanel.createSequentialGroup()
							.addPreferredGap(ComponentPlacement.RELATED)
							.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.LEADING)
								.addComponent(topologieCB)
								.addComponent(trainigDataCB)
								.addComponent(synapseCB))
							.addGap(48))
						.addGroup(gl_importDialogPanel.createSequentialGroup()
							.addGap(52)
							.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.TRAILING)
								.addComponent(btnSuchen)
								.addComponent(btnImport))
							.addGap(30))))
		);
		gl_importDialogPanel.setVerticalGroup(
			gl_importDialogPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_importDialogPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblImportFile)
						.addComponent(btnSuchen))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(lblTopology)
						.addComponent(topologieCB))
					.addPreferredGap(ComponentPlacement.UNRELATED)
					.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(synapseCB)
						.addComponent(lblSynapse))
					.addGap(6)
					.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(trainigDataCB)
						.addComponent(lblTrainingData))
					.addGap(18)
					.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.LEADING)
						.addComponent(btnCancel)
						.addComponent(btnImport))
					.addContainerGap(18, Short.MAX_VALUE))
		);
		importDialogPanel.setLayout(gl_importDialogPanel);
		getContentPane().add(importDialogPanel);

	}
}
