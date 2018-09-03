package com.example.android.newnews;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ListView;

import com.example.android.newnews.favourite_data.FavouriteContract;
import com.example.android.newnews.favourite_data.NewsDbHelper;
import com.example.android.newnews.favourite_data.FavouriteContract.NewsEntry;

import java.util.ArrayList;

public class FavouriteActivity extends AppCompatActivity {

    private static NewsAdapter mNewsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        NewsDbHelper mNewsDbHelper = new NewsDbHelper(this);
        SQLiteDatabase databaseRead = mNewsDbHelper.getReadableDatabase();

        ListView newsListView = findViewById(R.id.list_favourite);

        /*String[] projection = {NewsEntry.COLUMN_NEWS_TITLE, NewsEntry.COLUMN_NEWS_DATE,
                NewsEntry.COLUMN_NEWS_SURCE, NewsEntry.COLUMN_NEWS_DESCRIPTION,
                NewsEntry.COLUMN_NEWS_URL, NewsEntry.COLUMN_NEWS_IMAGE_URL,
                NewsEntry.COLUMN_NEWS_AUTHOR};*/
        Cursor cursor = databaseRead.query(
                NewsEntry.TABLE_NAME,       // The table to query
                null,                 // The array of columns to return (pass null to get all)
                null,               // The columns for the WHERE clause
                null,            // The values for the WHERE clause
                null,               // don't group the rows
                null,                // don't filter by row groups
                null                // The sort order
        );

        cursor.moveToFirst();
        cursor.moveToPrevious();
        ArrayList<NewsItem> News = new ArrayList<>();
        Log.i("favourite_activity", "before while loop");
        while (cursor.moveToNext()) {
            Log.i("favourite_activity", "start while loop");
            String title = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_TITLE));
            String description = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_DESCRIPTION));
            String surce = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_SURCE));
            String date = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_DATE));
            String url = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_URL));
            String imageUrl = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_IMAGE_URL));
            String author = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_AUTHOR));
            News.add(new NewsItem(title, date, surce, description, url, imageUrl, author));
        }

        mNewsAdapter = new NewsAdapter(this, News);
        newsListView.setAdapter(mNewsAdapter);

    }
}
