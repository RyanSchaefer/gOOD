package edu.cs3500.spreadsheets.model.formula.value;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.value.visitors.ValueVisitor;

/**
 * A boolean value.
 */
public class VBoolean implements Value, Formula {

  private Boolean value;

  public VBoolean(boolean b) {
    this.value = b;
  }

  @Override
  public <R> R accept(ValueVisitor<R> visitor) {
    return visitor.visitBoolean(this.value);
  }

  @Override
  public List<Value> evaluateToList() {
    return new ArrayList<>(Arrays.asList(this));
  }

  @Override
  public Value evaluate() {
    return this;
  }
}
