package edu.cs3500.spreadsheets.model.Formula;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Formula.Value.Value;
import edu.cs3500.spreadsheets.model.IWorksheet;

/**
 * A reference to another cell or rectangular region of cells.
 */
public class Reference implements Formula {

  private IWorksheet model;
  private List<Coord> references;

  public Reference (IWorksheet model, String cells) {
    this.model = model;
    references = parseReferences(cells);
  }

  @Override
  public List<Value> evaluate() {
    List<Value> values = new ArrayList<>();
    for (Coord c: references) {
      values.addAll(model.evaluateCellAt(c.col, c.row).evaluate());
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

      if (Coord.colNameToIndex(m.group(1)) > Coord.colNameToIndex(m.group(3)) &&
              Integer.parseInt(m.group(2)) > Integer.parseInt(m.group(4))) {
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
  public String toString() {
    Coord v = references.get(0);
    return model.evaluateCellAt(v.col, v.row).toString();
  }
}
