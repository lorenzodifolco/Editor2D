package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.Command;
import ch.supsi.ed2d.imageproc.ImageFactory;
import ch.supsi.ed2d.imageproc.model.Image;

import java.io.File;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class LoadCommand implements Command<LoadCommand.LoadCommandResult> {
    private final File file;

    public LoadCommand(File file)
    {
        this.file = file;
    }

    @Override
    public CompletableFuture<LoadCommandResult> execute() {
        return CompletableFuture.supplyAsync(()->{
            try {
                var img = ImageFactory.getInstance().load(file);
                return new LoadCommandResult(img, file.getAbsolutePath());
            }
            catch (Exception ex)
            {
                throw new CompletionException(ex);
            }
        });
    }

    public static class LoadCommandResult {
        private final Image img;
        private final String path;

        public LoadCommandResult(Image img, String path) {
            this.img = img;
            this.path = path;
        }

        public Image getImg() {
            return img;
        }

        public String getPath() {
            return path;
        }
    }
}
