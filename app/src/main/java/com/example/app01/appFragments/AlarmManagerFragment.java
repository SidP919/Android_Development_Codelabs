package com.example.app01.appFragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.app01.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import static android.content.Context.NOTIFICATION_SERVICE;

public class AlarmManagerFragment extends Fragment {

    //03
    //We will use these to display the notification:-
    private static final int NOTIFICATION_ID = 0;
    private static final String PRIMARY_CHANNEL_ID =
            "primary_notification_channel";
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

        alarmToggle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                String toastMessage;
                if (b) {
                    //Set the toast message for the "on" case.
                    toastMessage = "Stand Up Alarm On!";
                } else {
                    //07
                    //Cancel notification if the alarm is turned off
                    mNotificationManager.cancelAll();
                    //07 ends

                    //01 continues
                    //Set the toast message for the "off" case.
                    toastMessage = "Stand Up Alarm Off!";
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
                    (PRIMARY_CHANNEL_ID,
                            "Stand up notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifies every 15 minutes to stand up and walk");
            mNotificationManager.createNotificationChannel(notificationChannel);
        }
        //04 ends.

    }

    //05
    private void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context, getActivity().getClass());
        contentIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);

        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (context, NOTIFICATION_ID, contentIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        contentIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        /*
        PendingIntent flags tell the system how to handle the situation when multiple instances of
        the same PendingIntent are created (meaning that the instances contain the same Intent). The
        FLAG_UPDATE_CURRENT flag tells the system to use the old Intent but replace the extras data.
        Because you don't have any extras in this Intent,
        you can use the same PendingIntent over and over.
        */

        //05 ends.

        //06
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, PRIMARY_CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_stand_up_foreground)
                .setContentTitle("Stand Up Alert")
                .setContentText("You should stand up and walk around now!")
                .setContentIntent(contentPendingIntent)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL);

        mNotificationManager.notify(NOTIFICATION_ID, builder.build());
        //06 ends

    }
}
