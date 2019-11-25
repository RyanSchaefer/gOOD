package edu.cs3500.spreadsheets.commandline;

import java.io.FileReader;
import java.io.IOException;

import edu.cs3500.spreadsheets.model.BasicSlimWorksheet;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.ScrollView;

/**
 * Opens up the given file in a GUI (-in [file] -gui).
 */
public class GuiCommand implements CommandParser {
  @Override
  public String parse(WorksheetReader.WorksheetBuilder<IWorksheet> builder, String[] args) {
    if (args.length < 3) {
      return null;
    }
    if (args[0].equals("-in") &&
            args[1].matches(".+\\.gOOD") &&
            args[2].equals("-gui")) {
      try {
        IWorksheet model = WorksheetReader.read(
                builder,
                new FileReader(args[1]));
        new ScrollView(new BasicSlimWorksheet(model)).makeVisible();
        return "View shown. \nTerminating...";
      } catch (IOException e) {
        return "Error reading file.";
      }
    }
    return null;
  }
}
