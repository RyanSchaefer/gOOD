package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;

public class BasicWorksheet implements IWorksheet {

  private BasicWorksheet() {
  }

  public static class BasicWorksheetBuilder
          implements WorksheetReader.WorksheetBuilder<BasicWorksheet> {

    private BasicWorksheet model;

    public BasicWorksheetBuilder () {
      model = new BasicWorksheet();
    }

    @Override
    public WorksheetReader.WorksheetBuilder<BasicWorksheet> createCell(int col, int row, String contents) {
      model.changeCellAt(col, row, Parser.parse(contents));
      return this;
    }

    @Override
    public BasicWorksheet createWorksheet() {
      return model;
    }
  }

  @Override
  public Sexp getCellAt(int col, int row) {
    return null;
  }

  @Override
  public String getSheetName() {
    return null;
  }

  @Override
  public void changeCellAt(int col, int row, Sexp sexp) {

  }

  @Override
  public void changeSheetName(String name) {

  }
}
