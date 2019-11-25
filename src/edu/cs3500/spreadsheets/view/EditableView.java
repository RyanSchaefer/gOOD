package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ICell;
import edu.cs3500.spreadsheets.model.SlimWorksheet;

/**
 * A {@link IView} that has the facilities for editing built into it.
 */
public class EditableView extends ScrollView {

  private JTextField textBox;
  private SlimWorksheet model;
  private boolean textBoxFocused;

  /**
   * Represents a spreadsheet that is editable through the implementation of features.
   *
   * @param model the spreadsheet model
   */
  public EditableView(SlimWorksheet model) {
    super(model);
    this.model = model;
    ScrollView s = new ScrollView(model);
    this.setTitle("gOOD");
    this.setFocusable(true);

    JTextField box = new JTextField();

    this.textBox = new JTextField();
    box.setPreferredSize(new Dimension(CellView.CELL_SIZE.width
            * ((maxWCell - minWCell - 3) + 1),
            CellView.CELL_SIZE.height));

    this.setSize(new Dimension(s.getWidth(),
            s.getHeight()));

    this.textBox.addFocusListener(new FocusListener() {
      @Override
      public void focusGained(FocusEvent e) {
        textBoxFocused = true;
      }

      @Override
      public void focusLost(FocusEvent e) {
        EditableView.this.requestFocus();
        textBoxFocused = false;
      }
    });

    this.addKeyListener(new KeyAdapter() {
      @Override
      public void keyTyped(KeyEvent e) {
        super.keyTyped(e);
        if (features != null && !textBoxFocused) {
          if (e.getExtendedKeyCode() == KeyEvent.VK_BACK_SPACE) {
            features.deleteCellContents(activeCell);
          } else if (e.getExtendedKeyCode() == KeyEvent.VK_D) {
            activeCell = new Coord(activeCell.col + 1, activeCell.row);
            renderSpreadsheet();
            makeVisible();
          } else if (e.getExtendedKeyCode() == KeyEvent.VK_A) {
            if (activeCell.col != 0) {
              activeCell = new Coord(activeCell.col - 1, activeCell.row);
              renderSpreadsheet();
              makeVisible();
            }
          } else if (e.getExtendedKeyCode() == KeyEvent.VK_S) {
            activeCell = new Coord(activeCell.col, activeCell.row + 1);
            renderSpreadsheet();
            makeVisible();
          } else if (e.getExtendedKeyCode() == KeyEvent.VK_W) {
            if (activeCell.row != 0) {
              activeCell = new Coord(activeCell.col, activeCell.row - 1);
              renderSpreadsheet();
              makeVisible();
            }
          }
        }
      }
    });

    JMenuBar menuBar = new JMenuBar();
    JMenu menu = new JMenu("Operations");
    JMenuItem save = new JMenuItem("Save");
    menu.add(save);
    save.addActionListener((ActionEvent e) -> {
      if (features != null) {
        FileInput file = new FileInput();
        file.addFeatures(features);
        file.makeVisible();
      }
    });
    menuBar.add(menu);
    this.setJMenuBar(menuBar);
    menu.setVisible(true);
  }

  @Override
  protected void drawColumns(JScrollPane content) {
    JViewport j = new JViewport();
    JPanel panel = new ColumnHeaders(minWCell, maxWCell);
    JPanel container = new JPanel();
    JButton accept = new JButton("Accept");
    JButton reject = new JButton("Reject");
    reject.addActionListener((f) -> {
      EditableView.this.setBoxText();
    });
    accept.addActionListener((f) -> {
      if (features != null) {
        features.editCell(this.activeCell, this.textBox.getText());
      }
    });
    EditableView.this.setBoxText();
    accept.setPreferredSize(CellView.CELL_SIZE);
    reject.setPreferredSize(CellView.CELL_SIZE);
    JLabel push = new JLabel("gOOD™");
    push.setPreferredSize(CellView.CELL_SIZE);

    container.setLayout(new BoxLayout(container, BoxLayout.X_AXIS));
    container.add(push);
    container.add(accept);
    container.add(reject);
    container.add(this.textBox);
    panel.setPreferredSize(
            new Dimension(CellView.CELL_SIZE.width * ((maxWCell - minWCell) + 1),
                    CellView.CELL_SIZE.height));
    container.setPreferredSize(new Dimension(CellView.CELL_SIZE.width * (
            (maxWCell - minWCell) + 1),
            CellView.CELL_SIZE.height));
    JPanel container2 = new JPanel();
    container2.setLayout(new BoxLayout(container2, BoxLayout.Y_AXIS));
    container2.setPreferredSize(
            new Dimension(CellView.CELL_SIZE.width * ((maxWCell - minWCell) + 1),
                    2 * CellView.CELL_SIZE.height));
    container2.add(container);
    container2.add(panel);
    content.setColumnHeaderView(container2);
  }

  private void setBoxText() {
    if (this.activeCell != null) {
      ICell cell = this.model.getCellAt(this.activeCell.col, this.activeCell.row);
      if (cell != null) {
        this.textBox.setText(cell.toString());
      } else {
        this.textBox.setText("");
      }
    }
  }

}
