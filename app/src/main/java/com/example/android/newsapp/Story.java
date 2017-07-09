package com.example.android.newsapp;

import android.support.v7.app.AppCompatActivity;

public class Story extends AppCompatActivity {
    private String section;
    private String type;
    private String storyTitle;
    private String author;
    private String date;
    private String url;

    public Story(String section, String type, String storyTitle, String author, String date, String url) {
        this.section = section;
        this.type = type;
        this.storyTitle = storyTitle;
        this.author = author;
        this.date = date;
        this.url = url;
    }

    public String getSection() {
        return section;
    }

    public String getType() {
        return type;
    }

    public String getStoryTitle() {
        return storyTitle;
    }

    public String getAuthor() {
        return author;
    }

    public String getDate() {
        return date;
    }

    public String getUrl() {
        return url;
    }
}
