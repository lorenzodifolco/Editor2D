package ch.supsi.ed2d.gui.controllers;

import ch.supsi.ed2d.gui.commands.AbstractFilterCommand;
import ch.supsi.ed2d.gui.commands.AddFilterToPipelineCommand;
import ch.supsi.ed2d.gui.commands.RemoveFromPipelineCommand;
import ch.supsi.ed2d.gui.models.Controller;
import ch.supsi.ed2d.gui.models.FilterListCell;
import ch.supsi.ed2d.gui.models.PipelineManager;
import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.model.Pipeline;
import javafx.collections.ObservableList;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.input.*;
import javafx.stage.Window;

import java.util.HashMap;
import java.util.Objects;


public class PipelineController implements Controller {

    private final ListView<AbstractFilterCommand> pipelineListView;

    private final MainViewController parentController;

    private final Pipeline pipeline = new Pipeline();

    private final HashMap<String, AbstractFilterCommand> filterCommands;


    public PipelineController(MainViewController parentController, ListView<AbstractFilterCommand> pipelineListView, HashMap<String, AbstractFilterCommand> filterCommands) {
        this.parentController = parentController;
        this.pipelineListView = pipelineListView;
        this.filterCommands = filterCommands;
    }
    public void load() {
        PipelineManager.getInstance().setPipelineListView(pipelineListView);

        pipelineListView.setCellFactory(filterModelListView -> {
            ListCell<AbstractFilterCommand> listCell = new FilterListCell();

            listCell.setOnDragDetected((MouseEvent event) ->
            {
                ObservableList<AbstractFilterCommand> items = listCell.getListView().getItems();
                int thisIdx = items.indexOf(listCell.getItem());

                Dragboard db = listCell.startDragAndDrop(TransferMode.COPY);
                ClipboardContent content = new ClipboardContent();
                if (listCell.getItem() == null)
                    return;
                content.putString(listCell.getItem().getFilterModel().getFilterName());
                db.setContent(content);

                var command= listCell.getItem();

                command.execute().whenComplete((filter, throwable) -> {
                    RemoveFromPipelineCommand removeFromPipelineCommand = new RemoveFromPipelineCommand(PipelineManager.getInstance());
                    removeFromPipelineCommand.setIndex(thisIdx);
                    removeFromPipelineCommand.setFilterCommand(command);
                    removeFromPipelineCommand.setFilter(filter);
                    PipelineManager.getInstance().addCommand(removeFromPipelineCommand);
                    removeFromPipelineCommand.execute();
                });



                event.consume();
            });

            listCell.setOnDragEntered((DragEvent event) -> listCell.setStyle("-fx-background-color: aqua;"));

            listCell.setOnDragExited((DragEvent event) -> listCell.setStyle(""));

            listCell.setOnDragOver((DragEvent event) ->
            {
                Dragboard db = event.getDragboard();
                if (db.hasString()) {
                    event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
                }
                event.consume();
            });

            listCell.setOnDragDropped((DragEvent event) ->
            {
                Dragboard db = event.getDragboard();
                boolean success = false;
                if (db.hasString()) {
                    var filterCommand = Objects.requireNonNull(filterCommands.get(db.getString()));

                    ObservableList<AbstractFilterCommand> items = listCell.getListView().getItems();

                    filterCommand.execute().whenComplete((filter, throwable) -> {

                        if(filter==null)
                            return;

                        int idx = listCell.getIndex();
                        if (idx > items.size())
                            idx = items.size();

                        AddFilterToPipelineCommand addFilterToPipelineCommand = new AddFilterToPipelineCommand(PipelineManager.getInstance());
                        addFilterToPipelineCommand.setIndex(idx);
                        addFilterToPipelineCommand.setFilterCommand(filterCommand);
                        addFilterToPipelineCommand.setFilter(filter);
                        PipelineManager.getInstance().addCommand(addFilterToPipelineCommand);
                        addFilterToPipelineCommand.execute();
                    });



                    success = true;
                }
                event.setDropCompleted(success);
                event.consume();
            });

            return listCell;
        });

        pipelineListView.setOnDragOver((DragEvent event) ->
        {
            Dragboard db = event.getDragboard();
            if (db.hasString()) {
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        });

        pipelineListView.setOnDragDropped((DragEvent event) ->
        {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasString()) {
                var s = db.getString();
                var filterCommand = Objects.requireNonNull(filterCommands.get(s));

                filterCommand.execute().whenComplete((filter, throwable) -> {

                    AddFilterToPipelineCommand addFilterToPipelineCommand = new AddFilterToPipelineCommand(PipelineManager.getInstance());
                    addFilterToPipelineCommand.setIndex(pipelineListView.getItems().size());
                    addFilterToPipelineCommand.setFilterCommand(filterCommand);
                    addFilterToPipelineCommand.setFilter(filter);
                    PipelineManager.getInstance().addCommand(addFilterToPipelineCommand);
                    addFilterToPipelineCommand.execute();
                });

            }
            event.setDropCompleted(success);
            event.consume();
        });
    }



    @Override
    public Window getWindow() {
        return this.parentController.getWindow();
    }
}