import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;


/**
 * The tests for the model we are implementing.
 */
abstract public class TestModel {

  abstract IWorksheet model(String file);


  /**
   * Tests with a basic model.
   */
  static public class TestWithBasic extends TestModel {
    @Override
    IWorksheet model(String file) {
      try {
        return WorksheetReader.
                read(new BasicWorksheet.BasicWorksheetBuilder(),
                        new FileReader(new File("test/" + file)));

      } catch (IOException e) {
        e.printStackTrace();
        throw new RuntimeException();
      }
    }
  }

  @Test
  public void getCellValue() {
    IWorksheet sheet = model("test1.gOOD");
    assertEquals(String.format("%f", 3.0), sheet.getCellAt(1, 1));
    assertEquals(String.format("%f", 4.0), sheet.getCellAt(2, 1));
    assertEquals(String.format("%f", 9.0), sheet.getCellAt(3, 1));
    assertEquals(String.format("%f", 12.0), sheet.getCellAt(4, 1));
    assertEquals("(PRODUCT (SUM C1 A1) (SUM C1 A1))", sheet.getCellAt(1, 2));
    assertEquals("(PRODUCT (SUM D1 B1) (SUM D1 B1))", sheet.getCellAt(2, 2));
    assertEquals(String.format("(< A3 %f)", 10.0), sheet.getCellAt(2, 3));
  }

  @Test
  public void changeCellValue() {
    IWorksheet sheet = model("test1.gOOD");
    assertEquals(String.format("%f", 3.0), sheet.evaluateCellAt(1, 1));
    sheet.changeCellAt(1, 1, "5");
    assertEquals(String.format("%f", 5.0), sheet.evaluateCellAt(1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullSexpThrowsException() {
    IWorksheet sheet = model("test1.gOOD");
    sheet.changeCellAt(0, 0, null);
  }

  @Test
  public void circularReferenceCausesErrorWithSheet() {
    IWorksheet sheet = model("test4.gOOD");
    assertFalse(sheet.documentFreeOfErrors());
  }

  @Test
  public void changeCellToCircularCausesErrors() {
    IWorksheet sheet = model("test1.gOOD");
    sheet.changeCellAt(1, 1, "B2");
    assertFalse(sheet.documentFreeOfErrors());
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeCellToTwoCircular() {
    IWorksheet sheet = model("test1.gOOD");
    sheet.changeCellAt(1, 1, "A1");
    sheet.changeCellAt(0, 0, "B2");
    sheet.evaluateCellAt(1, 1);
  }

  @Test
  public void EvalCell() {
    IWorksheet sheet = model("test1.gOOD");
    assertNull(sheet.evaluateCellAt(123, 123));
    assertEquals(String.format("%f", 3.0), sheet.evaluateCellAt(1, 1));
    assertEquals(String.format("%f", 144.0), sheet.evaluateCellAt(1, 2));
  }

  @Test
  public void EvalCell2() {
    IWorksheet sheet = model("test3.gOOD");
    assertEquals("\"hello\"", sheet.evaluateCellAt(1, 1));
    assertEquals("true", sheet.evaluateCellAt(1, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void TypeMismatch() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "(< 2.2 \"test\")");
    sheet.evaluateCellAt(1, 1);
  }

  @Test
  public void changingCellPropagatesChanges() {
    IWorksheet sheet = model("test2.gOOD");
    assertEquals(String.format("%f", 163840.0), sheet.evaluateCellAt(1, 18));
    sheet.changeCellAt(1, 1, "3");
    sheet.changeCellAt(1, 2, "5");
    assertEquals(String.format("%f", 262144.0), sheet.evaluateCellAt(1, 18));
  }


}
