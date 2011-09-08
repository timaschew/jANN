package de.unikassel.ann.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class MainMenu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private ResourceBundle i18n;

	/**
	 * Constructor
	 */
	public MainMenu(ResourceBundle i18n) {
		super();

		this.i18n = i18n;
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
		JMenu mnDatei = new JMenu(i18n.getString("menu.file"));

		JMenuItem mntmNeu = new ActionMenuItem(i18n.getString("menu.file.new"),
				Action.NEW);
		mnDatei.add(mntmNeu);

		JMenuItem mntmOeffnen = new ActionMenuItem(
				i18n.getString("menu.file.open"), Action.OPEN);
		mnDatei.add(mntmOeffnen);

		JMenuItem mntmSpeichern = new ActionMenuItem(
				i18n.getString("menu.file.save"), Action.SAVE);
		mnDatei.add(mntmSpeichern);

		mnDatei.addSeparator();

		JMenuItem mntmBeenden = new ActionMenuItem(
				i18n.getString("menu.file.exit"), Action.EXIT);
		mnDatei.add(mntmBeenden);

		return mnDatei;
	}

	/**
	 * Edit menu
	 * 
	 * @return JMenu
	 */
	private JMenu getBearbeitenMenu() {
		JMenu mnBearbeiten = new JMenu(i18n.getString("menu.edit"));
		return mnBearbeiten;
	}

	/**
	 * View menu
	 * 
	 * @return JMenu
	 */
	private JMenu getAnsichtMenu() {
		JMenu mnAnsicht = new JMenu(i18n.getString("menu.view"));

		JMenuItem mntmDatenvisualisierung = new ActionMenuItem(
				i18n.getString("menu.view.showTrainingData"), Action.VIEW_DATA);
		mnAnsicht.add(mntmDatenvisualisierung);

		JMenuItem mntmTrainingfehlerverlauf = new ActionMenuItem(
				i18n.getString("menu.view.showTrainingError"),
				Action.VIEW_TRAINING);
		mnAnsicht.add(mntmTrainingfehlerverlauf);

		return mnAnsicht;
	}

	/**
	 * Help menu
	 * 
	 * @return JMenu
	 */
	private JMenu getHilfeMenu() {
		JMenu mnHilfe = new JMenu(i18n.getString("menu.help"));

		JMenuItem mntmUeber = new ActionMenuItem(
				i18n.getString("menu.help.about"), Action.ABOUT);
		mnHilfe.add(mntmUeber);

		return mnHilfe;
	}

	/**
	 * Menu actions
	 */
	private enum Action {
		NONE, NEW, OPEN, SAVE, EXIT, VIEW_DATA, VIEW_TRAINING, ABOUT
	}

	/**
	 * Menu action item class
	 */
	private class ActionMenuItem extends JMenuItem implements ActionListener {
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		Action action;

		public ActionMenuItem(final String text, final Action action) {
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
				break;
			case OPEN:
				// TODO
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

	}

}
