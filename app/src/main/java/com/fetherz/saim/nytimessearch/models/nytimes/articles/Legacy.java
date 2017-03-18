package com.fetherz.saim.nytimessearch.models.nytimes.articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Legacy {

    @SerializedName("thumbnailheight")
    @Expose
    int thumbnailheight;

    @SerializedName("thumbnail")
    @Expose
    String thumbnail;

    @SerializedName("thumbnailwidth")
    @Expose
    int thumbnailwidth;

    @SerializedName("xlargewidth")
    @Expose
    int xlargewidth;

    @SerializedName("xlarge")
    @Expose
    String xlarge;

    @SerializedName("xlargeheight")
    @Expose
    int xlargeheight;

    @SerializedName("wide")
    @Expose
    String wide;

    @SerializedName("widewidth")
    @Expose
    int widewidth;

    @SerializedName("wideheight")
    @Expose
    int wideheight;

    public Legacy() {}

    public int getThumbnailheight() {
        return thumbnailheight;
    }

    public void setThumbnailheight(int thumbnailheight) {
        this.thumbnailheight = thumbnailheight;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public int getThumbnailwidth() {
        return thumbnailwidth;
    }

    public void setThumbnailwidth(int thumbnailwidth) {
        this.thumbnailwidth = thumbnailwidth;
    }

    public int getXlargewidth() {
        return xlargewidth;
    }

    public void setXlargewidth(int xlargewidth) {
        this.xlargewidth = xlargewidth;
    }

    public String getXlarge() {
        return xlarge;
    }

    public void setXlarge(String xlarge) {
        this.xlarge = xlarge;
    }

    public int getXlargeheight() {
        return xlargeheight;
    }

    public void setXlargeheight(int xlargeheight) {
        this.xlargeheight = xlargeheight;
    }

    public String getWide() {
        return wide;
    }

    public void setWide(String wide) {
        this.wide = wide;
    }

    public int getWidewidth() {
        return widewidth;
    }

    public void setWidewidth(int widewidth) {
        this.widewidth = widewidth;
    }

    public int getWideheight() {
        return wideheight;
    }

    public void setWideheight(int wideheight) {
        this.wideheight = wideheight;
    }

}