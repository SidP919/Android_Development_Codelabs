package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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

    }
}
