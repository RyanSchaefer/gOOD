package edu.cs3500.spreadsheets.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.formula.Formula;

/**
 * A View of one cell.
 */
public class CellView extends JPanel {

  private Formula formula;
  private Graphics2D drawer;
  private static final int BORDER = 1;
  static final Dimension CELL_SIZE = new Dimension(60, 20);
  private boolean active;

  /**
   * Represents the view of a single cell.
   * @param c the coordinate position of the cell
   * @param f the formula to be evaluated
   * @param active whether the cell is active (is it currently selected)
   */
  CellView(Coord c, Formula f, boolean active) {
    super();
    this.setPreferredSize(CELL_SIZE);
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
