package com.example.app01;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

public class BookLoader extends AsyncTaskLoader<String> {

    private String mQueryString;// to hold the string for the Books API query

    //Modify the constructor to take a String as an argument and assign it to the
    // mQueryString variable:
    //Previous code:-
//    public BookLoader(@NonNull Context context) {
//        super(context);
//    }
    //New code:-
    public BookLoader(@NonNull Context context, String queryString) {
        super(context);
        mQueryString = queryString;
    }

    @Nullable
    @Override
    public String loadInBackground() {
        return NetworkUtils.getBookInfo(mQueryString);
    }

    @Override
    protected void onStartLoading() {//The system calls this method when you start the loader.
        super.onStartLoading();

        forceLoad();// to start the loadInBackground() method. The loader will not start loading
        // data until you call the forceLoad() method.
    }
}
