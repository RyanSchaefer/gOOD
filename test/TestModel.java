import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

import java.io.FileReader;
import java.io.IOException;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.SNumber;
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
    assertEquals(new SNumber(3), sheet.getCellAt(0, 0).getValue());
    assertEquals(new SNumber(4), sheet.getCellAt(1, 0).getValue());
    assertEquals(new SNumber(9), sheet.getCellAt(2, 0).getValue());
    assertEquals(new SNumber(12), sheet.getCellAt(3, 0).getValue());
    assertEquals(new SList(
            new SSymbol("PRODUCT"),
            new SList(
                    new SSymbol("SUB"),
                    new SSymbol("C1"),
                    new SSymbol("A1")),
            new SList(
                    new SSymbol("SUB"),
                    new SSymbol("C1"),
                    new SSymbol("A1"))), sheet.getCellAt(0,1).getValue());
    assertEquals(new SList(
            new SSymbol("PRODUCT"),
            new SList(
                    new SSymbol("SUB"),
                    new SSymbol("D1"),
                    new SSymbol("B1")),
            new SList(
                    new SSymbol("SUB"),
                    new SSymbol("D1"),
                    new SSymbol("B1"))), sheet.getCellAt(0, 2).getValue());
    assertEquals(new SList(
            new SSymbol("<"),
            new SSymbol("A3"),
            new SNumber(10)), sheet.getCellAt(1, 2).getValue());
  }

  @Test
  public void changeCellValue() {
    IWorksheet sheet = model("test_worksheets/test1.gOOD");
    assertEquals(new SNumber(3), sheet.getCellAt(0, 0).getValue());
    sheet.changeCellAt(0, 0, new SNumber(5));
    assertEquals(new SNumber(5), sheet.getCellAt(0, 0).getValue());
  }

  @Test(expected = IllegalArgumentException.class)
  public void nullSexpThrowsException() {
    IWorksheet sheet = model("test_worksheets/test.gOOD");
    sheet.changeCellAt(0, 0, null);
  }

}
