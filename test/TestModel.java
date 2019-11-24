import org.junit.Test;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.BasicWorksheet.BasicWorksheetBuilder;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.model.formula.functions.IFunction;
import edu.cs3500.spreadsheets.model.formula.functions.LessThanFunc;
import edu.cs3500.spreadsheets.model.formula.functions.LowerCase;
import edu.cs3500.spreadsheets.model.formula.functions.ProductFunc;
import edu.cs3500.spreadsheets.model.formula.functions.SumFunc;
import edu.cs3500.spreadsheets.model.formula.value.visitors.EvalPrintVisitor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;


/**
 * The tests for the model we are implementing.
 */
public abstract class TestModel {

  abstract IWorksheet model(String file);


  /**
   * Tests with a basic model.
   */
  public static class TestWithBasic extends TestModel {

    Map<String, IFunction> functionsSupported = new HashMap<>();

    private void setupFunctions() {
      functionsSupported.put("lowercase", new LowerCase());
      functionsSupported.put("<", new LessThanFunc());
      functionsSupported.put("product", new ProductFunc());
      functionsSupported.put("sum", new SumFunc());
    }

    @Override
    IWorksheet model(String file) {
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
  }

  @Test
  public void createEmptySpreadsheet() {
    BasicWorksheetBuilder builder = new BasicWorksheet.BasicWorksheetBuilder(new HashMap<>());

    // Creates an empty spreadsheet
    IWorksheet sheet = builder.createWorksheet();

    // sheet.allActiveCells(),size() = 0, this confirms that no cells in the spreadsheet have
    // values stored in them yet
    assertEquals(0, sheet.allActiveCells().size());
  }

