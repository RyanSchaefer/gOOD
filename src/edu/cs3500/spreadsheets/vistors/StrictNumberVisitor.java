package edu.cs3500.spreadsheets.vistors;

import java.util.List;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;


/**
 * Strictly visits only numbers otherwise throws an error.
 */
public class StrictNumberVisitor implements SexpVisitor<Double> {

  private IWorksheet model;

  public StrictNumberVisitor(IWorksheet model) {
    this.model = model;
  }

  @Override
  public Double visitBoolean(boolean b) {
    throw new IllegalArgumentException();
  }

  @Override
  public Double visitNumber(double d) {
    return d;
  }

  @Override
  public Double visitSList(List<Sexp> l) {
    return new SList(l).accept(
            new EvalVisitor(this.model)).accept(this);
  }

  // trying to visit a range of cells in the context of something that takes in a number
  // does not work
  @Override
  public Double visitSymbol(String s) {
    if (s.matches("^[A-Z]+[0-9]+$")) {

      Pattern r = Pattern.compile("^([A-Z]+)([0-9]+)$");
      Matcher m = r.matcher(s);
      m.find();
      String cell =
              model.evaluateCellAt(Coord.colNameToIndex(m.group(1)), Integer.parseInt(m.group(2)));

      if (cell == null) {
        throw new IllegalArgumentException();
      }

      Sexp ex = Parser.parse(cell);

      return ex.accept(this);
    }
    throw new IllegalArgumentException();
  }


  @Override
  public Double visitString(String s) {
    throw new IllegalArgumentException();
  }
}
