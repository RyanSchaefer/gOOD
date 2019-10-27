package edu.cs3500.spreadsheets.vistors;

import java.util.List;

import edu.cs3500.spreadsheets.functions.MultFunction;
import edu.cs3500.spreadsheets.functions.SumFunction;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.sexp.SBoolean;
import edu.cs3500.spreadsheets.sexp.SNumber;
import edu.cs3500.spreadsheets.sexp.SString;
import edu.cs3500.spreadsheets.sexp.SSymbol;
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
  public Sexp visitSList(List<Sexp> l) throws IllegalArgumentException {
    switch (l.get(0).toString().toLowerCase()) {
      case "product":
        return productHelp(l.subList(1, l.size()));
      case "sum":
        return sumHelp(l.subList(1, l.size()));
      case "<":
        if (l.size() == 3) {
         return lessThanHelp(l.get(1), l.get(2));
        }
        throw new IllegalArgumentException();
      case "lowercase":
        if (l.size() == 2) {
          return lowercaseHelp(l.get(1));
        }
        throw new IllegalArgumentException();
      default:
        throw new IllegalArgumentException();
    }
  }

  @Override
  public Sexp visitSymbol(String s) {
    return new SSymbol(s);
  }

  @Override
  public Sexp visitString(String s) {
    return new SString(s);
  }

  private Sexp productHelp(List<Sexp> s) {
    double product = 1;
    for (Sexp x: s) {
      product *= x.accept(new NumberVisitor(1, this.model, new MultFunction()));
    }
    return new SNumber(product);
  }

  private Sexp sumHelp(List<Sexp> s) {
    double sum = 0;
    for (Sexp x: s) {
      sum += x.accept(new NumberVisitor(0, this.model, new SumFunction()));
    }
    return new SNumber(sum);
  }

  private Sexp lessThanHelp(Sexp s1, Sexp s2) {
    return new SBoolean(
            s1.accept(new StrictNumberVisitor(this.model)) <
            s2.accept(new StrictNumberVisitor(this.model)));
  }

  private Sexp lowercaseHelp(Sexp s) {
    return s.accept(new StringVisitor(this.model));
  }
}
