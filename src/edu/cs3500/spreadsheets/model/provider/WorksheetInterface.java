package edu.cs3500.spreadsheets.model.provider;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Interface for a worksheet of any type.
 */
public interface WorksheetInterface {

  /**
   * Set a cell with the contents at the given position and evaluate it.
   *
   * @param position The position to set.
   * @param contents The contents of the cell.
   * @throws IllegalArgumentException If unable to set.
   */
  void setCellUpdate(Coord position, String contents) throws IllegalArgumentException;

  /**
   * Evaluates all cells in the worksheet.
   *
   * @throws IllegalArgumentException If unable to evaluate.
   */
  void evaluateAll() throws IllegalArgumentException;

  /**
   * Evaluates the final value of the cell as a String.
   *
   * @param position The location of the cell.
   * @return The final value of the cell as a String.
   * @throws IllegalArgumentException If cell value is invalid.
   */
  String evaluateString(Coord position) throws IllegalArgumentException;

  /**
   * Evaluates the final value of the cell as a Double.
   *
   * @param position The location of the cell.
   * @return The final value of the cell as a String.
   * @throws IllegalArgumentException If cell value is invalid.
   */
  double evaluateDouble(Coord position) throws IllegalArgumentException;

  /**
   * Evaluates the final value of the cell as a Boolean.
   *
   * @param position The location of the cell.
   * @return The final value of the cell as a String.
   * @throws IllegalArgumentException If cell value is invalid.
   */
  boolean evaluateBoolean(Coord position) throws IllegalArgumentException;

  /**
   * Get the raw unevaluated contents of a cell.
   *
   * @param position The location of the cell.
   * @return The name of the cell as a String.
   */
  String getRawContents(Coord position);

  /**
   * Get the name of the cell as a String such as "A1".
   *
   * @param position The location of the cell.
   * @return The name of the cell as a String.
   */
  String getName(Coord position);

  /**
   * Set the given cell to empty.
   *
   * @param position The location of the cell.
   */
  void removeCell(Coord position);

  /**
   * Get the number of columns in the worksheet.
   *
   * @return the number of columns in the worksheet.
   */
  int getColSize();

  /**
   * Get the number of rows in the worksheet.
   *
   * @return the number of rows in the worksheet.
   */
  int getRowSize();
}
