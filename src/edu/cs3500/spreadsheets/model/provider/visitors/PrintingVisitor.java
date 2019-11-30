package edu.cs3500.spreadsheets.model.provider.visitors;

import edu.cs3500.spreadsheets.model.formula.value.visitors.ValueVisitor;

public class PrintingVisitor implements ValueVisitor<String> {
  @Override
  public String visitBoolean(Boolean value) {
    return value.toString();
  }

  @Override
  public String visitDouble(Double value) {
    return String.format("%.2f", value);
  }

  @Override
  public String visitString(String value) {
    return value;
  }
}
