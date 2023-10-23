package ch.supsi.ed2d.imageproc.model.filters;

import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Pixel;

public class GrayScaleFilter implements Filter{

    private static GrayScaleFilter singleton;

    private GrayScaleFilter() {}

    public static GrayScaleFilter getInstance()
    {
        if(singleton == null)
            singleton = new GrayScaleFilter();
        return singleton;
    }

    @Override
    public Image apply(Image img) throws InvalidImageException {
        Image grayImg = new Image(img.getWidth(), img.getHeight());
        grayImg.apply((x,y)->{
            var pixel = img.getPixel(x,y);
            float avg = (pixel.getFirstChannel() + pixel.getSecondChannel() + pixel.getThirdChannel()) / 3f;
            grayImg.setPixel(new Pixel(avg,avg,avg,pixel.getFourthChannel()),x,y);
        });

        return grayImg;
    }
}
