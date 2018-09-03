package com.example.android.newnews.favourite_data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

public class FavouriteContract {

    public static final String CONTENT_AUTHORITY = "com.example.android.newnews";

    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    public static final String PATH_NEWS = "news";

    public static final class NewsEntry implements BaseColumns {

        public static final Uri CONTENT_URI = Uri.withAppendedPath(BASE_CONTENT_URI, PATH_NEWS);

        public static final String CONTENT_LIST_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;

        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_NEWS;

        public static final String TABLE_NAME = "news";

        public final static String _ID = BaseColumns._ID;

        public final static String COLUMN_NEWS_TITLE ="title";
        public final static String COLUMN_NEWS_DESCRIPTION = "description";
        public final static String COLUMN_NEWS_SURCE = "surce";
        public final static String COLUMN_NEWS_DATE = "date";
        public final static String COLUMN_NEWS_URL = "url";
        public final static String COLUMN_NEWS_IMAGE_URL = "image_url";
        public final static String COLUMN_NEWS_AUTHOR = "author";


    }

}
