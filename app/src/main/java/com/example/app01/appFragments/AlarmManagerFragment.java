package com.example.app01.appFragments;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.app01.AlarmReceiver;
import com.example.app01.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import static android.content.Context.ALARM_SERVICE;
import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmManagerFragment extends Fragment {

    //03
    //We will use these to display the notification:-
    private static final int NOTIFICATION_ID = 1;
    private static final String SECONDARY_CHANNEL_ID =
            "secondary_notification_channel";
    //02
    private NotificationManager mNotificationManager;
    //03 ends.

    //00
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_alarm_manager, null);
    }

    //00
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        //01
        ToggleButton alarmToggle = view.findViewById(R.id.alarmManagerApp_alarmToggle);
        createNotificationChannel();

        //09
        Intent notifyIntent = new Intent(getContext(), AlarmReceiver.class);

        //12
        boolean alarmUp = (PendingIntent.getBroadcast(getContext(), NOTIFICATION_ID, notifyIntent,
                PendingIntent.FLAG_NO_CREATE) != null);
        alarmToggle.setChecked(alarmUp);
        //12 ends go to AlarmReciever for //13

        //09 continues...
        final PendingIntent notifyPendingIntent = PendingIntent.getBroadcast
                (getContext(), NOTIFICATION_ID, notifyIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        final AlarmManager alarmManager = (AlarmManager) getContext().getSystemService(ALARM_SERVICE);
        //09 ends.

        //01 continues...
        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String toastMessage;
                if (b) {
                    //Set the toast message for the "on" case.
                    toastMessage = "Stand Up Alarm On!";

                    //10
                    long repeatInterval = 4000;
                    long triggerTime = SystemClock.elapsedRealtime()
                            + repeatInterval;

                    //If the Toggle is turned on, set the repeating alarm with a 15 minute interval
                    if (alarmManager != null) {
                        alarmManager.setInexactRepeating
                                (AlarmManager.ELAPSED_REALTIME_WAKEUP,
                                        triggerTime, repeatInterval, notifyPendingIntent);
                    }
                    //10 ends.

                } else {
                    //07
                    //Cancel notification if the alarm is turned off
                    mNotificationManager.cancelAll();
                    //07 ends Now go to AlarmReceiver for //08

                    //01 continues
                    //Set the toast message for the "off" case.
                    toastMessage = "Stand Up Alarm Off!";

                    //11
                    if (alarmManager != null) {
                        alarmManager.cancel(notifyPendingIntent);
                    }
                    //11 ends.
                }

                //Show a toast to say the alarm is turned on or off.
                Toast.makeText(getContext(), toastMessage, Toast.LENGTH_SHORT)
                        .show();
            }
        });
        //01 ends.

        //02
        mNotificationManager = (NotificationManager)
                getContext().getSystemService(NOTIFICATION_SERVICE);
        //02 ends.
    }

    //04

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    public void createNotificationChannel() {

        // Create a notification manager object.
        mNotificationManager =
                (NotificationManager) getContext().getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (SECONDARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        //04 ends go to AlarmReceiver for //05 and //06

    }
}
