package io.keepcoding.madridguide.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.adapters.ActivitiesAdapter;
import io.keepcoding.madridguide.model.Activities;
import io.keepcoding.madridguide.model.Activity;
import io.keepcoding.madridguide.views.OnElementClick;


public class ActivitiesFragment extends Fragment {
    private RecyclerView shopsRecyclerView;
    private Activities activities;
    private OnElementClick<Activity> listener;

    public ActivitiesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shops, container, false);
        shopsRecyclerView = (RecyclerView) view.findViewById(R.id.shops_recycler_view);
        shopsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        return view;
    }

    private void updateUI() {
        ActivitiesAdapter adapter = new ActivitiesAdapter(activities, getActivity());
        shopsRecyclerView.setAdapter(adapter);

        adapter.setOnElementClickListener(new OnElementClick<Activity>() {
            @Override
            public void clickedOn(Activity activity, int position) {
                if (listener != null) {
                    listener.clickedOn(activity, position);
                }
            }
        });
    }

    public Activities getActivities() {
        return activities;
    }

    public void setActivities(Activities activities) {
        this.activities = activities;
        updateUI();
    }

    public OnElementClick<Activity> getListener() {
        return listener;
    }

    public void setListener(OnElementClick<Activity> listener) {
        this.listener = listener;
    }
}
