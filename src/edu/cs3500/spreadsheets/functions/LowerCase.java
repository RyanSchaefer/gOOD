package edu.cs3500.spreadsheets.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.Formula;
import edu.cs3500.spreadsheets.model.VString;
import edu.cs3500.spreadsheets.model.Value;

public class LowerCase implements Formula {

  List<Formula> contents;
  String original;

  public LowerCase(List<Formula> contents, String original) {
    this.contents = contents;
    this.original = original;
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
