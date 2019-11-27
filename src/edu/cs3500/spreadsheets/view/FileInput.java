package edu.cs3500.spreadsheets.view;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import edu.cs3500.spreadsheets.controller.Features;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 * Represents a view to allow the user to enter in, and accept or reject, the desired name they
 * would like to save the file to.
 */
public class FileInput extends JFrame implements IView {

  private Features features;
  private JTextField textBox;

  FileInput() {
    this.setLayout(new FlowLayout());
    this.textBox = new JTextField();
    textBox.setPreferredSize(new Dimension(100, 30));
    JButton accept = new JButton("Accept");
    JButton cancel = new JButton("Cancel");

    accept.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        if (features != null) {
          try {
            features.save(textBox.getText());
            FileInput.this.setVisible(false);
          } catch (IOException exc) {
            JOptionPane.showMessageDialog(
                    FileInput.this,
                    "Could not save file.");
          }
        }
      }
    });

    cancel.addMouseListener(new MouseAdapter() {
      @Override
      public void mouseClicked(MouseEvent e) {
        FileInput.this.setVisible(false);
      }
    });

    this.add(textBox);
    this.add(accept);
    this.add(cancel);
    this.pack();
  }

  @Override
  public void renderSpreadsheet() {
    /*
   doesn't have a spreadsheet
     */
  }

  @Override
  public void makeVisible() {
    this.setVisible(true);
  }

  @Override
  public void addFeatures(Features f) {
    this.features = f;
  }
}
