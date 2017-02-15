package io.keepcoding.madridguide;

import android.support.annotation.NonNull;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


public class ShopsTests {
    @Test
    public void testCreatingAShopsWithNullListReturnsNonNullShops() {
        Shops sut = Shops.build(null);
        assertNotNull(sut);
        assertNotNull(sut.allElements());
    }

    @Test
    public void testCreatingAShopsWithAListReturnsNonNullShops() {
        List<Shop> data = getShops();

        Shops sut = Shops.build(data);
        assertNotNull(sut);
        assertNotNull(sut.allElements());
        assertEquals(sut.allElements(), data);
        assertEquals(sut.allElements().size(), data.size());
    }

    @NonNull
    private List<Shop> getShops() {
        List<Shop> data = new ArrayList<>();
        data.add(new Shop(1, "1").setAddress("AD 1"));
        data.add(new Shop(2, "2").setAddress("AD 2"));
        return data;
    }
}
