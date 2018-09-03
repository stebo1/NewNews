package com.example.android.newnews;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.android.newnews.favourite_data.FavouriteContract;
import com.example.android.newnews.favourite_data.NewsDbHelper;
import com.example.android.newnews.favourite_data.FavouriteContract.NewsEntry;

import java.util.ArrayList;

public class NewsAdapter extends ArrayAdapter<NewsItem> {

    public NewsAdapter (Context context, ArrayList<NewsItem> News) {
        super(context, 0, News);
    }

    @Override
    public View getView(int position, final View convertView, ViewGroup parent) {
        View itemListView = convertView;
        if (itemListView == null) {
            itemListView = LayoutInflater.from(getContext()).inflate(R.layout.item_news, parent, false);
        }

        final NewsItem currentNews = getItem(position);

        final TextView title = (TextView) itemListView.findViewById(R.id.news_title);
        TextView date = (TextView) itemListView.findViewById(R.id.news_date);

        title.setText(currentNews.getTitle());
        date.setText(currentNews.getDate());

        Log.i("NewsAdapter", "fine popolamento di news_item");


        final CheckBox NewsItemCheck = itemListView.findViewById(R.id.checkBox);

        NewsDbHelper mNewsDbHelper = new NewsDbHelper(getContext());
        SQLiteDatabase databaseRead = mNewsDbHelper.getReadableDatabase();


        String[] projection = {NewsEntry.COLUMN_NEWS_TITLE };
        Cursor cursor = databaseRead.query(
                NewsEntry.TABLE_NAME,       // The table to query
                projection,                 // The array of columns to return (pass null to get all)
                null,               // The columns for the WHERE clause
                null,            // The values for the WHERE clause
                null,               // don't group the rows
                null,                // don't filter by row groups
                null                // The sort order
        );

        Log.i("adapter", "before set check");

        NewsItemCheck.setChecked(false);

        cursor.moveToPosition(-1);
        boolean thereIsSomethig = cursor.moveToNext();
        while (thereIsSomethig == true) {
            Log.i("adapter", "in while loop");
            String titleInTable = cursor.getString(cursor.getColumnIndex(NewsEntry.COLUMN_NEWS_TITLE));
            String titleInObject = currentNews.getTitle();
            Log.i("adapter", "titleInTable = " +titleInTable+ ".");
            Log.i("adapter", "titleInObject = " +titleInObject+ ".");
            if ( titleInTable.equals(titleInObject)) {
                Log.i("adapter", "SAME TITLE!");
                NewsItemCheck.setChecked(true);
            }
            thereIsSomethig = cursor.moveToNext();
        }

        Log.i("adapter", "isFavourite = " +NewsItemCheck.isChecked());


        NewsItemCheck.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //Log.i("adapter", "clicked");

                NewsDbHelper mNewsDbHelper = new NewsDbHelper(getContext());
                SQLiteDatabase databaseRead = mNewsDbHelper.getReadableDatabase();
                SQLiteDatabase databaseWrite = mNewsDbHelper.getWritableDatabase();

                if (NewsItemCheck.isChecked() == true){

                    ContentValues values = new ContentValues();
                    values.put(NewsEntry.COLUMN_NEWS_TITLE, currentNews.getTitle());
                    values.put(NewsEntry.COLUMN_NEWS_DATE, currentNews.getDate());
                    values.put(NewsEntry.COLUMN_NEWS_SURCE, currentNews.getSurce());
                    values.put(NewsEntry.COLUMN_NEWS_DESCRIPTION, currentNews.getDescriprion());
                    values.put(NewsEntry.COLUMN_NEWS_URL, currentNews.getUrl());
                    values.put(NewsEntry.COLUMN_NEWS_IMAGE_URL, currentNews.getImageUrl());
                    values.put(NewsEntry.COLUMN_NEWS_AUTHOR, currentNews.getAuthor());

                    long id = databaseWrite.insert(NewsEntry.TABLE_NAME, null, values);
                    Log.i("adapter", "added news item ");
                } else {
                    String[] currentTitle = {currentNews.getTitle()};
                    int n = databaseWrite.delete(NewsEntry.TABLE_NAME,
                            NewsEntry.COLUMN_NEWS_TITLE + "=?", currentTitle);
                    Log.i("adapter", "removed " +n+ " item");
                }

                //count the total item saved in the database;
                String[] projection = {NewsEntry.COLUMN_NEWS_TITLE };
                Cursor cursor = databaseRead.query(
                        NewsEntry.TABLE_NAME,       // The table to query
                        projection,                 // The array of columns to return (pass null to get all)
                        null,               // The columns for the WHERE clause
                        null,            // The values for the WHERE clause
                        null,               // don't group the rows
                        null,                // don't filter by row groups
                        null                // The sort order
                );
                cursor.moveToLast();
                int tot = cursor.getPosition();
                Log.i("adapter", tot+ " total items");
            }
            });

        title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.i("adapter", "title clicked");

                NewsDbHelper mNewsDbHelper = new NewsDbHelper(getContext());
                SQLiteDatabase databaseWrite = mNewsDbHelper.getWritableDatabase();

                ContentValues values = new ContentValues();
                values.put(NewsEntry.COLUMN_NEWS_TITLE, currentNews.getTitle());
                values.put(NewsEntry.COLUMN_NEWS_DATE, currentNews.getDate());
                values.put(NewsEntry.COLUMN_NEWS_SURCE, currentNews.getSurce());
                values.put(NewsEntry.COLUMN_NEWS_DESCRIPTION, currentNews.getDescriprion());
                values.put(NewsEntry.COLUMN_NEWS_URL, currentNews.getUrl());
                values.put(NewsEntry.COLUMN_NEWS_IMAGE_URL, currentNews.getImageUrl());
                values.put(NewsEntry.COLUMN_NEWS_AUTHOR, currentNews.getAuthor());

                long num = databaseWrite.insert(NewsEntry.TABLE_NAME, null, values);
                //Log.i("main", "added news item " + num);
                if (NewsItemCheck.isChecked()) {
                    NewsActivity.setChecked(true);
                }
                else {
                    NewsActivity.setChecked(false);
                }
                Intent intent = new Intent(getContext(),NewsActivity.class);
                ContextCompat.startActivity(getContext(), intent, null);
            }
        });

        return itemListView;
    }

}
