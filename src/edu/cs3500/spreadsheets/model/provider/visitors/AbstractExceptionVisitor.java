package edu.cs3500.spreadsheets.model.provider.visitors;

import edu.cs3500.spreadsheets.model.formula.value.visitors.ValueVisitor;

public class AbstractExceptionVisitor<R> implements ValueVisitor<R> {
  @Override
  public R visitBoolean(Boolean value) {
    throw new IllegalArgumentException();
  }

  @Override
  public R visitDouble(Double value) {
    throw new IllegalArgumentException();
  }

  @Override
  public R visitString(String value) {
    throw new IllegalArgumentException();
  }
}