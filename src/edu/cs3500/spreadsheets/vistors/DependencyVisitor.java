package edu.cs3500.spreadsheets.vistors;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

/**
 * Gets the dependencies of this Sexp.
 */
public class DependencyVisitor implements SexpVisitor<Set<Coord>> {

  IWorksheet model;

  @Override
  public Set<Coord> visitBoolean(boolean b) {
    return new HashSet<>();
  }

  @Override
  public Set<Coord> visitNumber(double d) {
    return new HashSet<>();
  }

  @Override
  public Set<Coord> visitSList(List<Sexp> l) {
    Set<Coord> dependencies = new HashSet<>();
    for (Sexp s: l) {
      dependencies.addAll(s.accept(this));
    }
    return dependencies;
  }

  @Override
  public Set<Coord> visitSymbol(String s) {
    Set<Coord> ret = new HashSet<>();
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

      for (int col = Coord.colNameToIndex(m.group(1)); col <= Coord.colNameToIndex(m.group(3));
           col++) {
        for (int row = Integer.parseInt(m.group(2)); row <= Integer.parseInt(m.group(4)); row++) {
          ret.add(new Coord(col, row));
        }
      }

      return ret;
    }
    return new HashSet<>();
  }

  @Override
  public Set<Coord> visitString(String s) {
    return new HashSet<>();
  }
}
