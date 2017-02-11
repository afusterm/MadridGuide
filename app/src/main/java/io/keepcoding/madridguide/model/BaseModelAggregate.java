package io.keepcoding.madridguide.model;

import java.util.ArrayList;
import java.util.List;

public class BaseModelAggregate<E extends BaseModel> implements Iterable<E>, Updatable<E> {
    private List<E> elements;

    BaseModelAggregate() {
    }

    BaseModelAggregate(List<E> list) {
        elements = list;

        if (elements == null) {
            elements = new ArrayList<>();
        }
    }

    @Override
    public long size() {
        return elements.size();
    }

    @Override
    public E get(long index) {
        return elements.get((int)index);
    }

    @Override
    public List<E> allElements() {
        return elements;
    }

    @Override
    public void add(E element) {
        elements.add(element);
    }

    @Override
    public void delete(E element) {
        elements.remove(element);
    }

    @Override
    public void edit(E newElement, long index) {
        elements.set((int) index, newElement);
    }
}
