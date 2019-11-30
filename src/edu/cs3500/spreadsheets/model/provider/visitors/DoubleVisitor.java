package edu.cs3500.spreadsheets.model.provider.visitors;

public class DoubleVisitor extends AbstractExceptionVisitor<Double> {
  @Override
  public Double visitDouble(Double value) {
    return value;
  }
}
