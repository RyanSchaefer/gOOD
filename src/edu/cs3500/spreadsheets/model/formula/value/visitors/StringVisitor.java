package edu.cs3500.spreadsheets.model.formula.value.visitors;

/**
 * Allows for a value to be turned into a string.
 */
public class StringVisitor implements ValueVisitor<String> {
  @Override
  public String visitBoolean(Boolean value) {
    return null;
  }

  @Override
  public String visitDouble(Double value) {
    return null;
  }

  @Override
  public String visitString(String value) {
    return value;
  }
}
