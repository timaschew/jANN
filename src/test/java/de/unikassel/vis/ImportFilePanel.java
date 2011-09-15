package de.unikassel.vis;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.JCheckBox;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;



public class ImportFilePanel extends JDialog {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private JPanel searchFilePanel;
	private JTextArea area;

	private JCheckBox topologieCB;
	private JCheckBox synapseCB;
	private JCheckBox trainigDataCB;
	
	private static ImportFilePanel importFileInstance;
	

	public static ImportFilePanel getImportFileInstance() {
		if (importFileInstance == null) {
			importFileInstance = new ImportFilePanel();
		}
		return importFileInstance;
	}
	
	

	/**
	 * Create the panel.
	 */
	private ImportFilePanel() {
		
		searchFilePanel = new JPanel();
		searchFilePanel.setLayout(new BorderLayout());
		
		setTitle("Import");
		setSize(250, 210);
        setLocationRelativeTo(null);
        setModal(true);
        setResizable(false);
        
		JPanel importDialogPanel = new JPanel();
		
		JLabel lblImportFile = new JLabel("File Importieren");
		JButton btnSearch = new JButton("Suchen");
		btnSearch.setIcon(new ImageIcon(ImportFilePanel.class.getResource("/de/unikassel/vis/search-icon.png")));
		
		
		//
		btnSearch.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				openFileChooser();
			}

		});
		
		JLabel lblTopology = new JLabel("Topologie");
		topologieCB = new JCheckBox("");
		JLabel lblSynapse = new JLabel("Synapse");
		synapseCB = new JCheckBox("");
		JLabel lblTrainingData = new JLabel("Training Data");
		trainigDataCB = new JCheckBox("");
		JButton btnImport = new JButton("Import");
		//TODO Nach Import JungView Aktualisieren
		JButton btnCancel = new JButton("Abbrechen");
		
		btnCancel.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent event) {
				dispose();
			}
		});
		
		
		GroupLayout gl_importDialogPanel = new GroupLayout(importDialogPanel);
		gl_importDialogPanel.setHorizontalGroup(
			gl_importDialogPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_importDialogPanel.createSequentialGroup()
					.addContainerGap()
					.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(Alignment.TRAILING, gl_importDialogPanel.createSequentialGroup()
							.addComponent(btnImport)
							.addPreferredGap(ComponentPlacement.RELATED, 87, Short.MAX_VALUE)
							.addComponent(btnCancel)
							.addContainerGap())
						.addGroup(gl_importDialogPanel.createSequentialGroup()
							.addComponent(lblImportFile)
							.addPreferredGap(ComponentPlacement.RELATED, 75, Short.MAX_VALUE)
							.addComponent(btnSearch)
							.addContainerGap())
						.addGroup(Alignment.TRAILING, gl_importDialogPanel.createSequentialGroup()
							.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.TRAILING)
								.addGroup(Alignment.LEADING, gl_importDialogPanel.createSequentialGroup()
									.addComponent(lblTrainingData)
									.addPreferredGap(ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
									.addComponent(trainigDataCB))
								.addGroup(Alignment.LEADING, gl_importDialogPanel.createSequentialGroup()
									.addComponent(lblSynapse)
									.addPreferredGap(ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
									.addComponent(synapseCB))
								.addGroup(gl_importDialogPanel.createSequentialGroup()
									.addComponent(lblTopology)
									.addPreferredGap(ComponentPlacement.RELATED, 135, Short.MAX_VALUE)
									.addComponent(topologieCB)))
							.addGap(45))))
		);
		gl_importDialogPanel.setVerticalGroup(
			gl_importDialogPanel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_importDialogPanel.createSequentialGroup()
					.addGap(10)
					.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(lblImportFile)
						.addComponent(btnSearch))
					.addPreferredGap(ComponentPlacement.RELATED)
					.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.LEADING)
						.addGroup(gl_importDialogPanel.createSequentialGroup()
							.addComponent(lblTopology)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblSynapse)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(lblTrainingData))
						.addGroup(gl_importDialogPanel.createSequentialGroup()
							.addComponent(topologieCB)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(synapseCB)
							.addPreferredGap(ComponentPlacement.UNRELATED)
							.addComponent(trainigDataCB)))
					.addGap(18)
					.addGroup(gl_importDialogPanel.createParallelGroup(Alignment.BASELINE)
						.addComponent(btnCancel)
						.addComponent(btnImport))
					.addContainerGap())
		);
		importDialogPanel.setLayout(gl_importDialogPanel);
		getContentPane().add(importDialogPanel);

	}
	
	private void openFileChooser() {
		
		JFileChooser fileopen = new JFileChooser();
        FileFilter filter = new FileNameExtensionFilter("csv files", "csv");
        fileopen.addChoosableFileFilter(filter);

        int ret = fileopen.showDialog(searchFilePanel, "Open file");

        if (ret == JFileChooser.APPROVE_OPTION) {
            File file = fileopen.getSelectedFile();
            String text = readFile(file);
            area.setText(text);
        }

	}
	
	public String readFile(File file) {

        StringBuffer fileBuffer = null;
        String fileString = null;
        String line = null;

        try {
            FileReader in = new FileReader(file);
            BufferedReader brd = new BufferedReader(in);
            fileBuffer = new StringBuffer();

            while ((line = brd.readLine()) != null) {
                fileBuffer.append(line).append(
                        System.getProperty("line.separator"));
            }

            in.close();
            fileString = fileBuffer.toString();
        } catch (IOException e) {
            return null;
        }
        return fileString;
    }
}
