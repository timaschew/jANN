/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.ann.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JMenuItem;

import de.unikassel.ann.gui.ExportFilePanel;
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
			ExportFilePanel export = new ExportFilePanel();
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
			// TODO
			break;
		case EXIT:
			System.exit(e.getID());
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
