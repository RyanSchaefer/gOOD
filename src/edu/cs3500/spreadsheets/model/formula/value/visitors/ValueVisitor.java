package edu.cs3500.spreadsheets.model.formula.value.visitors;

public interface ValueVisitor<R> {

  R visitBoolean(Boolean value);

  R visitDouble(Double value);

  R visitString(String value);
}
