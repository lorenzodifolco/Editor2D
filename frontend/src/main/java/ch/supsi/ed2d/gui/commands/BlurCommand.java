package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.FilterModel;
import ch.supsi.ed2d.imageproc.model.filters.BlurFilter;

public class BlurCommand extends AbstractFilterCommand{

    private static BlurCommand istance;

    public BlurCommand() {
        super(new FilterModel("Blur", "views/icons/blur.png"));
        this.filter = BlurFilter.getInstance();
    }

    public static BlurCommand getInstance() {
        if(istance == null) {
            istance = new BlurCommand();
        }

        return istance;
    }
}
