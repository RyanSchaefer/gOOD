package edu.cs3500.spreadsheets.view;

import java.awt.*;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;

public class ColumnHeaders extends JPanel {


  ColumnHeaders(int start, int end) {
    super();
    this.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    c.gridy = 0;
    c.gridx = 0;
    this.add(new ColumnHeader(null), c);
    for (int i = 0; i < (end - start); i++) {
      c.gridy = 0;
      c.gridx = i + 1;
      this.add(new ColumnHeader(start + i), c);
    }
    this.setPreferredSize(new Dimension(
            CellView.CELL_SIZE.width * ((start - end) + 1),
            CellView.CELL_SIZE.height));
  }

  public class ColumnHeader extends JPanel {
    Integer col;


    ColumnHeader(Integer col) {
      super();
      this.col = col;
      this.setPreferredSize(CellView.CELL_SIZE);
    }

    @Override
    protected void paintComponent(Graphics g) {
      super.paintComponent(g);
      Graphics2D draw = (Graphics2D) g;
      draw.setColor(Color.lightGray);
      draw.fillRect(0, 0, getWidth(), getHeight());
      draw.setColor(Color.black);
      if (col != null) {
        draw.drawString(Coord.colIndexToName(col), getWidth() / 2, getHeight() / 2);
      }
    }
  }
}
