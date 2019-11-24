package edu.cs3500.spreadsheets.model;

import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.value.Value;

/**
 * Represents a basic cell implementing the ICell interface with a Formula within it to
 * allow it to be evaluated, and a String to hold its original value.
 */
public class BasicCell implements ICell {

  private Formula formula;
  private String original;

  BasicCell(Formula f, String original) {
    this.formula = f;
    this.original = original;
  }

  @Override
  public List<Value> evaluateToList() {
    return formula.evaluateToList();
  }

  @Override
  public Value evaluate() {
    return formula.evaluate();
  }

  @Override
  public String toString() {
    return this.original;
  }

}
