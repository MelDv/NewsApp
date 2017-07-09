package com.example.android.newsapp;


import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

public final class StoryUtils {
    public static final String LOG_TAG = StoryUtils.class.getSimpleName();

    //Don't delete!
    private StoryUtils() {
    }

    public static List<Story> extractStories(String requestUrl) {
        URL url = createUrl(requestUrl);
        String jsonResponse = null;

        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        List<Story> stories = extractStoryFromJson(jsonResponse);

        return stories;
    }

    private static List<Story> extractStoryFromJson(String storyJSON) {
        List<Story> stories = new ArrayList<>();

        if (TextUtils.isEmpty(storyJSON)) {
            return null;
        }

        try {
            JSONObject json = new JSONObject(storyJSON);
            JSONObject response = json.getJSONObject("response");
            JSONArray storyArray = response.getJSONArray("results");

            for (int i = 0; i < storyArray.length(); i++) {
                JSONObject currentStory = storyArray.getJSONObject(i);

                String section = currentStory.getString("sectionName");
                String type = currentStory.getString("type");
                type = type.substring(0, 1).toUpperCase() + type.substring(1);
                String title = currentStory.getString("webTitle");
                String author = "";
                if (currentStory.has("author")) {
                    author = currentStory.getString("author");
                }
                String date = currentStory.getString("webPublicationDate");
                date = formatDate(date);
                String storyUrl = currentStory.getString("webUrl");

                Story story = new Story(section, type, title, author, date, storyUrl);
                stories.add(story);
            }

        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return stories;
    }

    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error with creating URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the earthquake JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }

    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    private static String formatDate(String date) {
        String dateParts[] = date.split("T");
        String newParts[] = dateParts[0].split("-");
        String finalDate = newParts[2] + "." + newParts[1] + "." + newParts[0];

        return finalDate;
    }
}
