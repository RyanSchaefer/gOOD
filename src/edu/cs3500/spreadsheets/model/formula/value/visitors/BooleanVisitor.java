package edu.cs3500.spreadsheets.model.formula.value.visitors;

/**
 * Allows for each value to be turned into a boolean.
 */
public class BooleanVisitor implements ValueVisitor<Boolean> {
  @Override
  public Boolean visitBoolean(Boolean value) {
    return value;
  }

  @Override
  public Boolean visitDouble(Double value) {
    return null;
  }

  @Override
  public Boolean visitString(String value) {
    return null;
  }
}
