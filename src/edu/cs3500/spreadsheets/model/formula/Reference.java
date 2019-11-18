package edu.cs3500.spreadsheets.model.formula;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.formula.value.Value;

/**
 * A reference to another cell or rectangular region of cells.
 */
public class Reference implements Formula {

  private IWorksheet model;
  private List<Coord> references;
  private String original;

  /**
   * Constructs a Reference.
   *
   * @param model the model to which the reference points to
   * @param cells a string which represents the cells pointed to
   */
  public Reference(IWorksheet model, String cells) {
    this.model = model;
    references = parseReferences(cells);
    original = cells;
  }

  @Override
  public List<Value> evaluateToList() {
    List<Value> values = new ArrayList<>();
    for (Coord c: references) {
      if (model.getDependents(c.col, c.row).contains(c)) {
        throw new IllegalArgumentException("Cyclical reference");
      }
      values.addAll(model.getCellAt(c.col, c.row).evaluateToList());
    }
    return values;
  }

  private List<Coord> parseReferences(String cells) {
    List<Coord> ret = new ArrayList<>();
    if (cells.matches("^([A-Z]+)([0-9]+)$")) {

      Pattern r = Pattern.compile("^([A-Z]+)([0-9]+)$");
      Matcher m = r.matcher(cells);
      m.find();
      ret.add(new Coord(
              Coord.colNameToIndex(m.group(1)), Integer.parseInt(m.group(2))));
    } else if (cells.matches("^([A-Z]+)([0-9]+):([A-Z]+)([0-9]+)$")) {

      Pattern r = Pattern.compile("^([A-Z]+)([0-9]+):([A-Z]+)([0-9]+)$");
      Matcher m = r.matcher(cells);
      m.find();

      if (Coord.colNameToIndex(m.group(1)) > Coord.colNameToIndex(m.group(3))
              || Integer.parseInt(m.group(2)) > Integer.parseInt(m.group(4))) {
        throw new IllegalArgumentException("First cell must no smaller than the second");
      }

      for (int col = Coord.colNameToIndex(m.group(1)); col <= Coord.colNameToIndex(m.group(3));
           col++) {
        for (int row = Integer.parseInt(m.group(2)); row <= Integer.parseInt(m.group(4)); row++) {
          ret.add(new Coord(col, row));
        }
      }
    }
    return ret;
  }

  @Override
  public Value evaluate() {
    return this.evaluateToList().get(0);
  }

  @Override
  public String toString() {
    return "=" + original;
  }
}
