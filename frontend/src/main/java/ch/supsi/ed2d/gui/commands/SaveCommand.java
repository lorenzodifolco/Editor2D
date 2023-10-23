package ch.supsi.ed2d.gui.commands;

import ch.supsi.ed2d.gui.models.Command;
import ch.supsi.ed2d.imageproc.ImageFactory;
import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Type;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class SaveCommand implements Command<String> {

    private final Image img;
    private final String path;
    private final Type t;

    public SaveCommand(Image img, String path, Type t)
    {
        this.img = img;
        this.path = path;
        this.t = t;
    }

    @Override
    public CompletableFuture<String> execute() {
        return CompletableFuture.supplyAsync(()->{
            try
            {
                ImageFactory.getInstance().save(img, path, t);
                return path;
            }
            catch(Exception ex)
            {
                throw new CompletionException(ex);
            }
        });
    }
}
