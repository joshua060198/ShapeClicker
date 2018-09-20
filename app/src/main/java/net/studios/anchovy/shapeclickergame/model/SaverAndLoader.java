package net.studios.anchovy.shapeclickergame.model;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface SaverAndLoader <T> {
    void save(T data) throws IOException;
    T load() throws IOException;
}
