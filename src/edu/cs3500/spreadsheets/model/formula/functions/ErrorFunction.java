package edu.cs3500.spreadsheets.model.formula.functions;

import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.value.Value;

/**
 * A function which holds the formula that created this error.
 */
public class ErrorFunction implements IFunction {

  private String original;

  public ErrorFunction(String original) {
    this.original = original;
  }

  @Override
  public Formula build(List<Formula> contents, String original) {
    return new ErrorFunction(original);
  }

  @Override
  public List<Value> evaluate() {
    throw new IllegalArgumentException("Error in cell");
  }

  @Override
  public String toString() {
    return this.original;
  }
}
