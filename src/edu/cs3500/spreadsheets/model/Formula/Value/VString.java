package edu.cs3500.spreadsheets.model.Formula.Value;


/**
 * A String value.
 */
public class VString extends AbstractValue {

  private String value;

  public VString(String s) {
    this.value = s;
  }

  @Override
  public String toVString() {
    return value;
  }

  @Override
  public String toString() {
    return "\""+ value + "\"";
  }
}
