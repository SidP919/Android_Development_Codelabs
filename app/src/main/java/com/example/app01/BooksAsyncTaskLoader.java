package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class BooksAsyncTaskLoader extends AppCompatActivity {

    private EditText mBookInput;
    private TextView mTitleText;
    private TextView mAuthorText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_books_async_task_loader);
        mBookInput = (EditText) findViewById(R.id.BookAsyncTaskLoader_id_editText01);
        mTitleText = (TextView) findViewById(R.id.BookAsyncTaskLoader_id_titleTV);
        mAuthorText = (TextView) findViewById(R.id.BookAsyncTaskLoader_id_authorTV);
    }

    public void searchTheBook(View view) {
        String queryString = mBookInput.getText().toString();

        //HidetheKeyboard
        InputMethodManager inputManager = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);

        if (inputManager != null) {
            inputManager.hideSoftInputFromWindow(view.getWindowToken(),
                    InputMethodManager.HIDE_NOT_ALWAYS);
        }

        if (queryString.length() == 0) {
            Toast.makeText(this, "Enter a keyword to search a book.", Toast.LENGTH_SHORT).show();
            return;
        }

        if (NetworkUtils.isNetworkConnected(getApplicationContext())) {
            new FetchBook(mTitleText, mAuthorText).execute(queryString);

            mTitleText.setText("");
            mAuthorText.setText(R.string.BooksAsyncTaskLoader_string_loading);
        } else {
            Toast.makeText(this, "Please check your network connection", Toast.LENGTH_LONG).show();
        }
    }
}
