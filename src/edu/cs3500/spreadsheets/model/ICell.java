package edu.cs3500.spreadsheets.model;

import java.util.List;

import edu.cs3500.spreadsheets.model.formula.value.Value;

public interface ICell {

  /**
   * Evaluate the formula in this cell.
   *
   * @return the evaluated formula
   */
  List<Value> evaluate();

}
