package com.example.app01.appFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.widget.Button;

import com.example.app01.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class NotificationsFragment extends Fragment implements View.OnClickListener {

    private final static String PRIMARY_CHANNEL_ID = "primary_notification_channel";//Every
    private Button notifyMeButton;
    private Button updateButton;
    private Button cancelButton;
    // notification channel must be associated with an ID that is unique within your package.
    // You use this channel ID later,
    // to post your notifications.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        notifyMeButton = view.findViewById(R.id.notifyMeButton);
        notifyMeButton.setOnClickListener(this);

        updateButton = view.findViewById(R.id.updateNotificationButton);
        updateButton.setOnClickListener(this);

        cancelButton = view.findViewById(R.id.cancelNotificationButton);
        cancelButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.notifyMeButton:
                sendNotifications();
                break;
            case R.id.updateNotificationButton:

                break;
            case R.id.cancelNotificationButton:

                break;
        }
    }

    private void sendNotifications() {

    }
}
