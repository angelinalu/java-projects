/**
 * DatabaseModel.java 1.0 Dec 2, 2018
 *
 * Copyright (c) 2018 Thomas Sheehy. All Rights Reserved
 * Campus Box 9247. Elon University, Elon, NC 27244
 */
package edu.elon.contact2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * Start each class or interface with summary description line
 *
 * @author staltas
 * @version 1.0
 *
 */
public class DatabaseModel implements Model {

	//global variables for statements and table name
	static Statement stmt = null;
	static String table = null;
	private String[] fieldLabels;
	int pk;
	int firstKey;
	int lastKey;
	private boolean connectionComplete;
	ArrayList<DatabaseObserver> observers;
	ArrayList<Integer> primaryKeys;

	public DatabaseModel() {
		fieldLabels = new String[]{"First Name","Middle Name",
				"Last Name","Email","Major","","","","",""};
		observers = new ArrayList<DatabaseObserver>();
		pk = 0;
		firstKey = 0;
		connectionComplete = false;
		lastKey = 0;
		primaryKeys = new ArrayList<Integer>();
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#registerObserver(edu.elon.contact2.DatabaseObserver)
	 */
	@Override
	public void registerObserver(DatabaseObserver aDatabaseObserver) {
		observers.add(aDatabaseObserver);
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#removeObserver(edu.elon.contact2.DatabaseObserver)
	 */
	@Override
	public void removeObserver(DatabaseObserver aDatabaseObserver) {
		int i = observers.indexOf(aDatabaseObserver);
		if (i >= 0) {
			observers.remove(i);
		}
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#notifyObservers()
	 */
	@Override
	public void notifyObservers() {
		for(int i = 0; i < observers.size(); i++) {
			DatabaseObserver observer = (DatabaseObserver)observers.get(i);
			observer.updateFields();
		}
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#clear()
	 */
	@Override
	public void clear() {
		System.out.println("Clear DB");
		String clear = "DELETE FROM " + table;
		try {

			stmt.executeUpdate(clear);
			primaryKeys.clear();

			System.out.println("Cleared table");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		for (int i = 5; i < fieldLabels.length; i++) {
			fieldLabels[i] = "";
		}
		primaryKeys.clear();
		pk = 0;
		firstKey = 0;
		lastKey = 0;
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#add()
	 */
	@Override
	public void add(String firstName, String middleName, String lastName, 
			String email, String major) throws SQLException {
		String insert = "INSERT INTO " + table
				+ "(first_name, middle_name, last_name, email, major)\n" + 
				"VALUES (\"" + firstName + "\", \"" + middleName + "\", \"" 
				+ lastName + "\", \"" + email + "\", \"" + major + "\");";
		stmt.executeUpdate(insert);
		System.out.println("Added " + firstName + " to table");
		ResultSet rset = stmt.executeQuery("SELECT * FROM " + table + ";");
		rset.last();
		lastKey = rset.getInt("pk");
		ResultSet count = stmt.executeQuery("SELECT COUNT(*) AS rowCount FROM " + table + ";");
		count.last();
		int size = count.getInt("rowCount");
		if(size == 1) {
			pk = lastKey;
			firstKey = lastKey;
		}
		primaryKeys.add(lastKey);
		System.out.println(lastKey);
		fieldLabels[5] = firstName;
		fieldLabels[6] = middleName;
		fieldLabels[7] = lastName;
		fieldLabels[8] = email;
		fieldLabels[9] = major;
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#remove()
	 */
	@Override
	public void remove() {
		String delete = "DELETE FROM " + table + " WHERE pk = " + Integer.toString(pk) + ";";
		try {
			stmt.executeUpdate(delete);
			System.out.println("Deleted " + pk + " from table");
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (pk == lastKey) {
			// pk was the last primaryKey, remove it, display what was to the left and make new lastKey +
			// new pk what was to the left
			if (primaryKeys.size() > 1) {
				pk = primaryKeys.get(primaryKeys.size()-2);				
			}
			primaryKeys.remove(primaryKeys.indexOf(lastKey));
			lastKey = pk;
			String query = "SELECT * FROM " + table + " WHERE pk = " + Integer.toString(pk) + ";";
			ResultSet rset;
			try {
				rset = stmt.executeQuery(query);
				rset.last();
				fieldLabels[5] = rset.getString("first_name");
				fieldLabels[6] = rset.getString("middle_name");
				fieldLabels[7] = rset.getString("last_name");
				fieldLabels[8] = rset.getString("email");
				fieldLabels[9] = rset.getString("major");
			} catch (SQLException e) {
				fieldLabels[5] = "";
				fieldLabels[6] = "";
				fieldLabels[7] = "";
				fieldLabels[8] = "";
				fieldLabels[9] = "";
			}
		} else {
			// pk was not the last primaryKey, remove it, display what was to the right and make new pk
			// what was to the right;
			if (pk == firstKey) {
				firstKey = primaryKeys.get(1);
			}
			int temp = primaryKeys.indexOf(pk);
			primaryKeys.remove(pk);
			pk = primaryKeys.get(temp);
			String query = "SELECT * FROM " + table + " WHERE pk = " + Integer.toString(pk) + ";";
			ResultSet rset;
			try {
				rset = stmt.executeQuery(query);
				rset.last();
				fieldLabels[5] = rset.getString("first_name");
				fieldLabels[6] = rset.getString("middle_name");
				fieldLabels[7] = rset.getString("last_name");
				fieldLabels[8] = rset.getString("email");
				fieldLabels[9] = rset.getString("major");
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#update()
	 */
	@Override
	public void update(String first, String middle, String last, 
			String email, String major) throws SQLException {
		String update = "UPDATE " +table + 
				" SET first_name = \"" + first + "\", middle_name = \"" + middle + "\", last_name = \""
				+ last + "\", email = \"" + email + "\", major = \"" + major +
				"\" WHERE pk = " + pk + ";";
		stmt.executeUpdate(update);
		fieldLabels[5] = first;
		fieldLabels[6] = middle;
		fieldLabels[7] = last;
		fieldLabels[8] = email;
		fieldLabels[9] = major;
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#cancel()
	 */
	@Override
	public void cancel() throws SQLException {
		String query = "SELECT * FROM " + table + " WHERE pk = " + Integer.toString(pk) + ";";
		ResultSet rset = stmt.executeQuery(query);
		rset.last();
		fieldLabels[5] = rset.getString("first_name");
		fieldLabels[6] = rset.getString("middle_name");
		fieldLabels[7] = rset.getString("last_name");
		fieldLabels[8] = rset.getString("email");
		fieldLabels[9] = rset.getString("major");
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#next()
	 */
	@Override
	public void next() {
		if (pk != lastKey) {
			if (primaryKeys.size() > 0) {
				int temp = primaryKeys.indexOf(pk);
				pk = primaryKeys.get(temp + 1);
			}
			String query = "SELECT * FROM " + table + " WHERE pk = " + Integer.toString(pk) + ";";
			ResultSet rset;
			try {
				rset = stmt.executeQuery(query);
				rset.last();
				fieldLabels[5] = rset.getString("first_name");
				fieldLabels[6] = rset.getString("middle_name");
				fieldLabels[7] = rset.getString("last_name");
				fieldLabels[8] = rset.getString("email");
				fieldLabels[9] = rset.getString("major");
				notifyObservers();
			} catch (SQLException e) {

			}
		}
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#previous()
	 */
	@Override
	public void previous() {
		if (pk != firstKey) {
			if (primaryKeys.size() > 0) {
				int temp = primaryKeys.indexOf(pk);
				pk = primaryKeys.get(temp - 1);
			}
			String query = "SELECT * FROM " + table + " WHERE pk = " + Integer.toString(pk) + ";";
			ResultSet rset;
			try {
				rset = stmt.executeQuery(query);
				rset.last();
				fieldLabels[5] = rset.getString("first_name");
				fieldLabels[6] = rset.getString("middle_name");
				fieldLabels[7] = rset.getString("last_name");
				fieldLabels[8] = rset.getString("email");
				fieldLabels[9] = rset.getString("major");
				notifyObservers();
			} catch (SQLException e) {

			}
		}
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#exit()
	 */
	@Override
	public void exit() {
		System.exit(0);
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#connect()
	 */
	@Override
	public void connect(String username, String password, String ipAddress,
			String databaseName, String tableName) {
		table = tableName;
		//allocate database connection object
		try {
			Connection connection = DriverManager.getConnection
					("jdbc:mysql://" + ipAddress + ":3306/" + databaseName + "?useSSL=false",
							username, password);
			stmt = connection.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			connectionComplete = true;
		} catch (SQLException e) {
			connectionComplete = false;
			e.printStackTrace();
		}
		fieldLabels[0] = "First Name";
		fieldLabels[1] = "Middle Name";
		fieldLabels[2] = "Last Name";
		fieldLabels[3] = "Email";
		fieldLabels[4] = "Major";

		// Search for the first row in the appropriate table and fill in remaining fieldLabels
		getKeys();
		if (primaryKeys.size() > 0) {
			lastKey = primaryKeys.get(primaryKeys.size()-1);    	
			firstKey = primaryKeys.get(0);
			pk = firstKey;
		}
		String beginning = "SELECT * FROM " + table + " WHERE pk = " + Integer.toString(firstKey) + ";";
		ResultSet rset;
		try {
			rset = stmt.executeQuery(beginning);
			rset.last();
			fieldLabels[5] = rset.getString("first_name");
			fieldLabels[6] = rset.getString("middle_name");
			fieldLabels[7] = rset.getString("last_name");
			fieldLabels[8] = rset.getString("email");
			fieldLabels[9] = rset.getString("major");

		} catch (SQLException e) {

		}
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#getValues()
	 */
	@Override
	public String[] getValues() {
		return fieldLabels;
	}

	public void getKeys() {
		ArrayList<Integer> stringKeys = new ArrayList<Integer>();
		String select = "SELECT pk FROM " + table + ";";
		try {
			ResultSet keys = stmt.executeQuery(select);
			while (keys.next()) {
				stringKeys.add(Integer.parseInt(keys.getString(1)));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		primaryKeys = stringKeys;
		if (primaryKeys.size() == 0) {
			firstKey = -1;
			lastKey = -1;
		}
		else {
			firstKey = primaryKeys.get(0);
			lastKey = primaryKeys.get(primaryKeys.size()-1);
		}
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#readyConnect()
	 */
	@Override
	public void readyConnect() {
		fieldLabels[0] = "User Name";
		fieldLabels[1] = "Password";
		fieldLabels[2] = "IP Address";
		fieldLabels[3] = "Database Name";
		fieldLabels[4] = "Table Name";
		notifyObservers();
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#getConnection()
	 */
	@Override
	public boolean getConnection() {
		return connectionComplete;
	}

	/* (non-Javadoc)
	 * @see edu.elon.contact2.Model#readyAdd()
	 */
	@Override
	public void readyAdd() {
		fieldLabels[5] = "";
		fieldLabels[6] = "";
		fieldLabels[7] = "";
		fieldLabels[8] = "";
		fieldLabels[9] = "";
		notifyObservers();
	}

}
