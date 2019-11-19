package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.view.IView;

public class SheetController implements Features {

  IWorksheet model;
  IView view;

  public SheetController(IWorksheet model, IView view) {
    this.model = model;
    this.view = view;
    view.addFeatures(this);
    view.makeVisible();
  }

  @Override
  public void editCell(Coord c, String s) {
    if (s.equals("")) {
      this.model.changeCellAt(c.col, c.row, null);
    } else {
      this.model.changeCellAt(c.col, c.row, s);
    }
    this.view.renderSpreadsheet();
  }

  @Override
  public void saveSheet() {
  }

  @Override
  public void loadSheet(String sheet) {

  }
}
