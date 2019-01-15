/**
 * DisplayPanel.java 1.0 Sep 18, 2018
 *
 * Copyright (c) 2018 Angelina Lu, Thomas Sheehy. All Rights Reserved
 * Campus Box 9247. Elon University, Elon, NC 27244
 */
package edu.elon.contact;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

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

/**
 * Display Panel class manages all interactions that user has with the Gui for the Contact program
 *
 * @author Angelina Lu, Thomas Sheehy
 * @version 1.0
 *
 */
public class DisplayPanel implements ActionListener, Observer{
	private Observable observable;

	private ArrayList<String> primaryKeys = new ArrayList<String>();
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
	private boolean isConnected = false;
	private DatabaseModel databaseReader;
	private String currentKey;
	private String firstKey;
	private String lastKey;

	/**
	 * Constructor for Display Panel.
	 */
	public DisplayPanel() {
		currentKey = "0";
		firstKey = "0";
		lastKey = "0";
		databaseReader = new DatabaseModel();
		dataGui = new JFrame();
		fnLabel = new JLabel("First Name");
		mnLabel = new JLabel("Middle Name");
		lnLabel = new JLabel("Last Name");
		emLabel = new JLabel("Email");
		mrLabel = new JLabel("Major");
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
		cancel.addActionListener(this);
		okay.addActionListener(this);
		bottom = new JPanel();
		bottom.add(previous);
		bottom.add(next);
		previous.addActionListener(this);
		next.addActionListener(this);
		connect = new JMenuItem();
		connect.addActionListener(this);
		connect.setText("Connect");
		clear = new JMenuItem();
		clear.addActionListener(this);
		clear.setText("Clear DB");
		exit = new JMenuItem();
		exit.addActionListener(this);
		exit.setText("Exit");
		add = new JMenuItem();
		add.addActionListener(this);
		add.setText("Add");
		remove = new JMenuItem();
		remove.addActionListener(this);
		remove.setText("Remove");
		update = new JMenuItem();
		update.addActionListener(this);
		update.setText("Update");

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
	}

