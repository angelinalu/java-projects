/**
 * DisplayView.java 1.0 Dec 2, 2018
 *
 * Copyright (c) 2018 Thomas Sheehy. All Rights Reserved
 * Campus Box 9247. Elon University, Elon, NC 27244
 */
package edu.elon.contact2;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import edu.elon.contact.DatabaseModel;

/**
 * Start each class or interface with summary description line
 *
 * @author staltas
 * @version 1.0
 *
 */
public class DisplayView implements DatabaseObserver {
  private Controller controller;
  private Model model;
  
  private JFrame dataGui;
  private JLabel fnLabel;
  private JLabel mnLabel;
  private JLabel lnLabel;
  private JLabel emLabel;
  private JLabel mrLabel;
  private JTextField fnField;
  private JTextField mnField;
  private JTextField lnField;
  private JTextField emField;
  private JTextField mrField;
  private JButton previous;
  private JButton next;
  private JButton okay;
  private JButton cancel;
  private JPanel bottom;
  private JMenuItem connect;
  private JMenuItem clear;
  private JMenuItem exit;
  private JMenuItem add;
  private JMenuItem remove;
  private JMenuItem update;
  private JMenuBar menu;
  private JMenu file;
  private JMenu edit;
  private JPanel information;
  private JPanel complete;  
  
  public DisplayView(Controller controller, Model model) {
    this.controller = controller;
    this.model = model;
    model.registerObserver((DatabaseObserver) this);
  }

  /* (non-Javadoc)
   * @see edu.elon.contact2.DatabaseObserver#updateFields()
   */
  @Override
  public void updateFields() {
    String[] information = model.getValues();
    fnLabel.setText(information[0]);
    mnLabel.setText(information[1]);
    lnLabel.setText(information[2]);
    emLabel.setText(information[3]);
    mrLabel.setText(information[4]);
    fnField.setText(information[5]);
    mnField.setText(information[6]);
    lnField.setText(information[7]);
    emField.setText(information[8]);
    mrField.setText(information[9]);
  }
  
  public void dispose() {
    if (dataGui != null) {
      dataGui.dispose();
    }
  }
  
  public void settingsIncorrect() {
    JOptionPane.showMessageDialog(null, "You did not correctly specify db parameters",
        "DB Settings", JOptionPane.ERROR_MESSAGE);
  }

  /**
   * 
   */
  public void createGui() {
    dataGui = new JFrame();
    fnLabel = new JLabel("");
    mnLabel = new JLabel("");
    lnLabel = new JLabel("");
    emLabel = new JLabel("");
    mrLabel = new JLabel("");
    fnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    mnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    lnLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    emLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    mrLabel.setHorizontalAlignment(SwingConstants.RIGHT);
    fnField = new JTextField(10);
    mnField = new JTextField(10);
    lnField = new JTextField(10);
    emField = new JTextField(10);
    mrField = new JTextField(10);
    previous = new JButton("Previous");
    next = new JButton("Next");
    okay = new JButton("Okay");
    cancel = new JButton("Cancel");
    bottom = new JPanel();
    bottom.add(previous);
    bottom.add(next);
    connect = new JMenuItem();
    connect.setText("Connect");
    clear = new JMenuItem();
    clear.setText("Clear DB");
    exit = new JMenuItem();
    exit.setText("Exit");
    add = new JMenuItem();
    add.setText("Add");
    remove = new JMenuItem();
    remove.setText("Remove");
    update = new JMenuItem();
    update.setText("Update");
    
    // Add action Listeners
    cancel.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent e) {
        try {
          controller.cancel();
        } catch (SQLException e1) {
          // TODO Auto-generated catch block
          e1.printStackTrace();
        }
      }
    });
    okay.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent aE) {
        controller.okay(fnField.getText(), mnField.getText(), lnField.getText(),
            emField.getText(), mrField.getText());
      }
    });
    previous.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent aE) {
        try {
          controller.previous();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
    next.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent aE) {
        try {
          controller.next();
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
    connect.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent aE) {
        controller.connect();
      }
    });
    clear.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent aE) {
        controller.clear();
      }
    });
    exit.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent aE) {
        controller.exit();
      }
    });
    add.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent aE) {
        controller.add();
      }
    });
    remove.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent aE) {
        controller.remove();
      }
    });    
    update.addActionListener(new ActionListener() {
      @Override
      public void actionPerformed(ActionEvent aE) {
        try {
          controller.update(fnField.getText(), mnField.getText(), lnField.getText(),
              emField.getText(), mrField.getText());
        } catch (SQLException e) {
          // TODO Auto-generated catch block
          e.printStackTrace();
        }
      }
    });
    
    // Make the final menu
    menu = new JMenuBar();
    file = new JMenu("File");
    file.add(clear);
    file.add(connect);
    file.add(exit);
    edit = new JMenu("Edit");
    edit.add(add);
    edit.add(remove);
    edit.add(update);
    menu.add(file);
    menu.add(edit);

    // Formatting
    information = new JPanel();
    information.setLayout(new GridLayout(5,2));
    information.add(fnLabel);
    information.add(fnField);
    information.add(mnLabel);
    information.add(mnField);
    information.add(lnLabel);
    information.add(lnField);
    information.add(emLabel);
    information.add(emField);
    information.add(mrLabel);
    information.add(mrField);
    complete = new JPanel();
    complete.setLayout(new BoxLayout(complete, BoxLayout.Y_AXIS));
    complete.add(information);
    complete.add(bottom);
    
    fnLabel.setText("First Name");
    mnLabel.setText("Middle Name");
    lnLabel.setText("Last Name");
    emLabel.setText("Email");
    mrLabel.setText("Major");
/*    bottom.removeAll();
    dataGui.pack();
    bottom.add(okay);
  */  dataGui.pack();
    dataGui.setVisible(true);
    
    dataGui.setJMenuBar(menu);
    dataGui.setTitle("Contact Display View");
    dataGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    dataGui.getContentPane().add(complete);
    dataGui.pack();
    dataGui.setVisible(true);
  }

  /**
   * 
   */
  public void connected() {
    add.setEnabled(true);
    remove.setEnabled(true);
    update.setEnabled(true);
    clear.setEnabled(true);
    connect.setEnabled(false);
    next.setEnabled(true);
    previous.setEnabled(true);
  }

  /**
   * 
   */
  public void notConnected() {
    add.setEnabled(false);
    remove.setEnabled(false);
    update.setEnabled(false);
    clear.setEnabled(false);
    connect.setEnabled(true);
    next.setEnabled(false);
    previous.setEnabled(false);
  }

  /**
   * 
   */
  public void prevNext() {
    // replace bottom panel with Previous and Next buttons
    bottom.removeAll();
    dataGui.pack();
    bottom.add(previous);
    bottom.add(next);
    dataGui.pack();
  }

  /**
   * 
   */
  public void conOkay() {
    // replace bottom panel with only the okay button
    bottom.removeAll();
    dataGui.pack();
    bottom.add(okay);
    dataGui.pack();
  }

  /**
   * 
   */
  public void okayCan() {
    // replace bottom panel with okay and cancel buttons
    bottom.removeAll();
    dataGui.pack();
    bottom.add(okay);
    bottom.add(cancel);
    dataGui.pack();
  }

}
