package ch.supsi.ed2d.gui.controllers;

import ch.supsi.ed2d.gui.commands.AbstractFilterCommand;
import ch.supsi.ed2d.gui.models.Controller;
import ch.supsi.ed2d.gui.models.FilterListCell;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.Window;

import java.util.Collection;


public class FilterListController implements Controller {
    private final ListView<AbstractFilterCommand> filterListView;

    private final MainViewController parentController;

    private final Collection<AbstractFilterCommand> commandList;

    public FilterListController(MainViewController parentController, ListView<AbstractFilterCommand> filterListView, Collection<AbstractFilterCommand> commandList) {
        this.parentController = parentController;
        this.filterListView = filterListView;
        this.commandList = commandList;
    }

    public void load() {
        filterListView.setCellFactory(filterModelListView -> {
            ListCell<AbstractFilterCommand> listCell = new FilterListCell();

            listCell.setOnDragDetected((MouseEvent event) ->
            {
                Dragboard db = listCell.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                content.putString(listCell.getItem().getFilterModel().getFilterName());
                db.setContent(content);
                event.consume();
            });

            return listCell;
        });

        filterListView.getItems().addAll(commandList);
    }

    @Override
    public Window getWindow() {
        return this.parentController.getWindow();
    }
}
