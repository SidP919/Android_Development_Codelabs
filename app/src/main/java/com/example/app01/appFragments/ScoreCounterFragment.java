package com.example.app01.appFragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app01.NavigationDrawerActivity;
import com.example.app01.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ScoreCounterFragment extends Fragment implements View.OnClickListener {

    private TextView showCount;
    private int counter = 0;
    private Button toastButton;
    private Button showCountButton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_main,null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        showCount = view.findViewById(R.id.MainActivity_countTextView);

        toastButton = view.findViewById(R.id.MainActivity_toastButton);
        toastButton.setOnClickListener(this);

        showCountButton = view.findViewById(R.id.MainActivity_countButton);
        showCountButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.MainActivity_countButton:
                ++counter;
                showCount.setText(Integer.toString(counter));
                break;

            case R.id.MainActivity_toastButton:
                Toast toast = Toast.makeText(getContext(), R.string.helloToast, Toast.LENGTH_LONG);
                toast.show();
                break;
        }
    }
}
