/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.controller;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;

import de.unikassel.ann.gui.ExportSaveFilePanel;
import de.unikassel.ann.gui.ImportFilePanel;
import de.unikassel.ann.gui.Main;
import de.unikassel.ann.gui.MainMenu;
import de.unikassel.ann.gui.TrainDataPanel;

/**
 * Menu action item class
 * 
 * @author Way
 * 
 */
public class ActionJMenuItem extends JMenuItem implements ActionListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Actions action;

	public ActionJMenuItem(final String text, final Actions action) {
		super(text);
		this.action = action;
		addActionListener(this);
	}

	@Override
	public void actionPerformed(final ActionEvent e) {
		System.out.println(action);
		switch (action) {
		case NEW:
			Settings.getInstance().createNewSession(Settings.getI18n("session.initial.name"));
			break;
		case IMPORT:
			ImportFilePanel panel = new ImportFilePanel();
			panel.setVisible(true);
			break;
		case EXPORT:
			ExportSaveFilePanel export = new ExportSaveFilePanel();
			export.setVisible(true);
			break;
		case CLOSE_CURRENT_SESSION:
			System.err.println("not implemented");
			// TODO: Dialog öffnen und User fragen ob er speichern möchte, wenn ja
			// ExportPanel anzeigen

			// openSaveDialog()

			break;
		case CHANGE_BETWEEN_SESSIONS:
			Settings.getInstance().loadSesson(e.getActionCommand());
			break;
		case VIEW_DATA:
			// TODO
			break;
		case VIEW_TRAINING:
			// TODO
			break;
		case ABOUT:
			JFrame dialog = new JFrame();
			JOptionPane.showMessageDialog(dialog, "Anton Wilhelm, \n" + "Alexander Vey, \n" + "Sofia Daskalou, 26201618 \n", "Teilnehmer",
					JOptionPane.INFORMATION_MESSAGE);
			break;
		case EXIT:
			Object[] options = { "Speichern", "Nein", "Abbrechen" };
			Component frame = new JFrame();
			int n = JOptionPane.showOptionDialog(frame, "Die aktuelle Session ist nicht gespeichert \n" + "Möchten Sie speichern?",
					"Speichern", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[2]);

			if (n == JOptionPane.YES_OPTION) {
				// TODO call Export panel
			} else if (n == JOptionPane.NO_OPTION) {
				System.exit(e.getID());
			} else if (n == JOptionPane.CANCEL_OPTION) {
			}
			break;
		case LOAD_OR_NETWORK:
			loadNetworkFromFile("or");
			break;
		case LOAD_XOR_NETWORK:
			loadNetworkFromFile("xor");
			break;
		case LOAD_AND_NETWORK:
			loadNetworkFromFile("and");
			break;
		case LOAD_2_BIT_ADDIERER_NETWORK:
			loadNetworkFromFile("2-bit-addierer");
			break;
		case BACKPROPAGATION_VIEW:
			Main.instance.addBackproSidebarPanel();
			break;
		case SOM_VIEW:
			Main.instance.addSOMSidebarPanel();
			break;
		case NORMALIZE_TRAIN_DATA:
			TrainDataPanel trainData = TrainDataPanel.getTrainDataPanelInstance();
			trainData.setVisible(true);
			break;
		case NONE:
		default:
			System.out.println("Unknown command: " + action);
			break;
		}
	}

	/**
	 * @param string
	 */
	private void loadNetworkFromFile(final String name) {
		Settings settings = Settings.getInstance();
		File importFile;
		if (name.equals("or")) {
			try {
				importFile = new File(new URI(MainMenu.class.getClassLoader().getResource("net_cfg_or.csv").toString()));
				settings.loadNetworkFromFile(importFile);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (name.equals("xor")) {
			try {
				importFile = new File(new URI(MainMenu.class.getClassLoader().getResource("net_cfg_xor.csv").toString()));
				settings.loadNetworkFromFile(importFile);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (name.equals("and")) {
			try {
				importFile = new File(new URI(MainMenu.class.getClassLoader().getResource("net_cfg_and.csv").toString()));
				settings.loadNetworkFromFile(importFile);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} else if (name.equals("2-bit-addierer")) {
			try {
				importFile = new File(new URI(MainMenu.class.getClassLoader().getResource("net_cfg_2BitAddierer.csv").toString()));
				settings.loadNetworkFromFile(importFile);
			} catch (URISyntaxException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

}
