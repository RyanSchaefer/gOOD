package edu.cs3500.spreadsheets.model;


public class VBoolean extends AbstractValue {

  Boolean value;

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
