package com.fetherz.saim.nytimessearch.models.nytimes.articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Meta {

    @SerializedName("hits")
    @Expose
    int hits;

    @SerializedName("time")
    @Expose
    int time;

    @SerializedName("offset")
    @Expose
    int offset;

    public Meta() {}

    public int getHits() {
        return hits;
    }

    public void setHits(int hits) {
        this.hits = hits;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public int getOffset() {
        return offset;
    }

    public void setOffset(int offset) {
        this.offset = offset;
    }

}