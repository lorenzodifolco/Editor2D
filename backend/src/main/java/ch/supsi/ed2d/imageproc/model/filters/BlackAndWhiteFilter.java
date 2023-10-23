package ch.supsi.ed2d.imageproc.model.filters;

import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Pixel;

import java.util.HashMap;

public class BlackAndWhiteFilter implements Filter{

    private static BlackAndWhiteFilter singleton;
    private BlackAndWhiteFilter() {}

    public static BlackAndWhiteFilter getInstance()
    {
        if(singleton == null)
            singleton = new BlackAndWhiteFilter();
        return singleton;
    }

    @SuppressWarnings("ComparatorMethodParameterNotUsed")
    private static Pixel meanSurrounding(Image img, int x, int y) throws InvalidImageException
    {
        int xIndex = x-5;
        int yIndex = y-5;

        if(xIndex < 0)
            xIndex = 0;
        if(yIndex < 0)
            yIndex = 0;

        if(xIndex >= img.getWidth())
            xIndex = img.getWidth()-3;
        if(yIndex >= img.getHeight())
            yIndex = img.getHeight()-3;

        HashMap<Integer, Integer> values = new HashMap<>();

        for(; yIndex <= y+5 && yIndex < img.getHeight(); yIndex++)
            for(; xIndex <= x+5 && xIndex < img.getWidth(); xIndex++)
            {
                var p = img.getPixel(xIndex,yIndex);
                var value = values.get(p.getR());
                if (value == null)
                    values.put(p.getR(), 1);
                else
                    values.put(p.getR(), ++value);
            }

        var maxEntry = values.entrySet().stream().max((entry1, entry2) -> entry1.getValue() > entry2.getValue() ? 1 : -1).orElse(null);
        int threshold = 0;
        if (maxEntry != null)
            threshold = maxEntry.getKey();

        if (img.getPixel(x, y).getR() > threshold)
            return Pixel.rgb(255, 255, 255);
        else
            return Pixel.rgb(0, 0, 0);

    }

    @Override
    public Image apply(Image img) throws InvalidImageException {
        var bwImg = new Image(img.getWidth(), img.getHeight());
        var gsImg = GrayScaleFilter.getInstance().apply(img);
        bwImg.apply((x,y)->bwImg.setPixel(meanSurrounding(gsImg,x,y),x,y));
        return bwImg;
    }
}
