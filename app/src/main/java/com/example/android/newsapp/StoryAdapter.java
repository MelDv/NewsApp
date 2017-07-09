package com.example.android.newsapp;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

public class StoryAdapter extends ArrayAdapter<Story> {
    private static final String LOG_TAG = StoryAdapter.class.getName();
    ViewHolder viewHolder;

    public StoryAdapter(Context context, ArrayList<Story> quakes) {
        super(context, 0, quakes);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item, parent, false);

            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Story currentStory = getItem(position);

        viewHolder.sectionView.setText(currentStory.getSection());
        viewHolder.typeView.setText(currentStory.getType());
        viewHolder.storyTitleView.setText(currentStory.getStoryTitle());
        viewHolder.authorView.setText(currentStory.getAuthor());
        viewHolder.dateView.setText(currentStory.getDate());

        GradientDrawable sectionBackground = (GradientDrawable) viewHolder.sectionView.getBackground();
        int sectionColor = getSectionColor(currentStory.getSection());
        sectionBackground.setColor(sectionColor);

        return convertView;
    }

    private int getSectionColor(String section) {
        int sectionColorId;

        switch (section) {
            case "Politics":
                sectionColorId = R.color.politics;
                break;
            case "Culture":
                sectionColorId = R.color.culture;
                break;
            case "Music":
                sectionColorId = R.color.music;
                break;
            case "Technology":
                sectionColorId = R.color.technology;
                break;
            case "Business":
                sectionColorId = R.color.business;
                break;
            case "Lifestyle":
                sectionColorId = R.color.lifestyle;
                break;
            case "Travel":
                sectionColorId = R.color.travel;
                break;
            case "Fashion":
                sectionColorId = R.color.fashion;
                break;
            case "Television & radio":
            case "Entertainment":
            case "Film":
                sectionColorId = R.color.tv_radio_film;
                break;
            case "Environment":
                sectionColorId = R.color.environment;
                break;
            default:
                sectionColorId = R.color.other;
                break;
        }
        return ContextCompat.getColor(getContext(), sectionColorId);
    }
}
