package edu.cs3500.spreadsheets.model;

import java.util.List;

import edu.cs3500.spreadsheets.sexp.Sexp;

public interface ICell {

  /**
   * Return the calculated value of this cell
   * @return the evaluated Sexp of this cell
   * @throws IllegalArgumentException if this cell doesn't contain a double
   */
  Sexp getValue() throws IllegalArgumentException;

  /**
   * Return the text of the expression in this cell
   * @return the string representation of this cell
   */
  String getExpressionText();

  /**
   * Reevaluates this cell if any of the cells it depends on have changed.
   * @param changed what coordinates have changed
   */
  void reevaluate(List<Coord> changed);

  /**
   * Forces the evaluation of this cell.
   */
  void evaluate();
}
