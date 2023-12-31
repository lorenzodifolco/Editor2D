package ch.supsi.ed2d.imageproc.PNM;

import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Pixel;
import ch.supsi.ed2d.imageproc.model.filters.GrayScaleFilter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class P2 extends PNMInterpreter {

    private static P2 singleton;

    public static P2 getInstance()
    {
        if(singleton == null)
            singleton = new P2();
        return singleton;
    }
    private P2() {
        setType(PNMTypes.P2);
    }

    @Override
    public void save(Image img, String path) throws IOException, InvalidImageException {
        File f = new File(path);
        if(f.createNewFile())
        {
            try(var bw = new BufferedWriter(new FileWriter(f)))
            {
                StringBuilder sb = new StringBuilder();
                sb.append("P2\n")
                        .append("#Generated by supsi.ed2d\n")
                        .append(img.getWidth())
                        .append(" ")
                        .append(img.getHeight())
                        .append("\n")
                        .append("255\n");

                var gsImg = GrayScaleFilter.getInstance().apply(img);
                for (Pixel p: gsImg) {
                    //we ignore the other channels given that the image has been converted to grayscale, and therefore we would have the same values in the other two. The fourth channel is ignored.
                    sb.append(p.getR()).append("\n");
                }
                bw.write(sb.toString());
            }
        }
        else throw new IOException("File already exist");
    }

    protected Image loadBody(Scanner sc, PNMHeader header) throws InvalidImageException {
        Image img = new Image(header.getWidth(),header.getHeight());
        img.apply((x,y)->{
            int singleChannel = 0;
            if(sc.hasNextInt())
                singleChannel = sc.nextInt();

            img.setPixel(Pixel.triplets(singleChannel,singleChannel,singleChannel, header.getColorDepth()),x,y);
        });
        sc.close();
        return img;
    }
}
