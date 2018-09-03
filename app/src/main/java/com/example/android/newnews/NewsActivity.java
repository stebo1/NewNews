package com.example.android.newnews;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.CheckBox;
import android.widget.TextView;
import com.example.android.newnews.favourite_data.FavouriteContract.NewsEntry;

import com.example.android.newnews.favourite_data.FavouriteContract;
import com.example.android.newnews.favourite_data.NewsDbHelper;

public class NewsActivity extends AppCompatActivity {

    private static boolean mChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);

        NewsDbHelper mNewsDbHelper = new NewsDbHelper(NewsActivity.this);

        TextView title = (TextView) findViewById(R.id.news_activity_title);
        TextView date = (TextView) findViewById(R.id.news_activity_date);
        TextView description = (TextView) findViewById(R.id.news_activity_description);
        TextView author = (TextView) findViewById(R.id.news_activity_author);

        SQLiteDatabase databaseRead = mNewsDbHelper.getReadableDatabase();
        SQLiteDatabase databaseWrite = mNewsDbHelper.getWritableDatabase();

        /*String[] projection = {NewsEntry.COLUMN_NEWS_TITLE, NewsEntry.COLUMN_NEWS_DATE,
                NewsEntry.COLUMN_NEWS_SURCE, NewsEntry.COLUMN_NEWS_DESCRIPTION,
                NewsEntry.COLUMN_NEWS_URL, NewsEntry.COLUMN_NEWS_IMAGE_URL,
                NewsEntry.COLUMN_NEWS_AUTHOR, };*/
        Cursor cursor = databaseRead.query(
                NewsEntry.TABLE_NAME,       // The table to query
                null,                 // The array of columns to return (pass null to get all)
                null,               // The columns for the WHERE clause
                null,            // The values for the WHERE clause
                null,               // don't group the rows
                null,                // don't filter by row groups
                null                // The sort order
        );

        cursor.moveToLast();
        String titleString = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_TITLE));
        String descriptionString = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_DESCRIPTION));
        String surceString = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_SURCE));
        String dateString = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_DATE));
        String urlString = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_URL));
        String imageUrlString = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_IMAGE_URL));
        String authorString = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_AUTHOR));
        NewsItem CurrentNews = new NewsItem(titleString, dateString, surceString, descriptionString, urlString, imageUrlString, authorString);

        title.setText(CurrentNews.getTitle());
        date.setText(CurrentNews.getDate());
        description.setText(CurrentNews.getDescriprion());
        author.setText(CurrentNews.getAuthor());

        String[] currentTitle = { CurrentNews.getTitle() };
        int n = databaseWrite.delete(FavouriteContract.NewsEntry.TABLE_NAME,
                NewsEntry.COLUMN_NEWS_TITLE + "=?", currentTitle);
        Log.i("mews", "removed " +n+ " item");

        if (mChecked){
            ContentValues values = new ContentValues();
            values.put(NewsEntry.COLUMN_NEWS_TITLE, CurrentNews.getTitle());
            values.put(NewsEntry.COLUMN_NEWS_DATE, CurrentNews.getDate());
            values.put(NewsEntry.COLUMN_NEWS_SURCE, CurrentNews.getSurce());
            values.put(NewsEntry.COLUMN_NEWS_DESCRIPTION, CurrentNews.getDescriprion());
            values.put(NewsEntry.COLUMN_NEWS_URL, CurrentNews.getUrl());
            values.put(NewsEntry.COLUMN_NEWS_IMAGE_URL, CurrentNews.getImageUrl());
            values.put(NewsEntry.COLUMN_NEWS_AUTHOR, CurrentNews.getAuthor());

            long num = databaseWrite.insert(NewsEntry.TABLE_NAME, null, values);
            Log.i("news", "added news item " + num);
        }

    }

    public static void setChecked(boolean checked) {
        mChecked = checked;
    }
}
