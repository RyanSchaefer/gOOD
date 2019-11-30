package edu.cs3500.spreadsheets.controller.provider;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.cs3500.spreadsheets.model.Coord;
import edu.cs3500.spreadsheets.model.IWorksheet;
import edu.cs3500.spreadsheets.model.provider.BasicToProviderAdapter;
import edu.cs3500.spreadsheets.model.provider.WorksheetInterface;
import edu.cs3500.spreadsheets.view.provider.ViewSpreadsheetInterface;

public class ControllerToProvider {

  private ViewSpreadsheetInterface view;
  private WorksheetInterface model;
  private Features f = new Features() {
    @Override
    public void selectCell(int mouseX, int mouseY) {
      Coord c = new Coord(mouseX / 140, mouseY / 20);
      view.selectCell(c, model.getRawContents(c));
      highlight = c;
    }

    @Override
    public void confirmButton(String text) {
      if (highlight != null) {
        model.setCellUpdate(highlight, text);
        view.updateView(this.toListOfList());
        try {
          view.render();
        } catch (IOException e) {
          System.out.println("Couldn't render spreadsheet");
        }
      }
    }

    @Override
    public void denyButton() {
      if (highlight != null) {
        view.selectCell(highlight, model.getRawContents(highlight));
      }
    }

    @Override
    public void deleteCell() {
      if (highlight != null) {
        model.removeCell(highlight);
        view.updateView(this.toListOfList());
        try {
          view.render();
        } catch (IOException e) {
          System.out.println("Couldn't render spreadsheet");
        }
      }
    }

    @Override
    public void shiftSelection(Directions d) {
      if (highlight == null) {
        return;
      }
      if (d == Directions.DOWN) {
        highlight = new Coord(highlight.col, highlight.row + 1);
      } else if (d == Directions.LEFT) {
        if (highlight.col != 1) {
          highlight = new Coord(highlight.col - 1, highlight.row);
        }
      } else if (d == Directions.RIGHT) {
        highlight = new Coord(highlight.col + 1, highlight.row);
      } else if (d == Directions.UP) {
        if (highlight.row != 1) {
          highlight = new Coord(highlight.col, highlight.row - 1);
        }
      }
      view.selectCell(highlight, model.getRawContents(highlight));
      try {
        view.render();
      } catch (IOException e) {
        System.out.println("Couldn't render spreadsheet");
      }
    }

    @Override
    public void setView(ViewSpreadsheetInterface v) {
      view = v;
    }

    @Override
    public void run() throws IOException {
      // since our current view implementation expects something other than a model,
      // we should ensure that we always update the view to reflect the model
      view.updateView(toListOfList());
      view.addFeatures(this);
      try {
        view.render();
      } catch (IOException e) {
        System.out.println("Couldn't render spreadsheet");
      }
    }

    /**
     * Becuause our provider requires a <pre>List<List<String>></pre> we need some way for our
     * model to be quickly represented as that. Therefore this method will be included as though it
     * was on the interface.
     * @return the <pre>List<List<String>></pre> representing the strings at coordinates of the model
     */
    private List<List<String>> toListOfList() {
      List<List<String>> ret = new ArrayList<>();
      for (int col = 0; col < model.getColSize(); col++) {
        ret.add(new ArrayList<>());
        for (int row = 0; row < model.getRowSize(); row++) {
          String cell = "";
          Coord c = new Coord(col + 1, row + 1);
          try {
            cell = model.evaluateString(c);
          } catch (IllegalArgumentException e) {
            /*
            there is no cell at the given coords.
             */
          }
          ret.get(col).add(cell);
        }
      }
      return ret;
    }
  };

  // violating single point of truth but we believe that this is how they did it
  private Coord highlight = null;

  public ControllerToProvider(ViewSpreadsheetInterface view, IWorksheet model) {
    this.view = view;
    this.model = new BasicToProviderAdapter(model);
  }

  public void run() throws IOException {
    f.run();
  }
}
