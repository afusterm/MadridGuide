package io.keepcoding.madridguide.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Shops extends BaseModelAggregate<Shop> {
    private Shops() {
        super();
    }

    private Shops(List<Shop> shopList) {
        super(shopList);
    }

    public static @NonNull Shops build(@NonNull final List<Shop> shopList) {
        return new Shops(shopList);
    }

    public static @NonNull Shops build() {
        return build(new ArrayList<Shop>());
    }
}
