package io.keepcoding.madridguide.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.views.ActivityRowViewHolder;
import io.keepcoding.madridguide.views.OnElementClick;


public class ActivitiesAdapter extends RecyclerView.Adapter<ActivityRowViewHolder> {
    private final LayoutInflater layoutInflater;
    private final Activities activities;
    private Activities filteredActivities;

    private OnElementClick<Activity> listener;

    public ActivitiesAdapter(Activities activities, Context context) {
        this.activities = activities;
        filteredActivities = activities;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ActivityRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_entity, parent, false);

        return new ActivityRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityRowViewHolder row, final int position) {
        final Activity activity = filteredActivities.get(position);
        row.setActivity(activity);
        row.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ActivitiesAdapter.this.listener != null) {
                    listener.clickedOn(activity, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) filteredActivities.size();
    }

    public void setOnElementClickListener(@NonNull final OnElementClick listener) {
        this.listener = listener;
    }

    public void setFilter(final String filter) {
        if (filter == null || filter.isEmpty()) {
            filteredActivities = activities;
        } else {
            List<Activity> activityList = new ArrayList<>();
            for (int i = 0; i < activities.size(); i++) {
                Activity a = activities.get(i);
                if (a.getName().toLowerCase().contains(filter)) {
                    activityList.add(a);
                }
            }

            filteredActivities = Activities.build(activityList);
        }

        notifyDataSetChanged();
    }
}
