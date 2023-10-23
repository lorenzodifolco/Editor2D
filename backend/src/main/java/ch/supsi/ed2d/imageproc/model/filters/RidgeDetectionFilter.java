package ch.supsi.ed2d.imageproc.model.filters;

public class RidgeDetectionFilter extends KernelBasedFilter{
    private static RidgeDetectionFilter singleton;
    private RidgeDetectionFilter(){}

    public static RidgeDetectionFilter getInstance()
    {
        if(singleton == null) {
            singleton = new RidgeDetectionFilter();
            singleton.setKernel(new float[][]{{-1.0f,-1.0f,-1.0f},{-1.0f,8.0f,-1.0f},{-1.0f,-1.0f,-1.0f}});
            singleton.setSize(3);
        }

        return singleton;
    }
}
