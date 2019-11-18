package edu.cs3500.spreadsheets.model;

import java.util.List;

import edu.cs3500.spreadsheets.model.formula.value.Value;

/**
 * Represents a cell within the spreadsheet that can be evaluated.
 */
public interface ICell {

  /**
   * Evaluate the formula in this cell.
   *
   * @return the evaluated formula
   */
  List<Value> evaluateToList();

  /**
   * Evaluate this cell.
   *
   * @return a single value/
   */
  Value evaluate();

}
