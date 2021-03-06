package edu.cs3500.spreadsheets.model.formula.value.visitors;

/**
 * Formats Values properly for a {@link edu.cs3500.spreadsheets.view.IView}.
 */
public class GUIDisplayVisitor implements ValueVisitor<String> {
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
    return String.format("\"%s\"", value);
  }
}
