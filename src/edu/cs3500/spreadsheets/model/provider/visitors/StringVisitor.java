package edu.cs3500.spreadsheets.model.provider.visitors;

public class StringVisitor extends AbstractExceptionVisitor<String> {
  @Override
  public String visitString(String value) {
    return value;
  }
}
