package ch.supsi.ed2d.imageproc;

import ch.supsi.ed2d.imageproc.PNM.*;
import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Type;
import ch.supsi.ed2d.imageproc.model.ImageInterpreter;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class ImageFactory {

    private static ImageFactory singleton;

    private ImageFactory() {}
    public static ImageFactory getInstance() {
        if(singleton == null)
        {
            singleton = new ImageFactory();
            singleton.addInterpreter(P1.getInstance(), PNMInterpreter.PNMTypes.P1);
            singleton.addInterpreter(P2.getInstance(), PNMInterpreter.PNMTypes.P2);
            singleton.addInterpreter(P3.getInstance(), PNMInterpreter.PNMTypes.P3);
        }
        return singleton;
    }

    private final HashMap<Type, ImageInterpreter> interpreters = new HashMap<>();

    public void addInterpreter(ImageInterpreter interpreter, Type types)
    {
        interpreters.putIfAbsent(types,interpreter);
    }

    public Image load(File f) throws InvalidStrategyException, FileNotSupportedException, IOException, InvalidImageException {
        for(ImageInterpreter interpreter : interpreters.values())
        {
            try
            {
                return interpreter.load(f);
            }
            catch (FileNotSupportedException ignore){}
        }
        throw new FileNotSupportedException();
    }

    public void save(Image img, String path, Type type) throws FileNotSupportedException, InvalidStrategyException, IOException, InvalidImageException
    {
        var interpreter = interpreters.get(type);
        if(interpreter != null)
            interpreter.save(img, path);
        else throw new FileNotSupportedException();
    }
}
