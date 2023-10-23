package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.FilterModel;
import ch.supsi.ed2d.imageproc.model.filters.Flip;

public class OrizontalFlipCommand extends AbstractFilterCommand{

    private static OrizontalFlipCommand istance;


    public OrizontalFlipCommand() {
        super(new FilterModel("Orizontal Flip", "views/icons/flip.png"));
        this.filter = new Flip(false);
    }

    public static OrizontalFlipCommand getInstance() {
        if(istance == null) {
            istance = new OrizontalFlipCommand();
        }

        return istance;
    }
}
