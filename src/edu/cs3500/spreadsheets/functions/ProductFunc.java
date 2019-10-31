package edu.cs3500.spreadsheets.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.Formula;
import edu.cs3500.spreadsheets.model.VDouble;
import edu.cs3500.spreadsheets.model.Value;

public class ProductFunc implements Formula {

  List<Formula> contents;
  String original;

  public ProductFunc(List<Formula> contents, String original) {
    this.contents = contents;
    this.original = original;
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
