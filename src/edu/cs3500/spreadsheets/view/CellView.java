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
  private Graphics2D drawer;
  static final public int BORDER = 1;
  static final public Dimension CELL_SIZE = new Dimension(60, 20);

  public CellView(Coord c, Formula f) {
    super();
    this.setPreferredSize(CELL_SIZE);
    // TODO: check this vs f.evaluate().toString()
    this.coord = c;
    this.formula = f;
  }

  @Override
  public void renderChanges(List<Coord> cells) throws IOException {
  }

  @Override
  public void renderSpreadsheet() throws IOException {

  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D draw = (Graphics2D) g;

    draw.setColor(Color.BLACK);
    draw.fillRect(0, 0, getWidth(), getHeight());

    draw.setColor(Color.white);
    draw.fillRect(BORDER, BORDER, getWidth() - (BORDER * 2),
            getHeight() - (BORDER * 2));

    draw.setColor(Color.black);
    if (formula != null) {
      draw.drawString(formula.toString(), 0, getHeight() - (BORDER * 2));
    }

  }

  @Override
  public void makeVisible() {

  }
}
