package com.example.app01;

import android.net.Uri;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class NetworkUtils {

    private final static String LOG_TAG = NetworkUtils.class.getSimpleName();

    // Base URL for Books API.
    private static final String BOOK_BASE_URL = "https://www.googleapis.com/books/v1/volumes?";
    // Parameter for the search string.
    private static final String QUERY_PARAM = "q";
    // Parameter that limits search results.
    private static final String MAX_RESULTS = "maxResults";
    // Parameter to filter by print type.
    private static final String PRINT_TYPE = "printType";

    public static String getBookInfo(String queryString) {

        //Following variables for connecting to the internet, reading the incoming data, and
        // holding the response string:-
        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;
        String bookJSONString = null;

        try {

            //It is common practice to separate all of these query parameters into constants,
            // and combine them using an Uri.Builder so
            // they can be reused for different URIs.
            // The Uri class has a convenient method,
            // Uri.buildUpon(), that returns a URI.Builder
            Uri builtURI = Uri.parse(BOOK_BASE_URL).buildUpon()
                    .appendQueryParameter(QUERY_PARAM, queryString)
                    .appendQueryParameter(MAX_RESULTS, "10")
                    .appendQueryParameter(PRINT_TYPE, "books")
                    .build();

            //Convert your URI to a URL object
            URL requestURL = new URL(builtURI.toString());

            //This API request uses the HttpURLConnection class in combination with
            // an InputStream, BufferedReader, and
            // a StringBuffer to obtain the JSON response from the web.
            // If at any point the process fails and
            // InputStream or StringBuffer are empty,
            // the request returns null,
            // signifying that the query failed
            urlConnection = (HttpURLConnection) requestURL.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();


            //setting up the response from the connection using
            // an InputStream,
            // a BufferedReader and
            // a StringBuilder:----

            // Get the InputStream.
            InputStream inputStream = urlConnection.getInputStream();
            // Create a buffered reader from that input stream.
            reader = new BufferedReader(new InputStreamReader(inputStream));
            // Use a StringBuilder to hold the incoming response.
            StringBuilder builder = new StringBuilder();

            //Read the input line-by-line into the string while there is still input
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
                // Since it's JSON, adding a newline isn't necessary (it won't
                // affect parsing) but it does make debugging a *lot* easier
                // if you print out the completed buffer for debugging.
                builder.append("\n");
            }

            //check the string to see if there is existing response content.
            // Return null if the response is empty:-
            if (builder.length() == 0) {
                // Stream was empty. No point in parsing.
                return null;
            }

            //Convert the StringBuilder object to a String and
            // store it in the bookJSONString variable
            bookJSONString = builder.toString();

        } catch (IOException e) {

            e.printStackTrace();
        } finally {

            //close both the connection and the BufferedReader

            if (urlConnection != null) {
                urlConnection.disconnect();
            }

            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        Log.d(LOG_TAG, bookJSONString);
        return bookJSONString;
    }
}
