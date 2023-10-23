package ch.supsi.ed2d.imageproc;

@FunctionalInterface
public interface CheckedFunction<T,S> {
    void apply(T t1, S t2) throws InvalidImageException;
}
