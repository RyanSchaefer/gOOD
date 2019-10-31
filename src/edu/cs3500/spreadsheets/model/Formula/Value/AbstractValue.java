package edu.cs3500.spreadsheets.model.Formula.Value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.cs3500.spreadsheets.model.Formula.Formula;

/**
 * All values share this class, and since a value is a formula it should implement formula.
 */
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
