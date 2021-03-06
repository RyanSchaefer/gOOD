package edu.cs3500.spreadsheets.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;

import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.ValueHolder;
import edu.cs3500.spreadsheets.model.formula.functions.ErrorFunction;
import edu.cs3500.spreadsheets.model.formula.functions.IFunction;
import edu.cs3500.spreadsheets.model.formula.value.VString;
import edu.cs3500.spreadsheets.model.formula.value.Value;
import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;
import edu.cs3500.spreadsheets.vistors.DependencyVisitor;
import edu.cs3500.spreadsheets.vistors.SexpToFormula;
import edu.cs3500.spreadsheets.vistors.SexpToValue;

/**
 * A basic worksheet implementing the IWorksheet interface and allowing for expressions at
 * locations.
 */
public class BasicWorksheet implements IWorksheet {

  // a list of coordinates to Sexp
  private Map<Coord, BasicCell> grid = new HashMap<>();

  // a graph representing the a coordinate and which coordinates depend on that coordinate
  private Map<Coord, List<Coord>> dependencies = new HashMap<>();

  // builds a list of expressions that have evaluated, when a cell changes this is reset
  private Map<Coord, Formula> evalMap = new HashMap<>();

  private Map<String, IFunction> functions;

  private BasicWorksheet(Map<String, IFunction> functions) {
    this.functions = functions;
  }

  /**
   * Builds a basic worksheet one cell at a time.
   */
  public static class BasicWorksheetBuilder
          implements WorksheetReader.WorksheetBuilder<IWorksheet> {

    private BasicWorksheet model;

    /**
     * Return a basic worksheet builder to build with.
     */
    public BasicWorksheetBuilder(Map<String, IFunction> functions) {
      model = new BasicWorksheet(functions);
    }

    @Override
    public WorksheetReader.WorksheetBuilder<IWorksheet> createCell(int col,
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
  public ICell getCellAt(int col, int row) {
    Coord c = new Coord(col, row);
    if (grid.get(c) == null) {
      return null;
    }
    return grid.get(c);
  }


  @Override
  public Value evaluateCellAt(int col, int row) throws IllegalArgumentException {
    Coord coord = new Coord(col, row);
    if (!grid.containsKey(coord)) {
      return null;
    }
    if (getDependents(col, row).contains(coord)) {
      throw new IllegalArgumentException("Cyclical reference");
    }
    if (!evalMap.containsKey(coord)) {
      evalMap.put(coord, new ValueHolder(grid.get(coord).evaluateToList()));
    }
    return evalMap.get(coord).evaluate();
  }

  @Override
  public void changeCellAt(int col, int row, String s) {
    Coord coord = new Coord(col, row);

    removeDep(coord);
    removeDepFromEval(coord);

    if (s == null) {
      grid.remove(coord);
      evalMap.remove(coord);
      return;
    }
    Sexp sexp;
    if (s.startsWith("=")) {
      s = s.substring(1);
      try {
        sexp = Parser.parse(s);
        grid.put(new Coord(col, row),
                new BasicCell(
                        sexp.accept(new SexpToFormula(this, s, functions)),
                        "=" + s));
        updateDependents(sexp, new Coord(col, row));
      } catch (IllegalArgumentException e) {
        // This isn't a valid Sexp so make it an error
        grid.put(coord, new BasicCell(new ErrorFunction("=" + s), "=" + s));
      }
    } else {
      try {
        sexp = Parser.parse(s);
        grid.put(new Coord(col, row), new BasicCell(
                sexp.accept(new SexpToValue()),
                s));
      } catch (IllegalArgumentException e) {
        grid.put(coord, new BasicCell(
                new VString(s),
                s));
      }
    }
  }

  /**
   * We can no longer depend on this information so we must remove the things that depend on the
   * changed cell from our caches.
   *
   * @param coord the coordinate that changed
   */
  private void removeDepFromEval(Coord coord) {
    for (Coord dep : getDependents(coord.col, coord.row)) {
      evalMap.remove(dep);
    }
    evalMap.remove(coord);
  }

  /**
   * Updates the dependents of the given cell given that it was created with this sexp.
   *
   * @param s    the sexp it was created with
   * @param cell the cell that this was created at
   */
  private void updateDependents(Sexp s, Coord cell) {

    // update which cells depend this cell depends on
    for (Coord c : s.accept(new DependencyVisitor())) {
      if (dependencies.containsKey(c)) {
        dependencies.get(c).add(cell);
      } else {
        dependencies.put(c, new ArrayList<>(Arrays.asList(cell)));
      }
    }
  }

  /**
   * Remove all of the references to this cell from the graph of dependencies.
   *
   * @param cell the cell to remove references to
   */
  private void removeDep(Coord cell) {
    for (Coord c : dependencies.keySet()) {
      dependencies.get(c).remove(cell);
    }
  }

  @Override
  public List<Coord> getDependents(int col, int row) {
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

  /**
   * A helper function which applies dfs to find all of the dependents of that cells and avoid
   * infinite loops due to cycles.
   *
   * @param dependents the dependents so far
   * @param queue      the queue of coordinates to process
   * @param seen       what we have seen thus far
   */
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
    for (Coord c : allActiveCells()) {
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



