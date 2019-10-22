import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.vistors.EvalVisitor;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.SNumber;
import edu.cs3500.spreadsheets.sexp.SString;
import edu.cs3500.spreadsheets.sexp.SSymbol;

abstract public class TestModel {

  abstract IWorksheet model(String file);

  static public class TestWithBasic extends TestModel{
    @Override
    IWorksheet model(String file) {
      try {
        return WorksheetReader.read
                (new BasicWorksheet.BasicWorksheetBuilder(), new FileReader(file));
      } catch (IOException e) {
        throw new RuntimeException();
      }
    }
  }

  @Test
  public void getCellValue() {
    IWorksheet sheet = model("test_worksheets/test1.gOOD");
    assertEquals(new SNumber(3), sheet.getCellAt(0, 0));
    assertEquals(new SNumber(4), sheet.getCellAt(1, 0));
    assertEquals(new SNumber(9), sheet.getCellAt(2, 0));
    assertEquals(new SNumber(12), sheet.getCellAt(3, 0));
    assertEquals(new SList(
            new SSymbol("PRODUCT"),
            new SList(
                    new SSymbol("SUB"),
                    new SSymbol("C1"),
                    new SSymbol("A1")),
            new SList(
                    new SSymbol("SUB"),
                    new SSymbol("C1"),
                    new SSymbol("A1"))), sheet.getCellAt(0,1));
    assertEquals(new SList(
            new SSymbol("PRODUCT"),
            new SList(
                    new SSymbol("SUB"),
                    new SSymbol("D1"),
                    new SSymbol("B1")),
            new SList(
                    new SSymbol("SUB"),
                    new SSymbol("D1"),
                    new SSymbol("B1"))), sheet.getCellAt(0, 2));
    assertEquals(new SList(
            new SSymbol("<"),
            new SSymbol("A3"),
            new SNumber(10)), sheet.getCellAt(1, 2));
  }

  @Test
  public void changeCellValue() {
    IWorksheet sheet = model("test_worksheets/test1.gOOD");
    assertEquals(new SNumber(3), sheet.getCellAt(0, 0));
    sheet.changeCellAt(0, 0, new SNumber(5));
    assertEquals(new SNumber(5), sheet.getCellAt(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullSexpThrowsException() {
    IWorksheet sheet = model("test_worksheets/test1.gOOD");
    sheet.changeCellAt(0, 0, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void circularReferenceCausesError() {
    IWorksheet sheet = model("test_worksheets/test2.gOOD");
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeCellToCircular() {
    IWorksheet sheet = model("test_worksheets/test1.gOOD");
    sheet.changeCellAt(1, 1, new SSymbol("B2"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeCellToTwoCircular() {
    IWorksheet sheet = model("test_worksheets/test1.gOOD");
    sheet.changeCellAt(1, 1, new SSymbol("A1"));
    sheet.changeCellAt(0, 0, new SSymbol("B2"));
  }

  @Test
  public void EvalCell() {
    IWorksheet sheet = model("test_worksheets/test1.gOOD");
    assertEquals("",
            sheet.getCellAt(123, 123).accept(
                    new EvalVisitor(sheet)));
    assertEquals("3",  sheet.getCellAt(0, 0).accept(new EvalVisitor(sheet)));
    assertEquals("36", sheet.getCellAt(0, 1).accept(
            new EvalVisitor(sheet)));
  }

  @Test
  public void EvalCell2() {
    IWorksheet sheet = model("test_worksheets/test3.gOOD");
    assertEquals("\"hello\"",  sheet.getCellAt(0, 0).accept(
            new EvalVisitor(sheet)));
    assertEquals("true", sheet.getCellAt(0, 0).accept(
            new EvalVisitor(sheet)));
    assertEquals("\"hello\"", sheet.getCellAt(0, 3).accept(
            new EvalVisitor(sheet)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void TypeMismatch() {
    IWorksheet sheet = model("test_worksheets/empty.gOOD");
    sheet.changeCellAt(0, 0, new SList(
            new SSymbol("<"),
            new SNumber(2.2),
            new SString("test")));
  }



}
