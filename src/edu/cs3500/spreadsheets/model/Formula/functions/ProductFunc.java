package edu.cs3500.spreadsheets.model.Formula.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.Formula.Formula;
import edu.cs3500.spreadsheets.model.Formula.Value.VDouble;
import edu.cs3500.spreadsheets.model.Formula.Value.Value;

/**
 * The class representing the product function. Takes in any number of formula which evaluate to
 * anything.
 */
public class ProductFunc extends AbstractFunction {

  private List<Formula> contents = new ArrayList<>();
  private String original = "";

  public ProductFunc() {
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
