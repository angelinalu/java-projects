/**
 * ContactApplication.java 1.0 Sep 18, 2018
 *
 * Copyright (c) 2018 Angelina Lu, Thomas Sheehy. All Rights Reserved
 * Campus Box 9247. Elon University, Elon, NC 27244
 */
package edu.elon.contact;

/**
 * Contact application. Provides a Gui that can connect to a database and implement CRUD tasks.
 *
 * @author Angelina Lu and Thomas Sheehy
 * @version 1.0
 *
 */
public class ContactApplication {

  /**
   * Runs application.
   * 
   * @param terminal arguments, application doesn't require any to run.
   */
  public static void main(String[] args) {
    DisplayPanel displayPanel = new DisplayPanel();
    displayPanel.display();
  }

}
