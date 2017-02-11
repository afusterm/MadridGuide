package io.keepcoding.madridguide.manager.net;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response {
    @SerializedName("result")
    List<Entity> result;
}
