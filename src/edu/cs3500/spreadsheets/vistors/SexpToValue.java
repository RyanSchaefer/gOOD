package edu.cs3500.spreadsheets.vistors;

import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.value.VBoolean;
import edu.cs3500.spreadsheets.model.formula.value.VDouble;
import edu.cs3500.spreadsheets.model.formula.value.VString;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

/**
 * Convert this sexp into a Value, if we encounter a formula, we don't want to change how the
 * formula was typed in so we should throw an error and directly convert the String into a SValue.
 */
public class SexpToValue implements SexpVisitor<Formula> {
  @Override
  public Formula visitBoolean(boolean b) {
    return new VBoolean(b);
  }

  @Override
  public Formula visitNumber(double d) {
    return new VDouble(d);
  }

  @Override
  public Formula visitSList(List<Sexp> l) {
    throw new IllegalArgumentException();
  }

  @Override
  public Formula visitSymbol(String s) {
    return new VString(s);
  }

  @Override
  public Formula visitString(String s) {
    return new VString(s);
  }
}
