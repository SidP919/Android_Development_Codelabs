package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class UIHelperActivity extends AppCompatActivity {

    private final static String LOG_TAG = UIHelperActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(LOG_TAG, "UIHelperAcitvity got created.");
    }
}
