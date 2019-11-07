package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;

public class NoScrollView extends JFrame implements IView {

  IWorksheet model;
  Frame screen;

  Map<Coord, CellView> cells = new HashMap<>();

  private int minWCell = 1;
  private int maxWCell = 10;
  private int minHCell = 1;
  private int maxHCell = 20;

  public NoScrollView(IWorksheet model) throws IOException {
    super();
    this.model = model;
    this.setTitle("gOOD");
    this.setSize(CellView.CELL_SIZE.width * (maxWCell - minWCell),
            CellView.CELL_SIZE.height * (maxHCell - minHCell));
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        int oldMaxWCell = maxWCell;
        int oldMaxHCell = maxHCell;
        //super.componentResized(e);
        Dimension newSize = e.getComponent().getSize();
        maxHCell = minHCell + (newSize.height / CellView.CELL_SIZE.height) + 1;
        maxWCell = minWCell + (newSize.width / CellView.CELL_SIZE.width) + 2;
        try {
          if (oldMaxHCell != maxHCell || oldMaxWCell != maxWCell) {
            removeCells(oldMaxHCell, maxHCell, oldMaxWCell, maxWCell);
            renderSpreadsheet();
          }
        } catch (IOException ex) {

        }
      }
    });
    renderSpreadsheet();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void renderChanges(List<Coord> cells) throws IOException {

  }


  @Override
  public void renderSpreadsheet() throws IOException {
    JPanel content = new JPanel();
    content.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    for (int x = 0; x <= (maxWCell - minWCell); x++) {
      for (int y = 0; y <= (maxHCell - minHCell); y++) {
        c.gridx = x + 1;
        c.gridy = y + 1;
        Coord coord = new Coord(minWCell + x, minHCell + y);
        CellView view = new CellView(coord, model.evaluateCellAt(minWCell + x,
                minHCell + y));
        content.add(view, c);
        cells.put(coord, view);
      }
    }
    drawColumns(c, content);
    JScrollPane pane = new JScrollPane(content);
    pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    this.setContentPane(pane);
    this.setVisible(true);
  }

  private void drawColumns(GridBagConstraints c, JPanel content) {
    c.gridy = 0;
    c.gridx = 0;
    c.gridwidth = maxWCell - minWCell + 1;
    JPanel j = new ColumnHeaders(minWCell, maxWCell);
    j.setPreferredSize(new Dimension(CellView.CELL_SIZE.width * (maxWCell - minWCell),
            CellView.CELL_SIZE.height));
    content.add(j, c);
  }

  private void removeCells(int oldMaxH, int newMaxH, int oldMaxW, int newMaxW) {
    for (int x = newMaxW; x > oldMaxW; x--) {
      for (int y = newMaxH; y > oldMaxH; y--) {
        Coord coord = new Coord(x, y);
        if (cells.containsKey(coord)) {
          this.remove(cells.get(coord));
          cells.remove(coord);
        }
      }
    }
    for (int y = newMaxH; y > oldMaxH; y--) {
      for (int x = newMaxW; x > oldMaxW; x--) {
        Coord coord = new Coord(x, y);
        if (cells.containsKey(coord)) {
          this.remove(cells.get(coord));
          cells.remove(coord);
        }
      }
    }
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }
}
