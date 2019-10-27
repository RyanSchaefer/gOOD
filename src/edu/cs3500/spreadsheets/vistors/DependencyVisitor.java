package edu.cs3500.spreadsheets.vistors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

/**
 * Gets the dependencies of this Sexp in the context of the provided model.
 */
public class DependencyVisitor implements SexpVisitor<List<Coord>> {

  IWorksheet model;

  public DependencyVisitor(IWorksheet model) {
    this.model = model;
  }

  @Override
  public List<Coord> visitBoolean(boolean b) {
    return new ArrayList<>();
  }

  @Override
  public List<Coord> visitNumber(double d) {
    return new ArrayList<>();
  }

  @Override
  public List<Coord> visitSList(List<Sexp> l) {
    ArrayList<Coord> dependencies = new ArrayList<>();
    for (Sexp s: l) {
      dependencies.addAll(s.accept(this));
    }
    return dependencies;
  }

  @Override
  public List<Coord> visitSymbol(String s) {
    ArrayList<Coord> ret = new ArrayList();
    if (s.matches("^[A-Z]+[0-9]+$")) {
      Pattern r = Pattern.compile("^([A-Z]+)([0-9]+)$");
      Matcher m = r.matcher(s);
      m.find();
      ret.add(new Coord(Coord.colNameToIndex(m.group(1)), Integer.parseInt(m.group(2))));
      return ret;
    } else if (s.matches("^[A-Z]+[0-9]+:[A-Z]+[0-9]+$")) {

      Pattern r = Pattern.compile("^([A-Z]+)([0-9]+):([A-Z]+)([0-9]+)$");
      Matcher m = r.matcher(s);
      m.find();

      for (int col = Coord.colNameToIndex(m.group(1)); col < Coord.colNameToIndex(m.group(3));
           col++) {
        for (int row = Integer.parseInt(m.group(2)); row < Integer.parseInt(m.group(4)); row++) {
          ret.add(new Coord(col, row));
        }
      }

      return ret;
    }
    throw new IllegalArgumentException();
  }

  @Override
  public List<Coord> visitString(String s) {
    return new ArrayList<>();
  }
}
