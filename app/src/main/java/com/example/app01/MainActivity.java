package com.example.app01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private TextView showCount;
    private int counter = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showCount = findViewById(R.id.MainActivity_countTextView);
    }

    public void showToast(View view) {
        Toast toast = Toast.makeText(this, R.string.helloToast, Toast.LENGTH_LONG);
        toast.show();
    }

    public void count(View view) {
        ++counter;
        showCount.setText(Integer.toString(counter));
    }
}
