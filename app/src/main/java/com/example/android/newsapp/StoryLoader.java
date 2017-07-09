package com.example.android.newsapp;

import android.content.AsyncTaskLoader;
import android.content.Context;
import android.util.Log;

import java.util.List;

public class StoryLoader extends AsyncTaskLoader<List<Story>> {
    private static final String LOG_TAG = StoryLoader.class.getName();
    private String url;

    public StoryLoader(Context context, String url) {
        super(context);
        this.url = url;
    }

    @Override
    protected void onStartLoading() {
        Log.i(LOG_TAG, "onStartLoading");
        forceLoad();
    }

    @Override
    public List<Story> loadInBackground() {
        Log.i(LOG_TAG, "loadInBackground");
        if (url == null) {
            return null;
        }
        List<Story> stories = StoryUtils.extractStories(url);

        return stories;
    }
}
