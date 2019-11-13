package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.formula.functions.ErrorFunction;

public class ScrollView extends JFrame implements IView {

  private IWorksheet model;
  private Features features;

  private int minWCell = 1;
  private int maxWCell = 10;
  private int minHCell = 1;
  private int maxHCell = 20;

  private Coord activeCell;

  private JScrollPane scrollPane;
  private Point lastP = new Point(0, 0);

  public ScrollView(IWorksheet model) {
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
        Dimension newSize = e.getComponent().getSize();
        maxHCell = minHCell + (newSize.height / CellView.CELL_SIZE.height) + 2;
        maxWCell = minWCell + (newSize.width / CellView.CELL_SIZE.width) + 2;
        if (oldMaxHCell != maxHCell || oldMaxWCell != maxWCell) {
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
        CellView view;
        try {
          view = new CellView(coord, model.evaluateCellAt(minWCell + x,
                  minHCell + y), coord.equals(this.activeCell));
        } catch (IllegalArgumentException e) {
          view = new ErrorCell(coord, new ErrorFunction(model.getCellAt(coord.col, coord.row)),
                  coord.equals(this.activeCell));
        }
        view.addMouseListener(new MouseAdapter() {
          @Override
          public void mouseClicked(MouseEvent e) {
            ScrollView.this.makeCellActive(coord);
            ScrollView.this.renderSpreadsheet();
          }
        });
        content.add(view, c);
      }
    }
    JScrollPane pane = new JScrollPane(content);
    this.scrollPane = pane;
    pane.setLayout(new ScrollPaneLayout());
    drawColumns(pane);
    drawRows(pane);
    pane
            .getHorizontalScrollBar()
            .addAdjustmentListener(e -> {
              int oldMaxHCell = maxHCell;
              int current = this.getWidth() + e.getAdjustable().getValue();
              int max = e.getAdjustable().getMaximum();
              if ((max - current) / CellView.CELL_SIZE.width < 1
                      && lastP.x < e.getAdjustable().getValue()) {
                maxWCell += 1;
                minWCell += 1;
                e.getAdjustable().setValue(CellView.CELL_SIZE.width);
                lastP.x = e.getAdjustable().getValue();
                renderSpreadsheet();
              } else if (e.getAdjustable().getValue() / CellView.CELL_SIZE.width < 1 &&
                      minWCell != 1 && lastP.x > e.getAdjustable().getValue()) {
                maxWCell -= 1;
                minWCell -= 1;
                e.getAdjustable().setValue(1);
                lastP.x = e.getAdjustable().getValue();
                renderSpreadsheet();
              }
              lastP.x = e.getAdjustable().getValue();
            });
    pane
            .getVerticalScrollBar()
            .addAdjustmentListener(e -> {
              int oldMaxHCell = maxHCell;
              int current = this.getHeight() + e.getAdjustable().getValue();
              int max = e.getAdjustable().getMaximum();
              if ((max - current) / CellView.CELL_SIZE.height < 1
                      && lastP.y < e.getAdjustable().getValue()) {
                maxHCell += 1;
                minHCell += 1;
                e.getAdjustable().setValue(CellView.CELL_SIZE.height);
                lastP.y = e.getAdjustable().getValue();
                renderSpreadsheet();
              } else if (e.getAdjustable().getValue() / CellView.CELL_SIZE.height < 1 &&
                      minHCell != 1 && lastP.y > e.getAdjustable().getValue()) {
                maxHCell -= 1;
                minHCell -= 1;
                e.getAdjustable().setValue(1);
                lastP.y = e.getAdjustable().getValue();
                renderSpreadsheet();
              }
              lastP.y = e.getAdjustable().getValue();
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
