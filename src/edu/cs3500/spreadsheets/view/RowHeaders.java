package edu.cs3500.spreadsheets.view;

import java.awt.*;

import javax.swing.*;

/**
 * The row headers to be drawn. They are drawn so that they cover the range of cells that are
 * visible. They go from 1 to infinity.
 */
class RowHeaders extends JPanel {

  RowHeaders(int start, int end) {
    super();
    this.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    int last = 0;
    for (int i = 0; i < (end - start); i++) {
      c.gridy = i;
      c.gridx = 0;
      this.add(new RowHeader(start + i), c);
      last = i;
    }
    this.setPreferredSize(new Dimension(
            CellView.CELL_SIZE.width,
            CellView.CELL_SIZE.height * (start - end)));
  }

  /**
   * One row header from 1 - infinity.
   */
  private class RowHeader extends JPanel {
    Integer row;


    RowHeader(int row) {
      super();
      this.row = row;
      this.setPreferredSize(CellView.CELL_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D draw = (Graphics2D) g;
      draw.setColor(Color.lightGray);
      draw.fillRect(0, 0, getWidth(), getHeight());
      draw.setColor(Color.black);
      draw.drawString(row.toString(), getWidth() / 2, (getHeight() / 2) + 5);
    }
  }
}