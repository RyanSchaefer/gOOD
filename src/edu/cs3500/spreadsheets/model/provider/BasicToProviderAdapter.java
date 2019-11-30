package edu.cs3500.spreadsheets.model.provider;

import java.util.Objects;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.provider.visitors.BooleanVisitor;
import edu.cs3500.spreadsheets.model.provider.visitors.DoubleVisitor;
import edu.cs3500.spreadsheets.model.provider.visitors.PrintingVisitor;

public class BasicToProviderAdapter implements WorksheetInterface {

  private IWorksheet worksheet;

  public BasicToProviderAdapter(IWorksheet worksheet) {
    this.worksheet = Objects.requireNonNull(worksheet);
  }

  @Override
  public void setCellUpdate(Coord position, String contents) throws IllegalArgumentException {
    worksheet.changeCellAt(position.col, position.row, contents);
  }

  @Override
  public void evaluateAll() throws IllegalArgumentException {
    /*
    all cells are evaluated automatically.
     */
  }

  @Override
  public String evaluateString(Coord position) throws IllegalArgumentException {
    try {
      return worksheet.evaluateCellAt(position.col, position.row).accept(new PrintingVisitor());
    } catch (NullPointerException e) {
      return "";
    }
  }

  @Override
  public double evaluateDouble(Coord position) throws IllegalArgumentException {
    return worksheet.evaluateCellAt(position.col, position.row).accept(new DoubleVisitor());
  }

  @Override
  public boolean evaluateBoolean(Coord position) throws IllegalArgumentException {
    return worksheet.evaluateCellAt(position.col, position.row).accept(new BooleanVisitor());
  }

  @Override
  public String getRawContents(Coord position) {
    try {
      return worksheet.getCellAt(position.col, position.row).toString();
    } catch (NullPointerException e) {
      return "";
    }
  }

  @Override
  public String getName(Coord position) {
    return Coord.colIndexToName(position.col) + position.row;
  }

  @Override
  public void removeCell(Coord position) {
    worksheet.changeCellAt(position.col, position.row, null);
  }

  @Override
  public int getColSize() {
    int max = 0;
    for (Coord c : worksheet.allActiveCells()) {
      if (c.col > max) {
        max = c.col;
      }
    }
    return max;
  }

  @Override
  public int getRowSize() {
    int max = 0;
    for (Coord c : worksheet.allActiveCells()) {
      if (c.row > max) {
        max = c.row;
      }
    }
    return max;
  }
}
