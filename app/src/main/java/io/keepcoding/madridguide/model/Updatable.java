package io.keepcoding.madridguide.model;

public interface Updatable<E> {
    void add(E element);
    void delete(E element);
    void edit(E newElement, long index);
}
