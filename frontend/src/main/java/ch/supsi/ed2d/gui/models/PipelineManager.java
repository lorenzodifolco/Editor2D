package ch.supsi.ed2d.gui.models;

import ch.supsi.ed2d.gui.commands.AbstractFilterCommand;
import ch.supsi.ed2d.gui.controllers.MainViewController;
import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.model.Pipeline;
import ch.supsi.ed2d.imageproc.model.filters.Filter;
import javafx.scene.control.ListView;

import java.util.concurrent.CompletableFuture;

public class PipelineManager extends CommandManager<Pipeline> {


    private final Pipeline pipeline = new Pipeline();
    private ListView<AbstractFilterCommand> pipelineListView;

    boolean imageLoaded = false;

    public void notifyLoadedImage() {
        imageLoaded = true;
        pipeline.clearPipeline();
        checkUndoRedoEnabled();
    }

    private MainViewController mainViewController;

    public void setPipelineListView(ListView<AbstractFilterCommand> pipelineListView) {
        this.pipelineListView = pipelineListView;
    }

    public void setMainViewController(MainViewController mainViewController) {
        this.mainViewController = mainViewController;
    }

    public void checkUndoRedoEnabled() {
        if (commands.size() == 0)
            mainViewController.disableUndoItem(true);
        else {
            if (mainViewController.UndoIsDisabled()) {
                mainViewController.disableUndoItem(false);
            }
        }

        if (StackUndo.isEmpty())
            mainViewController.disableRedoItem(true);
        else {
            if (mainViewController.RedoIsDisabled()) {
                mainViewController.disableRedoItem(false);
            }
        }
    }

    @Override
    public void undo() {
        super.undo();
        drawImage();
        checkUndoRedoEnabled();
    }

    @Override
    public void redo() {
        super.redo();
        drawImage();
        checkUndoRedoEnabled();
    }

    @Override
    public void addCommand(ReversibleCommand<Pipeline> command) {
        super.addCommand(command);
        checkUndoRedoEnabled();
    }

    private static PipelineManager instance = null;

    public static PipelineManager getInstance() {
        if (instance == null) {
            instance = new PipelineManager();
        }
        return instance;
    }


    public CompletableFuture<Pipeline> addFilterToPipeline(AbstractFilterCommand filterCommand, Filter filter, int index) {

        pipeline.addFilterIndex(index, filter);
        pipelineListView.getItems().add(index, filterCommand);
        drawImage();
        return CompletableFuture.completedFuture(pipeline);
    }



    public CompletableFuture<Pipeline> removeFilterToPipeline(int index) {
        pipelineListView.getItems().remove(index);
        pipeline.remove(index);
        drawImage();
        return CompletableFuture.completedFuture(pipeline);
    }

    public void drawImage() {
        try {
            mainViewController.setImage(pipeline.apply(mainViewController.getLastSavedImage()));
        } catch (InvalidImageException e) {
            throw new RuntimeException(e);
        }
    }


}
