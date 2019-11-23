import org.junit.Test;

import java.util.List;

import edu.cs3500.spreadsheets.controller.Controller;
import edu.cs3500.spreadsheets.controller.Features;
import edu.cs3500.spreadsheets.controller.SheetController;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.ICell;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.formula.value.Value;
import edu.cs3500.spreadsheets.view.IView;

import static org.junit.Assert.assertEquals;

class MockView implements IView {

  StringBuilder log;
  Features f;

  MockView(StringBuilder log) {
    this.log = log;
  }

  @Override
  public void renderSpreadsheet() {
    log.append("renderSpreadsheet()\n");
  }

  @Override
  public void makeVisible() {
    log.append("makeVisible()\n");
  }

  @Override
  public void addFeatures(Features f) {
    log.append(String.format("addFeatures(%s)\n", f.toString()));
    this.f = f;
  }

  public void click(Coord c) {
    f.editCell(c, "");
  }
}

class MockModel implements IWorksheet {

  StringBuilder log;

  MockModel(StringBuilder log) {
    this.log = log;
  }

  @Override
  public List<Coord> allActiveCells() {
    log.append("allActiveCells()\n");
    return null;
  }

  @Override
  public ICell getCellAt(int col, int row) throws IllegalArgumentException {
    log.append(String.format("getCellAt(%d, %d)\n", col, row));
    return null;
  }

  @Override
  public Value evaluateCellAt(int col, int row) {
    log.append(String.format("evaluateCellAt(%d, %d)\n", col, row));
    return null;
  }

  @Override
  public void changeCellAt(int col, int row, String sexp) {
    log.append(String.format("changeCellAt(%d, %d, %s)\n", col, row, sexp));
  }

  @Override
  public boolean documentFreeOfErrors() {
    log.append("documentFreeOfErrors()\n");
    return false;
  }

  @Override
  public List<Coord> getDependents(int col, int row) {
    log.append(String.format("getDependents(%d, %d)\n", col, row));
    return null;
  }
}

public class TestController {

  @Test
  public void testWiring() {
    StringBuilder modelOut = new StringBuilder();
    StringBuilder viewOut = new StringBuilder();
    IWorksheet model = new MockModel(modelOut);
    MockView view = new MockView(viewOut);
    Controller c = new SheetController(model, view);
    c.go();
    assertEquals("", modelOut.toString());
    assertEquals("addFeatures(SheetControllerFeatures)\n" +
            "renderSpreadsheet()\n" +
            "makeVisible()\n", viewOut.toString());
    view.click(new Coord(1, 1));
    assertEquals("changeCellAt(1, 1, null)\n", modelOut.toString());
    assertEquals("addFeatures(SheetControllerFeatures)\n" +
            "renderSpreadsheet()\n" +
            "makeVisible()\n" +
            "renderSpreadsheet()\n" +
            "makeVisible()\n", viewOut.toString());
  }
}
