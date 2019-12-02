package edu.cs3500.spreadsheets.view.provider;

import java.awt.*;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.util.List;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.provider.Features;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents a graphical interface of a spreadsheet.
 */
public class ViewGUISpreadsheet extends JFrame implements ViewSpreadsheetInterface {

  GridPaintPanel gridPanel;
  List<List<String>> cellContents;

  private JScrollBar eastScrollBar;
  private JScrollBar southScrollBar;

  /**
   * Constructor for a graphical interface of a spreadsheet.
   *
   * @param cellContents The contents of the spreadsheet.
   */
  public ViewGUISpreadsheet(List<List<String>> cellContents) {
    super();
    this.setTitle("Spreadsheet");
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setMinimumSize(new Dimension(500, 500));
    this.setLayout(new BorderLayout());

    this.cellContents = cellContents;
    gridPanel = new GridPaintPanel(cellContents);
    this.add(gridPanel, BorderLayout.CENTER);
    eastScrollBar = new JScrollBar(Adjustable.VERTICAL, 0, 10, 0, 100);
    this.add(eastScrollBar, BorderLayout.EAST);
    southScrollBar = new JScrollBar(Adjustable.HORIZONTAL);
    this.add(southScrollBar, BorderLayout.SOUTH);

    eastScrollBar.addAdjustmentListener(new VerticalAdjustmentListener());
    southScrollBar.addAdjustmentListener(new HorizontalAdjustmentListener());

    //Display the window.
    this.pack();
  }

  @Override
  public void render() {
    this.setVisible(true);
  }

  @Override
  public void addFeatures(Features features) {
    // Cannot control regular GUI spreadsheet.
  }

  @Override
  public String selectCell(Coord position, String rawContent) {
    String results;
    int col = (position.col - 1) + this.gridPanel.horizontalOffset;
    int row = (position.row - 1) + this.gridPanel.verticalOffset;
    if (col <= this.cellContents.size()) {
      if (row <= this.cellContents.get(col).size()) {
        results = this.cellContents.get(col).get(row);
        return results;
      }
    }
    results = "";
    return results;
  }

  @Override
  public void updateView(List<List<String>> update) {
    this.cellContents = update;
    this.gridPanel.setContents(update);
  }

  @Override
  public int getXScroll() {
    return this.gridPanel.horizontalOffset;
  }

  @Override
  public int getYScroll() {
    return this.gridPanel.verticalOffset;
  }

  /**
   * Represents the adjustment listener for vertical scrolling.
   */
  private class VerticalAdjustmentListener implements AdjustmentListener {
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
      if (eastScrollBar.getValue() == eastScrollBar.getMaximum() - 10) {
        eastScrollBar.setMaximum(eastScrollBar.getMaximum() + 1);
      }

      gridPanel.setVerticalOffset(e.getValue());
      gridPanel.repaint();
    }
  }

  /**
   * Represents the adjustment listener for horizontal scrolling.
   */
  private class HorizontalAdjustmentListener implements AdjustmentListener {
    @Override
    public void adjustmentValueChanged(AdjustmentEvent e) {
      if (southScrollBar.getValue() == southScrollBar.getMaximum() - 10) {
        southScrollBar.setMaximum(southScrollBar.getMaximum() + 1);
      }

      gridPanel.setHorizontalOffset(e.getValue());
      gridPanel.repaint();
    }
  }

}
