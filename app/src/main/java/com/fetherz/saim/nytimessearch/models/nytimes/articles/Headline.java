
package com.fetherz.saim.nytimessearch.models.nytimes.articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

@Parcel
public class Headline {

    @SerializedName("main")
    @Expose
    String main;

    @SerializedName("print_headline")
    @Expose
    String printHeadline;

    public Headline() {}

    public String getMain() {
        return main;
    }

    public void setMain(String main) {
        this.main = main;
    }

    public String getPrintHeadline() {
        return printHeadline;
    }

    public void setPrintHeadline(String printHeadline) {
        this.printHeadline = printHeadline;
    }

}