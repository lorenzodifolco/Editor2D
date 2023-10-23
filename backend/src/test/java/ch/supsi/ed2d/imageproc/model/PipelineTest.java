package ch.supsi.ed2d.imageproc.model;

import ch.supsi.ed2d.imageproc.model.filters.BlurFilter;
import ch.supsi.ed2d.imageproc.model.filters.GrayScaleFilter;
import ch.supsi.ed2d.imageproc.model.filters.Rotate;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PipelineTest {

    @Test
    void addFilter() {

        assertDoesNotThrow(()->{
            Pipeline pipeline=new Pipeline();
            assertEquals(0,pipeline.filterLength());

            pipeline.addFilter(BlurFilter.getInstance());
            assertEquals(1,pipeline.filterLength());

            pipeline.addFilter(GrayScaleFilter.getInstance());
            assertEquals(2,pipeline.filterLength());
        });

    }

    @Test
    void apply() {
        BlurFilter blur= BlurFilter.getInstance();

        Image img = new Image(3, 3);
        Rotate rotate=new Rotate(true);

        Pipeline pipeline=new Pipeline();
        pipeline.addFilter(blur);
        pipeline.addFilter(rotate);

        assertDoesNotThrow(() -> {

            img.setPixel(new Pixel(0.1f, 0.1f, 0.1f, 0.0f), 0, 0);
            img.setPixel(new Pixel(0.2f, 0.2f, 0.2f, 0.0f), 0, 1);
            img.setPixel(new Pixel(0.3f, 0.3f, 0.3f, 0.0f), 0, 2);

            img.setPixel(new Pixel(0.3f, 0.3f, 0.3f, 0.0f), 1, 0);
            img.setPixel(new Pixel(0.2f, 0.2f, 0.2f, 0.0f), 1, 1);
            img.setPixel(new Pixel(0.1f, 0.1f, 0.1f, 0.0f), 1, 2);

            img.setPixel(new Pixel(0.1f, 0.1f, 0.1f, 0.0f), 2, 0);
            img.setPixel(new Pixel(0.2f, 0.2f, 0.2f, 0.0f), 2, 1);
            img.setPixel(new Pixel(0.3f, 0.3f, 0.3f, 0.0f), 2, 2);


            Image result=pipeline.apply(img);
            Image expectedResult=blur.apply(img);
            expectedResult= rotate.apply(expectedResult);

            assertEquals(result, expectedResult);
        });
    }

    @Test
    void filterLength() {
        Pipeline pipeline=new Pipeline();
        Rotate rotate=new Rotate(true);

        assertEquals(0,pipeline.filterLength());

        pipeline.addFilter(BlurFilter.getInstance());
        pipeline.addFilter(rotate);

        assertEquals(2,pipeline.filterLength());

    }

    @Test
    void clearPipeline() {
        Pipeline pipeline=new Pipeline();
        assertEquals(0,pipeline.filterLength());

        Rotate rotate=new Rotate(true);
        pipeline.addFilter(BlurFilter.getInstance());
        pipeline.addFilter(rotate);
        pipeline.addFilter(GrayScaleFilter.getInstance());

        assertEquals(3,pipeline.filterLength());

        pipeline.clearPipeline();
        assertEquals(0,pipeline.filterLength());



    }
}