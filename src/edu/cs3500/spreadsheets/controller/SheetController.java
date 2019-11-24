package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.view.IView;

/**
 * Handles mediation between an IView and a IWorksheet.
 */
public class SheetController implements Controller {

  private IWorksheet model;
  private IView view;

  private class SheetControllerFeatures implements Features {
    @Override
    public void editCell(Coord c, String s) {
      if (s.equals("")) {
        model.changeCellAt(c.col, c.row, null);
      } else {
        model.changeCellAt(c.col, c.row, s);
      }
      view.renderSpreadsheet();
      view.makeVisible();
    }

    @Override
    public void deleteCellContents(Coord c) {
      model.changeCellAt(c.col, c.row, null);
      view.renderSpreadsheet();
      view.makeVisible();
    }

    @Override
    public String toString() {
      return "SheetControllerFeatures";
    }
  }

  /**
   * Construct a new controller.
   *
   * @param model the model to control.
   * @param view  the view to control.
   */
  public SheetController(IWorksheet model, IView view) {
    this.model = model;
    this.view = view;
  }

  @Override
  public void displayView() {
    view.addFeatures(new SheetControllerFeatures());
    view.renderSpreadsheet();
    view.makeVisible();
  }
}
