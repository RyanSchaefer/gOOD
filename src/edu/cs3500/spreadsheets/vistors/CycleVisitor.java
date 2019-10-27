package edu.cs3500.spreadsheets.vistors;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

/**
 * Answers if there is a cycle in this Sexp in the context of the provided model.
 */
public class CycleVisitor implements SexpVisitor<Boolean> {

  private IWorksheet model;
  private List<Coord> visited;

  public CycleVisitor(IWorksheet model, ArrayList<Coord> coord) {
    this.model = model;
    this.visited = new ArrayList<>(coord);
  }

  @Override
  public Boolean visitBoolean(boolean b) {
    return false;
  }

  @Override
  public Boolean visitNumber(double d) {
    return false;
  }

  @Override
  public Boolean visitSList(List<Sexp> l) {
    boolean ormap = false;
    for (Sexp s: l) {
      ormap |= s.accept(new CycleVisitor(this.model, new ArrayList<>(this.visited)));
    }
    return ormap;
  }

  @Override
  public Boolean visitSymbol(String s) {
    if (s.matches("^([A-Z]+)([0-9]+)$")) {

      Pattern r = Pattern.compile("^([A-Z]+)([0-9]+)$");
      Matcher m = r.matcher(s);
      m.find();
      String cell = model.getCellAt(Coord.colNameToIndex(m.group(1)), Integer.parseInt(m.group(2)));
      Coord c = new Coord(
              Coord.colNameToIndex(m.group(1)),
              Integer.parseInt(m.group(2)));
      if (cell == null) {
        return false;
      }
      Sexp ex = Parser.parse(cell);
      if (this.visited.contains(c)) {
        return true;
      }
      this.visited.add(c);
      return ex.accept(new CycleVisitor(this.model, new ArrayList<>(this.visited)));
    }
    return false;
  }

  @Override
  public Boolean visitString(String s) {
    return false;
  }
}
