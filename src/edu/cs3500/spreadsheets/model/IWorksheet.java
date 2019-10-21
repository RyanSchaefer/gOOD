package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.Sexp;

public interface IWorksheet {


  /**
   * Returns the cell at the given coordinates or null if there is no cell there
   * @param col the column of the cell
   * @param row the row of the cell
   * @return the cell or null
   */
  ICell getCellAt(int col, int row);

  /**
   * Returns the name of this worksheet
   * @return the name of the worksheet
   */
  String getSheetName();

  /**
   * Changes the cell at the given coordinates to contain the given sexp
   * @param col the column of the cell
   * @param row the row of the cell
   * @param sexp the sexp to insert
   */
  void changeCellAt(int col, int row, Sexp sexp);

  /**
   * Change the sheets name to the given name
   * @param name the new name
   */
  void changeSheetName(String name);

}
