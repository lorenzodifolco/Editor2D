package ch.supsi.ed2d.imageproc;

public class FileNotSupportedException extends Exception{
    public FileNotSupportedException()
    {
        super("Invalid file, check the extension and the content");
    }
}
