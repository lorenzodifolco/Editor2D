package ch.supsi.ed2d.imageproc.model;

import ch.supsi.ed2d.imageproc.FileNotSupportedException;
import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.InvalidStrategyException;

import java.io.File;
import java.io.IOException;

public interface ImageInterpreter {
    Image load(File f) throws InvalidStrategyException, IOException, FileNotSupportedException, InvalidImageException;
    void save(Image img, String path) throws InvalidStrategyException, InvalidImageException, IOException;
}
