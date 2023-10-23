package ch.supsi.ed2d.gui.commands;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class SaveFileChooserCommand extends AbstractSaveFileChooserCommand{
    private final Window window;
    public SaveFileChooserCommand(Window window) {
        this.window = window;
    }

    @Override
    public CompletableFuture<File> execute() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save as..");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(getExtension(), "*." + getExtension()));
        File f = fc.showSaveDialog(window);
        return CompletableFuture.completedFuture(f);
    }
}
