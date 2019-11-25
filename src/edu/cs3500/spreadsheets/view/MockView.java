package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents a mock view to ensure the view is responding correctly to the controller.
 */
public class MockView implements IView {

  private StringBuilder log;
  private Features f;

  public MockView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void renderSpreadsheet() {
    log.append("renderSpreadsheet()\n");
  }

  @Override
  public void makeVisible() {
    log.append("makeVisible()\n");
  }

  @Override
  public void addFeatures(Features f) {
    log.append(String.format("addFeatures(%s)\n", f.toString()));
    this.f = f;
  }

  public void click(Coord c) {
    f.editCell(c, "");
  }
}

