package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.formula.Formula;

public class CellView extends JPanel implements IView {

  private Coord coord;
  private Formula formula;

  public CellView(Coord c, Formula f) {
    super();
    this.setPreferredSize(new Dimension(50, 15));
    TextArea t = new TextArea();
    t.setPreferredSize(new Dimension(50, 15));
    // TODO: check this vs f.evaluate().toString()
    t.setText(f.toString());
    this.add(t);
    this.coord = c;
    this.formula = f;

  }

  @Override
  public void renderChanges(List<Coord> cells) throws IOException {
  }

  @Override
  public void renderSpreadsheet() throws IOException {

  }
}
