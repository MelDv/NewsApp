package com.example.android.newsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<List<Story>> {
    public static final String LOG_TAG = MainActivity.class.getName();
    private static final int NEWS_LOADER = 0;
    private static final String URL_STORIES = "http://content.guardianapis.com/search?q=startrek&api-key=760a7188-9308-495b-9b9b-13fddd261baa";
    private TextView emptyStateTextView;
    private StoryAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        emptyStateTextView = (TextView) findViewById(R.id.empty);
        ListView list = (ListView) findViewById(R.id.list);
        list.setEmptyView(emptyStateTextView);

        adapter = new StoryAdapter(this, new ArrayList<Story>());
        list.setAdapter(adapter);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                Story story = adapter.getItem(position);
                String url = story.getUrl();

                Intent urlIntent = new Intent(Intent.ACTION_VIEW);
                urlIntent.setData(Uri.parse(url));
                startActivity(urlIntent);
            }
        });

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER, null, this);
        } else {
            View loadingIndicator = findViewById(R.id.loading_spinner);
            loadingIndicator.setVisibility(View.GONE);
            emptyStateTextView.setText(R.string.no_connection);
        }
    }

    @Override
    public Loader<List<Story>> onCreateLoader(int i, Bundle bundle) {
        Log.i(LOG_TAG, "onCreateLoader");
        if (URL_STORIES != null) {
            return new StoryLoader(this, URL_STORIES);
        }
        Log.i(LOG_TAG, "RETURNING NULL");
        return null;
    }

    @Override
    public void onLoadFinished(Loader<List<Story>> loader, List<Story> stories) {
        adapter.clear();
        adapter.notifyDataSetChanged();

        ProgressBar pb = (ProgressBar) findViewById(R.id.loading_spinner);
        pb.setVisibility(View.GONE);
        if (stories != null && !stories.isEmpty()) {
            adapter.addAll(stories);
        } else {
            emptyStateTextView.setText(getString(R.string.no_stories));
        }
        Log.i(LOG_TAG, "onLoadFinished");
    }

    @Override
    public void onLoaderReset(Loader<List<Story>> loader) {
        adapter.clear();
        Log.i(LOG_TAG, "onLoaderReset");
    }
}
