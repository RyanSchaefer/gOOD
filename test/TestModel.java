import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;

abstract public class TestModel {

  abstract IWorksheet model(String file);

  static public class TestWithBasic extends TestModel{
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
    assertEquals(String.format("%f", 3.0), sheet.getCellAt(1, 1));
    assertEquals(String.format("%f", 4.0), sheet.getCellAt(2, 1));
    assertEquals(String.format("%f", 9.0), sheet.getCellAt(3, 1));
    assertEquals(String.format("%f", 12.0), sheet.getCellAt(4, 1));
    assertEquals("(PRODUCT (SUM C1 A1) (SUM C1 A1))", sheet.getCellAt(1,2));
    assertEquals("(PRODUCT (SUM D1 B1) (SUM D1 B1))", sheet.getCellAt(2, 2));
    assertEquals(String.format("(< A3 %f)", 10.0), sheet.getCellAt(2, 3));
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeCellToNull() {
    IWorksheet sheet = model("test1.gOOD");
    sheet.changeCellAt(1, 1, null);
  }

  @Test
  public void changeCellValue() {
    IWorksheet sheet = model("test1.gOOD");
    assertEquals(String.format("%f", 3.0), sheet.getCellAt(1, 1));
    sheet.changeCellAt(1, 1, "5");
    assertEquals(String.format("%f", 5.0), sheet.getCellAt(1, 1));
  }

  @Test(expected = IllegalArgumentException.class)
  public void evaluateCellWithError() {
    IWorksheet sheet = model("test2.gOOD");
    // The cell at the given coordinates has a circular reference error
    sheet.evaulateCellAt(1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void evaluateCellWithError2() {
    IWorksheet sheet = model("test2.gOOD");
    // The cell at the given coordinates DOES NOT directly contain an error, however there is
    // a circular reference error within the function that makes up the cell
    sheet.evaulateCellAt(2, 4);
    // When B4 =(SUM B1:B3) this does not throw an error but should
  }

  @Test(expected = IllegalArgumentException.class)
  public void evaluateCellInvalidColumn() {
    IWorksheet sheet = model("test2.gOOD");
    // Column must be > 0
    sheet.evaulateCellAt(0, 4);
  }

  @Test(expected = IllegalArgumentException.class)
  public void evaluateCellInvalidRow() {
    IWorksheet sheet = model("test2.gOOD");
    // Row must be > 0
    sheet.evaulateCellAt(4, 0);
  }

  @Test
  public void evalCell() {
    IWorksheet sheet = model("test1.gOOD");
    // If the cell at the given coordinates is null:
    assertNull(null, sheet.evaulateCellAt(123, 123));
    // If the cell at the given coordinates is NOT null:
    assertEquals(String.format("%f", 3.0),  sheet.evaulateCellAt(1, 1));
    assertEquals(String.format("%f", 144.0), sheet.evaulateCellAt(1, 2));
  }

  @Test
  public void evalCell2() {
    IWorksheet sheet = model("test3.gOOD");
    assertEquals("\"hello\"",  sheet.evaulateCellAt(1, 1));
    assertEquals("true", sheet.evaulateCellAt(1, 2));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullSexpThrowsException() {
    IWorksheet sheet = model("test1.gOOD");
    sheet.changeCellAt(1, 1, null);
  }

  @Test
  public void verifyValidDocument() {
    IWorksheet sheet = model("test3.gOOD");
    assertTrue(sheet.documentFreeOfErrors());
  }

  @Test
  public void circularReferenceCausesErrorWithSheet() {
    IWorksheet sheet = model("test2.gOOD");
    assertFalse(sheet.documentFreeOfErrors());
  }

  @Test
  public void changeCellToCircularCausesErrors() {
    IWorksheet sheet = model("test1.gOOD");
    assertTrue(sheet.documentFreeOfErrors());
    sheet.changeCellAt(1, 1, "A1");
    assertFalse(sheet.documentFreeOfErrors());
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
    sheet.changeCellAt(1, 1, "A1");
    sheet.changeCellAt(0, 0, "B2");
    sheet.evaulateCellAt(1, 1);
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeToInvalidSexp() {
    IWorksheet sheet = model("test1.gOOD");
    sheet.changeCellAt(1, 1, "(SUM 3 3 4)");
  }

  // NONE OF THESE WORK PROPERLY YET (but they pass)

  @Test(expected = IllegalArgumentException.class)
  public void changeTypeMismatch1() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "(< 2.2 \"test\")");
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeTypeMismatch2() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "(< 3.5 false)");
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeTypeMismatch3() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "(SUM 3 5)");
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeTypeMismatch4() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "(PRODUCT 4 \"test\")");
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeTypeMismatch5() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "(PRODUCT 4 true)");
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeTypeMismatch6() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, "(PRODUCT 4 \"test\")");
  }

}
