package edu.cs3500.spreadsheets.controller;

import edu.cs3500.spreadsheets.model.Coord;

public interface Features {

  void editCell(Coord c, String s);

  void saveSheet();

  void loadSheet(String sheet);

}
