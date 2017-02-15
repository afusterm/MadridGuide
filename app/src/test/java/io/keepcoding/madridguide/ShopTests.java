package io.keepcoding.madridguide;

import org.junit.Test;

import io.keepcoding.madridguide.model.Shop;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;


public class ShopTests {
    private static final String SHOP = "shop";
    private static final String ADDRESS = "ADDRESS";
    private static final String DESC = "DESC";

    @Test
    public void testCanCreateAShop() {
        Shop sut = new Shop(0, SHOP);
        assertNotNull(sut);
    }

    @Test
    public void testANewShopStoresDataCorrectly() {
        Shop sut = new Shop(10, SHOP);
        assertEquals(10, sut.getId());
        assertEquals(SHOP, sut.getName());
    }

    @Test
    public void testANewShopStoresDataInPropertiesCorrectly() {
        Shop sut = new Shop(11, SHOP)
                .setAddress(ADDRESS)
                .setDescription(DESC)
                .setImageUrl("URL");
        assertEquals(sut.getAddress(), ADDRESS);
        assertEquals(sut.getDescription(), DESC);
    }
}
