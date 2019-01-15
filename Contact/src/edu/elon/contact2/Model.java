/**
 * Model.java 1.0 Dec 2, 2018
 *
 * Copyright (c) 2018 Thomas Sheehy. All Rights Reserved
 * Campus Box 9247. Elon University, Elon, NC 27244
 */
package edu.elon.contact2;

import java.sql.SQLException;

/**
 * Start each class or interface with summary description line
 *
 * @author staltas
 * @version 1.0
 *
 */
public interface Model {

  /**
   * @param aDatabaseObserver
   */
  public void registerObserver(DatabaseObserver aDatabaseObserver);
  
  public void removeObserver(DatabaseObserver aDatabaseObserver);
  
  public void notifyObservers();
  
  public String[] getValues();
  
  public void clear();
  
  public void add(String firstName, String middleName, String lastName, 
      String email, String major) throws SQLException;
  
  public void remove();
  
  public void update(String first, String middle, String last, 
      String email, String major) throws SQLException;
  
  public void cancel() throws SQLException;
  
  public void next() throws SQLException;
  
  public void previous() throws SQLException;
    
  public void exit();
  
  public void connect(String username, String password, String ipAddress,
      String databaseName, String tableName);

  /**
   * 
   */
  public void readyConnect();

  /**
   * @return
   */
  public boolean getConnection();

  /**
   * 
   */
  public void readyAdd();

}
