package com.example.app01;

import android.os.AsyncTask;
import android.widget.TextView;

import java.lang.ref.WeakReference;

public class FetchBook extends AsyncTask<String, Void, String> {
    private WeakReference<TextView> mTitleText;
    private WeakReference<TextView> mAuthorText;

    FetchBook(TextView titleTV, TextView authorTV) {
        this.mTitleText = new WeakReference<>(titleTV);
        this.mAuthorText = new WeakReference<>(authorTV);
    }

    @Override
    protected String doInBackground(String... strings) {
        String ans = "";
        return ans;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);

    }
}
