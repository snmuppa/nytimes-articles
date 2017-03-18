package com.fetherz.saim.nytimessearch.models.nytimes.articles;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import java.util.List;

@Parcel
public class Doc {

    @SerializedName("web_url")
    @Expose
    String webUrl;

    @SerializedName("snippet")
    @Expose
    String snippet;

    @SerializedName("lead_paragraph")
    @Expose
    String leadParagraph;

    @SerializedName("print_page")
    @Expose
    int printPage;


    @SerializedName("source")
    @Expose
    String source;

    @SerializedName("multimedia")
    @Expose
    List<Multimedium> multimedia = null;

    @SerializedName("headline")
    @Expose
    Headline headline;

    @SerializedName("keywords")
    @Expose
    List<Keyword> keywords = null;

    @SerializedName("pub_date")
    @Expose
    String pubDate;

    @SerializedName("document_type")
    @Expose
    String documentType;

    @SerializedName("news_desk")
    @Expose
    String newsDesk;

    @SerializedName("section_name")
    @Expose
    String sectionName;

    @SerializedName("byline")
    @Expose
    Byline byline;

    @SerializedName("type_of_material")
    @Expose
    String typeOfMaterial;

    @SerializedName("_id")
    @Expose
    String id;

    @SerializedName("word_count")
    @Expose
    int wordCount;

    public Doc() {}

    public String getWebUrl() {
        return webUrl;
    }

    public void setWebUrl(String webUrl) {
        this.webUrl = webUrl;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getLeadParagraph() {
        return leadParagraph;
    }

    public void setLeadParagraph(String leadParagraph) {
        this.leadParagraph = leadParagraph;
    }

    public int getPrintPage() {
        return printPage;
    }

    public void setPrintPage(int printPage) {
        this.printPage = printPage;
    }


    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public List<Multimedium> getMultimedia() {
        return multimedia;
    }

    public void setMultimedia(List<Multimedium> multimedia) {
        this.multimedia = multimedia;
    }

    public Headline getHeadline() {
        return headline;
    }

    public void setHeadline(Headline headline) {
        this.headline = headline;
    }

    public List<Keyword> getKeywords() {
        return keywords;
    }

    public void setKeywords(List<Keyword> keywords) {
        this.keywords = keywords;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getNewsDesk() {
        return newsDesk;
    }

    public void setNewsDesk(String newsDesk) {
        this.newsDesk = newsDesk;
    }

    public String getSectionName() {
        return sectionName;
    }

    public void setSectionName(String sectionName) {
        this.sectionName = sectionName;
    }

    public Byline getByline() {
        return byline;
    }

    public void setByline(Byline byline) {
        this.byline = byline;
    }

    public String getTypeOfMaterial() {
        return typeOfMaterial;
    }

    public void setTypeOfMaterial(String typeOfMaterial) {
        this.typeOfMaterial = typeOfMaterial;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

}