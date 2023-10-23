package ch.supsi.ed2d.imageproc.model;

import ch.supsi.ed2d.imageproc.InvalidImageException;
import ch.supsi.ed2d.imageproc.model.filters.Filter;

import java.util.ArrayList;
import java.util.List;

public class Pipeline implements Filter {

    private final List<Filter> filters=new ArrayList<>();

    public void clearPipeline()
    {
        filters.clear();
    }

    public int filterLength()
    {
        return filters.size();
    }


    public void addFilter(Filter filter)
    {
        filters.add(filter);
    }

    public void addFilterIndex(int i, Filter filter) {filters.add(i,filter);}

    @Override
    public Image apply(Image img) throws InvalidImageException {
        for (Filter filter : filters)
            img=filter.apply(img);
        return img;
    }

    public void remove(int i) {
        filters.remove(i);
    }
}
