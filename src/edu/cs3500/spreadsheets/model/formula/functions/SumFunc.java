package edu.cs3500.spreadsheets.model.formula.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.value.VDouble;
import edu.cs3500.spreadsheets.model.formula.value.Value;

/**
 * The class representing the summation function. Takes in any number of formula which evaluate to
 * anything.
 */
public class SumFunc implements IFunction {

  private List<Formula> contents = new ArrayList<>();
  private String original = "";

  /**
   * Allows a default way of constructing this method.
   */
  public SumFunc() {
    /*
    Return the default sum function
     */
  }

  private SumFunc(List<Formula> contents, String original) {
    this.contents = contents;
    this.original = original;
  }

  @Override
  public Formula build(List<Formula> contents, String original) {
    return new SumFunc(contents, original);
  }

  @Override
  public List<Value> evaluate() {
    double base = 0;
    for (Formula f: contents) {
      for (Value v: f.evaluate()) {
        Double d = v.toVDouble();
        if (d != null) {
          base += d;
        }
      }
    }
    return new ArrayList<>(Arrays.asList(new VDouble(base)));
  }

  @Override
  public String toString() {
    return this.original;
  }
}
