package edu.cs3500.spreadsheets.view;

import java.awt.*;
import java.io.IOException;
import java.util.List;

import javax.swing.*;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;

public class NoScrollView extends JFrame implements IView {

  IWorksheet model;
  Frame screen;

  public NoScrollView(IWorksheet model) {
    super();
    this.setTitle("gOOD");
    this.setSize(500, 500);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    this.setLayout(new GridBagLayout());
  }

  @Override
  public void renderChanges(List<Coord> cells) throws IOException {

  }

  @Override
  public void renderSpreadsheet() throws IOException {

  }
}
