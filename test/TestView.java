import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.formula.functions.IFunction;
import edu.cs3500.spreadsheets.model.formula.functions.LessThanFunc;
import edu.cs3500.spreadsheets.model.formula.functions.LowerCase;
import edu.cs3500.spreadsheets.model.formula.functions.ProductFunc;
import edu.cs3500.spreadsheets.model.formula.functions.SumFunc;
import edu.cs3500.spreadsheets.view.IView;
import edu.cs3500.spreadsheets.view.TextualView;

import static org.junit.Assert.assertEquals;

/**
 * Tests to make sure the view is rendering and saving properly.
 */
public class TestView {

  private Map<String, IFunction> functionsSupported = new HashMap<>();

  private void setupFunctions() {
    functionsSupported.put("lowercase", new LowerCase());
    functionsSupported.put("<", new LessThanFunc());
    functionsSupported.put("product", new ProductFunc());
    functionsSupported.put("sum", new SumFunc());
  }

  private IWorksheet model(String file) {
    setupFunctions();
    try {
      return WorksheetReader
              .read(new BasicWorksheet.BasicWorksheetBuilder(functionsSupported),
                      new FileReader(new File("test/" + file)));

    } catch (IOException e) {
      e.printStackTrace();
      throw new RuntimeException();
    }
  }

  @Test
  public void testInSameAsOut() throws IOException {
    IWorksheet model = model("test1.gOOD");
    PrintWriter writer = new PrintWriter("test/testOut.gOOD");
    IView view = new TextualView(model, writer);
    view.renderSpreadsheet();
    writer.close();

    IWorksheet model2 = model("testOut.gOOD");

    for (Coord c : model2.allActiveCells()) {
      assertEquals(model.getCellAt(c.col, c.row), model2.getCellAt(c.col, c.row));
    }
  }

  @Test(expected = AssertionError.class)
  public void testInNotSameAsOutWithChange() throws IOException {
    IWorksheet model = model("test1.gOOD");
    PrintWriter writer = new PrintWriter("test/testOut.gOOD");
    IView view = new TextualView(model, writer);
    view.renderSpreadsheet();
    writer.close();

    IWorksheet model2 = model("testOut.gOOD");
    model2.changeCellAt(100000, 10000, "5");

    for (Coord c : model2.allActiveCells()) {
      assertEquals(model.getCellAt(c.col, c.row), model2.getCellAt(c.col, c.row));
    }
  }

  @Test
  public void testInSameAsOutWithError() throws IOException {
    IWorksheet model = model("test2.gOOD");
    PrintWriter writer = new PrintWriter("test/testOut2.gOOD");
    IView view = new TextualView(model, writer);
    view.renderSpreadsheet();
    writer.close();

    IWorksheet model2 = model("testOut2.gOOD");

    for (Coord c : model2.allActiveCells()) {
      assertEquals(model.getCellAt(c.col, c.row), model2.getCellAt(c.col, c.row));
    }
  }

  @Test
  public void testEdit() {
  }

}
