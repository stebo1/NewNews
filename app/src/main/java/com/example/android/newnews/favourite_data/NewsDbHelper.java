package com.example.android.newnews.favourite_data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CursorAdapter;
import com.example.android.newnews.favourite_data.FavouriteContract.NewsEntry;

public class NewsDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "news.db";

    private static final int DATABASE_VERSION = 1;

    public NewsDbHelper (Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_NEWS_TABLE =  "CREATE TABLE " + NewsEntry.TABLE_NAME + " ("
                + NewsEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + NewsEntry.COLUMN_NEWS_TITLE + " TEXT, "
                + NewsEntry.COLUMN_NEWS_DATE + " TEXT, "
                + NewsEntry.COLUMN_NEWS_SURCE + " TEXT, "
                + NewsEntry.COLUMN_NEWS_DESCRIPTION + " TEXT, "
                + NewsEntry.COLUMN_NEWS_URL + " TEXT, "
                + NewsEntry.COLUMN_NEWS_IMAGE_URL + " TEXT, "
                + NewsEntry.COLUMN_NEWS_AUTHOR + " TEXT);";

        db.execSQL(SQL_CREATE_NEWS_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
