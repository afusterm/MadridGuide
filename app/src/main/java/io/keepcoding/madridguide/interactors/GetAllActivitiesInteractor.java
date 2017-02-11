package io.keepcoding.madridguide.interactors;

import android.content.Context;

import java.util.List;

import io.keepcoding.madridguide.manager.net.Entity;
import io.keepcoding.madridguide.manager.net.NetworkManager;
import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.model.mappers.ActivityEntityActivityMapper;

public class GetAllActivitiesInteractor {
    public void execute(final Context context, final GetAllActivitiesInteractorResponse response) {
        NetworkManager networkManager = new NetworkManager(context);
        networkManager.getActivitiesFromServer(new NetworkManager.GetEntitiesListener() {
            @Override
            public void getEntitiesSuccess(List<Entity> result) {
                List<Activity> activities = new ActivityEntityActivityMapper().map(result);

                if (response != null) {
                    response.response(Activities.build(activities));
                }
            }

            @Override
            public void getEntitiesDidFail() {
                if (response != null) {
                    response.response(null);
                }
            }
        });
    }
}
