package edu.cs3500.spreadsheets.commandLine;

import java.io.FileReader;
import java.io.IOException;

import edu.cs3500.spreadsheets.controller.SheetController;
import edu.cs3500.spreadsheets.model.BasicSlimWorksheet;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.EditableView;

/**
 * Opens a saved spreadsheet in an editable gui (-in [file] -edit).
 */
public class EditableGUICommand implements CommandParser {
  @Override
  public String parse(WorksheetReader.WorksheetBuilder<IWorksheet> builder, String[] args) {
    if (args.length < 3) {
      return null;
    }
    if (args[0].equals("-in") &&
            args[1].matches(".+\\.gOOD") &&
            args[2].equals("-edit")) {
      try {
        IWorksheet model = WorksheetReader.read(
                builder,
                new FileReader(args[1]));
        new SheetController(model, new EditableView(new BasicSlimWorksheet(model))).go();
        return "Controller Running\nTerminating...";
      } catch (IOException e) {
        return "Error reading file.";
      }
    }
    return null;
  }
}