  @Test
  public void createSpreadsheetWithValues() {
    BasicWorksheetBuilder builder = new BasicWorksheet.BasicWorksheetBuilder(new HashMap<>());

    // Creates an empty spreadsheet
    IWorksheet sheet = builder.createWorksheet();

    // spreadsheet is currently empty
    assertEquals(0, sheet.allActiveCells().size());

    sheet.changeCellAt(1, 1, "7");
    sheet.changeCellAt(1, 2, "8");
    sheet.changeCellAt(1, 3, "true");
    sheet.changeCellAt(1, 4, "\"test\"");
    sheet.changeCellAt(2, 1, ("=(SUM A1 A2)"));

    // spreadsheet now has values
    assertEquals(5, sheet.allActiveCells().size());

    // confirm all cells added to spreadsheet are actually there
    assertEquals("7", sheet.getCellAt(1, 1).toString());
    assertEquals("8", sheet.getCellAt(1, 2).toString());
    assertEquals("true", sheet.getCellAt(1, 3).toString());
    assertEquals("\"test\"", sheet.getCellAt(1, 4).toString());
    assertEquals("=(SUM A1 A2)", sheet.getCellAt(2, 1).toString());
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCellAtInvalidColumn() {
    IWorksheet sheet = model("test1.gOOD");
    // Column must be > 0
    sheet.getCellAt(0, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void getCellAtInvalidRow() {
    IWorksheet sheet = model("test1.gOOD");
    // Row must be > 0
    sheet.getCellAt(5, 0);
  }

  @Test
  public void getCellValue() {
    IWorksheet sheet = model("test1.gOOD");
    // If the cell at the given coordinates is null:
    assertNull(sheet.getCellAt(123, 123));
    // If the cell at the given coordinates is NOT null:
    assertEquals("3", sheet.getCellAt(1, 1).toString());
    assertEquals("4", sheet.getCellAt(2, 1).toString());
    assertEquals("9", sheet.getCellAt(3, 1).toString());
    assertEquals("12", sheet.getCellAt(4, 1).toString());
    assertEquals("=(PRODUCT (SUM C1 A1) (SUM C1 A1))",
            sheet.getCellAt(1, 2).toString());
    assertEquals("=(PRODUCT (SUM D1 B1) (SUM D1 B1))",
            sheet.getCellAt(2, 2).toString());
    assertEquals("=(< A3 10)", sheet.getCellAt(2, 3).toString());
    assertEquals("=(LOWERCASE \"TEST\")", sheet.getCellAt(1, 4).toString());
  }

  @Test
  public void changeCellValue() {
    IWorksheet sheet = model("test1.gOOD");
    assertEquals(String.format("%f", 3.0), sheet.evaluateCellAt(1,
        1).accept(new EvalPrintVisitor()));
    sheet.changeCellAt(1, 1, "5");
    assertEquals(String.format("%f", 5.0), sheet.evaluateCellAt(1,
        1).accept(new EvalPrintVisitor()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void evaluateCellWithError() {
    IWorksheet sheet = model("test2.gOOD");
    // The cell at the given coordinates has a circular reference error
    sheet.evaluateCellAt(2, 2);
  }

  @Test(expected = IllegalArgumentException.class)
  public void evaluateCellWithError2() {
    IWorksheet sheet = model("test2.gOOD");
    // The cell at the given coordinates DOES NOT directly contain an error, however there is
    // a circular reference error within the function that makes up the cell
    sheet.evaluateCellAt(2, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void evaluateCellInvalidColumn() {
    IWorksheet sheet = model("test2.gOOD");
    // Column must be > 0
    sheet.evaluateCellAt(0, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void evaluateCellInvalidRow() {
    IWorksheet sheet = model("test2.gOOD");
    // Row must be > 0
    sheet.evaluateCellAt(4, 0);
  }

  @Test
  public void evalCell() {
    IWorksheet sheet = model("test1.gOOD");
    // If the cell at the given coordinates is null:
    assertNull(null, sheet.evaluateCellAt(123, 123));
    // Evaluates cell's that are numbers
    assertEquals(String.format("%f", 3.0), sheet.evaluateCellAt(1,
        1).accept(new EvalPrintVisitor()));
    assertEquals(String.format("%f", 144.0), sheet.evaluateCellAt(1,
        2).accept(new EvalPrintVisitor()));
  }

  @Test
  public void evalCell2() {
    IWorksheet sheet = model("test3.gOOD");
    // Evaluates a cell that is a String
    assertEquals("\"hello\"", sheet.evaluateCellAt(1,
        1).accept(new EvalPrintVisitor()));
    // Evaluates a cell that is a boolean
    assertEquals("true", sheet.evaluateCellAt(1,
        2).accept(new EvalPrintVisitor()));
  }

  @Test
  public void evalCellWithFormulas() {
    IWorksheet sheet = model("test3.gOOD");
    // Evaluates a cell that is a SUM function
    assertEquals(String.format("%f", 6.0), sheet.evaluateCellAt(2,
        4).accept(new EvalPrintVisitor()));
    assertEquals(String.format("%f", 4.0), sheet.evaluateCellAt(2,
        7).accept(new EvalPrintVisitor()));
    // Evaluates a cell that is a PRODUCT function
    assertEquals(String.format("%f", 2.0), sheet.evaluateCellAt(2,
        5).accept(new EvalPrintVisitor()));
    // Evaluates a cell that is a PRODUCT function with an incorrect type within it
    assertEquals(String.format("%f", 9.0), sheet.evaluateCellAt(2,
        8).accept(new EvalPrintVisitor()));
    // Evaluates a cell that is a < function
    assertEquals("false", sheet.evaluateCellAt(2,
        6).accept(new EvalPrintVisitor()));
    // Evaluates a cell that is a LOWERCASE function
    assertEquals("\"test\"", sheet.evaluateCellAt(1,
        5).accept(new EvalPrintVisitor()));
  }

  @Test
  public void verifyValidDocument() {
    IWorksheet sheet = model("test3.gOOD");
    assertTrue(sheet.documentFreeOfErrors());
  }

  @Test
  public void circularReferenceCausesErrorWithSheet() {
    IWorksheet sheet = model("test4.gOOD");
    assertFalse(sheet.documentFreeOfErrors());
  }

  @Test
  public void changeCellToCircularCausesErrors() {
    IWorksheet sheet = model("test1.gOOD");
    assertTrue(sheet.documentFreeOfErrors());
    sheet.changeCellAt(1, 1, "=A1");
    assertFalse(sheet.documentFreeOfErrors());
  }

  @Test
  public void threeCircularCausesErrors() {
    IWorksheet worksheet = model("empty.gOOD");
    worksheet.changeCellAt(1, 1, "=B1");
    worksheet.changeCellAt(2, 1, "=C1");
    worksheet.changeCellAt(3, 1, "=A1");
    assertFalse(worksheet.documentFreeOfErrors());
  }

  @Test
  public void changeCellToFixCircularErrors() {
    IWorksheet sheet = model("test2.gOOD");
    assertFalse(sheet.documentFreeOfErrors());
    sheet.changeCellAt(1, 1, "3");
    sheet.changeCellAt(2, 2, "3");
    assertTrue(sheet.documentFreeOfErrors());
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeCellToTwoCircular() {
    IWorksheet sheet = model("test1.gOOD");
    sheet.changeCellAt(1, 1, "=A1");
    sheet.changeCellAt(0, 0, "=B2");
    sheet.evaluateCellAt(1, 1);
  }

  @Test
  public void testDefaultValuesAre0() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "=(SUM)");
    sheet.changeCellAt(1, 2, "=(PRODUCT)");
    assertEquals(String.format("%f", 0.0), sheet.evaluateCellAt(1, 1)
            .accept(new EvalPrintVisitor()));
    assertEquals(String.format("%f", 0.0), sheet.evaluateCellAt(1, 2)
            .accept(new EvalPrintVisitor()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void testFunctionRequired() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "=()");
    assertEquals("=()", sheet.getCellAt(1, 1).toString());
    sheet.evaluateCellAt(1, 1);
  }

  @Test
  public void testBlank() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, null);
    assertNull(sheet.getCellAt(1, 1));
    assertNull(sheet.evaluateCellAt(1, 1));
  }

  @Test
  public void testNumeric() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "4");
    assertEquals("4", sheet.getCellAt(1, 1).toString());
    assertEquals(String.format("%f", 4.0), sheet.evaluateCellAt(1, 1)
            .accept(new EvalPrintVisitor()));
  }

  @Test
  public void testBoolean() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "true");
    assertEquals("true", sheet.getCellAt(1, 1).toString());
    assertEquals("true", sheet.evaluateCellAt(1, 1)
            .accept(new EvalPrintVisitor()));
  }

  @Test
  public void testString() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "\"test\"");
    assertEquals("\"test\"", sheet.getCellAt(1, 1).toString());
    assertEquals("\"test\"", sheet.evaluateCellAt(1, 1)
            .accept(new EvalPrintVisitor()));
  }

  @Test
  public void testSum() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "=(sum 4 4)");
    assertEquals("=(sum 4 4)", sheet.getCellAt(1, 1).toString());
    assertEquals(String.format("%f", 8.0), sheet.evaluateCellAt(1, 1)
            .accept(new EvalPrintVisitor()));
  }

