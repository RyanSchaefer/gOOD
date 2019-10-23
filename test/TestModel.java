import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.sexp.SBoolean;
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
    assertEquals(new SNumber(3), sheet.getCellAt(1, 1));
    assertEquals(new SNumber(4), sheet.getCellAt(2, 1));
    assertEquals(new SNumber(9), sheet.getCellAt(3, 1));
    assertEquals(new SNumber(12), sheet.getCellAt(4, 1));
    assertEquals(new SList(
            new SSymbol("PRODUCT"),
            new SList(
                    new SSymbol("SUM"),
                    new SSymbol("C1"),
                    new SSymbol("A1")),
            new SList(
                    new SSymbol("SUM"),
                    new SSymbol("C1"),
                    new SSymbol("A1"))), sheet.getCellAt(1,2));
    assertEquals(new SList(
            new SSymbol("PRODUCT"),
            new SList(
                    new SSymbol("SUM"),
                    new SSymbol("D1"),
                    new SSymbol("B1")),
            new SList(
                    new SSymbol("SUM"),
                    new SSymbol("D1"),
                    new SSymbol("B1"))), sheet.getCellAt(2, 2));
    assertEquals(new SList(
            new SSymbol("<"),
            new SSymbol("A3"),
            new SNumber(10)), sheet.getCellAt(2, 3));
  }

  @Test
  public void changeCellValue() {
    IWorksheet sheet = model("test1.gOOD");
    assertEquals(new SNumber(3), sheet.getCellAt(0, 0));
    sheet.changeCellAt(0, 0, new SNumber(5));
    assertEquals(new SNumber(5), sheet.getCellAt(0, 0));
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullSexpThrowsException() {
    IWorksheet sheet = model("test1.gOOD");
    sheet.changeCellAt(0, 0, null);
  }

  @Test(expected = IllegalArgumentException.class)
  public void circularReferenceCausesError() {
    IWorksheet sheet = model("test2.gOOD");
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeCellToCircular() {
    IWorksheet sheet = model("test1.gOOD");
    sheet.changeCellAt(1, 1, new SSymbol("B2"));
  }

  @Test(expected = IllegalArgumentException.class)
  public void changeCellToTwoCircular() {
    IWorksheet sheet = model("test1.gOOD");
    sheet.changeCellAt(1, 1, new SSymbol("A1"));
    sheet.changeCellAt(0, 0, new SSymbol("B2"));
  }

  @Test
  public void EvalCell() {
    IWorksheet sheet = model("test1.gOOD");
    assertEquals(null,
            sheet.getCellAt(123, 123));
    assertEquals(new SNumber(3),  sheet.getCellAt(1, 1).accept(new EvalVisitor(sheet)));
    assertEquals(new SNumber(144), sheet.getCellAt(1, 2).accept(
            new EvalVisitor(sheet)));
  }

  @Test
  public void EvalCell2() {
    IWorksheet sheet = model("test3.gOOD");
    assertEquals(new SString("hello"),  sheet.getCellAt(1, 1).accept(
            new EvalVisitor(sheet)));
    assertEquals(new SBoolean(true), sheet.getCellAt(1, 2).accept(
            new EvalVisitor(sheet)));
    assertEquals("\"hello\"", sheet.getCellAt(1, 4).accept(
            new EvalVisitor(sheet)));
  }

  @Test(expected = IllegalArgumentException.class)
  public void TypeMismatch() {
    IWorksheet sheet = model("empty.gOOD");
    sheet.changeCellAt(1, 1, new SList(
            new SSymbol("<"),
            new SNumber(2.2),
            new SString("test")));
  }



}
