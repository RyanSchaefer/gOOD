package edu.cs3500.spreadsheets;

import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.Formula.functions.AbstractFunction;
import edu.cs3500.spreadsheets.model.Formula.functions.LessThanFunc;
import edu.cs3500.spreadsheets.model.Formula.functions.LowerCase;
import edu.cs3500.spreadsheets.model.Formula.functions.ProductFunc;
import edu.cs3500.spreadsheets.model.Formula.functions.SumFunc;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;

/**
 * The main class for our program.
 */
public class BeyondGood {
  /**
   * The main entry point.
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    Map<String, AbstractFunction> functionsSupported = new HashMap<>();
    functionsSupported.put("lowercase", new LowerCase());
    functionsSupported.put("<", new LessThanFunc());
    functionsSupported.put("product", new ProductFunc());
    functionsSupported.put("sum", new SumFunc());

    if (args[0].equals("-in")
            && args[2].equals("-eval")
            && args[3].matches("^([A-Z]+)([0-9]+)$")) {
      try {
        Pattern r = Pattern.compile("^([A-Z]+)([0-9]+)$");
        Matcher m = r.matcher(args[3]);
        m.find();
        Coord cell = new Coord(Coord.colNameToIndex(m.group(1)), Integer.parseInt(m.group(2)));

        IWorksheet model = WorksheetReader.read(new BasicWorksheet.BasicWorksheetBuilder(functionsSupported),
                new FileReader(args[1]));

        if (model.documentFreeOfErrors()) {
          System.out.print(model.evaluateCellAt(cell.col, cell.row));
        } else {
          for (Coord c : model.allActiveCells()) {
            try {
              model.evaluateCellAt(c.col, c.row);
            } catch (IllegalArgumentException e) {
              System.out.print("Error in cell " +
                      Coord.colIndexToName(c.col) +
                      c.row + " : "
                      + e.getMessage() + "\n");
            }
          }
        }
      } catch (IOException e) {
        System.out.print("Error reading file.");
      }
    } else {
      System.out.print("Malformed comamand-line input.");
    }
  }
}
