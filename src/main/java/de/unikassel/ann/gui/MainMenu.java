package de.unikassel.ann.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

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
	public static final int SESSION_MAIN_MENU_POSITION = 6;

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

		JMenu mnBearbeiten = getBearbeitenMenu();
		this.add(mnBearbeiten);

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

		JMenuItem mntmNeu = new ActionMenuItem(Settings.i18n.getString("menu.file.new"), Actions.NEW);
		fileMenu.add(mntmNeu);

		JMenuItem mntmImport = new ActionMenuItem(Settings.i18n.getString("menu.file.import"), Actions.IMPORT);
		fileMenu.add(mntmImport);

		JMenuItem mntmImportRecent = new ActionMenuItem(Settings.i18n.getString("menu.file.openRecent"), Actions.IMPORT_RECENT);
		fileMenu.add(mntmImportRecent);

		fileMenu.addSeparator();

		JMenuItem mntmExport = new ActionMenuItem(Settings.i18n.getString("menu.file.export"), Actions.EXPORT);
		fileMenu.add(mntmExport);

		fileMenu.addSeparator();

		// hier is the Position, if sessions exists
		// addItemToMenu()

		fileMenu.addSeparator();

		JMenuItem mntmCloseCurrentSession = new ActionMenuItem(Settings.i18n.getString("menu.file.closeCurrentsession"),
				Actions.CLOSE_CURRENT_SESSION);
		fileMenu.add(mntmCloseCurrentSession, -1);

		JMenuItem mntmBeenden = new ActionMenuItem(Settings.i18n.getString("menu.file.exit"), Actions.EXIT);
		mntmBeenden.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, ActionEvent.CTRL_MASK));
		fileMenu.add(mntmBeenden, -1);
	}

	/**
	 * Edit menu
	 * 
	 * @return JMenu
	 */
	private JMenu getBearbeitenMenu() {
		JMenu mnBearbeiten = new JMenu(Settings.i18n.getString("menu.edit"));

		JMenuItem mntmUndo = new ActionMenuItem(Settings.i18n.getString("menu.edit.undo"), Actions.UNDO);
		mntmUndo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		mnBearbeiten.add(mntmUndo);

		JMenuItem mntmRedo = new ActionMenuItem(Settings.i18n.getString("menu.edit.redo"), Actions.REDO);
		mntmRedo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		mnBearbeiten.add(mntmRedo);

		return mnBearbeiten;
	}

	/**
	 * View menu
	 * 
	 * @return JMenu
	 */
	private JMenu getAnsichtMenu() {
		JMenu mnAnsicht = new JMenu(Settings.i18n.getString("menu.view"));

		JMenuItem mntmDatenvisualisierung = new ActionMenuItem(Settings.i18n.getString("menu.view.showTrainingData"), Actions.VIEW_DATA);
		mnAnsicht.add(mntmDatenvisualisierung);

		JMenuItem mntmTrainingfehlerverlauf = new ActionMenuItem(Settings.i18n.getString("menu.view.showTrainingError"),
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

		JMenuItem mntmBackpropagation = new ActionMenuItem(Settings.i18n.getString("menu.options.backpropagation"),
				Actions.BACKPROPAGATION_VIEW);
		mnOptions.add(mntmBackpropagation);

		JMenuItem mntmSOM = new ActionMenuItem(Settings.i18n.getString("menu.options.SOM"), Actions.SOM_VIEW);
		mnOptions.add(mntmSOM);

		mnOptions.addSeparator();

		JMenuItem mntmTrainData = new ActionMenuItem(Settings.i18n.getString("menu.options.trainData"), Actions.NORMALIZE_TRAIN_DATA);
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

		JMenuItem mntnORNetwork = new ActionMenuItem(Settings.i18n.getString("menu.network.or"), Actions.LOAD_OR_NETWORK);
		mntmNetzwerk.add(mntnORNetwork);

		JMenuItem mntnXORNetwork = new ActionMenuItem(Settings.i18n.getString("menu.network.xor"), Actions.LOAD_XOR_NETWORK);
		mntmNetzwerk.add(mntnXORNetwork);

		JMenuItem mntnANDNetwork = new ActionMenuItem(Settings.i18n.getString("menu.network.and"), Actions.LOAD_AND_NETWORK);
		mntmNetzwerk.add(mntnANDNetwork);

		JMenuItem mntn2BitAddiererNetwork = new ActionMenuItem(Settings.i18n.getString("menu.network.2bitaddierer"),
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

		JMenuItem mntmUeber = new ActionMenuItem(Settings.i18n.getString("menu.help.about"), Actions.ABOUT);
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
				GraphController.getInstance().clear();

				// Reset Sidebar
				Settings.getInstance().createNewSession("Session");
				Main.instance.initSidebarPanel();
				// sidebarModel.reset();

				break;
			case IMPORT:
				ImportFilePanel panel = new ImportFilePanel();
				panel.setVisible(true);
				break;
			case IMPORT_RECENT:
				// TODO
				break;
			case EXPORT:
				ExportFilePanel export = new ExportFilePanel();
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
			case UNDO:
				// TODO
				break;
			case REDO:
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
			// case TEST_NETWORK:
			// try {
			// testNetwork();
			// } catch (Exception ex) {
			// ex.printStackTrace();
			// }
			// break;
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
		String curDir = System.getProperty("user.dir");
		UserSession session = Settings.getInstance().getCurrentSession();
		File importFile;
		if (name.equals("or") || name.equals("net_cfg_or.csv")) {
			importFile = new File(curDir + "/src/test/resources/net_cfg_or.csv");
			session.loadNetworkFromFile(importFile);
		} else if (name.equals("xor") || name.equals("net_cfg.csv")) {
			importFile = new File(curDir + "/src/test/resources/net_cfg.csv");
			session.loadNetworkFromFile(importFile);
		} else if (name.equals("and")) {
			importFile = new File(curDir + "/src/test/resources/net_cfg_and.csv");
			session.loadNetworkFromFile(importFile);
		} else if (name.equals("2-bit-addierer")) {
			importFile = new File(curDir + "/src/test/resources/net_cfg_2BitAddierer.csv");
			session.loadNetworkFromFile(importFile);
		}
		addToMenu();
	}

	public void addToMenu() {
		String name = Settings.getInstance().getCurrentSession().toString();
		JMenuItem mtSession = new ActionMenuItem(name, Actions.CHANGE_BETWEEN_SESSIONS);
		fileMenu.add(mtSession, SESSION_MAIN_MENU_POSITION);
	}
}
