package com.example.android.newnews;

public class NewsItem {

    private String mTitle;
    private String mDescription;
    private String mSurce;
    private String mUrl;
    private String mDate;
    private String mImageUrl;
    private String mAuthor;

    public NewsItem (String title, String date, String surce, String description, String url, String imageUrl, String author) {
        mTitle = title;
        mDescription = description;
        mSurce = surce;
        mDate = date;
        mUrl = url;
        mImageUrl = imageUrl;
        mAuthor = author;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getImageUrl() {
        return mImageUrl;
    }

    public String getDate() {
        return mDate;
    }

    public String getUrl() {
        return mUrl;
    }

    public String getDescriprion() {
        return mDescription;
    }

    public String getSurce() {
        return mSurce;
    }

    public String getTitle() {
        return mTitle;
    }
}
