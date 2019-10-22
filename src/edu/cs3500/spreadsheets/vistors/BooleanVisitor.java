package edu.cs3500.spreadsheets.vistors;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class BooleanVisitor implements SexpVisitor<Double> {

  private IWorksheet model;

  public BooleanVisitor(IWorksheet model) {
    this.model = model;
  }
  @Override
  public Double visitBoolean(boolean b) {
    throw new IllegalArgumentException();
  }

  @Override
  public Double visitNumber(double d) {
    throw new IllegalArgumentException();
  }

  @Override
  public Double visitSList(List<Sexp> l) {
    return new SList(l).accept(
            new EvalVisitor(this.model)).accept(
                    new NumberVisitor(0, this.model));
  }

  @Override
  public Double visitSymbol(String s) {
    if (s.matches("^[A-Z]*[0-9]*$")) {

      Pattern r = Pattern.compile("^(A-Z]*)([0-9]*)$");
      Matcher m = r.matcher(s);
      Sexp ex = model.getCellAt(Coord.colNameToIndex(m.group(0)), Integer.parseInt(m.group(1)));

      if (ex == null) {
        throw new IllegalArgumentException();
      }

      return ex.accept(this);
    }
    throw new IllegalArgumentException();
  }

  @Override
  public Double visitString(String s) {
    throw new IllegalArgumentException();
  }
}
