/**
 * Projekt ANNtool 
 *
 * Copyright (c) 2011 github.com/timaschew/jANN
 * 
 * anton
 */
package de.unikassel.mvc;

import java.awt.BorderLayout;
import java.awt.event.*;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

import javax.swing.*;

public class MVCProgressBarThread {
   private static void createAndShowUI() {
      MVC_View view = new MVC_View();
      MVC_Model model = new MVC_Model();
      MVC_Control control = new MVC_Control(view, model);
      view.setControl(control);

      JFrame frame = new JFrame("MVC_ProgressBarThread");
      frame.getContentPane().add(view);
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.pack();
      frame.setLocationRelativeTo(null);
      frame.setVisible(true);
   }

   public static void main(String[] args) {
      java.awt.EventQueue.invokeLater(new Runnable() {
         public void run() {
            createAndShowUI();
         }
      });
   }
}

@SuppressWarnings("serial")
class MVC_View extends JPanel {
   private MVC_Control control;
   private JProgressBar progressBar = new JProgressBar();
   private JButton startActionButton = new JButton("Start Action");

   public MVC_View() {
      startActionButton.addActionListener(new ActionListener() {
         public void actionPerformed(ActionEvent e) {
            buttonActionPerformed();
         }
      });

      JPanel buttonPanel = new JPanel();
      buttonPanel.add(startActionButton);
      setLayout(new BorderLayout());
      add(buttonPanel, BorderLayout.NORTH);
      add(progressBar, BorderLayout.CENTER);
   }

   public void setControl(MVC_Control control) {
      this.control = control;
   }

   private void buttonActionPerformed() {
      if (control != null) {
         control.doButtonAction();
      }
   }

   public void setProgress(int progress) {
      progressBar.setValue(progress);
   }

   public void start() {
      startActionButton.setEnabled(false);
   }

   public void done() {
      startActionButton.setEnabled(true);
      setProgress(100);
   }
}

class MVC_Control {
   private MVC_View view;
   private MVC_Model model;

   public MVC_Control(final MVC_View view, final MVC_Model model) {
      this.view = view;
      this.model = model;
      model.addPropertyChangeListener(new PropertyChangeListener() {
         public void propertyChange(PropertyChangeEvent pce) {
            if (MVC_Model.PROGRESS.equals(pce.getPropertyName())) {
               view.setProgress((Integer)pce.getNewValue());
            }
         }
      });
   }

   public void doButtonAction() {
      view.start();
      SwingWorker<Void, Void> swingworker = new SwingWorker<Void, Void>() {
         @Override
         protected Void doInBackground() throws Exception {
            model.reset();
            model.startSearch();
            return null;
         }

         @Override
         protected void done() {
            view.done();
         }
      };
      swingworker.execute();
   }

}

class MVC_Model {
   public static final String PROGRESS = "progress";
   private static final int MAX = 100;
   private static final long SLEEP_DELAY = 100;
   private int progress = 0;
   private PropertyChangeSupport pcs = new PropertyChangeSupport(this);

   public void setProgress(int progress) {
      int oldProgress = this.progress;
      this.progress = progress;

      PropertyChangeEvent evt = new PropertyChangeEvent(this, PROGRESS, oldProgress, progress);
      pcs.firePropertyChange(evt);
   }

   public void reset() {
      setProgress(0);
   }

   public void addPropertyChangeListener(PropertyChangeListener listener) {
      pcs.addPropertyChangeListener(listener);
   }

   public void startSearch() {
      for (int i = 0; i < MAX; i++) {
         int newValue = (100 * i) / MAX;
         setProgress(newValue);
         try {
            Thread.sleep(SLEEP_DELAY);
         } catch (InterruptedException e) {}
      }
   }
}
