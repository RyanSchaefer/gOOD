package edu.cs3500.spreadsheets.model.formula.value.visitors;

/**
 * Allows for each value to be turned into a double.
 */
public class DoubleVisitor implements ValueVisitor<Double> {
  @Override
  public Double visitBoolean(Boolean value) {
    return null;
  }

  @Override
  public Double visitDouble(Double value) {
    return value;
  }

  @Override
  public Double visitString(String value) {
    return null;
  }
}
