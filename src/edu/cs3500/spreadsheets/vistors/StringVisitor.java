package edu.cs3500.spreadsheets.vistors;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.sexp.SString;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class StringVisitor implements SexpVisitor<Sexp> {

  IWorksheet model;

  public StringVisitor(IWorksheet model) {
    this.model = model;
  }

  @Override
  public Sexp visitBoolean(boolean b) {
    throw new IllegalArgumentException();
  }

  @Override
  public Sexp visitNumber(double d) {
    throw new IllegalArgumentException();
  }

  @Override
  public Sexp visitSList(List<Sexp> l) {
    throw new IllegalArgumentException();
  }

  @Override
  public Sexp visitSymbol(String s) {
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
  public Sexp visitString(String s) {
    return new SString(s.toLowerCase());
  }
}
