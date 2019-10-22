package edu.cs3500.spreadsheets.vistors;

import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.sexp.SBoolean;
import edu.cs3500.spreadsheets.sexp.SNumber;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class EvalVisitor implements SexpVisitor<Sexp> {

  private IWorksheet model;

  public EvalVisitor(IWorksheet model) {
    this.model = model;
  }

  @Override
  public Sexp visitBoolean(boolean b) {
    return new SBoolean(b);
  }

  @Override
  public Sexp visitNumber(double d) {
    return new SNumber(d);
  }

  @Override
  public Sexp visitSList(List<Sexp> l) {
    switch (l.get(0).toString().toLowerCase()) {
      case "product":
        return productHelp(l.remove(0));
      case "sum":
        return sumHelp(l.remove(0));
      case "<":
        l.remove(0);
        if (l.size() == 2) {
         return lessThanHelp(l.get(0), l.get(1));
        }
        throw new IllegalArgumentException();
      case "lowercase":
        l.remove(0);
        if (l.size() == 1) {
          return lowercaseHelp(l.get(0));
        }
        throw new IllegalArgumentException();
      default:
        throw new IllegalArgumentException();
    }
  }

  @Override
  public Sexp visitSymbol(String s) {
    return null;
  }

  @Override
  public Sexp visitString(String s) {
    return null;
  }

  private Sexp productHelp(Sexp... s) {
    double product = 1;
    for (Sexp x: s) {
      product *= x.accept(new NumberVisitor(1, this.model));
    }
    return new SNumber(product);
  }

  private Sexp sumHelp(Sexp... s) {
    double sum = 0;
    for (Sexp x: s) {
      sum += x.accept(new NumberVisitor(0, this.model));
    }
    return new SNumber(sum);
  }

  private Sexp lessThanHelp(Sexp s1, Sexp s2) {
    return new SBoolean(
            s1.accept(new BooleanVisitor(this.model)) <
            s2.accept(new BooleanVisitor(this.model)));
  }

  private Sexp lowercaseHelp(Sexp s) {
    return s.accept(new StringVisitor(this.model));
  }
}
