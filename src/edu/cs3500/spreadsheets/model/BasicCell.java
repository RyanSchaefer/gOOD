package edu.cs3500.spreadsheets.model;

import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.value.Value;

public class BasicCell implements ICell {

  private Formula formula;
  private String original;

  public BasicCell(Formula f, String original) {
    this.formula = f;
    this.original = original;
  }

  @Override
  public List<Value> evaluate() {
    return formula.evaluate();
  }


  @Override
  public String toString() {
    return this.original;
  }

}
