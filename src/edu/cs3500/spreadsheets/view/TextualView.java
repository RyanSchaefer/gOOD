package edu.cs3500.spreadsheets.view;

import java.io.IOException;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;

public class TextualView implements IView {

  private IWorksheet model;
  private Appendable out;

  public TextualView(IWorksheet model, Appendable out) {
    this.model = model;
    this.out = out;
  }

  @Override
  public void renderChanges(List<Coord> cells) throws IOException {
    /*
    Since textual views save the state once, they do not support rendering changes.
     */
  }

  @Override
  public void renderSpreadsheet() throws IOException {
    for (Coord c : model.allActiveCells()) {
      out.append(Coord.colIndexToName(c.col) + c.row + " " + model.getCellAt(c.col, c.row) + "\n");
    }
  }

  @Override
  public void makeVisible() {
    /*
    There is no way to make a save visible to the user.
     */
  }
}
