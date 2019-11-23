package edu.cs3500.spreadsheets.commandLine;

import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.formula.value.Value;
import edu.cs3500.spreadsheets.model.formula.value.visitors.EvalPrintVisitor;

/**
 * Evaluates a specific cell in a file (-in [file] -eval [cell]).
 */
public class EvalCommand implements CommandParser {
  @Override
  public String parse(WorksheetReader.WorksheetBuilder<IWorksheet> builder, String[] args) {
    if (args.length < 4) {
      return null;
    }
    if (args[0].equals("-in")
            && args[1].matches(".+\\.gOOD")
            && args[2].equals("-eval")
            && args[3].matches("^([A-Z]+)([0-9]+)$")
    ) {
      try {
        Pattern r = Pattern.compile("^([A-Z]+)([0-9]+)$");
        Matcher m = r.matcher(args[3]);
        m.find();
        Coord cell = new Coord(Coord.colNameToIndex(m.group(1)), Integer.parseInt(m.group(2)));

        IWorksheet model = WorksheetReader.read(
                builder, new FileReader(args[1]));

        if (model.documentFreeOfErrors()) {
          Value eval = model.evaluateCellAt(cell.col, cell.row);
          if (eval == null) {
            return "";
          } else {
            return eval.accept(new EvalPrintVisitor());
          }
        } else {
          StringBuilder res = new StringBuilder();
          for (Coord c : model.allActiveCells()) {
            try {
              model.evaluateCellAt(c.col, c.row);
            } catch (IllegalArgumentException e) {
              res.append("Error in cell ").append(
                      Coord.colIndexToName(c.col))
                      .append(c.row)
                      .append(" : ")
                      .append(e.getMessage())
                      .append("\n");
            }
          }
          return res.toString();
        }
      } catch (IOException e) {
        return "Error reading file.";
      }
    }
    return null;
  }
}
