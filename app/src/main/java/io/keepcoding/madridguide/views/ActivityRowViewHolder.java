package io.keepcoding.madridguide.views;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.lang.ref.WeakReference;

import io.keepcoding.madridguide.R;
import io.keepcoding.madridguide.model.Activity;


public class ActivityRowViewHolder extends RecyclerView.ViewHolder {
    private TextView nameTextView;
    private ImageView logoImageView;
    private ImageView backgroundImageView;
    private WeakReference<Context> context;

    public ActivityRowViewHolder(View rowActivity) {
        super(rowActivity);

        context = new WeakReference<>(rowActivity.getContext());
        nameTextView = (TextView) rowActivity.findViewById(R.id.row_entity_name);
        logoImageView = (ImageView) rowActivity.findViewById(R.id.row_entity_logo);
        backgroundImageView = (ImageView) rowActivity.findViewById(R.id.row_entity_background_image);
    }

    public void setActivity(final @NonNull Activity activity) {
        if (activity == null) {
            return;
        }

        nameTextView.setText(activity.getName());
        Picasso.with(context.get())
                .load(activity.getLogoImgUrl())
                .placeholder(android.R.drawable.ic_dialog_email)
                .into(logoImageView);
        Picasso.with(context.get())
                .load(activity.getImageUrl())
                .into(backgroundImageView);
    }
}
