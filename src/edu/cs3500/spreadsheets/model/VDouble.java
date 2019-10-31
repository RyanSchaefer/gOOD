package edu.cs3500.spreadsheets.model;


public class VDouble extends AbstractValue {

  double value;

  public VDouble(double d) {
    this.value = d;
  }


  @Override
  public Double toVDouble() {
    return value;
  }

  @Override
  public String toString() {
    return String.format("%f", value);
  }
}