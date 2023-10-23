package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.FilterModel;
import ch.supsi.ed2d.imageproc.model.filters.RidgeDetectionFilter;

public class RidgeDetectionCommand extends AbstractFilterCommand{

    private static RidgeDetectionCommand istance;

    public RidgeDetectionCommand() {
        super(new FilterModel("Ridge Detection","views/icons/ridgeDetection.png"));
        this.filter = RidgeDetectionFilter.getInstance();
    }

    public static RidgeDetectionCommand getInstance() {
        if(istance == null) {
            istance = new RidgeDetectionCommand();
        }

        return istance;
    }
}
