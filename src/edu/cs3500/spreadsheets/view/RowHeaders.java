package edu.cs3500.spreadsheets.view;

import java.awt.*;

import javax.swing.*;

public class RowHeaders extends JPanel {


  RowHeaders(int start, int end) {
    super();
    this.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    for (int i = 0; i < (end - start); i++) {
      c.gridy = i;
      c.gridx = 0;
      this.add(new RowHeader(start + i), c);
    }
    this.setPreferredSize(new Dimension(
            CellView.CELL_SIZE.width,
            CellView.CELL_SIZE.height * (start - end)));
  }

  public class RowHeader extends JPanel {
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
      draw.drawString(row.toString(), getWidth() / 2, getHeight() / 2);
    }
  }
}