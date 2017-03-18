package com.fetherz.saim.nytimessearch.models.nytimes.articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Multimedium {

    @SerializedName("width")
    @Expose
    int width;

    @SerializedName("url")
    @Expose
    String url;

    @SerializedName("rank")
    @Expose
    int rank;

    @SerializedName("height")
    @Expose
    int height;

    @SerializedName("subtype")
    @Expose
    String subtype;

    @SerializedName("legacy")
    @Expose
    Legacy legacy;

    @SerializedName("type")
    @Expose
    String type;

    public Multimedium() {}

    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getRank() {
        return rank;
    }

    public void setRank(int rank) {
        this.rank = rank;
    }

    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public Legacy getLegacy() {
        return legacy;
    }

    public void setLegacy(Legacy legacy) {
        this.legacy = legacy;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}