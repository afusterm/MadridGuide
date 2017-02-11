package io.keepcoding.madridguide.model.mappers;

import java.util.LinkedList;
import java.util.List;

import io.keepcoding.madridguide.manager.net.Entity;
import io.keepcoding.madridguide.model.Activity;

public class ActivityEntityActivityMapper {
    public List<Activity> map(List<Entity> activityEntities) {
        List<Activity> result = new LinkedList<>();

        for (Entity entity: activityEntities) {
            Activity activity = new Activity(entity.getId(), entity.getName());

            // TODO: detect current language

            activity.setDescription(entity.getDescriptionEs());
            activity.setLogoImgUrl(entity.getLogoImg());
            activity.setAddress(entity.getAddress());
            activity.setLatitude(entity.getLatitude());
            activity.setLongitude(entity.getLongitude());
            activity.setImageUrl(entity.getImg());
            // ...

            result.add(activity);
        }

        return result;
    }
}
