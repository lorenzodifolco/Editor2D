package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.controllers.ScaleDialogController;
import ch.supsi.ed2d.gui.models.Controller;
import ch.supsi.ed2d.gui.models.FilterModel;
import ch.supsi.ed2d.imageproc.model.filters.Filter;

import java.util.concurrent.CompletableFuture;

public class ScaleCommand extends AbstractFilterCommand {

    private final Controller scaleDialogParentController;
    public ScaleCommand(Controller scaleDialogParentController) {
        super(new FilterModel("Scale", "views/icons/scale.png"));
        this.scaleDialogParentController = scaleDialogParentController;
    }

    @Override
    public CompletableFuture<Filter> execute() {
        return new ScaleDialogController(scaleDialogParentController).loadAndShowView();
    }
}
