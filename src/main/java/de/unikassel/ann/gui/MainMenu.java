package de.unikassel.ann.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;

import de.unikassel.ann.controller.ActionJMenuItem;
import de.unikassel.ann.controller.Actions;
import de.unikassel.ann.controller.GraphController;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.model.UserSession;

public class MainMenu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JMenu fileMenu;
	public JMenu subMenuSession;

	/**
	 * Constructor
	 */
	public MainMenu() {
		super();
		fileMenu = new JMenu(Settings.i18n.getString("menu.file"));
		addMenus();
	}

	/**
	 * Add all menus with their items to this menu.
	 */
	private void addMenus() {
		initFileMenu();
		this.add(fileMenu);

		JMenu mnAnsicht = getAnsichtMenu();
		this.add(mnAnsicht);

		JMenu mnOptions = getOptionsMenu();
		this.add(mnOptions);

		JMenu mnTest = getTestMenu();
		this.add(mnTest);

		JMenu mnHilfe = getHilfeMenu();
		this.add(mnHilfe);
	}

	/**
	 * File menu
	 * 
	 * @return JMenu
	 */
	public void initFileMenu() {

		JMenuItem mntmNeu = new ActionJMenuItem(Settings.i18n.getString("menu.file.new"), Actions.NEW);
		fileMenu.add(mntmNeu);

		JMenuItem mntmImport = new ActionJMenuItem(Settings.i18n.getString("menu.file.import"), Actions.IMPORT);
		fileMenu.add(mntmImport);

		JMenuItem mntmExport = new ActionJMenuItem(Settings.i18n.getString("menu.file.export"), Actions.EXPORT);
		fileMenu.add(mntmExport);

		fileMenu.addSeparator();

		// hier is the Position, if sessions exists
		// addItemToMenu()
		subMenuSession = new JMenu(Settings.i18n.getString("menu.file.submenu.sessions"));
		fileMenu.add(subMenuSession);

		fileMenu.addSeparator();

		JMenuItem mntmCloseCurrentSession = new ActionJMenuItem(Settings.i18n.getString("menu.file.closeCurrentsession"),
				Actions.CLOSE_CURRENT_SESSION);
		fileMenu.add(mntmCloseCurrentSession, -1);

		JMenuItem mntmBeenden = new ActionJMenuItem(Settings.i18n.getString("menu.file.exit"), Actions.EXIT);
		mntmBeenden.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		fileMenu.add(mntmBeenden, -1);
	}

	/**
	 * View menu
	 * 
	 * @return JMenu
	 */
	private JMenu getAnsichtMenu() {
		JMenu mnAnsicht = new JMenu(Settings.i18n.getString("menu.view"));

		JMenuItem mntmDatenvisualisierung = new ActionJMenuItem(Settings.i18n.getString("menu.view.showTrainingData"), Actions.VIEW_DATA);
		mnAnsicht.add(mntmDatenvisualisierung);

		JMenuItem mntmTrainingfehlerverlauf = new ActionJMenuItem(Settings.i18n.getString("menu.view.showTrainingError"),
				Actions.VIEW_TRAINING);
		mnAnsicht.add(mntmTrainingfehlerverlauf);

		return mnAnsicht;
	}

	/**
	 * Options Menu
	 * 
	 * @return JMenu
	 */

	private JMenu getOptionsMenu() {
		JMenu mnOptions = new JMenu(Settings.i18n.getString("menu.options"));

		JMenuItem mntmBackpropagation = new ActionJMenuItem(Settings.i18n.getString("menu.options.backpropagation"),
				Actions.BACKPROPAGATION_VIEW);
		mnOptions.add(mntmBackpropagation);

		JMenuItem mntmSOM = new ActionJMenuItem(Settings.i18n.getString("menu.options.SOM"), Actions.SOM_VIEW);
		mnOptions.add(mntmSOM);

		mnOptions.addSeparator();

		JMenuItem mntmTrainData = new ActionJMenuItem(Settings.i18n.getString("menu.options.trainData"), Actions.NORMALIZE_TRAIN_DATA);
		mnOptions.add(mntmTrainData);

		return mnOptions;
	}

	/**
	 * Test menu
	 * 
	 * @return JMenu
	 */
	private JMenu getTestMenu() {
		JMenu mntmNetzwerk = new JMenu(Settings.i18n.getString("menu.network"));

		JMenuItem mntnORNetwork = new ActionJMenuItem(Settings.i18n.getString("menu.network.or"), Actions.LOAD_OR_NETWORK);
		mntmNetzwerk.add(mntnORNetwork);

		JMenuItem mntnXORNetwork = new ActionJMenuItem(Settings.i18n.getString("menu.network.xor"), Actions.LOAD_XOR_NETWORK);
		mntmNetzwerk.add(mntnXORNetwork);

		JMenuItem mntnANDNetwork = new ActionJMenuItem(Settings.i18n.getString("menu.network.and"), Actions.LOAD_AND_NETWORK);
		mntmNetzwerk.add(mntnANDNetwork);

		JMenuItem mntn2BitAddiererNetwork = new ActionJMenuItem(Settings.i18n.getString("menu.network.2bitaddierer"),
				Actions.LOAD_2_BIT_ADDIERER_NETWORK);
		mntmNetzwerk.add(mntn2BitAddiererNetwork);

		return mntmNetzwerk;
	}

	/**
	 * Help menu
	 * 
	 * @return JMenu
	 */
	private JMenu getHilfeMenu() {
		JMenu mnHilfe = new JMenu(Settings.i18n.getString("menu.help"));

		JMenuItem mntmUeber = new ActionJMenuItem(Settings.i18n.getString("menu.help.about"), Actions.ABOUT);
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
				// Check if the current session is unsaved -> save?
				// Create a new Session

				// Clear Graph
				GraphController.getInstance().reset();

				// Reset Sidebar
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
				Settings.getInstance().getCurrentSession();
				// TODO soll die current session beim schließen gespeichert werden?

				break;
			case CHANGE_BETWEEN_SESSIONS:
				// TODO aktuelle Session auf die neue ausgewählte setzen
				System.out.println(e.getActionCommand());
				UserSession current = Settings.getInstance().getCurrentSession();
				System.out.println("Current: " + current);
				// loadNetworkFromFile(e.getActionCommand());
				// NetConfig netConfig = new NetConfig();
				// Settings.getInstance().getCurrentSession().setNetworkConfig(netConfig);
				// Main.instance.getGraphLayoutViewer().renderNetwork(netConfig.getNetwork());
				break;
			case VIEW_DATA:
				// TODO
				break;
			case VIEW_TRAINING:
				// TODO
				break;
			case ABOUT:
				JFrame dialog = new JFrame();
				JOptionPane.showMessageDialog(dialog, "Anton Wilhelm, \n" + "Alexander Vey, \n" + "Sofia Daskalou, 26201618 \n",
						"Teilnehmer", JOptionPane.INFORMATION_MESSAGE);
				// TODO
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
				e.printStackTrace();
			}

		} else if (name.equals("xor")) {
			try {
				importFile = new File(new URI(MainMenu.class.getClassLoader().getResource("net_cfg.csv").toString()));
				settings.loadNetworkFromFile(importFile);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

		} else if (name.equals("and")) {
			try {
				importFile = new File(new URI(MainMenu.class.getClassLoader().getResource("net_cfg_and.csv").toString()));
				settings.loadNetworkFromFile(importFile);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

		} else if (name.equals("2-bit-addierer")) {
			try {
				importFile = new File(new URI(MainMenu.class.getClassLoader().getResource("net_cfg_2BitAddierer.csv").toString()));
				settings.loadNetworkFromFile(importFile);
			} catch (URISyntaxException e) {
				e.printStackTrace();
			}

		}
		addSessionToSubMenu();
	}

	public void addSessionToSubMenu() {
		String name = Settings.getInstance().getCurrentSession().toString();
		JMenuItem mtSession = new ActionMenuItem(name, Actions.CHANGE_BETWEEN_SESSIONS);
		subMenuSession.add(mtSession);
	}
}
