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
import io.keepcoding.madridguide.model.Shop;
import io.keepcoding.madridguide.model.Shops;
import io.keepcoding.madridguide.views.OnElementClick;
import io.keepcoding.madridguide.views.ShopRowViewHolder;

public class ShopsAdapter extends RecyclerView.Adapter<ShopRowViewHolder> {
    private final LayoutInflater layoutInflater;
    private final Shops shops;
    private Shops filteredShops;

    private OnElementClick<Shop> listener;

    public ShopsAdapter(Shops shops, Context context) {
        this.shops = shops;
        filteredShops = shops;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public ShopRowViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = layoutInflater.inflate(R.layout.row_entity, parent, false);

        return new ShopRowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ShopRowViewHolder row, final int position) {
        final Shop shop = filteredShops.get(position);
        row.setShop(shop);
        row.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ShopsAdapter.this.listener != null) {
                    listener.clickedOn(shop, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return (int) filteredShops.size();
    }

    public void setOnElementClickListener(@NonNull final OnElementClick listener) {
        this.listener = listener;
    }

    public void setFilter(final String filter) {
        if (filter == null || filter.isEmpty()) {
            filteredShops = shops;
        } else {
            List<Shop> shopList = new ArrayList<>();
            for (int i = 0; i < shops.size(); i++) {
                Shop shop = shops.get(i);
                if (shop.getName().toLowerCase().contains(filter)) {
                    shopList.add(shop);
                }
            }

            filteredShops = Shops.build(shopList);
        }

        notifyDataSetChanged();
    }
}
