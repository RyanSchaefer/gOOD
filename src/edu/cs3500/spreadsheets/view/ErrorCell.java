package edu.cs3500.spreadsheets.view;


import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.formula.Formula;

/**
 * Represents a cell with an error in it.
 */
public class ErrorCell extends JPanel implements IView {

  private Formula formula;
  private Graphics2D drawer;
  private static final int BORDER = 1;
  private static final Dimension CELL_SIZE = new Dimension(60, 20);
  private boolean active;

  /**
   * A cell with an error in it
   * @param c the coordinate of this cell
   * @param f the formula causing this error
   * @param active is this cell active?
   */
  ErrorCell(Coord c, Formula f, boolean active) {
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
      draw.setColor(Color.RED);
    }
    draw.fillRect(BORDER, BORDER, getWidth() - (BORDER * 2),
            getHeight() - (BORDER * 2));

    draw.setColor(Color.black);
    if (formula != null) {
      draw.drawString(formula.toString(), 0, getHeight() - (BORDER * 2));
    }

  }

  @Override
  public void renderSpreadsheet() throws IOException {
    /*
    A single cell can't render a spreadsheet
     */
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void addFeatures(Features f) {
    /*
    No one is listening.
     */
  }
}

