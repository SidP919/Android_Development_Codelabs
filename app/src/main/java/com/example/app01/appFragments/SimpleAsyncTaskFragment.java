package com.example.app01.appFragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.app01.R;

import java.lang.ref.WeakReference;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SimpleAsyncTaskFragment extends Fragment implements View.OnClickListener {
    private TextView mTextView;
    private Button mStartTaskButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_simple_async_task, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mTextView = view.findViewById(R.id.simpleAsyncTask_textView1);
        mStartTaskButton = view.findViewById(R.id.simpleAsyncTask_button);
        mStartTaskButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.simpleAsyncTask_button) {
            mTextView.setText(R.string.SimpleAsyncTask_progressMessage);//while AsyncTask in background
            //is running

            new MyAsyncTask(mTextView).execute();
            /**
             * The execute() method is where you pass comma-separated parameters that
             * are then passed into doInBackground() by the system.
             * Because this AsyncTask has no parameters, you leave it blank.
             */
        }
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
