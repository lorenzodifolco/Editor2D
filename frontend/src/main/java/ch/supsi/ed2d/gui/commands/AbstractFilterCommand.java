package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.Command;
import ch.supsi.ed2d.gui.models.FilterModel;
import ch.supsi.ed2d.imageproc.model.filters.Filter;

import java.util.concurrent.CompletableFuture;

public abstract class AbstractFilterCommand implements Command<Filter> {
    private final FilterModel filterModel;
    protected Filter filter;
    public AbstractFilterCommand(FilterModel model)
    {
        this.filterModel = model;
    }

    public FilterModel getFilterModel() {
        return filterModel;
    }

    @Override
    public CompletableFuture<Filter> execute() {
        return CompletableFuture.completedFuture(filter);
    }
}
