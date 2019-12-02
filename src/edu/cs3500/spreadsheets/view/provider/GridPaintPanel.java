package edu.cs3500.spreadsheets.view.provider;

import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.List;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents the GUI spreadsheet panel.
 */
public class GridPaintPanel extends JPanel {

  private final int cellWidth = 140;
  private final int cellHeight = 20;

  int verticalOffset;
  int horizontalOffset;

  private int highlightX;
  private int highlightY;
  private Coord highlight;

  private List<List<String>> cellContents;

  /**
   * Given a spreadsheet file name, open the spreadsheet from it and create the grid.
   *
   * @param cellContents The contents of the spreadsheet.
   */
  GridPaintPanel(List<List<String>> cellContents) {
    super();

    this.verticalOffset = 0;
    this.horizontalOffset = 0;

    this.cellContents = cellContents;
    this.highlight = null;

    this.highlightX = 0;
    this.highlightY = 0;
  }

  /**
   * Sets the vertical scroll of the sheet.
   *
   * @param verticalOffset The desired vertical scroll.
   */
  void setVerticalOffset(int verticalOffset) {
    this.highlightY = this.highlightY + (this.verticalOffset - verticalOffset);

    this.verticalOffset = verticalOffset;

  }

  /**
   * Sets the horizontal scroll of the sheet.
   *
   * @param horizontalOffset The desired horizontal scroll.
   */
  void setHorizontalOffset(int horizontalOffset) {
    this.highlightX = this.highlightX + (this.horizontalOffset - horizontalOffset);

    this.horizontalOffset = horizontalOffset;
  }

  /**
   * Draw the upper left blank cell.
   *
   * @param g2d The graphic to draw on.
   */
  private void drawBlankCell(Graphics2D g2d) {
    this.drawSheetCell(g2d, Color.PINK, 0, 0, false, "");
  }

  /**
   * Draw the column headers.
   *
   * @param g2d     The graphic to draw on.
   * @param numCols The number of columns to draw.
   */
  private void drawColHeaders(Graphics2D g2d, int numCols) {
    for (int i = 0; i < numCols; i++) {
      String colName = Coord.colIndexToName(i + 1 + this.horizontalOffset);
      final int xCoord = (i + 1) * this.cellWidth;
      final int yCoord = 0;
      this.drawSheetCell(g2d, Color.PINK, xCoord, yCoord, true, colName);
    }
  }

  /**
   * Draw the row headers.
   *
   * @param g2d     The graphic to draw on.
   * @param numRows The number of rows to draw.
   */
  private void drawRowHeaders(Graphics2D g2d, int numRows) {
    for (int i = 0; i < numRows; i++) {
      final int xCoord = 0;
      final int yCoord = (i + 1) * this.cellHeight;
      this.drawSheetCell(g2d, Color.PINK, xCoord, yCoord, true,
              Integer.toString(i + 1 + this.verticalOffset));
    }
  }

  /**
   * Draw all the cells and their contents.
   *
   * @param g2d     The graphic to draw on.
   * @param numCols The number of columns to draw.
   * @param numRows The number of rows to draw.
   */
  private void drawAllCells(Graphics2D g2d, int numCols, int numRows) {
    for (int col = 0; col < numCols; col++) {
      for (int row = 0; row < numRows; row++) {
        String cellContents = this.getCellContents(col, row);
        final int xCoord = (col + 1) * this.cellWidth;
        final int yCoord = (row + 1) * this.cellHeight;

        this.drawSheetCell(g2d, Color.WHITE, xCoord, yCoord, false, cellContents);

      }
    }
  }

  /**
   * Retrieve the cells contents at a given row and column.
   *
   * @param col Zero indexed desired column.
   * @param row Zero indexed desired row.
   * @return The contents of that cell.
   */
  private String getCellContents(int col, int row) {
    int desiredCol = col + this.horizontalOffset;
    int desiredRow = row + this.verticalOffset;
    if (desiredCol < this.cellContents.size()) {
      List<String> foundCol = this.cellContents.get(desiredCol);
      if (desiredRow < foundCol.size()) {
        return foundCol.get(desiredRow);
      }
    }
    return "";
  }

  /**
   * Draw a grid cell at the given location with the given text and colors.
   *
   * @param g2d    The graphic to draw on.
   * @param fill   The fill color of the cell.
   * @param x      The x coordinate of the cell.
   * @param y      The y coordinate of the cell.
   * @param center Flag if text should be centered.
   * @param text   The text to draw on the cell.
   */
  private void drawSheetCell(Graphics2D g2d, Color fill, int x, int y, boolean center,
                             String text) {
    final int textWidth = 5;
    final int textHeight = 15;

    g2d.setColor(Color.BLACK);

    g2d.draw(new Rectangle2D.Double(x, y, this.cellWidth, this.cellHeight));
    g2d.setColor(fill);
    g2d.fillRect(x, y, this.cellWidth, this.cellHeight);
    g2d.setColor(Color.BLACK);

    int textX;
    int textY;
    if (center) {
      textX = x + (this.cellWidth / 2) + textWidth - (textWidth * text.length());
      textY = y + textHeight;
    } else {
      textX = x + textWidth;
      textY = y + textHeight;
    }
    g2d.drawString(text, textX, textY);
  }

  /**
   * Set the highlighted cell to a new position.
   *
   * @param position The new position of the highlighted cell.
   */
  void setHighlight(Coord position) {
    this.highlightX = 0;
    this.highlightY = 0;
    this.highlight = position;
  }

  /**
   * Draw the cell highlighted if it exists.
   *
   * @param g2d The graphic to draw on.
   */
  private void drawHighlight(Graphics2D g2d) {
    if (this.highlight != null) {
      int x = (this.highlight.col + this.highlightX) * this.cellWidth;
      int y = (this.highlight.row + this.highlightY) * this.cellHeight;
      if (x != 0 && y != 0) {
        g2d.setColor(Color.GREEN);
        g2d.draw(new Rectangle2D.Double(x, y, this.cellWidth, this.cellHeight));
      }

    }
  }

  @Override
  protected void paintComponent(Graphics g) {
    super.paintComponent(g);

    Graphics2D g2d = (Graphics2D) g;

    int numCols = (this.getWidth() / this.cellWidth);
    int numRows = (this.getHeight() / this.cellHeight);

    this.drawBlankCell(g2d);

    this.drawColHeaders(g2d, numCols);

    this.drawRowHeaders(g2d, numRows);

    this.drawAllCells(g2d, numCols, numRows);

    this.drawHighlight(g2d);
  }

  /**
   * Set the contents of the grid.
   *
   * @param update The cell contents.
   */
  void setContents(List<List<String>> update) {
    this.cellContents = update;
    this.repaint();
  }


}
