package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.FilterModel;
import ch.supsi.ed2d.imageproc.model.filters.SharpenFilter;

public class SharpenCommand extends AbstractFilterCommand{

    private static SharpenCommand instance;


    public SharpenCommand() {
        super(new FilterModel("Sharpen", "views/icons/sharpen.png"));
        this.filter = SharpenFilter.getInstance();
    }

    public static SharpenCommand getInstance() {
        if(instance == null) {
            instance = new SharpenCommand();
        }

        return instance;
    }
}
