package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.FilterModel;
import ch.supsi.ed2d.imageproc.model.filters.Flip;

public class VerticalFlipCommand extends AbstractFilterCommand{

    private static VerticalFlipCommand instance;

    public VerticalFlipCommand() {
        super(new FilterModel("Vertical Flip", "views/icons/flip.png"));
        this.filter = new Flip(true);
    }

    public static VerticalFlipCommand getInstance() {
        if(instance == null) {
            instance = new VerticalFlipCommand();
        }

        return instance;
    }
}
