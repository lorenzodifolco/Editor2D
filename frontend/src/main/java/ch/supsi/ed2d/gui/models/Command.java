package ch.supsi.ed2d.gui.models;

import java.util.concurrent.CompletableFuture;

public interface Command<T> {
    CompletableFuture<T> execute();
}
