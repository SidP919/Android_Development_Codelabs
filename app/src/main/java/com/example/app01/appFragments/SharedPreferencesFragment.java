package com.example.app01.appFragments;

import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.TextView;

import com.example.app01.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import static android.content.Context.MODE_PRIVATE;

public class SharedPreferencesFragment extends Fragment implements View.OnClickListener {

    // Key for current count
    private final String COUNT_KEY = "count";
    // Key for current color
    private final String COLOR_KEY = "color";
    Button redBgButton, blackBgButton, greenBgButton, blueBgButton;
    Button countUpButton, resetButton;
    //03
    // Current count
    private int mCount = 0;
    // Current background color
    private int mColor;
    // Text view to display both count and color
    private TextView mShowCountTextView;
    //03 ends.
    //11
    private SharedPreferences mPreferences;
    private String sharedPrefFile =
            "com.example.app01.appFragments.SharedPreferencesFragment";
    //11 ends.

    //00
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_shared_preferences, null);
    }//00 ends.

    //01
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //04
        redBgButton = view.findViewById(R.id.red_background_button);
        blackBgButton = view.findViewById(R.id.black_background_button);
        blueBgButton = view.findViewById(R.id.blue_background_button);
        greenBgButton = view.findViewById(R.id.green_background_button);

        countUpButton = view.findViewById(R.id.count_button);
        resetButton = view.findViewById(R.id.reset_button);

        redBgButton.setOnClickListener(this);
        blueBgButton.setOnClickListener(this);
        blackBgButton.setOnClickListener(this);
        greenBgButton.setOnClickListener(this);

        countUpButton.setOnClickListener(this);
        resetButton.setOnClickListener(this);

        // Initialize views, color
        mShowCountTextView = view.findViewById(R.id.count_textview);
        mColor = ContextCompat.getColor(getContext(),
                R.color.default_background);
        //04 ends.

        //12
        mPreferences = getContext().getSharedPreferences(sharedPrefFile, MODE_PRIVATE);
        //12 ends.

        //14
        // Restore preferences
        mCount = mPreferences.getInt(COUNT_KEY, 0);
        mShowCountTextView.setText(String.format("%s", mCount));
        mColor = mPreferences.getInt(COLOR_KEY, mColor);
        mShowCountTextView.setBackgroundColor(mColor);
        //14 ends.

    }//01 ends. go to NavigationDrawerActivity & find SharedPreferencesApp: point //02

    //05
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.red_background_button:
                changeBackground(view);
                break;
            case R.id.black_background_button:
                changeBackground(view);
                break;
            case R.id.blue_background_button:
                changeBackground(view);
                break;
            case R.id.green_background_button:
                changeBackground(view);
                break;
            case R.id.count_button:
                countUp(view);
                break;
            case R.id.reset_button:
                reset(view);
                break;
        }
    }
    //05 ends.

    //06

    /**
     * Handles the onClick for the background color buttons. Gets background
     * color of the button that was clicked, and sets the TextView background
     * to that color.
     *
     * @param view The view (Button) that was clicked.
     */
    public void changeBackground(View view) {
        int color = ((ColorDrawable) view.getBackground()).getColor();
        mShowCountTextView.setBackgroundColor(color);
        mColor = color;
    }
    //06 ends.

    //07

    /**
     * Handles the onClick for the Count button. Increments the value of the
     * mCount global and updates the TextView.
     *
     * @param view The view (Button) that was clicked.
     */
    public void countUp(View view) {
        mCount++;
        mShowCountTextView.setText(String.format("%s", mCount));
    }
    //07 ends.

    //08

    /**
     * Saves the instance state if the activity is restarted (for example,
     * on device rotation.) Here you save the values for the count and the
     * background color.
     *
     * @param outState The state data.
     */
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        Log.d("SharedPreferencesFrag", "OnSaveInstanceState() is called.");

        outState.putInt(COUNT_KEY, mCount);
        outState.putInt(COLOR_KEY, mColor);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);

        // Restore the saved instance state.
        if (savedInstanceState != null) {

            mCount = savedInstanceState.getInt(COUNT_KEY);
            if (mCount != 0) {
                mShowCountTextView.setText(String.format("%s", mCount));
            }

            mColor = savedInstanceState.getInt(COLOR_KEY);
            mShowCountTextView.setBackgroundColor(mColor);
        }
        Log.d("SharedPreferencesFrag", "OnViewStateRestored() is called.");

    }
    //08 ends. go to NavigationDrawerActivity and find SharedPreferencesFragment: point //09

    //10

    /**
     * Handles the onClick for the Reset button. Resets the global count and
     * background variables to the defaults and resets the views to those
     * default values.
     *
     * @param view The view (Button) that was clicked.
     */
    public void reset(View view) {
        // Reset count
        mCount = 0;
        mShowCountTextView.setText(String.format("%s", mCount));

        // Reset color
        mColor = ContextCompat.getColor(getContext(),
                R.color.default_background);
        mShowCountTextView.setBackgroundColor(mColor);

        //15
        // Clear preferences
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.clear();
        preferencesEditor.apply();
        //15 ends.
    }
    //10 ends.

    //13
    @Override
    public void onPause() {
        super.onPause();
        SharedPreferences.Editor preferencesEditor = mPreferences.edit();
        preferencesEditor.putInt(COUNT_KEY, mCount);
        preferencesEditor.putInt(COLOR_KEY, mColor);
        preferencesEditor.apply();

    }
    //13 ends.
}
