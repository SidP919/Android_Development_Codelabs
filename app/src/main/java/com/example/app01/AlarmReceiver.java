package com.example.app01;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import androidx.core.app.NotificationCompat;

public class AlarmReceiver extends BroadcastReceiver {

    private static final int NOTIFICATION_ID = 1;
    // Notification channel ID.
    private static final String SECONDARY_CHANNEL_ID =
            "secondary_notification_channel";
    //13
    private static final String NOTIFICATION_FRAGMENT_INTENT_ID = "notificationFragmentIntentId";
    //08
    //08
    private NotificationManager mNotificationManager;
    //13

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //08
        mNotificationManager = (NotificationManager)
                context.getSystemService(Context.NOTIFICATION_SERVICE);
        deliverNotification(context);
        //08 ends go to AlarmManagerFragment for //09
//        throw new UnsupportedOperationException("Not yet implemented");
    }

    //05
    public void deliverNotification(Context context) {
        Intent contentIntent = new Intent(context, NavigationDrawerActivity.class);
        //13 continues...
        contentIntent.putExtra(NOTIFICATION_FRAGMENT_INTENT_ID, 1);
        //13 ends go to NavigationDrawerActivity for //14
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
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, SECONDARY_CHANNEL_ID)
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
