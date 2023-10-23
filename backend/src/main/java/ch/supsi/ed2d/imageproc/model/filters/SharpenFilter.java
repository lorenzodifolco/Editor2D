package ch.supsi.ed2d.imageproc.model.filters;

public class SharpenFilter extends KernelBasedFilter{


    private static SharpenFilter singleton;
    private SharpenFilter(){}

    public static SharpenFilter getInstance()
    {
        if(singleton == null) {
            singleton = new SharpenFilter();
            singleton.setKernel(new float[][]{{0.0f,-1.0f,0.0f},{-1.0f,5.0f,-1.0f},{0.0f,-1.0f,0.0f}});
            singleton.setSize(3);
        }

        return singleton;
    }

}
