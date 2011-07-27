package de.unikassel.jung;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.print.PrinterJob;
import java.io.File;
import java.util.HashMap;

import javax.swing.AbstractAction;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import edu.uci.ics.jung.algorithms.layout.AbstractLayout;
import edu.uci.ics.jung.algorithms.layout.StaticLayout;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.visualization.GraphZoomScrollPane;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.annotations.AnnotationControls;
import edu.uci.ics.jung.visualization.control.CrossoverScalingControl;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ScalingControl;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;

import org.apache.commons.collections15.Factory;
import org.apache.commons.collections15.functors.MapTransformer;
import org.apache.commons.collections15.map.LazyMap;

public class JungTest extends JFrame {
	
	/**
     * the graph
     */
    Graph<Number,Number> graph;
    
    AbstractLayout<Number,Number> layout;

    /**
     * the visual component and renderer for the graph
     */
    VisualizationViewer<Number,Number> vv;
    
	
	public static void main (String args[]) {
//		JFrame frame = new JFrame();
		final JungTest demo = new JungTest();
		demo.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        
        JMenu menu = new JMenu("File");
//        menu.add(new AbstractAction("Make Image") {
//            public void actionPerformed(ActionEvent e) {
//                JFileChooser chooser  = new JFileChooser();
//                int option = chooser.showSaveDialog(demo);
//                if(option == JFileChooser.APPROVE_OPTION) {
//                    File file = chooser.getSelectedFile();
//                    demo.writeJPEGImage(file);
//                }
//            }});
//        menu.add(new AbstractAction("Print") {
//            public void actionPerformed(ActionEvent e) {
//                    PrinterJob printJob = PrinterJob.getPrinterJob();
//                    printJob.setPrintable(demo);
//                    if (printJob.printDialog()) {
//                        try {
//                            printJob.print();
//                        } catch (Exception ex) {
//                            ex.printStackTrace();
//                        }
//                    }
//            }});
        JPopupMenu.setDefaultLightWeightPopupEnabled(false);
        JMenuBar menuBar = new JMenuBar();
        menuBar.add(menu);
        demo.setJMenuBar(menuBar);
//        demo.getContentPane().add(demo);
        demo.pack();
        demo.setVisible(true);
	}
	
	private JungTest() {
		// create a simple graph for the demo
        graph = new SparseMultigraph<Number,Number>();

        this.layout = new StaticLayout<Number,Number>(graph, 
        	new Dimension(600,600));
        
        vv =  new VisualizationViewer<Number,Number>(layout);
        vv.setBackground(Color.white);

        
        
        vv.getRenderContext().setVertexLabelTransformer(MapTransformer.<Number,String>getInstance(
        		LazyMap.<Number,String>decorate(new HashMap<Number,String>(), new ToStringLabeller<Number>())));
        
        vv.getRenderContext().setEdgeLabelTransformer(MapTransformer.<Number,String>getInstance(
        		LazyMap.<Number,String>decorate(new HashMap<Number,String>(), new ToStringLabeller<Number>())));

        vv.setVertexToolTipTransformer(vv.getRenderContext().getVertexLabelTransformer());
        

        Container content = getContentPane();
        final GraphZoomScrollPane panel = new GraphZoomScrollPane(vv);
        content.add(panel);
        Factory<Number> vertexFactory = new VertexFactory();
        Factory<Number> edgeFactory = new EdgeFactory();
        
        final EditingModalGraphMouse<Number,Number> graphMouse = 
        	new EditingModalGraphMouse<Number,Number>(vv.getRenderContext(), vertexFactory, edgeFactory);
        
        // the EditingGraphMouse will pass mouse event coordinates to the
        // vertexLocations function to set the locations of the vertices as
        // they are created
//        graphMouse.setVertexLocations(vertexLocations);
        vv.setGraphMouse(graphMouse);
        vv.addKeyListener(graphMouse.getModeKeyListener());

        graphMouse.setMode(ModalGraphMouse.Mode.EDITING);
        
        final ScalingControl scaler = new CrossoverScalingControl();
        JButton plus = new JButton("+");
        plus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1.1f, vv.getCenter());
            }
        });
        JButton minus = new JButton("-");
        minus.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                scaler.scale(vv, 1/1.1f, vv.getCenter());
            }
        });
        
        JButton help = new JButton("Help");
        help.addActionListener(new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(vv, "Instructions...");
            }});

        AnnotationControls<Number,Number> annotationControls = 
        	new AnnotationControls<Number,Number>(graphMouse.getAnnotatingPlugin());
        JPanel controls = new JPanel();
        controls.add(plus);
        controls.add(minus);
        JComboBox modeBox = graphMouse.getModeComboBox();
        controls.add(modeBox);
        controls.add(annotationControls.getAnnotationsToolBar());
        controls.add(help);
        content.add(controls, BorderLayout.SOUTH);
    }

	class VertexFactory implements Factory<Number> {
		
		int i=0;
	
		public Number create() {
			return i++;
		}
	}
	
	class EdgeFactory implements Factory<Number> {
	
		int i=0;
		
		public Number create() {
			return i++;
		}
	}

}
