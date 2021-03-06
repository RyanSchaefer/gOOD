package edu.cs3500.spreadsheets.model;

import java.util.List;

import edu.cs3500.spreadsheets.model.formula.value.Value;

/**
 * A worksheet that acts as a read only adapter to ensure the view does not mutate the model.
 */
public interface SlimWorksheet {

  /**
   * Provides a copy of all of the active cells for iteration purposes.
   *
   * @return the cells with Sexps in them.
   */
  List<Coord> allActiveCells();

  /**
   * Returns the literal representation of the cell at the given coordinates or null if there is no
   * cell there.
   *
   * @param col the column of the cell, 1 indexed
   * @param row the row of the cell, 1 indexed
   * @return the cell or null
   * @throws IllegalArgumentException col or row are less than 0
   */
  ICell getCellAt(int col, int row) throws IllegalArgumentException;

  /**
   * Returns the final representation of the cell at the given coordinates, evaluated in the context
   * of this worksheet.
   *
   * @param col the column of the cell, 1 index
   * @param row the row of the cell, 1 index
   * @return the evaluated contents as a string or null
   * @throws IllegalArgumentException there is an error preventing the cell from being evaluated
   */
  Value evaluateCellAt(int col, int row);

  /**
   * Answers if the entire document is free of errors.
   *
   * @return the answer to the question
   */
  boolean documentFreeOfErrors();

  /**
   * Gets all of the cells that depend on the given cell.
   *
   * @param col the column of the cell
   * @param row the row of the cell
   * @return a list of all of the cells that depend on that cell
   */
  List<Coord> getDependents(int col, int row);
}
