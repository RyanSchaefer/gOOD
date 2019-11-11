package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;

public class NoScrollView extends JFrame implements IView {

  private IWorksheet model;
  private Features features;

  private Map<Coord, CellView> cells = new HashMap<>();

  private int minWCell = 1;
  private int maxWCell = 10;
  private int minHCell = 1;
  private int maxHCell = 20;

  private Coord activeCell;

  private JScrollPane scrollPane;

  public NoScrollView(IWorksheet model) {
    super();
    this.model = model;
    this.setTitle("gOOD");
    this.setSize(CellView.CELL_SIZE.width * (maxWCell - minWCell),
            CellView.CELL_SIZE.height * (maxHCell - minHCell));
    JMenuBar menu = new JMenuBar();
    JMenu m1 = new JMenu("Test");
    JMenuItem save = new JMenuItem("Save...");
    m1.add(save);
    menu.add(m1);
    this.setJMenuBar(menu);
    menu.setVisible(true);
    this.addComponentListener(new ComponentAdapter() {
      @Override
      public void componentResized(ComponentEvent e) {
        int oldMaxWCell = maxWCell;
        int oldMaxHCell = maxHCell;
        //super.componentResized(e);
        Dimension newSize = e.getComponent().getSize();
        maxHCell = minHCell + (newSize.height / CellView.CELL_SIZE.height) + 2;
        maxWCell = minWCell + (newSize.width / CellView.CELL_SIZE.width) + 2;
        if (oldMaxHCell != maxHCell || oldMaxWCell != maxWCell) {
          removeCells(oldMaxHCell, maxHCell, oldMaxWCell, maxWCell);
          renderSpreadsheet();
        }
      }
    });
    renderSpreadsheet();
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  @Override
  public void renderSpreadsheet() {
    Point scrollP;
    if (this.scrollPane == null) {
      scrollP = new Point(0, 0);
    } else {
      scrollP = this.scrollPane.getViewport().getViewPosition();
    }
    JPanel content = new JPanel();
    content.setLayout(new GridBagLayout());
    GridBagConstraints c = new GridBagConstraints();
    for (int x = 0; x < (maxWCell - minWCell); x++) {
      for (int y = 0; y < (maxHCell - minHCell); y++) {
        c.gridx = x + 1;
        c.gridy = y + 1;
        Coord coord = new Coord(minWCell + x, minHCell + y);
        CellView view = new CellView(coord, model.evaluateCellAt(minWCell + x,
                minHCell + y), coord.equals(this.activeCell));
        view.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            NoScrollView.this.makeCellActive(coord);
            NoScrollView.this.renderSpreadsheet();
          }
        });
        content.add(view, c);
        cells.put(coord, view);
      }
    }
    JScrollPane pane = new JScrollPane(content);
    this.scrollPane = pane;
    pane.setLayout(new ScrollPaneLayout());
    drawColumns(pane);
    drawRows(pane);
    //pane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
    //pane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_NEVER);
    pane
            .getHorizontalScrollBar()
            .addAdjustmentListener(e -> {
              int oldMaxWCell = maxWCell;
              int current = this.getWidth() + e.getAdjustable().getValue();
              int max = e.getAdjustable().getMaximum();
              if ((max - current) / CellView.CELL_SIZE.width < 2) {
                maxWCell += 1;
                renderSpreadsheet();
              }
            });
    pane
            .getVerticalScrollBar()
            .addAdjustmentListener(e -> {
              int oldMaxHCell = maxHCell;
              int current = this.getHeight() + e.getAdjustable().getValue();
              int max = e.getAdjustable().getMaximum();
              if ((max - current) / CellView.CELL_SIZE.height < 2) {
                maxHCell += 1;
                renderSpreadsheet();
              }
            });
    pane.getViewport().setViewPosition(scrollP);
    this.setContentPane(pane);
    this.setVisible(true);
  }

  private void drawColumns(JScrollPane content) {
    JViewport j = new JViewport();
    JPanel panel = new ColumnHeaders(minWCell, maxWCell);
    JPanel container = new JPanel();
    JTextField box = new JTextField();
    box.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        features.editCell(activeCell, e.toString());
      }
    });
    box.setPreferredSize(new Dimension(CellView.CELL_SIZE.width * ((maxWCell - minWCell) + 1),
            CellView.CELL_SIZE.height));
    container.setLayout(new BoxLayout(container, BoxLayout.Y_AXIS));
    container.add(box);
    panel.setPreferredSize(new Dimension(CellView.CELL_SIZE.width * ((maxWCell - minWCell) + 1),
            CellView.CELL_SIZE.height));
    container.add(panel);
    container.setPreferredSize(new Dimension(CellView.CELL_SIZE.width * ((maxWCell - minWCell) + 1),
            2 * CellView.CELL_SIZE.height));
    content.setColumnHeaderView(container);
  }

  private void drawRows(JScrollPane content) {
    JViewport j = new JViewport();
    JPanel panel = new RowHeaders(minHCell, maxHCell);
    panel.setPreferredSize(new Dimension(CellView.CELL_SIZE.width,
            CellView.CELL_SIZE.height * (maxHCell - minHCell)));
    j.setPreferredSize(panel.getPreferredSize());
    j.setView(panel);
    content.setRowHeader(j);
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

  @Override
  public void addFeatures(Features f) {
    this.features = f;
  }

  private void makeCellActive(Coord c) {
    this.activeCell = c;
  }
}
