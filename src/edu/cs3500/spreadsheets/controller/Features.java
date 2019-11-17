package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Functionality offered by the controller for the spreadsheet.
 */
public interface Features {

  /**
   * Allows user to edit and change the value of a cell.
   * @param c the cell's Coordinate
   * @param s the new desired value of the cell
   * @return The String so far
   */
  String editCell(Coord c, String s);


  /**
   * Saves a spreadsheet.
   */
  void saveSheet();

  /**
   * Loads a spreadsheet.
   */
  void loadSheet(String sheet);

}
