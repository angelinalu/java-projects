/**
 * MysqlAccess.java 1.0 Sep 18, 2018
 *
 * Copyright (c) 2018 Angelina Lu, Thomas Sheehy. All Rights Reserved
 * Campus Box 9247. Elon University, Elon, NC 27244
 */
package edu.elon.contact;

import java.sql.*;
import java.util.ArrayList;
import java.util.Observable;

/**
 * Manages all database interactions for this program through use of JDBC
 * 
 * @author Angelina Lu, Thomas Sheehy
 * @version 1.0
 *
 */
public class DatabaseModel extends Observable{

	//global variables for statements and table name
	static Statement stmt = null;
	static String table = null;
	String[] entry = new String[5];
	/**
	 * Constructor for MysqlAccess.
	 */
	public DatabaseModel() {

	}

	/**
	 * Attempts to log user into database.
	 * 
	 * @param username String for database credential.
	 * @param passsword String for database credential.
	 * @param ipAddress String for database credential.
	 * @param databaseName String for database credential.
	 * @param tableName String for database credential.
	 */
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

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Gets list of all preexisting primary keys in database
	 * 
	 * @return ArrayList<String> of primary keys found in database's table.
	 */
	public ArrayList<String> getKeys() {
		ArrayList<String> stringKeys = new ArrayList<String>();
		String select = "SELECT pk FROM " + table + ";";
		try {
			ResultSet keys = stmt.executeQuery(select);
			while (keys.next()) {
				stringKeys.add(keys.getString(1));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return stringKeys;
	}

	/**
	 * Searches database's table for row related to specified primary key and returns all records
	 * from found row except for the primary key itself.
	 * 
	 * @param pk String value of primary key to be searched.
	 * @return String[] containing row information from database's table.
	 * @throws SQLException
	 */
	public void scrollTable(String pk)
			throws SQLException{
		String query = "SELECT * FROM " + table + " WHERE pk = " + pk + ";";
		ResultSet rset = stmt.executeQuery(query);
		rset.last();
		String[] entry = new String[5];
		entry[0] = rset.getString("first_name");
		entry[2] = rset.getString("middle_name");
		entry[1] = rset.getString("last_name");
		entry[3] = rset.getString("email");
		entry[4] = rset.getString("major");
	}

	/**
	 * Creates a new row in database's table using provided information and returns back that new
	 * row's (synthetic) primary key.
	 * 
	 * @param firstName String information on row to be added.
	 * @param middleName String information on row to be added.
	 * @param lastName String information on row to be added.
	 * @param email String information on row to be added.
	 * @param major String information on row to be added.
	 * @return int value of primary key for newly added row.
	 * @throws SQLException
	 */
	public void addEntry(String firstName, String middleName, String lastName, 
			String email, String major) throws SQLException {
		String insert = "INSERT INTO " + table
				+ "(first_name, middle_name, last_name, email, major)\n" + 
				"VALUES (\"" + firstName + "\", \"" + middleName + "\", \"" 
				+ lastName + "\", \"" + email + "\", \"" + major + "\");";

		stmt.executeUpdate(insert);
		System.out.println("Added " + firstName + " to table");
		ResultSet rset = stmt.executeQuery("SELECT * FROM " + table + ";");
		rset.last();
		entry[0] = rset.getString("first_name");
		entry[2] = rset.getString("middle_name");
		entry[1] = rset.getString("last_name");
		entry[3] = rset.getString("email");
		entry[4] = rset.getString("major");
	}

	/**
	 * Removes the row in database's table that matches provided primary key.
	 * 
	 * @param pk String value for primary key.
	 */
	public void removeEntry(String pk) {
		String delete = "DELETE FROM " + table + " WHERE pk = " + pk;
		try {
			stmt.executeUpdate(delete);
			System.out.println("Deleted " + pk + " from table");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}

	/**
	 * Clears all rows from database's table.
	 * @throws SQLException 
	 */
	public void clear() throws SQLException {

		String clear = "DELETE FROM " + table;
		try {
			stmt.executeUpdate(clear);
			System.out.println("Cleared table");

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResultSet rset = stmt.executeQuery("SELECT * FROM " + table + ";");
		rset.last();
		entry[0] = rset.getString("first_name");
		entry[2] = rset.getString("middle_name");
		entry[1] = rset.getString("last_name");
		entry[3] = rset.getString("email");
		entry[4] = rset.getString("major");
	}

	/**
	 * Updates database row that matches provided primary key so that records match the rest of the
	 * inputed Strings.
	 * 
	 * @param pk String value for primary key.
	 * @param first String new value for record to become.
	 * @param middle String new value for record to become.
	 * @param last String new value for record to become.
	 * @param email String new value for record to become.
	 * @param major String new value for record to become.
	 * @return String[] of new values
	 * @throws SQLException
	 */
	public void updateEntry(String pk, String first, String middle,
			String last, String email, String major) throws SQLException {
		String update = "UPDATE " +table + 
				" SET first_name = \"" + first + "\", middle_name = \"" + middle + "\", last_name = \""
				+ last + "\", email = \"" + email + "\", major = \"" + major +
				"\" WHERE pk = " + pk + ";";
		stmt.executeUpdate(update);
		String query = "SELECT * FROM " + table + " WHERE pk = " + pk + ";";

		ResultSet rset = stmt.executeQuery(query);
		rset.next();
		entry[0] = rset.getString("first_name");
		entry[1] = rset.getString("middle_name");
		entry[2] = rset.getString("last_name");
		entry[3] = rset.getString("email");
		entry[4] = rset.getString("major");
	}

}
