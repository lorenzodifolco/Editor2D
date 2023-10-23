package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.FilterModel;
import ch.supsi.ed2d.imageproc.model.filters.BlackAndWhiteFilter;

public class BlackAndWhiteCommand extends AbstractFilterCommand{

    private static BlackAndWhiteCommand istance;

    public BlackAndWhiteCommand() {
        super(new FilterModel("Black & White", "views/icons/bw.png"));
        this.filter = BlackAndWhiteFilter.getInstance();
    }

    public static BlackAndWhiteCommand getInstance() {
        if(istance == null) {
            istance = new BlackAndWhiteCommand();
        }

        return istance;
    }
}
