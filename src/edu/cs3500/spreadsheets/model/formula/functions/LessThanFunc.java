package edu.cs3500.spreadsheets.model.formula.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.value.VBoolean;
import edu.cs3500.spreadsheets.model.formula.value.Value;

/**
 * The class which represents the less than formula. Takes in two formulas which strictly evaluate
 * down to numbers.
 */
public class LessThanFunc implements IFunction {

  private List<Formula> contents = new ArrayList<>();
  private String original = "";

  /**
   * Allows a default way of constructing this function.
   */
  public LessThanFunc() {
    /*
    Return the default LessThanFunc.
     */
  }

  private LessThanFunc(List<Formula> contents, String original) {
    this.contents = contents;
    this.original = original;
  }

  @Override
  public Formula build(List<Formula> contents, String original) {
    return new LessThanFunc(contents, original);
  }

  @Override
  public List<Value> evaluate() {
    if (contents.size() == 2) {
      List<Value> lv1 = contents.get(0).evaluate();
      List<Value> lv2 = contents.get(1).evaluate();
      if (lv1.size() != 1 || lv2.size() != 1) {
        throw new IllegalArgumentException("Malformed < function.");
      }
      Double d1 = lv1.get(0).toVDouble();
      Double d2 = lv2.get(0).toVDouble();
      if (d1 == null || d2 == null) {
        throw new IllegalArgumentException("Malformed < function.");
      }
      return new ArrayList<>(Arrays.asList(new VBoolean(d1 < d2)));
    }
    throw new IllegalArgumentException("Malformed < function.");
  }

  @Override
  public String toString() {
    return this.original;
  }
}
