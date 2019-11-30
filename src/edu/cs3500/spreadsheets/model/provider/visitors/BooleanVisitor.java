package edu.cs3500.spreadsheets.model.provider.visitors;

public class BooleanVisitor extends AbstractExceptionVisitor<Boolean> {
  @Override
  public Boolean visitBoolean(Boolean value) {
    return value;
  }
}
