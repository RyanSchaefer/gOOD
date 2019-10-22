import org.junit.Test;

import edu.cs3500.spreadsheets.sexp.SBoolean;
import edu.cs3500.spreadsheets.sexp.SList;
import edu.cs3500.spreadsheets.sexp.SNumber;
import edu.cs3500.spreadsheets.sexp.SString;
import edu.cs3500.spreadsheets.sexp.SSymbol;

import static org.junit.Assert.assertEquals;

class TestEvalVisitor {
  @Test
  public void SumTest() {
    assertEquals(3, new SList(new SSymbol("SUM"),
            new SNumber(1),
            new SSymbol("SUM"),
            new SBoolean(false),
            new SString("hello")));
  }
}
