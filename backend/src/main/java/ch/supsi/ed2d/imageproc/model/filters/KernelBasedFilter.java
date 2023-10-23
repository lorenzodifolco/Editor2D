package ch.supsi.ed2d.imageproc.model.filters;

import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.model.Image;
import ch.supsi.ed2d.imageproc.model.Pixel;

public abstract class KernelBasedFilter implements Filter{
    float[][] kernel;
    int size;

    public float[][] getKernel() {
        return kernel;
    }

    public void setKernel(float[][] kernel) {
        this.kernel = kernel;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public static float clamp(float val, float min, float max) {
        return Math.max(min, Math.min(max, val));
    }

    @Override
    public Image apply(Image img) throws InvalidImageException {
        Image newImage=new Image(img.getWidth(),img.getHeight());

        for(int y=0;y<img.getHeight();y++)
            for(int x=0;x< img.getWidth();x++)
            {

                float r=0,g=0,b=0;

                for(int yKernel=0;yKernel<size;yKernel++)
                    for(int xKernel=0;xKernel<size;xKernel++)
                    {
                        int xTemp=x-((size-1)/2)+xKernel;
                        int yTemp=y-((size-1)/2)+yKernel;

                        while (xTemp<0)
                            xTemp++;
                        while (xTemp>=img.getWidth())
                            xTemp--;
                        while(yTemp<0)
                            yTemp++;
                        while (yTemp>=img.getHeight())
                            yTemp--;

                        float tempR=img.getPixel(xTemp,yTemp).getFirstChannel();
                        float tempG=img.getPixel(xTemp,yTemp).getSecondChannel();
                        float tempB=img.getPixel(xTemp,yTemp).getThirdChannel();

                        r+=tempR *kernel[xKernel][yKernel];
                        g+=tempG *kernel[xKernel][yKernel];
                        b+=tempB *kernel[xKernel][yKernel];
                    }


                r=clamp(r,0,1);
                g=clamp(g,0,1);
                b=clamp(b,0,1);

                newImage.setPixel(new Pixel(r,g,b,0) ,x,y);
            }
        return newImage;
    }



}
