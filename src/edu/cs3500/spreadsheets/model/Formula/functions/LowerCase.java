package edu.cs3500.spreadsheets.model.Formula.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.Formula.Formula;
import edu.cs3500.spreadsheets.model.Formula.Value.VString;
import edu.cs3500.spreadsheets.model.Formula.Value.Value;

/**
 * The class representing the lowercase function. Takes in one formula which evaluates to a string.
 */
public class LowerCase extends AbstractFunction {

  private List<Formula> contents = new ArrayList<>();
  private String original = "";

  public LowerCase() {
  }

  private LowerCase(List<Formula> contents, String original) {
    this.contents = contents;
    this.original = original;
  }

  @Override
  public Formula build(List<Formula> contents, String original) {
    return new LowerCase(contents, original);
  }

  @Override
  public List<Value> evaluate() {
    if (this.contents.size() == 1) {
      List<Value> v = this.contents.get(0).evaluate();
      if (v.size() != 1) {
        throw new IllegalArgumentException("Malformed lowercase formula: too many args.");
      }
      String s = v.get(0).toVString();
      if (s == null) {
        throw new IllegalArgumentException("Malformed lowercase formula: not a string.");
      }
      return new ArrayList<>(Arrays.asList(new VString(s.toLowerCase())));
    }
    throw new IllegalArgumentException("Malformed lowercase formula: to0 many args.");
  }

  @Override
  public String toString() {
    return this.original;
  }
}
