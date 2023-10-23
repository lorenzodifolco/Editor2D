package ch.supsi.ed2d.imageproc.model;

import ch.supsi.ed2d.imageproc.InvalidImageException;

import java.util.Objects;

public class Pixel {

    private static final String INVALID_IMAGE_CHANNEL_RGB_EXCEPTION_MESSAGE = "RGB channels must be made of channels with values between 0 and 255";
    private static final String INVALID_IMAGE_CHANNEL_EXCEPTION_MESSAGE = "Channel must be a value between 0 and 1";
    private float firstChannel;
    private float secondChannel;
    private float thirdChannel;
    private float fourthChannel;

    public static Pixel rgb(int r, int g, int b) throws InvalidImageException
    {
        return new Pixel(r,g,b, 1, 255);
    }

    public static Pixel rgba(int r, int g, int b, float a) throws InvalidImageException
    {
        return new Pixel(r,g,b,a, 255);
    }

    public static Pixel triplets(int a, int b, int c, int scale) throws InvalidImageException { return new Pixel(a, b, c, 1,  scale); }

    private Pixel(int firstChannel, int secondChannel, int thirdChannel, float fourthChannel, float scale) throws InvalidImageException{
        setFirstChannel(firstChannel / scale);
        setSecondChannel(secondChannel / scale);
        setThirdChannel(thirdChannel / scale);
        setFourthChannel(fourthChannel);
    }

    public Pixel(float firstChannel, float secondChannel, float thirdChannel, float fourthChannel) throws InvalidImageException {
        setFirstChannel(firstChannel);
        setSecondChannel(secondChannel);
        setThirdChannel(thirdChannel);
        setFourthChannel(fourthChannel);
    }

    public float getFirstChannel() { return firstChannel; }

    public float getSecondChannel() { return secondChannel; }

    public float getThirdChannel() { return thirdChannel; }

    public float getFourthChannel() {
        return fourthChannel;
    }


    public int getR() { return (int)(getFirstChannel() *255); }
    public int getG() {
        return (int)(getSecondChannel() *255);
    }

    public int getB() { return (int)(getThirdChannel() *255); }

    public float getA() { return getFourthChannel(); }

    public void setFirstChannel(float firstChannel) throws InvalidImageException{
        if(firstChannel >= 0 && firstChannel <= 1)
            this.firstChannel = firstChannel;
        else throw new InvalidImageException("");
    }

    public void setSecondChannel(float secondChannel) throws InvalidImageException{
        if(secondChannel >= 0 && secondChannel <= 1)
            this.secondChannel = secondChannel;
        else throw new InvalidImageException(INVALID_IMAGE_CHANNEL_EXCEPTION_MESSAGE);
    }

    public void setThirdChannel(float thirdChannel) throws InvalidImageException {
        if(thirdChannel >= 0 && thirdChannel <= 1)
            this.thirdChannel = thirdChannel;
        else throw new InvalidImageException(INVALID_IMAGE_CHANNEL_EXCEPTION_MESSAGE);
    }

    public void setFourthChannel(float fourthChannel) throws InvalidImageException{
        if(fourthChannel >= 0 && fourthChannel <= 1)
            this.fourthChannel = fourthChannel;
        else throw new InvalidImageException(INVALID_IMAGE_CHANNEL_EXCEPTION_MESSAGE);
    }

    public void setR(int r) throws InvalidImageException{
        if(r >= 0 && r <= 255)
            setFirstChannel(r / 255f);
        else throw new InvalidImageException(INVALID_IMAGE_CHANNEL_RGB_EXCEPTION_MESSAGE);
    }
    public void setG(int g) throws InvalidImageException{
        if(g >= 0 && g <= 255)
            setSecondChannel(g / 255f);
        else throw new InvalidImageException(INVALID_IMAGE_CHANNEL_RGB_EXCEPTION_MESSAGE);
    }

    public void setB(int b) throws InvalidImageException{
        if(b >= 0 && b <= 255)
            setThirdChannel(b / 255f);
        else throw new InvalidImageException(INVALID_IMAGE_CHANNEL_RGB_EXCEPTION_MESSAGE);
    }

    public void setA(float a) throws InvalidImageException
    {
        setFourthChannel(a);
    }

    public void setRgb(int r, int g, int b) throws InvalidImageException{
        setR(r);
        setG(g);
        setB(b);
        setA(1f);
    }

    public void setRgba(int r, int g, int b, float a) throws InvalidImageException
    {
        setR(r);
        setG(g);
        setB(b);
        setA(a);
    }

    public void setTriplets(int a, int b, int c, int scale) throws InvalidImageException
    {
        setFirstChannel(a / (float)scale);
        setSecondChannel(b / (float)scale);
        setThirdChannel(c / (float)scale);
        setFourthChannel(1);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pixel pixel = (Pixel) o;
        return Float.compare(pixel.firstChannel, firstChannel) == 0 && Float.compare(pixel.secondChannel, secondChannel) == 0 && Float.compare(pixel.thirdChannel, thirdChannel) == 0 && Float.compare(pixel.fourthChannel, fourthChannel) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstChannel, secondChannel, thirdChannel, fourthChannel);
    }
}

