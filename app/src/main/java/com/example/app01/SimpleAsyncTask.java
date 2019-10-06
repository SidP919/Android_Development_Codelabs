package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import java.lang.ref.WeakReference;
import java.util.Random;

/**
 * The SimpleAsyncTask activity contains a button that launches an AsyncTask
 * which sleeps in the asynchronous thread for a random amount of time.
 **/
public class SimpleAsyncTask extends AppCompatActivity {

    private TextView mTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_simple_async_task);

        mTextView = findViewById(R.id.simpleAsyncTask_textView1);
    }

    public void startTask(View view) {

        mTextView.setText(R.string.SimpleAsyncTask_progressMessage);//while AsyncTask in background
        //is running

        new MyAsyncTask(mTextView).execute();
        /**
         * The execute() method is where you pass comma-separated parameters that
         * are then passed into doInBackground() by the system.
         * Because this AsyncTask has no parameters, you leave it blank.
         */
    }

    public void searchBook(View view) {
        Intent in = new Intent(this, BooksAsyncTaskLoader.class);
        startActivity(in);
    }

    public class MyAsyncTask extends AsyncTask<Void, Void, String> {

        private WeakReference<TextView> mTextView;

        MyAsyncTask(TextView tv) {
            mTextView = new WeakReference<>(tv);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(Void... voids) {//Async must have doInBackground method atleast

            int num = 11;
            Random r = new Random();
            int n = r.nextInt(num);//produce a random number between 0 and (num-1)

            int s = n * 200;

            try {
                Thread.sleep(s);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "Awake at last after sleeping for " + s + " milliseconds";
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(String s) {//You can update the UI in onPostExecute() because
            // that method is run on the main thread.
            // You cannot update the TextView with the new string in the doInBackground() method,
            // because that method is executed on a separate thread.
            mTextView.get().setText(s);
            super.onPostExecute(s);
        }
    }
}
