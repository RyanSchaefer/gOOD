import edu.cs3500.spreadsheets.model.MockModel;
import edu.cs3500.spreadsheets.view.MockView;
import org.junit.Test;

import edu.cs3500.spreadsheets.controller.Controller;
import edu.cs3500.spreadsheets.controller.SheetController;
import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;

import static org.junit.Assert.assertEquals;

/**
 * Tests the controller to make sure it is correctly calling methods when it is intended to.
 */
public class TestController {

  @Test
  public void testWiring() {
    StringBuilder modelOut = new StringBuilder();
    StringBuilder viewOut = new StringBuilder();
    IWorksheet model = new MockModel(modelOut);
    MockView view = new MockView(viewOut);
    Controller c = new SheetController(model, view);
    c.displayView();
    assertEquals("", modelOut.toString());
    assertEquals("addFeatures(SheetControllerFeatures)\n"
        + "renderSpreadsheet()\n"
        + "makeVisible()\n", viewOut.toString());
    view.click(new Coord(1, 1));
    assertEquals("changeCellAt(1, 1, null)\n", modelOut.toString());
    assertEquals("addFeatures(SheetControllerFeatures)\n"
        + "renderSpreadsheet()\n"
        + "makeVisible()\n"
        + "renderSpreadsheet()\n"
        + "makeVisible()\n", viewOut.toString());
  }
}
