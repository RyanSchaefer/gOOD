package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.model.formula.value.Value;
import java.util.List;

/**
 * Represents a mock model to ensure the methods from the model are being called correctly.
 */
public class MockModel implements IWorksheet {

  private StringBuilder log;

  public MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public List<Coord> allActiveCells() {
    log.append("allActiveCells()\n");
    return null;
  }

  @Override
  public ICell getCellAt(int col, int row) throws IllegalArgumentException {
    log.append(String.format("getCellAt(%d, %d)\n", col, row));
    return null;
  }

  @Override
  public Value evaluateCellAt(int col, int row) {
    log.append(String.format("evaluateCellAt(%d, %d)\n", col, row));
    return null;
  }

  @Override
  public void changeCellAt(int col, int row, String sexp) {
    log.append(String.format("changeCellAt(%d, %d, %s)\n", col, row, sexp));
  }

  @Override
  public boolean documentFreeOfErrors() {
    log.append("documentFreeOfErrors()\n");
    return false;
  }

  @Override
  public List<Coord> getDependents(int col, int row) {
    log.append(String.format("getDependents(%d, %d)\n", col, row));
    return null;
  }
}

