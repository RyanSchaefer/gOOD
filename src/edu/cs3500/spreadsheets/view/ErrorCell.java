package edu.cs3500.spreadsheets.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.formula.Formula;

/**
 * Represents a cell with an error in it.
 */
public class ErrorCell extends CellView {

  private Formula formula;
  private Graphics2D drawer;
  private static final int BORDER = 1;
  private static final Dimension CELL_SIZE = new Dimension(60, 20);
  private boolean active;

  /**
   * Represents a cell with an error in it to distinguish between valid and invalid cells.
   */
  ErrorCell(Coord c, Formula f, boolean active) {
    super(c, f, active);
    this.setPreferredSize(CELL_SIZE);
    // TODO: check this vs f.evaluate().toString()
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
      draw.setColor(Color.RED);
    }
    draw.fillRect(BORDER, BORDER, getWidth() - (BORDER * 2),
            getHeight() - (BORDER * 2));

    draw.setColor(Color.black);
    if (formula != null) {
      draw.drawString(formula.toString(), 0, getHeight() - (BORDER * 2));
    }

  }
}

