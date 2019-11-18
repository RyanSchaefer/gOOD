package edu.cs3500.spreadsheets.model.formula;

import java.util.ArrayList;
import java.util.List;

import edu.cs3500.spreadsheets.model.formula.value.Value;

/**
 * A placeholder value used in the eval map. Evaluates to itself but to String is first in the array
 * list which should already be evaluated.
 */
public class ValueHolder implements Formula {

  private List<Value> placeholder;

  public ValueHolder(List<Value> placeholder) {
    this.placeholder = placeholder;
  }

  @Override
  public List<Value> evaluateToList() {
    return new ArrayList<>(placeholder);
  }

  @Override
  public Value evaluate() {
    return placeholder.get(0);
  }
}
