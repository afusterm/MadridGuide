package io.keepcoding.madridguide.model;

import java.util.List;

public interface IShopsIterateble {
    long size();
    Shop get(long index);
    List<Shop> allShops();
}
