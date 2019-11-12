package edu.cs3500.spreadsheets.view;

import java.awt.*;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.formula.Formula;

/**
 * A View of one cell.
 */
public class CellView extends JPanel {

  private Coord coord;
  private Formula formula;
  private Graphics2D drawer;
  static final public int BORDER = 1;
  static final public Dimension CELL_SIZE = new Dimension(60, 20);
  private boolean active;

  public CellView(Coord c, Formula f, boolean active) {
    super();
    this.setPreferredSize(CELL_SIZE);
    // TODO: check this vs f.evaluate().toString()
    this.coord = c;
    this.formula = f;
    this.active = active;
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D draw = (Graphics2D) g;

    if (this.active) {
      draw.setColor(Color.blue);
    } else {
      draw.setColor(Color.BLACK);
    }
    draw.fillRect(0, 0, getWidth(), getHeight());

    if (this.active) {
      draw.setColor(new Color(187, 194, 196));
    } else {
      draw.setColor(Color.white);
    }
    draw.fillRect(BORDER, BORDER, getWidth() - (BORDER * 2),
            getHeight() - (BORDER * 2));

    draw.setColor(Color.black);
    if (formula != null) {
      draw.drawString(formula.toString(), 0, getHeight() - (BORDER * 2));
    }

  }
}
