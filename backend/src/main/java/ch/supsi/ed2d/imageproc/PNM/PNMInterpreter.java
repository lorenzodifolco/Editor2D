package ch.supsi.ed2d.imageproc.PNM;

import ch.supsi.ed2d.imageproc.FileNotSupportedException;
import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.InvalidStrategyException;
import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Type;
import ch.supsi.ed2d.imageproc.model.ImageInterpreter;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public abstract class PNMInterpreter implements ImageInterpreter {

    private PNMTypes type;

    protected void setType(PNMTypes type)
    {
        this.type = type;
    }

    protected PNMTypes getType()
    {
        return this.type;
    }

    @Override
    public Image load(File f) throws InvalidImageException, IOException, InvalidStrategyException, FileNotSupportedException {
        var sc = new Scanner(f);
        PNMHeader header;
        header = readHeader(sc);

        if(!header.getType().equals(getType()))
            throw new FileNotSupportedException();
        else return loadBody(sc,header);
    }

    protected abstract Image loadBody(Scanner sc, PNMHeader header) throws InvalidImageException;

    protected PNMHeader readHeader(Scanner sc) throws FileNotSupportedException {
        PNMTypes type = null;
        int[] dimensions = new int[3];
        boolean finish = false;
        int i = 0;
        boolean begin = true;
        while(!finish && sc.hasNextLine())
        {
            String possible_comment = sc.nextLine(); //skipping comment
            var pieces = possible_comment.split("#");
            var stringDimensions = pieces[0].split(" ");
            for (int k = 0; k<stringDimensions.length && i < 3; k++) {
                if(begin) {
                    try {
                        type = PNMTypes.valueOf(stringDimensions[0]);
                        begin = false;
                    }
                    catch (IllegalArgumentException ignored){
                    }
                }
                else {
                    try {
                        dimensions[i] = Integer.parseInt(stringDimensions[k]);
                        i++;
                        if(type == PNMTypes.P1 && i == 2)
                            finish = true;
                    } catch (NumberFormatException ignored) {
                    }
                }
            }


            if(i == 3)
                finish = true;
        }

        if(type != null && dimensions[0] > 0 && dimensions[1] > 0)
            return new PNMHeader(type,dimensions);
        else throw new FileNotSupportedException();
    }

    public enum PNMTypes implements Type {P1, P2, P3, P4, P5, P6}

    public static class PNMHeader {
        private final PNMTypes type;
        private final int width;
        private final int height;
        private final int colorDepth;

        public PNMHeader(PNMTypes type, int[] dimensions) {
            this.type = type;
            this.width = dimensions[0];
            this.height = dimensions[1];
            this.colorDepth = dimensions[2];
        }

        public PNMTypes getType() {
            return type;
        }

        public int getWidth() {
            return width;
        }

        public int getHeight() {
            return height;
        }

        public int getColorDepth() {
            return colorDepth;
        }
    }
}
