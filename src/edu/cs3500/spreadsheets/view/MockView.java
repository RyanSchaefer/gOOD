package edu.cs3500.spreadsheets.view;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;

public class MockView implements IView {

  StringBuilder log;
  Features f;

  public MockView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void renderSpreadsheet() {
    log.append("renderSpreadsheet()");
  }

  @Override
  public void makeVisible() {
    log.append("makeVisible()");
  }

  @Override
  public void addFeatures(Features f) {
    log.append(String.format("addFeatures(%s)", f.toString()));
    this.f = f;
  }

  public void click(Coord c) {
    f.editCell(c, "");
  }
}
