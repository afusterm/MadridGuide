package io.keepcoding.madridguide.views;

public interface OnElementClick<T> {
    public void clickedOn(T model, int position);
}
