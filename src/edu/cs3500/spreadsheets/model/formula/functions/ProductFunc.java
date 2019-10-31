package edu.cs3500.spreadsheets.model.formula.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.value.VDouble;
import edu.cs3500.spreadsheets.model.formula.value.Value;

/**
 * The class representing the product function. Takes in any number of formula which evaluate to
 * anything.
 */
public class ProductFunc implements IFunction {

  private List<Formula> contents = new ArrayList<>();
  private String original = "";

  /**
   * Allows a default way of constructing this method.
   */
  public ProductFunc() {
    /*
    Return the default product func.
     */
  }

  private ProductFunc(List<Formula> contents, String original) {
    this.contents = contents;
    this.original = original;
  }

  @Override
  public Formula build(List<Formula> contents, String original) {
    return new ProductFunc(contents, original);
  }

  @Override
  public List<Value> evaluate() {
    double base = 0;
    boolean allNonNumeric = true;
    for (Formula f : contents) {
      for (Value v : f.evaluate()) {
        Double d = v.toVDouble();
        if (d != null) {
          if (allNonNumeric) {
            base = 1;
            allNonNumeric = false;
          }
          base *= d;
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
