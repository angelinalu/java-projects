/**
 * DatabaseController.java 1.0 Dec 2, 2018
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
public class DatabaseController implements Controller{
  Model model;
  DisplayView view;
  
  public DatabaseController(Model model) {
      this.model = model;
      view = new DisplayView(this, model);
      view.createGui();
      view.notConnected();
  }

  /* (non-Javadoc)
   * @see edu.elon.contact2.Controller#clear()
   */
  @Override
  public void clear() {
    model.clear();
  }

  /* (non-Javadoc)
   * @see edu.elon.contact2.Controller#add()
   */
  @Override
  public void add() {
    model.readyAdd();
    view.okayCan();
  }

  /* (non-Javadoc)
   * @see edu.elon.contact2.Controller#remove()
   */
  @Override
  public void remove() {
    model.remove();
  }

  /* (non-Javadoc)
   * @see edu.elon.contact2.Controller#update()
   */
  @Override
  public void update(String first, String middle, String last, 
      String email, String major) throws SQLException {
    model.update(first, middle, last, email, major);
  }

  /* (non-Javadoc)
   * @see edu.elon.contact2.Controller#cancel()
   */
  @Override
  public void cancel() throws SQLException {
    model.cancel();
    view.prevNext();
  }

  /* (non-Javadoc)
   * @see edu.elon.contact2.Controller#next()
   */
  @Override
  public void next() throws SQLException {
    model.next();
  }

  /* (non-Javadoc)
   * @see edu.elon.contact2.Controller#previous()
   */
  @Override
  public void previous() throws SQLException {
    model.previous();
  }

  /* (non-Javadoc)
   * @see edu.elon.contact2.Controller#okay()
   */
  @Override
  public void okay(String username, String password, String ipAddress,
      String databaseName, String tableName) {
    // if conncetion already exists, run add. else run connect
    if (model.getConnection()) {
      try {
        model.add(username, password, ipAddress, databaseName, tableName);
        view.prevNext();
      } catch (SQLException e) {
        // TODO Auto-generated catch block
        e.printStackTrace();
      }
    } else {
      try {
        model.connect(username, password, ipAddress, databaseName, tableName);
        view.connected();
        view.prevNext();
      } catch (Exception e) {
        view.settingsIncorrect();
      }
    }
  }

  /* (non-Javadoc)
   * @see edu.elon.contact2.Controller#exit()
   */
  @Override
  public void exit() {
    view.dispose();
    model.exit();
  }

  /* (non-Javadoc)
   * @see edu.elon.contact2.Controller#connect()
   */
  @Override
  public void connect() {
    model.readyConnect();
    view.conOkay();
  }
  
}
