package edu.cs3500.spreadsheets.model.formula.value;

/**
 * A value is one of a String, Double, or Boolean.
 */
public interface Value {

  /**
   * Turn this Value into a String. If it cannot be turned into a String, return null.
   *
   * @return the Value as a String.
   */
  String toVString();

  /**
   * Turn this Value into a Double. If it cannot be turned into a Double, return null.
   * @return the Value as a Double.
   */
  Double toVDouble();

  /**
   * Turn this Value into a Boolean. If it cannot be turned into a Boolean, return null.
   * @return the Value as a Boolean.
   */
  Boolean toVBoolean();
}