	/**
	 * Displays the Contact Display Gui.
	 */
	public void display() {
		clear.setEnabled(isConnected);
		add.setEnabled(isConnected);
		update.setEnabled(isConnected);
		remove.setEnabled(isConnected);
		fnLabel.setText("First Name");
		mnLabel.setText("Middle Name");
		lnLabel.setText("Last Name");
		emLabel.setText("Email");
		mrLabel.setText("Major");
		bottom.removeAll();
		dataGui.pack();
		bottom.add(previous);
		bottom.add(next);

		if (currentKey == "0") {
			fnField.setText("");
			mnField.setText("");
			lnField.setText("");
			emField.setText("");
			mrField.setText("");
		} else if (isConnected == true) {
			String[] rowResults;
			try {
				databaseReader.scrollTable(currentKey);
				rowResults = databaseReader.entry;
				
				fnField.setText(rowResults[0]);
				mnField.setText(rowResults[1]);
				lnField.setText(rowResults[2]);
				emField.setText(rowResults[3]);
				mrField.setText(rowResults[4]); 
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		dataGui.setJMenuBar(menu);
		dataGui.setTitle("Contact Display View");
		dataGui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dataGui.getContentPane().add(complete);
		dataGui.pack();
		dataGui.setVisible(true);
	}

	/**
	 * Handles user interactions with Gui's JButtons and JMenuItems and delegates tasks to various
	 * methods based upon details of the interaction.
	 * 
	 * @param e JButton or JMenuItem clicked.
	 */
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			dataGui.dispose();
			System.exit(0);
		} else if (e.getSource() == clear) {
			this.conductClear();
		} else if (e.getSource() == connect) {
			this.connectionGui();
		} else if (e.getSource() == add) {
			this.addGui();
		} else if (e.getSource() == remove) {
			this.conductRemove();
		} else if (e.getSource() == update) {
			this.conductUpdate();
		} else if (e.getSource() == previous) {
			this.conductPrevious();
		} else if (e.getSource() == next) {
			this.conductNext();
		} else if (e.getSource() == cancel) {
			this.display();
		} else if (e.getSource() == okay) {
			if (fnLabel.getText() == "First Name") {
				String newValue;
				try {
					databaseReader.addEntry(fnField.getText(), mnField.getText(), lnField.getText(), emField.getText(), mrField.getText());
					newValue = Integer.toString();
					primaryKeys.add(newValue);
					lastKey = newValue;
					if (primaryKeys.size() == 1) {
						firstKey = newValue;
						currentKey = newValue;
					}
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				this.display();
			} else {
				this.testConnection();
			}
		}
	}

	/**
	 * Calls update query method and provides maintenance logic for list of stored primary keys.
	 */
	public void conductUpdate() {
		try {
			databaseReader.updateEntry(currentKey, fnField.getText(), mnField.getText(), lnField.getText(), emField.getText(), mrField.getText());
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.display();
	}

	/**
	 * Calls clear query method and provides maintenance logic for list of stored primary keys.
	 * @throws SQLException 
	 */
	public void conductClear() throws SQLException {
		System.out.println("Clear DB");
		databaseReader.clear();
		primaryKeys.clear();
		currentKey = "0";
		firstKey = "0";
		lastKey = "0";
		this.display();
	}

	/**
	 * Provides logic for traversing to previous row in database and maintenance logic for list of 
	 * stored primary keys.
	 */
	public void conductPrevious() {
		if (currentKey != firstKey) {
			int currentIndex = primaryKeys.indexOf(currentKey);
			currentKey = primaryKeys.get(currentIndex-1);   
		}
		this.display();
	}

	/**
	 * Provides logic for traversing to next row in database and maintenance logic for list of 
	 * stored primary keys.
	 */
	public void conductNext() {
		if (currentKey != lastKey) {
			int currentIndex = primaryKeys.indexOf(currentKey);
			currentKey = primaryKeys.get(currentIndex+1);
		}
		this.display();
	}

	/**
	 * Calls remove query method and provides maintenance logic for list of stored primary keys.
	 */
	public void conductRemove() {
		databaseReader.removeEntry(currentKey);
		if (currentKey == lastKey) {
			int currentIndex = primaryKeys.indexOf(currentKey);
			primaryKeys.remove(currentIndex);
			currentKey = primaryKeys.get(currentIndex-1);
			lastKey = currentKey;
		} else if (currentKey == firstKey) {
			primaryKeys.remove(0);
			firstKey = primaryKeys.get(0);
			currentKey = primaryKeys.get(0);
		} else {
			int currentIndex = primaryKeys.indexOf(currentKey);
			primaryKeys.remove(currentIndex);
			currentKey = primaryKeys.get(currentIndex);
		}
		this.display();
	}

	/**
	 * Breaks down the user's entered information to pass on to MysqlAccess class in order to connect
	 * to database. Returns to initial Contact Display Gui JLabels and JButtons if connection is
	 * successful, calls settingsIncorrect if connection is unsuccessful.
	 */
	public void testConnection() {
		String username = fnField.getText();
		String password = mnField.getText();
		String ipAddress = lnField.getText();
		String database = emField.getText();
		String table = mrField.getText();
		try {
			databaseReader.connect(username, password, ipAddress, database, table);
			isConnected = true;
			primaryKeys = databaseReader.getKeys();
			if (primaryKeys.isEmpty() == false) {
				currentKey = primaryKeys.get(0);
				firstKey = primaryKeys.get(0);
				lastKey = primaryKeys.get(primaryKeys.size()-1);
			}      
			this.display();
		} catch (Exception e) {
			this.settingsIncorrect();
		}    
	}

	/**
	 * Configures the Contact Display Gui to allow user to connect to the database by:
	 *    Renaming JLabels to prompt user to enter database credentials
	 *    Restructuring bottom buttons to provide "Okay" button in order to complete transaction.
	 */
	public void connectionGui() {
		fnLabel.setText("User Name");
		mnLabel.setText("Password");
		lnLabel.setText("IP Address");
		emLabel.setText("Database Name");
		mrLabel.setText("Table Name");
		bottom.removeAll();
		dataGui.pack();
		bottom.add(okay);
		dataGui.pack();
		dataGui.setVisible(true);
	}

	/**
	 * Provides error message indicative of incorrect credentials for connecting to database.
	 */
	public void settingsIncorrect() {
		JOptionPane.showMessageDialog(null, "You did not correctly specify db parameters",
				"DB Settings", JOptionPane.ERROR_MESSAGE);
	}

	/**
	 * Configures the Contact Display Gui to allow user to add new member to the database by:
	 *    Emptying JTextFields so user can input new data.
	 *    Restructuring bottom buttons to provide "Okay" and "Cancel" buttons to complete transaction.
	 */
	public void addGui() {
		fnField.setText("");
		mnField.setText("");
		lnField.setText("");
		emField.setText("");
		mrField.setText("");
		bottom.removeAll();
		dataGui.pack();
		bottom.add(okay);
		bottom.add(cancel);
		dataGui.pack();
		dataGui.setVisible(true);
	}

	@Override
	public void update(Observable o, Object arg) {
		fnField.setText(databaseReader.entry[0]);
		mnField.setText(databaseReader.entry[1]);
		lnField.setText(databaseReader.entry[2]);
		emField.setText(databaseReader.entry[3]);
		mrField.setText(databaseReader.entry[4]);
	}
}
