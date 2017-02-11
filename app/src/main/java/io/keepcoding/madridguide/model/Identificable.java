package io.keepcoding.madridguide.model;

public interface Identificable<E> {
    long getId();
    E setId(long id);
}
