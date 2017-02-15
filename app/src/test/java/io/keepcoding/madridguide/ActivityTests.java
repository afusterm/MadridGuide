package io.keepcoding.madridguide;

import org.junit.Test;

import io.keepcoding.madridguide.model.Activity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;

public class ActivityTests {
    private static final String ACTIVITY = "activity";
    private static final String ADDRESS = "ADDRESS";
    private static final String DESC = "DESC";

    @Test
    public void testCanCreateAnActivity() {
        Activity sut = new Activity(0, ACTIVITY);
        assertNotNull(sut);
    }

    @Test
    public void testANewActivityStoresDataCorrectly() {
        Activity sut = new Activity(10, ACTIVITY);
        assertEquals(10, sut.getId());
        assertEquals(ACTIVITY, sut.getName());
    }

    @Test
    public void testANewActivityStoresDataInPropertiesCorrectly() {
        Activity sut = new Activity(11, ACTIVITY)
                .setAddress(ADDRESS)
                .setDescription(DESC)
                .setImageUrl("URL");
        assertEquals(sut.getAddress(), ADDRESS);
        assertEquals(sut.getDescription(), DESC);
    }
}
