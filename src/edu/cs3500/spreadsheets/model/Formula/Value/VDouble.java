package edu.cs3500.spreadsheets.model.Formula.Value;


/**
 * A Double value.
 */
public class VDouble extends AbstractValue {

  private double value;

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