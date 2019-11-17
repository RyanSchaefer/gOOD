package edu.cs3500.spreadsheets.model.formula.value;

/**
 * A value is one of a String, Double, or Boolean.
 */
public interface Value {

  /**
   * A way for a Value to accept a visitor. By convention, if we can't convert a value to the type
   * requested then we should return null.
   * @param visitor what visitor we want to use
   * @param <T> the return type
   * @return the converted value
   */
  <T> T accept(ValueVisitor<T> visitor);
}