package ch.supsi.ed2d.imageproc.TGA;

import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.ImageInterpreter;

import java.io.File;

public abstract class TGAInterpreter implements ImageInterpreter {

    @Override
    public Image load(File f) {
        return null;
    }

    @Override
    public void save(Image img, String path) {

    }
}
