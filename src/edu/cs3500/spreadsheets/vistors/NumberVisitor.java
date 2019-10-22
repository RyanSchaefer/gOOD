package edu.cs3500.spreadsheets.vistors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class NumberVisitor implements SexpVisitor<Double> {
  private double base;
  private IWorksheet model;

  public NumberVisitor(double base, IWorksheet model) {
    this.base = base;
    this.model = model;
  }

  @Override
  public Double visitBoolean(boolean b) {
    return base;
  }

  @Override
  public Double visitNumber(double d) {
    return d;
  }

  @Override
  public Double visitSList(List<Sexp> l) {
    try {
       return new SList(l).accept(
               new EvalVisitor(this.model)).accept(
                       new NumberVisitor(base, this.model));
    } catch (IllegalArgumentException e) {
      return base;
    }
  }

  @Override
  public Double visitSymbol(String s) {
    if (s.matches("^[A-Z]*[0-9]*$")) {

      Pattern r = Pattern.compile("^(A-Z]*)([0-9]*)$");
      Matcher m = r.matcher(s);
      Sexp ex = model.getCellAt(Coord.colNameToIndex(m.group(0)), Integer.parseInt(m.group(1)));

      if (ex == null) {
        return base;
      }

      return ex.accept(this);
    }
    return base;
  }

  @Override
  public Double visitString(String s) {
    return base;
  }
}
