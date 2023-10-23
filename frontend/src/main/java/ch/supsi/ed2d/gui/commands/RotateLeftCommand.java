package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.FilterModel;
import ch.supsi.ed2d.imageproc.model.filters.Rotate;

public class RotateLeftCommand extends AbstractFilterCommand {

    private static RotateLeftCommand instance;

    public RotateLeftCommand() {
        super(new FilterModel("Left Rotate","views/icons/rotate.png"));
        this.filter = new Rotate(false);
    }

    public static RotateLeftCommand getInstance() {
        if(instance == null) {
            instance = new RotateLeftCommand();
        }

        return instance;
    }
}
