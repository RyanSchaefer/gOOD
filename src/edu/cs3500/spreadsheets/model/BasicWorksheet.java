package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.SString;
import edu.cs3500.spreadsheets.sexp.Sexp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

import edu.cs3500.spreadsheets.vistors.SexpToFormula;
import edu.cs3500.spreadsheets.vistors.DependencyVisitor;

/**
 * A basic worksheet implementing the IWorksheet interface and allowing for expressions at
 * locations.
 */
public class BasicWorksheet implements IWorksheet {

  // a list of coordinates to Sexp
  private Map<Coord, Formula> grid = new HashMap<>();

  // a graph representing the a coordinate and which coordinates depend on that coordinate
  private Map<Coord, List<Coord>> dependencies = new HashMap<>();

  // builds a list of expressions that have evaluated, when a cell changes this is reset
  private Map<Coord, Formula> evalMap = new HashMap<>();

  private List<Coord> cellsWithErrors = new ArrayList<>();

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
      model.changeCellAt(col, row, contents);
      return this;
    }

    @Override
    public BasicWorksheet createWorksheet() {
      return model;
    }
  }

  @Override
  public String getCellAt(int col, int row) {
    Coord c = new Coord(col, row);
    if (grid.get(c) == null) {
      return null;
    }
    if (cellsWithErrors.contains(c)) {
      return "ERROR";
    }
    return grid.get(c).toString();
  }


  @Override
  public Formula evaluateCellAt(int col, int row) throws IllegalArgumentException {
    Coord coord = new Coord(col, row);
    if (!grid.containsKey(coord)) {
      return null;
    }
    if (getDependents(col, row).contains(coord)) {
      throw new IllegalArgumentException("Cyclical reference");
    }
    if (cellsWithErrors.contains(coord)) {
      throw new IllegalArgumentException("Error in cell");
    }
    if (!evalMap.containsKey(coord)) {
      evalMap.put(coord, new ValueHolder(grid.get(coord).evaluate()));
    }
    return evalMap.get(coord);
  }

  @Override
  public void changeCellAt(int col, int row, String s) {
    Coord coord = new Coord(col, row);

    if (s == null) {
      throw new IllegalArgumentException();
    }
    cellsWithErrors.remove(coord);
    Sexp sexp;
    if (s.startsWith("=")) {
      s = s.substring(1);
      try {
        sexp = Parser.parse(s);
      } catch (IllegalArgumentException e) {
        sexp = new SString(s);
        cellsWithErrors.add(coord);
      }
    } else {
      try {
        sexp = Parser.parse(s);
      } catch (IllegalArgumentException e) {
        sexp = new SString(s);
        cellsWithErrors.add(coord);
      }
    }

    removeDepFromEval(coord);

    updateDependents(sexp, new Coord(col, row));

    grid.put(new Coord(col, row), sexp.accept(new SexpToFormula(this)));
  }

  private void removeDepFromEval(Coord coord) {
    for (Coord dep: getDependents(coord.col, coord.row)) {
      evalMap.remove(dep);
    }
    evalMap.remove(coord);
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
        dependencies.put(c, new ArrayList<>(Arrays.asList(cell)));
      }
    }
  }

  private List<Coord> getDependents(int col, int row) {
    Coord coord = new Coord(col, row);
    ArrayList<Coord> ret = new ArrayList<>();
    Queue<Coord> queue = new LinkedList<>();
    ArrayList<Coord> seen = new ArrayList<>();
    if (dependencies.containsKey(new Coord(col, row))) {

      queue.addAll(new ArrayList<>(dependencies.get(coord)));
      seen.add(coord);
      ret.addAll(dependencies.get(coord));

      dependenciesHelper(ret,
              queue,
              seen);
    }
    return ret;
  }

  private void dependenciesHelper(List<Coord> dependents, Queue<Coord> queue, List<Coord> seen) {
    while (!queue.isEmpty()) {
      Coord c = queue.remove();
      if (seen.contains(c)) {
        continue;
      } else {
        if (dependencies.containsKey(c)) {
          dependents.addAll(dependencies.get(c));
          queue.addAll(dependencies.get(c));
          seen.add(c);
        }
      }
    }
  }

  @Override
  public boolean documentFreeOfErrors() {
    for (Coord c: allActiveCells()) {
      try {
        evaluateCellAt(c.col, c.row);
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



