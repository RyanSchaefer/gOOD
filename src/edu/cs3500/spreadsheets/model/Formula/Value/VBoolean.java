package edu.cs3500.spreadsheets.model.Formula.Value;


/**
 * A boolean value.
 */
public class VBoolean extends AbstractValue {

  private Boolean value;

  public VBoolean(boolean b) {
    this.value = b;
  }

  @Override
  public Boolean toVBoolean() {
    return this.value;
  }


  @Override
  public String toString() {
    return value.toString();
  }
}
