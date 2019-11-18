package edu.cs3500.spreadsheets.model.formula.value;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.value.visitors.ValueVisitor;

/**
 * A String value.
 */
public class VString implements Formula, Value {

  private String value;

  public VString(String s) {
    this.value = s;
  }

  @Override
  public List<Value> evaluateToList() {
    return new ArrayList<>(Arrays.asList(this));
  }

  @Override
  public Value evaluate() {
    return this;
  }

  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitString(this.value);
  }
}
