package edu.cs3500.spreadsheets.model.formula.value.visitors;

/**
 * Formats values properly for the {@link edu.cs3500.spreadsheets.commandLine.EvalCommand}.
 */
public class EvalPrintVisitor implements ValueVisitor<String> {
  @Override
  public String visitBoolean(Boolean value) {
    return value.toString();
  }

  @Override
  public String visitDouble(Double value) {
    return String.format("%f", value);
  }

  @Override
  public String visitString(String value) {
    return String.format("\"%s\"", value);
  }
}
