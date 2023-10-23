package ch.supsi.ed2d.imageproc.model.filters;

public class BlurFilter extends KernelBasedFilter{

    private static BlurFilter singleton;
    private BlurFilter(){}

    public static BlurFilter getInstance()
    {
        if(singleton == null) {
            singleton = new BlurFilter();
            singleton.setKernel(new float[][]{{1.0f/9,1.0f/9,1.0f/9},{1.0f/9,1.0f/9,1.0f/9},{1.0f/9,1.0f/9,1.0f/9}});
            singleton.setSize(3);
        }

        return singleton;
    }
}
