package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.FilterModel;
import ch.supsi.ed2d.imageproc.model.filters.GrayScaleFilter;

public class GrayScaleCommand extends AbstractFilterCommand{

    private static GrayScaleCommand istance;


    public GrayScaleCommand() {
        super(new FilterModel("Gray Scale", "views/icons/grayscale.png"));
        this.filter = GrayScaleFilter.getInstance();
    }

    public static GrayScaleCommand getInstance() {
        if(istance == null) {
            istance = new GrayScaleCommand();
        }

        return istance;
    }
}
