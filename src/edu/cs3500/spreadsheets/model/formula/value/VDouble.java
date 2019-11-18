package edu.cs3500.spreadsheets.model.formula.value;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.value.visitors.ValueVisitor;

/**
 * A Double value.
 */
public class VDouble implements Value, Formula {

  private double value;

  public VDouble(double d) {
    this.value = d;
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
    return visitor.visitDouble(this.value);
  }
}