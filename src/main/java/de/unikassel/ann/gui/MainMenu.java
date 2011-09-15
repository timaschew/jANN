package de.unikassel.ann.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.Actions;
import de.unikassel.ann.io.NetIO;
import de.unikassel.vis.ImportFilePanel;

public class MainMenu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Main main;

	/**
	 * Constructor
	 */
	public MainMenu(Main main) {
		super();

		this.main = main;
		addMenus();
	}

	/**
	 * Add all menus with their items to this menu.
	 */
	private void addMenus() {
		JMenu mnDatei = getDateiMenu();
		this.add(mnDatei);

		JMenu mnBearbeiten = getBearbeitenMenu();
		this.add(mnBearbeiten);

		JMenu mnAnsicht = getAnsichtMenu();
		this.add(mnAnsicht);

		JMenu mnHilfe = getHilfeMenu();
		this.add(mnHilfe);
	}

	/**
	 * File menu
	 * 
	 * @return JMenu
	 */
	private JMenu getDateiMenu() {
		JMenu mnDatei = new JMenu(Main.i18n.getString("menu.file"));

		JMenuItem mntmNeu = new ActionMenuItem(
				Main.i18n.getString("menu.file.new"), Actions.NEW);
		mnDatei.add(mntmNeu);

		JMenuItem mntmOeffnen = new ActionMenuItem(
				Main.i18n.getString("menu.file.open"), Actions.OPEN);
		mnDatei.add(mntmOeffnen);

		JMenuItem mntmSpeichern = new ActionMenuItem(
				Main.i18n.getString("menu.file.save"), Actions.SAVE);
		mnDatei.add(mntmSpeichern);

		mnDatei.addSeparator();

		JMenuItem mntmBeenden = new ActionMenuItem(
				Main.i18n.getString("menu.file.exit"), Actions.EXIT);
		mnDatei.add(mntmBeenden);

		return mnDatei;
	}

	/**
	 * Edit menu
	 * 
	 * @return JMenu
	 */
	private JMenu getBearbeitenMenu() {
		JMenu mnBearbeiten = new JMenu(Main.i18n.getString("menu.edit"));

		return mnBearbeiten;
	}

	/**
	 * View menu
	 * 
	 * @return JMenu
	 */
	private JMenu getAnsichtMenu() {
		JMenu mnAnsicht = new JMenu(Main.i18n.getString("menu.view"));

		JMenuItem mntmDatenvisualisierung = new ActionMenuItem(
				Main.i18n.getString("menu.view.showTrainingData"),
				Actions.VIEW_DATA);
		mnAnsicht.add(mntmDatenvisualisierung);

		JMenuItem mntmTrainingfehlerverlauf = new ActionMenuItem(
				Main.i18n.getString("menu.view.showTrainingError"),
				Actions.VIEW_TRAINING);
		mnAnsicht.add(mntmTrainingfehlerverlauf);

		return mnAnsicht;
	}

	/**
	 * Help menu
	 * 
	 * @return JMenu
	 */
	private JMenu getHilfeMenu() {
		JMenu mnHilfe = new JMenu(Main.i18n.getString("menu.help"));

		JMenuItem mntmUeber = new ActionMenuItem(
				Main.i18n.getString("menu.help.about"), Actions.ABOUT);
		mnHilfe.add(mntmUeber);

		return mnHilfe;
	}

	/**
	 * Menu action item class
	 */
	private class ActionMenuItem extends JMenuItem implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Actions action;

		public ActionMenuItem(final String text, final Actions action) {
			super(text);
			this.action = action;
			addActionListener(this);
		}

		@Override
		public void actionPerformed(final ActionEvent e) {
			System.out.println(action);
			switch (action) {
			case NEW:
				// TODO
				testRenderNetwork();
				break;
			case OPEN:
				// TODO
				ImportFilePanel panel = ImportFilePanel.getImportFileInstance();
				panel.show();
				break;
			case SAVE:
				// TODO
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
			case NONE:
			default:
				System.out.println("Unknown command: " + action);
				break;
			}
		}

		private void testRenderNetwork() {
			String curDir = System.getProperty("user.dir");
			File importFile = new File(curDir
					+ "/src/test/resources/net_cfg.csv");

			NetIO netIO = new NetIO();
			// read and parse file
			try {
				netIO.readConfigFile(importFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			// create network (topology and synapses)
			NetConfig netConfig = netIO.generateNetwork();
			main.getGraphLayoutViewer().renderNetwork(netConfig.getNetwork());
		}

	}

}
