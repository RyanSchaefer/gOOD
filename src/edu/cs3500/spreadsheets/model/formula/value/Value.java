package edu.cs3500.spreadsheets.model.formula.value;

import edu.cs3500.spreadsheets.model.formula.value.visitors.ValueVisitor;

/**
 * A value is one of a String, Double, or Boolean.
 */
public interface Value {

  /**
   * A way for a Value to accept a visitor. By convention, if we can't convert a value to the type
   * requested then we should return null.
   * @param visitor what visitor we want to use
   * @return the converted value
   */
  <R> R accept(ValueVisitor<R> visitor);
}