package com.fetherz.saim.nytimessearch.models.nytimes.articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Article {

    @SerializedName("response")
    @Expose
    Response response;
    @SerializedName("status")
    @Expose
    String status;
    @SerializedName("copyright")
    @Expose
    String copyright;

    public Article() {}

    public Response getResponse() {
        return response;
    }

    public void setResponse(Response response) {
        this.response = response;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCopyright() {
        return copyright;
    }

    public void setCopyright(String copyright) {
        this.copyright = copyright;
    }

    @Override
    public String toString() {
        return "Article{" +
                "response=" + response +
                ", status='" + status + '\'' +
                ", copyright='" + copyright + '\'' +
                '}';
    }
}