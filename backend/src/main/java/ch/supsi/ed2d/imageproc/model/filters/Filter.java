package ch.supsi.ed2d.imageproc.model.filters;

import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.model.Image;

public interface Filter {
    Image apply(Image img) throws InvalidImageException;
}
