package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.Command;

import java.io.File;

public abstract class AbstractSaveFileChooserCommand implements Command<File> {
    private String extension;
    public AbstractSaveFileChooserCommand()
    {
        this.extension = "*";
    }

    public String getExtension() {
        return extension;
    }

    public AbstractSaveFileChooserCommand setExtension(String extension)
    {
        this.extension = extension;
        return this;
    }
}
