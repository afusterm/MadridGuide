package io.keepcoding.madridguide.interactors;

import android.content.Context;

import io.keepcoding.madridguide.manager.db.ActivityDAO;
import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.util.MainThread;

public class CacheAllActivitiesInteractor {
    public void execute(final Context context, final Activities activities,
                        final CacheAllInteractorResponse response) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                ActivityDAO dao = new ActivityDAO(context);

                boolean success = true;
                for (Activity activity: activities.allElements()) {
                    success = dao.insert(activity) > 0;

                    if (!success) {
                        break;
                    }
                }

                final boolean activitiesInserted = success;

                MainThread.run(new Runnable() {
                    @Override
                    public void run() {
                        if (response != null) {
                            response.response(activitiesInserted);
                        }
                    }
                });
            }
        }).start();
    }
}
