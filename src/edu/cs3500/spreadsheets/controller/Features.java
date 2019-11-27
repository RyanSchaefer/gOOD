package edu.cs3500.spreadsheets.controller;

import java.io.IOException;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Functionality offered by the controller for the spreadsheet.
 */
public interface Features {

  /**
   * Allows user to edit and change the value of a cell.
   * @param c the cell's Coordinate
   * @param s the new desired value of the cell
   */
  void editCell(Coord c, String s);

  /**
   * Allows the user to delete the contents of a cell by pressing the delete key.
   * @param c the cell's Coordinate
   */
  void deleteCellContents(Coord c);

  /**
   * Saves the file to whatever the user inputs as the file name.
   * @param filename the name of the file
   * @throws IOException if the file cannot be saved
   */
  void save(String filename) throws IOException;

}
