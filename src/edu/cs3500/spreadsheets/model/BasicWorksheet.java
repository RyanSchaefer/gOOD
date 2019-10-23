package edu.cs3500.spreadsheets.model;

import edu.cs3500.spreadsheets.sexp.Parser;
import edu.cs3500.spreadsheets.sexp.Sexp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.sexp.SexpVisitor;

public class BasicWorksheet implements IWorksheet {

  Map<Coord, Sexp> grid;

  private BasicWorksheet() {
    grid = new HashMap<>();
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
    return grid.get(new Coord(col, row));
  }


  @Override
  public void changeCellAt(int col, int row, Sexp sexp) {
    grid.put(new Coord(col, row), sexp);
  }

}



