package edu.cs3500.spreadsheets.vistors;

import java.util.List;

import edu.cs3500.spreadsheets.sexp.SString;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class PrintVisitor implements SexpVisitor<String> {
  @Override
  public String visitBoolean(boolean b) {
    return Boolean.toString(b);
  }

  @Override
  public String visitNumber(double d) {
    return String.format("%f", d);
  }

  @Override
  public String visitSList(List<Sexp> l) {
    StringBuilder s = new StringBuilder();
    s.append("(");
    for (Sexp sexp: l) {
      s.append(sexp.accept(this)).append(" ");
    }
    String ret = s.toString();
    if (!ret.equals("(")) {
      s.replace(s.length() - 1, s.length(), "");
    }
    s.append(")");
    return s.toString();
  }

  @Override
  public String visitSymbol(String s) {
    return s;
  }

  @Override
  public String visitString(String s) {
    return new SString(s).toString();
  }
}
