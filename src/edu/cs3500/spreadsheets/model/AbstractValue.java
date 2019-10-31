package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class AbstractValue implements Formula, Value {

  @Override
  public List<Value> evaluate() {
    return new ArrayList<>(Arrays.asList(this));
  }

  @Override
  public String toVString() {
    return null;
  }

  @Override
  public Double toVDouble() {
    return null;
  }

  @Override
  public Boolean toVBoolean() {
    return null;
  }
}
