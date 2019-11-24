package edu.cs3500.spreadsheets.commandline;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;

import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.view.TextualView;

/**
 * The command to save one file to another (-in [file] -save [file]).
 */
public class SaveCommand implements CommandParser {

  @Override
  public String parse(WorksheetReader.WorksheetBuilder<IWorksheet> builder, String[] args) {
    if (args.length < 4) {
      return null;
    }
    if (args[0].equals("-in") &&
            args[1].matches(".+\\.gOOD") &&
            args[2].equals("-save") &&
            args[3].matches(".+\\.gOOD")) {
      try {
        IWorksheet model = WorksheetReader.read(
                builder,
                new FileReader(args[1]));
        PrintWriter writer = new PrintWriter(args[3]);
        new TextualView(model, writer).renderSpreadsheet();
        writer.close();
        return "File saved.\n Terminating...";
      } catch (IOException e) {
        return "Error reading file.";
      }
    }
    return null;
  }
}
