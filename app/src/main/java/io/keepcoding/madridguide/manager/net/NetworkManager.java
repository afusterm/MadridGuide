package io.keepcoding.madridguide.manager.net;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Reader;
import java.io.StringReader;
import java.lang.ref.WeakReference;
import java.util.List;

import io.keepcoding.madridguide.R;

public class NetworkManager {
    public interface GetEntitiesListener {
        void getEntitiesSuccess(List<Entity> result);
        void getEntitiesDidFail();
    }

    private WeakReference<Context> context;

    public NetworkManager(Context context) {
        this.context = new WeakReference<Context>(context);
    }

    public void getShopsFromServer(final @NonNull GetEntitiesListener listener) {
        String url = context.get().getString(R.string.shops_url);
        getEntitiesFromServer(listener, url);
    }

    public void getActivitiesFromServer(final @NonNull GetEntitiesListener listener) {
        String url = context.get().getString(R.string.activities_url);
        getEntitiesFromServer(listener, url);
    }

    private void getEntitiesFromServer(final @NonNull GetEntitiesListener listener, final @NonNull String url) {
        RequestQueue queue = Volley.newRequestQueue(context.get());
        StringRequest request = new StringRequest(
                url,
                new com.android.volley.Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("JSON", response);
                        List<Entity> entityResponse = parseResponse(response);

                        if (listener != null) {
                            listener.getEntitiesSuccess(entityResponse);
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        if (listener != null) {
                            listener.getEntitiesDidFail();
                        }
                    }
                }
        );

        queue.add(request);
    }

    private List<Entity> parseResponse(String response) {
        Reader reader = new StringReader(response);
        Gson gson = new GsonBuilder().create();
        Response resp = gson.fromJson(reader, Response.class);
        return resp.result;
    }
}
