package io.keepcoding.madridguide.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.views.ActivityRowViewHolder;
import io.keepcoding.madridguide.views.OnElementClick;


public class ActivitiesAdapter extends RecyclerView.Adapter<ActivityRowViewHolder> {
    private final LayoutInflater layoutInflater;
    private final Activities activities;

    private OnElementClick<Activity> listener;

    public ActivitiesAdapter(Activities activities, Context context) {
        this.activities = activities;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ActivityRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_entity, parent, false);

        return new ActivityRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ActivityRowViewHolder row, final int position) {
        final Activity activity = activities.get(position);
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
        return (int) activities.size();
    }

    public void setOnElementClickListener(@NonNull final OnElementClick listener) {
        this.listener = listener;
    }
}
