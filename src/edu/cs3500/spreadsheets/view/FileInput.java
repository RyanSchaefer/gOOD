package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

import javax.swing.*;

import edu.cs3500.spreadsheets.controller.Features;

public class FileInput extends JFrame implements IView {

  Features features;
  JTextField textBox;

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
