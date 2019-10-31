package edu.cs3500.spreadsheets.functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.Formula;
import edu.cs3500.spreadsheets.model.VString;
import edu.cs3500.spreadsheets.model.Value;

public class ErrorFunction implements Formula {

  List<Formula> contents;
  String original;

  public ErrorFunction(List<Formula> contents, String original) {
    this.contents = contents;
    this.original = original;
  }


  @Override
  public List<Value> evaluate() {
    return new ArrayList<>(Arrays.asList(
            new VString("ERROR")));
  }

  @Override
  public String toString() {
    return this.original;
  }
}
