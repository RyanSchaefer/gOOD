package edu.cs3500.spreadsheets.model;

import java.util.List;

import edu.cs3500.spreadsheets.model.formula.Formula;

/**
 * A basic slim worksheet implementing the SlimWorksheet interface and allowing for expressions at
 * locations.
 */
public class BasicSlimWorksheet implements SlimWorksheet {

  private IWorksheet worksheet;

  public BasicSlimWorksheet(IWorksheet worksheet) {
    this.worksheet = worksheet;
  }

  @Override
  public List<Coord> allActiveCells() {
    return worksheet.allActiveCells();
  }

  @Override
  public String getCellAt(int col, int row) throws IllegalArgumentException {
    return worksheet.getCellAt(col, row);
  }

  @Override
  public Formula evaluateCellAt(int col, int row) {
    return worksheet.evaluateCellAt(col, row);
  }

  @Override
  public boolean documentFreeOfErrors() {
    return worksheet.documentFreeOfErrors();
  }

  @Override
  public List<Coord> getDependents(int col, int row) {
    return worksheet.getDependents(col, row);
  }
}
