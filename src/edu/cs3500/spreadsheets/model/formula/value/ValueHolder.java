package edu.cs3500.spreadsheets.model.formula.value;

import java.util.ArrayList;
import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;

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
  public List<Value> evaluate() {
    return new ArrayList<>(placeholder);
  }

  @Override
  public String toString() {
    if (placeholder.size() == 0) {
      return null;
    }
    return this.placeholder.get(0).toString();
  }
}
