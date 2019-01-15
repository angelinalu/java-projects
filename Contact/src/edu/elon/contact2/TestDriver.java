/**
 * TestDriver.java 1.0 Dec 2, 2018
 *
 * Copyright (c) 2018 Thomas Sheehy. All Rights Reserved
 * Campus Box 9247. Elon University, Elon, NC 27244
 */
package edu.elon.contact2;

/**
 * Start each class or interface with summary description line
 *
 * @author staltas
 * @version 1.0
 *
 */
public class TestDriver {
  
  public static void main(String[] args) {
    Model model = new DatabaseModel();
    new DatabaseController(model);
  }

}
