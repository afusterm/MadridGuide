package io.keepcoding.madridguide.model;

import java.util.List;

public interface Iterable<E> {
    long size();
    E get(long index);
    List<E> allElements();
}
