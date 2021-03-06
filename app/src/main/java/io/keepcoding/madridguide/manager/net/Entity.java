package io.keepcoding.madridguide.manager.net;

import com.google.gson.annotations.SerializedName;

public class Entity {
    @SerializedName("id") private Long id;
    @SerializedName("name") private String name;
    @SerializedName("img") private String img;
    @SerializedName("logo_img") private String logoImg;
    @SerializedName("address") private String address;
    @SerializedName("url") private String url;
    @SerializedName("description_es") private String descriptionEs;
    @SerializedName("gps_lat") private float latitude;
    @SerializedName("gps_lon") private float longitude;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImg() {
        return img;
    }

    public String getLogoImg() {
        return logoImg;
    }

    public String getAddress() {
        return address;
    }

    public String getUrl() {
        return url;
    }

    public String getDescriptionEs() {
        return descriptionEs;
    }

    public float getLatitude() {
        return latitude;
    }

    public float getLongitude() {
        return longitude;
    }
}
