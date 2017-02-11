package io.keepcoding.madridguide.model;

import android.support.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class Activities extends BaseModelAggregate<Activity> {
    private Activities() {
        super();
    }

    private Activities(List<Activity> activityList) {
        super(activityList);
    }

    public static @NonNull
    Activities build(@NonNull final List<Activity> activityList) {
        return new Activities(activityList);
    }

    public static @NonNull Activities build() {
        return build(new ArrayList<Activity>());
    }
}
