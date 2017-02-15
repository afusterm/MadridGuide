package io.keepcoding.madridguide;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Activity;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertSame;

public class ActivitiesTests {
    @Test
    public void testEmptyActivities() {
        Activities activities = Activities.build();
        assertEquals(0, activities.size());
    }

    @Test
    public void testThatCreatingActivitiesWithAListItsTheSameAsList() {
        List<Activity> activityList = new ArrayList<>();
        activityList.add(new Activity(1, "activity1"));
        activityList.add(new Activity(2, "activity2"));
        Activities activities = Activities.build(activityList);

        assertEquals(2, activities.size());
        assertSame(activityList.get(0), activities.get(0));
        assertSame(activityList.get(1), activities.get(1));
    }
}
