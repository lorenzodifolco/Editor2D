package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.Command;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.concurrent.CompletableFuture;

public class OpenFileChooserCommand implements Command<File> {
    private final Window window;

    public OpenFileChooserCommand(Window window) {
        this.window = window;
    }

    @Override
    public CompletableFuture<File> execute() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Image");

        File fis = fileChooser.showOpenDialog(window);
        return CompletableFuture.completedFuture(fis);
    }
}
