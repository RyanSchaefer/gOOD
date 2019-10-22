package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.Sexp;

public interface IWorksheet {


  /**
   * Returns the cell at the given coordinates or null if there is no cell there
   * @param col the column of the cell, 0 indexed
   * @param row the row of the cell, 0 indexed
   * @return the cell or null
   */
  Sexp getCellAt(int col, int row);

  /**
   * Changes the cell at the given coordinates to contain the given sexp
   * @param col the column of the cell, 0 indexed
   * @param row the row of the cell, 0 indexed
   * @param sexp the sexp to insert
   * @throws IllegalArgumentException Sexp is null
   */
  void changeCellAt(int col, int row, Sexp sexp) throws IllegalArgumentException;

}
