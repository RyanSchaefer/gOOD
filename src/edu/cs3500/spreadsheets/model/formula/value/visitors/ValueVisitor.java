package edu.cs3500.spreadsheets.model.formula.value.visitors;

/**
 * The visitor for values.
 *
 * @param <R> the return type of the visitor.
 */
public interface ValueVisitor<R> {

  /**
   * Visit a {@link edu.cs3500.spreadsheets.model.formula.value.VBoolean}.
   * @param value the boolean from the {@link edu.cs3500.spreadsheets.model.formula.value.VBoolean}
   * @return a value consistent with the return type or null if it cannot be converted.
   */
  R visitBoolean(Boolean value);

  /**
   * Visit a {@link edu.cs3500.spreadsheets.model.formula.value.VDouble}.
   * @param value the double from the {@link edu.cs3500.spreadsheets.model.formula.value.VDouble}
   * @return a value consistent with the return type or null if it cannot be converted.
   */
  R visitDouble(Double value);

  /**
   * Visit a {@link edu.cs3500.spreadsheets.model.formula.value.VString}.
   * @param value the String from the {@link edu.cs3500.spreadsheets.model.formula.value.VString}
   * @return a value consistent with the return type or null if it cannot be converted.
   */
  R visitString(String value);
}
