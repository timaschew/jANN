package de.unikassel.ann.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import de.unikassel.ann.config.NetConfig;
import de.unikassel.ann.controller.Actions;
import de.unikassel.ann.controller.Settings;
import de.unikassel.ann.gui.graph.GraphLayoutViewer;
import de.unikassel.ann.io.NetIO;
import de.unikassel.ann.model.UserSession;

public class MainMenu extends JMenuBar {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Main main;
	private JMenu mnDatei;

	/**
	 * Constructor
	 */
	public MainMenu(final Main main) {
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
	private JMenu getDateiMenu() {
		mnDatei = new JMenu(Settings.i18n.getString("menu.file"));

		JMenuItem mntmNeu = new ActionMenuItem(Settings.i18n.getString("menu.file.new"), Actions.NEW);
		mnDatei.add(mntmNeu);

		JMenuItem mntmImport = new ActionMenuItem(Settings.i18n.getString("menu.file.import"), Actions.IMPORT);
		mnDatei.add(mntmImport);

		JMenuItem mntmImportRecent = new ActionMenuItem(Settings.i18n.getString("menu.file.openRecent"), Actions.IMPORT_RECENT);
		mnDatei.add(mntmImportRecent);

		mnDatei.addSeparator();

		JMenuItem mntmExport = new ActionMenuItem(Settings.i18n.getString("menu.file.export"), Actions.EXPORT);
		mnDatei.add(mntmExport);

		mnDatei.addSeparator();

		// List<UserSession> liste = Settings.getInstance().getUserSessions();
		// for (int i = 0; i < liste.size(); i++) {
		// JMenuItem mntm = new ActionMenuItem(liste.get(i).toString(), Actions.SAVE);
		// mnDatei.add(mntm);
		// }

		// JMenuItem mntmSpeichern = new ActionMenuItem(Settings.i18n.getString("menu.file.save"), Actions.SAVE);
		// mnDatei.add(mntmSpeichern);

		mnDatei.addSeparator();

		JMenuItem mntmCloseCurrentSession = new ActionMenuItem(Settings.i18n.getString("menu.file.closeCurrentsession"),
				Actions.CLOSE_CURRENT_SESSION);
		mnDatei.add(mntmCloseCurrentSession);

		JMenuItem mntmBeenden = new ActionMenuItem(Settings.i18n.getString("menu.file.exit"), Actions.EXIT);
		mnDatei.add(mntmBeenden);

		return mnDatei;
	}

	/**
	 * Edit menu
	 * 
	 * @return JMenu
	 */
	private JMenu getBearbeitenMenu() {
		JMenu mnBearbeiten = new JMenu(Settings.i18n.getString("menu.edit"));

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
				GraphLayoutViewer.getInstance().clear();

				// Reset Sidebar
				Settings.getInstance().createNewSession("Session");
				Main.instance.initSidebarPanel();
				// sidebarModel.reset();

				List<UserSession> liste = Settings.getInstance().getUserSessions();
				for (int i = 0; i < liste.size(); i++) {
					JMenuItem mntm = new ActionMenuItem(liste.get(i).toString(), Actions.SAVE);
					mnDatei.add(mntm);
				}

				break;
			case IMPORT:
				// TODO
				ImportFilePanel panel = ImportFilePanel.getImportFileInstance();
				panel.setVisible(true);
				break;
			case IMPORT_RECENT:
				break;
			case EXPORT:
				break;
			case CLOSE_CURRENT_SESSION:
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
			case LOAD_OR_NETWORK:
				GraphLayoutViewer.getInstance().clear();
				try {
					createOrNetwork();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case LOAD_XOR_NETWORK:
				GraphLayoutViewer.getInstance().clear();
				try {
					createXorNetwork();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case LOAD_AND_NETWORK:
				GraphLayoutViewer.getInstance().clear();
				try {
					createAndNetwork();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				break;
			case LOAD_2_BIT_ADDIERER_NETWORK:
				GraphLayoutViewer.getInstance().clear();
				try {
					createTwoBitAddiererNetwork();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				} catch (ClassNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
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

		/**
		 * @throws ClassNotFoundException
		 * @throws IOException
		 * 
		 */
		private void createTwoBitAddiererNetwork() throws IOException, ClassNotFoundException {
			String curDir = System.getProperty("user.dir");
			File importFile = new File(curDir + "/src/test/resources/net_cfg_2BitAddierer.csv");

			NetIO netIO = new NetIO();

			// read and parse file
			netIO.readConfigFile(importFile);

			// create network (topology and synapses)
			NetConfig netConfig = netIO.generateNetwork();

			// render network as graph
			main.getGraphLayoutViewer().renderNetwork(netConfig.getNetwork());
		}

		/**
		 * @throws ClassNotFoundException
		 * @throws IOException
		 * 
		 */
		private void createAndNetwork() throws IOException, ClassNotFoundException {
			String curDir = System.getProperty("user.dir");
			File importFile = new File(curDir + "/src/test/resources/net_cfg_and.csv");

			NetIO netIO = new NetIO();

			// read and parse file
			netIO.readConfigFile(importFile);

			// create network (topology and synapses)
			NetConfig netConfig = netIO.generateNetwork();

			// render network as graph
			main.getGraphLayoutViewer().renderNetwork(netConfig.getNetwork());
		}

		/**
		 * @throws ClassNotFoundException
		 * @throws IOException
		 * 
		 */
		private void createXorNetwork() throws IOException, ClassNotFoundException {
			String curDir = System.getProperty("user.dir");
			File importFile = new File(curDir + "/src/test/resources/net_cfg.csv");

			NetIO netIO = new NetIO();

			// read and parse file
			netIO.readConfigFile(importFile);

			// create network (topology and synapses)
			NetConfig netConfig = netIO.generateNetwork();

			// render network as graph
			main.getGraphLayoutViewer().renderNetwork(netConfig.getNetwork());

		}

		/**
		 * @throws ClassNotFoundException
		 * @throws IOException
		 * 
		 */
		private void createOrNetwork() throws IOException, ClassNotFoundException {
			String curDir = System.getProperty("user.dir");
			File importFile = new File(curDir + "/src/test/resources/net_cfg_or.csv");

			NetIO netIO = new NetIO();

			// read and parse file
			netIO.readConfigFile(importFile);

			// create network (topology and synapses)
			NetConfig netConfig = netIO.generateNetwork();

			// render network as graph
			main.getGraphLayoutViewer().renderNetwork(netConfig.getNetwork());
		}

		private void testNetwork() throws IOException, ClassNotFoundException {
			String curDir = System.getProperty("user.dir");
			File importFile = new File(curDir + "/src/test/resources/net_cfg.csv");

			NetIO netIO = new NetIO();

			// read and parse file
			netIO.readConfigFile(importFile);

			// create network (topology and synapses)
			NetConfig netConfig = netIO.generateNetwork();

			// render network as graph
			main.getGraphLayoutViewer().renderNetwork(netConfig.getNetwork());
		}

	}

}
