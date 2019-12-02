package edu.cs3500.spreadsheets.view.provider;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.provider.Directions;
import edu.cs3500.spreadsheets.controller.provider.Features;
import edu.cs3500.spreadsheets.model.Coord;

/**
 * Represents a graphical interface of a spreadsheet.
 */
public class ViewControllableSpreadsheet extends ViewGUISpreadsheet {

  private InputField inputField;

  /**
   * Constructor for a controllable graphical interface of a spreadsheet.
   *
   * @param cellContents The contents of the spreadsheet.
   */
  public ViewControllableSpreadsheet(List<List<String>> cellContents) {
    super(cellContents);
    this.inputField = new InputField();
    this.add(this.inputField, BorderLayout.NORTH);
    this.setFocusable(true);
    this.requestFocus();
  }

  @Override
  public void addFeatures(Features features) {
    this.inputField.confirm.addActionListener(evt -> features.confirmButton(
            this.inputField.input.getText()));
    this.inputField.deny.addActionListener(evt -> features.denyButton());
    this.gridPanel.addMouseListener(new SpreadsheetMouseListener(features));
    this.addKeyListener(new SpreadsheetKeyboardListener(features));
  }

  @Override
  public String selectCell(Coord position, String rawContent) {
    this.requestFocus();
    this.gridPanel.setHighlight(position);
    this.gridPanel.repaint();
    this.inputField.input.setText(rawContent);
    return rawContent;
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
   * Represents the top banner input field of the GUI.
   */
  private static class InputField extends JPanel {
    Button confirm;
    Button deny;
    TextField input;

    /**
     * Constructor for the top banner input field of the GUI.
     */
    InputField() {
      super();
      this.setLayout(new FlowLayout());
      this.confirm = new Button();
      this.confirm.setLabel("✓");
      this.confirm.setPreferredSize(new Dimension(25, 25));
      this.deny = new Button();
      this.deny.setLabel("X");
      this.deny.setPreferredSize(new Dimension(25, 25));
      this.input = new TextField();
      this.input.setPreferredSize(new Dimension(400, 25));
      this.add(this.confirm);
      this.add(this.deny);
      this.add(this.input);
    }
  }

  /**
   * Handles any key presses on a spreadsheet.
   */
  private static class SpreadsheetKeyboardListener extends KeyAdapter {

    private Features features;

    /**
     * Constructor for a SpreadsheetKeyboardListener.
     *
     * @param features The controller features to use.
     */
    SpreadsheetKeyboardListener(Features features) {
      this.features = features;
    }

    @Override
    public void keyPressed(KeyEvent e) {
      if (e.getKeyChar() == KeyEvent.VK_DELETE) {
        features.deleteCell();
      } else if (e.getKeyChar() == KeyEvent.VK_UP || e.getKeyChar() == KeyEvent.VK_KP_UP
              || e.getKeyChar() == 'w') {
        features.shiftSelection(Directions.UP);
      } else if (e.getKeyChar() == KeyEvent.VK_DOWN || e.getKeyChar() == KeyEvent.VK_KP_DOWN
              || e.getKeyChar() == 's') {
        features.shiftSelection(Directions.DOWN);
      } else if (e.getKeyChar() == KeyEvent.VK_LEFT || e.getKeyChar() == KeyEvent.VK_KP_LEFT
              || e.getKeyChar() == 'a') {
        features.shiftSelection(Directions.LEFT);
      } else if (e.getKeyChar() == KeyEvent.VK_RIGHT || e.getKeyChar() == KeyEvent.VK_KP_RIGHT
              || e.getKeyChar() == 'd') {
        features.shiftSelection(Directions.RIGHT);
      }
    }
  }

  /**
   * Handles any mouse clicks on a spreadsheet.
   */
  private class SpreadsheetMouseListener extends MouseAdapter {
    private Features features;

    /**
     * Constructor for a SpreadsheetMouseListener.
     *
     * @param features The controller features to use.
     */
    SpreadsheetMouseListener(Features features) {
      this.features = features;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
      features.selectCell(e.getX(), e.getY());
    }
  }

}
