package edu.cs3500.spreadsheets.model.Formula;

import java.util.List;

import edu.cs3500.spreadsheets.model.Formula.Value.Value;

/**
 * A Function, Reference, or Value.
 */
public interface Formula {

  /**
   * Evaluate this formula into its simplest form. Returns a list because references are to lists of
   * Values. All other values return a list with just the value in it.
   *
   * @return a list of evaluated values.
   */
  List<Value> evaluate();

}
