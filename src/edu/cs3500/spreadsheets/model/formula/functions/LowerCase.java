package edu.cs3500.spreadsheets.model.formula.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.value.VString;
import edu.cs3500.spreadsheets.model.formula.value.Value;
import edu.cs3500.spreadsheets.model.formula.value.visitors.StringVisitor;

/**
 * The class representing the lowercase function. Takes in one formula which evaluates to a string.
 */
public class LowerCase implements IFunction {

  private List<Formula> contents = new ArrayList<>();
  private String original = "";

  /**
   * Allows a default way of constructing this function.
   */
  public LowerCase() {
    /*
    Return the default LowerCaseFunction.
     */
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
  public List<Value> evaluateToList() {
    if (this.contents.size() == 1) {
      List<Value> v = this.contents.get(0).evaluateToList();
      if (v.size() != 1) {
        throw new IllegalArgumentException("Malformed lowercase formula: too many args.");
      }
      String s = v.get(0).accept(new StringVisitor());
      if (s == null) {
        throw new IllegalArgumentException("Malformed lowercase formula: not a string.");
      }
      return new ArrayList<>(Arrays.asList(new VString(s.toLowerCase())));
    }
    throw new IllegalArgumentException("Malformed lowercase formula: too many args.");
  }

  @Override
  public Value evaluate() {
    if (this.contents.size() == 1) {
      List<Value> v = this.contents.get(0).evaluateToList();
      if (v.size() != 1) {
        throw new IllegalArgumentException("Malformed lowercase formula: too many args.");
      }
      String s = v.get(0).accept(new StringVisitor());
      if (s == null) {
        throw new IllegalArgumentException("Malformed lowercase formula: not a string.");
      }
      return new VString(s.toLowerCase());
    }
    throw new IllegalArgumentException("Malformed lowercase formula: too many args.");
  }

  @Override
  public String toString() {
    return this.original;
  }
}
