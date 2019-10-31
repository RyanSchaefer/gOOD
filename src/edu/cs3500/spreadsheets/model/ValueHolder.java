package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.List;

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
