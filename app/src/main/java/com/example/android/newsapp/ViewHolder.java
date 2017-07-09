package com.example.android.newsapp;

import android.view.View;
import android.widget.TextView;

public class ViewHolder {
    TextView sectionView;
    TextView typeView;
    TextView storyTitleView;
    TextView authorView;
    TextView dateView;

    public ViewHolder(View view) {
        this.sectionView = (TextView) view.findViewById(R.id.list_section);
        this.typeView = (TextView) view.findViewById(R.id.list_type);
        this.storyTitleView = (TextView) view.findViewById(R.id.list_title);
        this.authorView = (TextView) view.findViewById(R.id.list_author);
        this.dateView = (TextView) view.findViewById(R.id.list_date);
    }
}
