package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import edu.cs3500.spreadsheets.vistors.DependencyVisitor;
import edu.cs3500.spreadsheets.vistors.EvalVisitor;
import edu.cs3500.spreadsheets.vistors.PrintVisitor;

/**
 * A basic worksheet implementing the IWorksheet interface and allowing for expressions at
 * locations.
 */
public class BasicWorksheet implements IWorksheet {

  // a list of coordinates to Sexp
  private Map<Coord, Sexp> grid = new HashMap<>();

  // a graph representing the a coordinate and which coordinates depend on that coordinate
  private Map<Coord, Set<Coord>> dependencies = new HashMap<>();

  // builds a list of expressions that have evaluated, when a cell changes this is reset
  private Map<Coord, String> evalMap = new HashMap<>();

  private BasicWorksheet() {
  }

  /**
   * Builds a basic worksheet one cell at a time.
   */
  public static class BasicWorksheetBuilder
          implements WorksheetReader.WorksheetBuilder<BasicWorksheet> {

    private BasicWorksheet model;

    /**
     * Return a basic worksheet builder to build with.
     */
    public BasicWorksheetBuilder() {
      model = new BasicWorksheet();
    }

    @Override
    public WorksheetReader.WorksheetBuilder<BasicWorksheet> createCell(int col,
                                                                       int row,
                                                                       String contents) {
      if (contents == null) {
        throw new IllegalArgumentException();
      }
      if (contents.startsWith("=")) {
        model.changeCellAt(col, row, contents.substring(1));
      } else {
        model.changeCellAt(col, row, contents);
      }
      return this;
    }

    @Override
    public BasicWorksheet createWorksheet() {
      return model;
    }
  }

  @Override
  public String getCellAt(int col, int row) {
    if (grid.get(new Coord(col, row)) == null) {
      return null;
    }
    return grid.get(new Coord(col, row)).accept(new PrintVisitor());
  }


  @Override
  public String evaluateCellAt(int col, int row) throws IllegalArgumentException {
    Coord coord = new Coord(col, row);
    Sexp cell = grid.get(new Coord(col, row));
    if (cell == null) {
      return null;
    }
    if (getDependents(col, row).contains(coord)) {
      throw new IllegalArgumentException("Cyclical reference");
    }
    if (!evalMap.containsKey(coord)) {
      evalMap.put(coord, grid.get(coord).accept(new EvalVisitor(this)).accept(
              new PrintVisitor()));
    }
    return evalMap.get(coord);
  }

  @Override
  public void changeCellAt(int col, int row, String s) {
    evalMap = new HashMap<>();
    if (s == null) {
      throw new IllegalArgumentException();
    }
    Sexp sexp = Parser.parse(s);
    updateDependents(sexp, new Coord(col, row));
    grid.put(new Coord(col, row), sexp);
  }

  private void updateDependents(Sexp s, Coord cell) {

    // remove all references to this cell
    for (Coord c: dependencies.keySet()) {
      dependencies.get(c).remove(cell);
    }

    // update which cells depend this cell depends on
    for (Coord c: s.accept(new DependencyVisitor())) {
      if (dependencies.containsKey(c)) {
        dependencies.get(c).add(cell);
      } else {
        dependencies.put(c, new HashSet<>(Arrays.asList(cell)));
      }
    }
  }

  @Override
  public Set<Coord> getDependents(int col, int row) {
    if (dependencies.containsKey(new Coord(col, row))) {
      return dependencies.get(new Coord(col, row));
    }
    return new HashSet<>();
  }

  @Override
  public boolean documentFreeOfErrors() {
    for (Coord c: grid.keySet()) {
      try {
        Sexp cell = grid.get(c);
        if (getDependents(c.col, c.row).contains(c)) {
          return false;
        }
        cell.accept(new EvalVisitor(this));
      } catch (IllegalArgumentException e) {
        return false;
      }
    }
    return true;
  }

  @Override
  public List<Coord> allActiveCells() {
    return new ArrayList<>(this.grid.keySet());
  }
}



