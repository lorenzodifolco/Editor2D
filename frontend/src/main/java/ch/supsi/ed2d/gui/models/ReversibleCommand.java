package ch.supsi.ed2d.gui.models;

import java.util.concurrent.CompletableFuture;

public interface ReversibleCommand<T> extends Command<T> {
    CompletableFuture<T> undo();
}