  @Test
  public void testProduct() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "=(product 4 4)");
    assertEquals("=(product 4 4)", sheet.getCellAt(1, 1).toString());
    assertEquals(String.format("%f", 16.0), sheet.evaluateCellAt(1, 1)
            .accept(new EvalPrintVisitor()));
  }

  @Test
  public void testLessThan() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "=(< 3 4)");
    assertEquals("=(< 3 4)", sheet.getCellAt(1, 1).toString());
    assertEquals("true", sheet.evaluateCellAt(1, 1)
            .accept(new EvalPrintVisitor()));
  }

  @Test
  public void testLowerCase() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "=(lowercase \"TEST\")");
    assertEquals("=(lowercase \"TEST\")", sheet.getCellAt(1, 1).toString());
    assertEquals("\"test\"", sheet.evaluateCellAt(1, 1)
            .accept(new EvalPrintVisitor()));
  }

  @Test
  public void sameCell2Times() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "3");
    sheet.changeCellAt(1, 2, "=(sum A1 A1)");
    assertEquals(String.format("%f", 6.0), sheet.evaluateCellAt(1, 2)
            .accept(new EvalPrintVisitor()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeTypeMismatch1() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "=(< 2.2 \"test\")");
    sheet.evaluateCellAt(1, 1);
  }

  @Test
  public void changingCellPropagatesChanges() {
    IWorksheet sheet = model("test2.gOOD");
    assertEquals(String.format("%f", 163840.0), sheet.evaluateCellAt(1, 18)
            .accept(new EvalPrintVisitor()));
    sheet.changeCellAt(1, 1, "3");
    sheet.changeCellAt(1, 2, "5");
    assertEquals(String.format("%f", 262144.0), sheet.evaluateCellAt(1, 18)
            .accept(new EvalPrintVisitor()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeTypeMismatch2() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "=(< 3.5 false)");
    sheet.evaluateCellAt(1, 1);
  }

  @Test
  public void changeTypeMismatch4() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "=(PRODUCT 4 true)");
    assertEquals(String.format("%f", 4.0), sheet.evaluateCellAt(1, 1)
            .accept(new EvalPrintVisitor()));
  }

  @Test
  public void changeTypeMismatch5() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "=(PRODUCT 4 true)");
    assertEquals(String.format("%f", 4.0), sheet.evaluateCellAt(1, 1)
            .accept(new EvalPrintVisitor()));
  }

  @Test
  public void changeTypeMismatch6() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "=(PRODUCT 4 \"test\")");
    assertEquals(String.format("%f", 4.0), sheet.evaluateCellAt(1, 1)
            .accept(new EvalPrintVisitor()));
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidReference() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "=B1:A1");
    sheet.evaluateCellAt(1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void invalidReference2() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "=(Sum B1:A1)");
    sheet.evaluateCellAt(1, 1);
  }


}
