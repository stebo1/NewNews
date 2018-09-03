package com.example.android.newnews;

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

public class QueryUtils {

    public static URL createUrl (String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {
            Log.e("createUrl", "Problem building the URL ", e);
        }
        return url;
    }

    private static String makeHttpRequest (URL url) throws IOException{
        String JsonResponse = "";

        if (url == null) {
            return JsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(20000 /* millisecond */);
            urlConnection.setConnectTimeout(25000 /* millisecond */);
            urlConnection.setRequestMethod("GET");
            Log.e("HttpRequest", "prima");
            urlConnection.connect();
            Log.e("HttpRequest", "dopo");

            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                JsonResponse = readFromStream(inputStream);
            } else {
                Log.e("HttpRequest", "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("HttpRequest", "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return JsonResponse;
    }

    private static String readFromStream (InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String line = bufferedReader.readLine();
            while (line != null) {
                output.append(line);
                line = bufferedReader.readLine();
            }
        }
        return output.toString();
    }

    public static ArrayList<NewsItem> extractFeatureFromJson (String NewsJson) {
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(NewsJson)) {
            return null;
        }

        // Create an empty ArrayList that we can start adding News to
        ArrayList<NewsItem> News = new ArrayList<>();

        // Try to parse the JSON response string. If there's a problem with the way the JSON
        // is formatted, a JSONException exception object will be thrown.
        // Catch the exception so the app doesn't crash, and print the error message to the logs.
        try {
            String title, description, surce, date, newsUrl, imageUrl, author;

            JSONObject mainJson = new JSONObject(NewsJson);

            JSONArray newsList = mainJson.getJSONArray("articles");

            for (int i = 0; i < newsList.length(); i++) {
                JSONObject newsItem = newsList.getJSONObject(i);
                title = newsItem.getString("title");
                description = newsItem.getString("description");
                JSONObject source = newsItem.getJSONObject("source");
                surce = source.getString("name");
                date = newsItem.getString("publishedAt");
                date = date.substring(0, 10);
                newsUrl = newsItem.getString("url");
                imageUrl = newsItem.getString("urlToImage");
                author = newsItem.getString("author");
                News.add(new NewsItem(title, date, surce, description, newsUrl, imageUrl, author));
            }

        } catch (JSONException e) {
            // If an error is thrown when executing any of the above statements in the "try" block,
            // catch the exception here, so the app doesn't crash. Print a log message
            // with the message from the exception.
            Log.e("extractFeatureFromJson", "Problem parsing the news JSON results", e);
        }

        // Return the list of earthquakes
        return News;
    }

    public static ArrayList<NewsItem> fetchNewsData (String requestUrl) {
        URL url = createUrl(requestUrl);

        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e("fetchNewsData", "Problem making the HTTP request.", e);
        }

        ArrayList<NewsItem> News = extractFeatureFromJson(jsonResponse);
        return News;
    }

}
