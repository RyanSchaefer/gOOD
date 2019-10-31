package edu.cs3500.spreadsheets.model;

import java.util.List;
import java.util.Set;


/**
 * A worksheet of expressions. Everything in the worksheet environment is a expression that
 * can be visited an manipulated.
 */
public interface IWorksheet {

  /**
   * Provides a copy of all of the active cells for iteration purposes.
   * @return the cells with Sexps in them.
   */
  List<Coord> allActiveCells();

  /**
   * Returns the literal representation of the cell at the given coordinates
   * or null if there is no cell there.
   * @param col the column of the cell, 1 indexed
   * @param row the row of the cell, 1 indexed
   * @return the cell or null
   */
  String getCellAt(int col, int row);

  /**
   * Returns the final representation of the cell at the given coordinates, evaluated in the
   * context of this worksheet.
   * @param col the column of the cell, 1 index
   * @param row the row of the cell, 1 index
   * @return the evaluated contents as a string or null
   * @throws IllegalArgumentException there is an error preventing the cell from being evaluated
   */
  Formula evaluateCellAt(int col, int row);

  /**
   * Changes the cell at the given coordinates to contain the given sexp.
   * @param col the column of the cell, 1 indexed
   * @param row the row of the cell, 1 indexed
   * @param sexp the String representation of the Sexp to change the cell to
   * @throws IllegalArgumentException String is null or invalid expression
   *     valid-ness of expressions is determined by if the String is a valid {@code Sexp}
   */
  void changeCellAt(int col, int row, String sexp) throws IllegalArgumentException;

  /**
   * Answers if the entire document is free of errors.
   * @return the answer to the question
   */
  boolean documentFreeOfErrors();

}
