package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.controllers.MainViewController;
import ch.supsi.ed2d.gui.models.CommandManager;
import ch.supsi.ed2d.gui.models.PipelineManager;
import ch.supsi.ed2d.gui.models.ReversibleCommand;
import ch.supsi.ed2d.imageproc.model.Pipeline;
import ch.supsi.ed2d.imageproc.model.filters.Filter;
import javafx.scene.control.ListView;

import java.util.concurrent.CompletableFuture;

public class AddFilterToPipelineCommand implements ReversibleCommand<Pipeline> {

    private PipelineManager pipelineCommandManager;
    private int index;
    private AbstractFilterCommand filterCommand;

    private Filter filter;

    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    public AddFilterToPipelineCommand(PipelineManager pipelineCommandManager) {
        this.pipelineCommandManager = pipelineCommandManager;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setFilterCommand(AbstractFilterCommand filterCommand) {
        this.filterCommand = filterCommand;
    }

    @Override
    public CompletableFuture<Pipeline> execute() {
       return pipelineCommandManager.addFilterToPipeline(filterCommand, filter , index);
    }

    @Override
    public CompletableFuture<Pipeline> undo() {
        return pipelineCommandManager.removeFilterToPipeline(index);
    }
}
