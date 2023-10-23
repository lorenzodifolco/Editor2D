package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.FilterModel;
import ch.supsi.ed2d.imageproc.model.filters.Rotate;

public class RotateRightCommand extends AbstractFilterCommand{

    private static RotateRightCommand instance;

    public RotateRightCommand() {
        super(new FilterModel("Right Rotate", "views/icons/rotate.png"));
        this.filter = new Rotate(true);
    }

    public static RotateRightCommand getInstance() {
        if(instance == null) {
            instance = new RotateRightCommand();
        }

        return instance;
    }
}
