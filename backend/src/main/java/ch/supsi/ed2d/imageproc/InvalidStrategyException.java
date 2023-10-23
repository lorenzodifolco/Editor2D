package ch.supsi.ed2d.imageproc;

import ch.supsi.ed2d.imageproc.model.Type;

public class InvalidStrategyException extends Exception{
    public InvalidStrategyException(Type type)
    {
        super("No compatible strategy found for type "+type.toString());
    }
}
