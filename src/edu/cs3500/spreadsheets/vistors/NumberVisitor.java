package edu.cs3500.spreadsheets.vistors;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;


/**
 * Converts any Sexp into a number given a function to combine the numbers and a base
 * to return if the expression should be ignored.
 */
public class NumberVisitor implements SexpVisitor<Double> {
  private double base;
  private IWorksheet model;
  private BiFunction<Double, Double, Double> function;

  public NumberVisitor(double base, IWorksheet model, BiFunction<Double, Double, Double> function) {
    this.base = base;
    this.model = model;
    this.function = function;
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
                       new NumberVisitor(base, this.model, this.function));
    } catch (IllegalArgumentException e) {
      return base;
    }
  }

  @Override
  public Double visitSymbol(String s) {
    if (s.matches("^([A-Z]+)([0-9]+)$")) {

      Pattern r = Pattern.compile("^([A-Z]+)([0-9]+)$");
      Matcher m = r.matcher(s);
      m.find();
      Sexp ex = Parser.parse(
              model.getCellAt(Coord.colNameToIndex(m.group(1)), Integer.parseInt(m.group(2)))
      );

      if (ex == null) {
        return base;
      }

      return ex.accept(this);
    } else if (s.matches("^([A-Z]+)([0-9]+):([A-Z]+)([0-9]+)$")) {
      Pattern r = Pattern.compile("^([A-Z]+)([0-9]+):([A-Z]+)([0-9]+)$");
      Matcher m = r.matcher(s);
      m.find();
      ArrayList<Sexp> dependencies = new ArrayList<>();

      double sofar = base;

      for (int col = Coord.colNameToIndex(m.group(1)); col < Coord.colNameToIndex(m.group(3));
           col++) {
        for (int row = Integer.parseInt(m.group(2)); row < Integer.parseInt(m.group(4)); row++) {
          Sexp cell = Parser.parse(model.getCellAt(col, row));
          if (cell == null) {
            continue;
          }
          sofar = function.apply(sofar, cell.accept(this));
        }
      }
      return sofar;
    }
    return base;
  }

  @Override
  public Double visitString(String s) {
    return base;
  }
}
