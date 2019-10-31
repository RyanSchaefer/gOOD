package edu.cs3500.spreadsheets.vistors;

import java.util.List;

import edu.cs3500.spreadsheets.model.Formula.Formula;
import edu.cs3500.spreadsheets.model.Formula.Value.VBoolean;
import edu.cs3500.spreadsheets.model.Formula.Value.VDouble;
import edu.cs3500.spreadsheets.model.Formula.Value.VString;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

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
