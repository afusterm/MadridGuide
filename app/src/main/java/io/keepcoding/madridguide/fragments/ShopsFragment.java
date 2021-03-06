package io.keepcoding.madridguide.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.adapters.ShopsAdapter;
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;
import io.keepcoding.madridguide.views.OnElementClick;


public class ShopsFragment extends Fragment {
    private RecyclerView shopsRecyclerView;
    private ShopsAdapter adapter;
    private Shops shops;
    private OnElementClick<Shop> listener;

    public ShopsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_shops, container, false);
        shopsRecyclerView = (RecyclerView) view.findViewById(R.id.shops_recycler_view);
        shopsRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        setHasOptionsMenu(true);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {;
        inflater.inflate(R.menu.menu_list, menu);
        final MenuItem menuItem = menu.findItem(R.id.menu_list_action_search);
        final SearchView searchView = (SearchView) MenuItemCompat.getActionView(menuItem);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                adapter.setFilter(newText);
                return true;
            }
        });
    }

    private void updateUI() {
        adapter = new ShopsAdapter(shops, getActivity());
        shopsRecyclerView.setAdapter(adapter);

        adapter.setOnElementClickListener(new OnElementClick<Shop>() {
            @Override
            public void clickedOn(Shop shop, int position) {
                if (listener != null) {
                    listener.clickedOn(shop, position);
                }
            }
        });
    }

    public Shops getShops() {
        return shops;
    }

    public void setShops(Shops shops) {
        this.shops = shops;
        updateUI();
    }

    public OnElementClick<Shop> getListener() {
        return listener;
    }

    public void setListener(OnElementClick<Shop> listener) {
        this.listener = listener;
    }
}
