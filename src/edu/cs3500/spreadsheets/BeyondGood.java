package edu.cs3500.spreadsheets;

import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.formula.Formula;
import edu.cs3500.spreadsheets.model.formula.functions.IFunction;
import edu.cs3500.spreadsheets.model.formula.functions.LessThanFunc;
import edu.cs3500.spreadsheets.model.formula.functions.LowerCase;
import edu.cs3500.spreadsheets.model.formula.functions.ProductFunc;
import edu.cs3500.spreadsheets.model.formula.functions.SumFunc;
import edu.cs3500.spreadsheets.view.ScrollView;
import edu.cs3500.spreadsheets.view.TextualView;

/**
 * The main class for our program.
 */
public class BeyondGood {
  /**
   * The main entry point.
   * @param args any command-line arguments
   */
  public static void main(String[] args) {
    Map<String, IFunction> functionsSupported = new HashMap<>();
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

        IWorksheet model = WorksheetReader.read(
                new BasicWorksheet.BasicWorksheetBuilder(functionsSupported),
                new FileReader(args[1]));

        if (model.documentFreeOfErrors()) {
          Formula eval = model.evaluateCellAt(cell.col, cell.row);
          if (eval == null) {
            System.out.print("");
          } else {
            System.out.print(eval.toString());
          }
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
    } else if (args[0].equals("-in") && args[2].equals("-save") &&
            args[3].matches(".+\\.gOOD")) {
      try {
        IWorksheet model = WorksheetReader.read(
                new BasicWorksheet.BasicWorksheetBuilder(functionsSupported),
                new FileReader(args[1]));
        PrintWriter writer = new PrintWriter(args[3]);
        new TextualView(model, writer).renderSpreadsheet();
        writer.close();
      } catch (IOException e) {
        System.out.println("Error reading file.");
      }
    } else if (args[0].equals("-in") &&
            args[2].equals("-gui")) {
      try {
        IWorksheet model = WorksheetReader.read(
                new BasicWorksheet.BasicWorksheetBuilder(functionsSupported),
                new FileReader(args[1]));
        new ScrollView(model).makeVisible();
      } catch (IOException e) {
        System.out.println("Error reading file.");
      }
    } else if (args[0].equals("-gui")) {
      IWorksheet model = new BasicWorksheet
              .BasicWorksheetBuilder(functionsSupported).createWorksheet();
      new ScrollView(model).makeVisible();
    } else {
      System.out.print("Malformed comamand-line input.");
    }
  }
}
