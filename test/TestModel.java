import org.junit.Test;

import static org.junit.Assert.assertEquals

import java.io.FileReader;
import java.io.IOException;

import edu.cs3500.spreadsheets.model.BasicWorksheet;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.WorksheetReader;
import edu.cs3500.spreadsheets.sexp.SNumber;

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
  }

}
