package edu.cs3500.spreadsheets.commandline;

import java.io.FileReader;
import java.io.IOException;

import edu.cs3500.spreadsheets.controller.provider.ControllerToProvider;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.provider.ViewControllableSpreadsheet;

public class ProviderCommand implements CommandParser {
  @Override
  public String parse(WorksheetReader.WorksheetBuilder<IWorksheet> builder, String[] args) {
    if (args.length == 3 && args[0].equals("-in")
            && args[1].matches(".+\\.gOOD")
            && args[2].equals("-provider")) {
      try {
        IWorksheet model = WorksheetReader.read(
                builder,
                new FileReader(args[1]));
        new ControllerToProvider(
                new ViewControllableSpreadsheet(null),
                model).displayView();
        return "Controller Running\nTerminating...";
      } catch (IOException e) {
        return "Error reading file.";
      }
    }
    return null;
  }
}
