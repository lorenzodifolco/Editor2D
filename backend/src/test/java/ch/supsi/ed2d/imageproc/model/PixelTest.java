package ch.supsi.ed2d.imageproc.model;

import ch.supsi.ed2d.imageproc.InvalidImageException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PixelTest {

    @Test
    void rgb() {
        assertDoesNotThrow(()->{
            var p = Pixel.rgb(1,2,3);
            assertEquals(1/255f,p.getFirstChannel());
            assertEquals(2/255f,p.getSecondChannel());
            assertEquals(3/255f,p.getThirdChannel());
            assertEquals(1,p.getFourthChannel());
        });

        assertThrows(InvalidImageException.class, ()->{
            Pixel.rgb(453,4354,4);
        });
    }

    @Test
    void rgba() {
        assertDoesNotThrow(()->{
            var p = Pixel.rgba(1,2,3,0.5f);
            assertEquals(1/255f,p.getFirstChannel());
            assertEquals(2/255f,p.getSecondChannel());
            assertEquals(3/255f,p.getThirdChannel());
            assertEquals(0.5f,p.getFourthChannel());
        });

        assertThrows(InvalidImageException.class, ()->{
            Pixel.rgba(3243,324,1232,23f);
        });
    }

    @Test
    void triplets() {
        assertThrows(InvalidImageException.class, ()->{
            Pixel.triplets(213,232,21321,2);
        });
    }


    @Test
    void setA() {
        assertDoesNotThrow(()->{
            Pixel p = Pixel.rgba(3,4,5,0.75f);
            p.setA(0.23f);
            assertEquals(0.23f,p.getA());
        });

        assertThrows(InvalidImageException.class,()->{
            Pixel p = Pixel.rgba(3,4,5,0.75f);
            p.setA(213f);
        });
    }


    @Test
    void setR() {
        assertDoesNotThrow(()->{
            Pixel p = Pixel.rgba(3,4,5,0.75f);
            p.setR(86);
            assertEquals(86,p.getR());
        });

        assertThrows(InvalidImageException.class,()->{
            Pixel p = Pixel.rgba(3,4,5,0.75f);
            p.setR(3423423);
        });
    }


    @Test
    void setG() {
        assertDoesNotThrow(()->{
            Pixel p = Pixel.rgba(3,4,5,0.75f);
            p.setG(86);
            assertEquals(86,p.getG());
        });

        assertThrows(InvalidImageException.class,()->{
            Pixel p = Pixel.rgba(3,4,5,0.75f);
            p.setG(3423423);
        });

    }

    @Test
    void setB() {
        assertDoesNotThrow(()->{
            Pixel p = Pixel.rgba(3,4,5,0.75f);
            p.setB(86);
            assertEquals(86,p.getB());
        });

        assertThrows(InvalidImageException.class,()->{
            Pixel p = Pixel.rgba(3,4,5,0.75f);
            p.setB(3423423);
        });

    }

    @Test
    void setRgb() {
        assertDoesNotThrow(()->{
            Pixel p = Pixel.rgb(3,4,5);
            p.setRgb(45,32,67);
            assertEquals(Pixel.rgb(45,32,67),p);
        });

        assertThrows(InvalidImageException.class,()->{
            Pixel p = Pixel.rgba(3,4,5,0.75f);
            p.setRgb(42345,32,67);
        });
    }

    @Test
    void setRgba() {
        assertDoesNotThrow(()->{
            Pixel p = Pixel.rgba(3,4,5,0.75f);
            p.setRgba(45,32,67,0.6f);
            assertEquals(Pixel.rgba(45,32,67,0.6f),p);
        });

        assertThrows(InvalidImageException.class,()->{
            Pixel p = Pixel.rgba(3,4,5,0.75f);
            p.setRgba(45,32,67,3.6f);
        });
    }

    @Test
    void setTriplets() {
        assertDoesNotThrow(()->{
            Pixel p = Pixel.rgba(3,4,5,0.75f);
            p.setTriplets(45,32,67,255);
            assertEquals(Pixel.rgba(45,32,67,1),p);
        });

        assertThrows(InvalidImageException.class,()->{
            Pixel p = Pixel.rgba(3,4,5,0.75f);
            p.setTriplets(42345,32,67,1);
        });
    }

    @Test
    void equals() {
        assertDoesNotThrow(()->{
            assertEquals(Pixel.rgba(3, 4, 5, 0.75f), Pixel.rgba(3, 4, 5, 0.75f));
        });
    }

    @Test
    void setFirstChannel(){
        assertDoesNotThrow(()->{
            Pixel p = new Pixel(0.4f,0.1f,0.3f,0.1f);
            p.setFirstChannel(0.7f);
            assertEquals(0.7f, p.getFirstChannel());
        });

        assertThrows(InvalidImageException.class, ()->{
            Pixel p = new Pixel(0.4f,0.1f,0.3f,0.1f);
            p.setFirstChannel(3.5f);
        });
    }

    @Test
    void setSecondChannel(){
        assertDoesNotThrow(()->{
            Pixel p = new Pixel(0.4f,0.1f,0.3f,0.1f);
            p.setSecondChannel(0.7f);
            assertEquals(0.7f, p.getSecondChannel());
        });

        assertThrows(InvalidImageException.class, ()->{
            Pixel p = new Pixel(0.4f,0.1f,0.3f,0.1f);
            p.setSecondChannel(3.5f);
        });
    }

    @Test
    void setThirdChannel() {
        assertDoesNotThrow(()->{
            Pixel p = new Pixel(0.4f,0.1f,0.3f,0.1f);
            p.setThirdChannel(0.7f);
            assertEquals(0.7f, p.getThirdChannel());
        });

        assertThrows(InvalidImageException.class, ()->{
            Pixel p = new Pixel(0.4f,0.1f,0.3f,0.1f);
            p.setThirdChannel(3.5f);
        });
    }

    @Test
    void setFourthChannel() {
        assertDoesNotThrow(()->{
            Pixel p = new Pixel(0.4f,0.1f,0.3f,0.1f);
            p.setFourthChannel(0.7f);
            assertEquals(0.7f, p.getFourthChannel());
        });

        assertThrows(InvalidImageException.class, ()->{
            Pixel p = new Pixel(0.4f,0.1f,0.3f,0.1f);
            p.setFourthChannel(3.5f);
        });
    }
}