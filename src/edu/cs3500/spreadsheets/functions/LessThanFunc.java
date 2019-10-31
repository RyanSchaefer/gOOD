package edu.cs3500.spreadsheets.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.Formula;
import edu.cs3500.spreadsheets.model.VBoolean;
import edu.cs3500.spreadsheets.model.Value;

public class LessThanFunc implements Formula {

  private List<Formula> contents;
  String original;

  public LessThanFunc(List<Formula> contents, String original) {
    this.contents = contents;
    this.original = original;
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
