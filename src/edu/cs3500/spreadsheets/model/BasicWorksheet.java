package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cs3500.spreadsheets.vistors.CycleVisitor;
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
  private Map<Coord, List<Coord>> dependencies = new HashMap<>();

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
    public BasicWorksheetBuilder () {
      model = new BasicWorksheet();
    }

    @Override
    public WorksheetReader.WorksheetBuilder<BasicWorksheet> createCell(int col, int row, String contents) {
      if (contents == null) {
        throw new IllegalArgumentException();
      }
      if (contents.startsWith("=")) {
        model.grid.put(new Coord(col, row), Parser.parse(contents.substring(1)));
      } else {
        model.grid.put(new Coord(col, row), Parser.parse(contents));
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
    if (cell.accept(new CycleVisitor(this, new ArrayList<>(Arrays.asList(coord))))) {
      throw new IllegalArgumentException("Cyclical reference");
    }
    return grid.get(new Coord(col, row)).accept(new EvalVisitor(this)).accept(
            new PrintVisitor());
  }

  @Override
  public void changeCellAt(int col, int row, String s) {
    if (s == null) {
      throw new IllegalArgumentException();
    }
    Sexp sexp = Parser.parse(s);
    for (Coord c: sexp.accept(new DependencyVisitor(this))) {
      if (dependencies.containsKey(c)) {
        dependencies.get(c).add(c);
      } else {
        dependencies.put(c, new ArrayList<>(Arrays.asList(c)));
      }
    }
    grid.put(new Coord(col, row), sexp);
  }

  @Override
  public List<Coord> getDependents(int col, int row) {
    List<Coord> dependents = new ArrayList<>(dependencies.get(new Coord(col, row)));
    for (Coord c: dependents) {
      dependentsHelper(c, dependents);
    }
    return dependents;
  }

  /**
   * Given a coordinate add all of its dependents to the list of dependents we have seen thus far
   * if it is not already in it
   * @param c the cell we are evaluating
   * @param sofar the cells known to be dependents
   */
  private void dependentsHelper(Coord c, List<Coord> sofar) {
    List<Coord> dependents = dependencies.get(c);
    for (Coord dep: dependents) {
      if (!sofar.contains(dep)) {
        sofar.add(dep);
        dependentsHelper(dep, sofar);
      }
    }
  }

  @Override
  public boolean documentFreeOfErrors() {
    for (Coord c: grid.keySet()){
      try {
        Sexp cell = grid.get(c);
        if (cell.accept(new CycleVisitor(this, new ArrayList<>(Arrays.asList(c))))) {
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



