package com.example.app01;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

//00
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class JobSchedulerNotificationService extends JobService {

    // Notification channel ID.
    private static final String THIRD_CHANNEL_ID =
            "Third_notification_channel";
    private static final String NOTIFICATION_FRAGMENT_INTENT_ID = "notificationFragmentIntentId";
    //01
    NotificationManager mNotifyManager;

    @Override
    public boolean onStartJob(JobParameters jobParameters) {
//Create the notification channel
        createNotificationChannel();

        Intent intent = new Intent(getBaseContext(), NavigationDrawerActivity.class);
        intent.putExtra(NOTIFICATION_FRAGMENT_INTENT_ID, 2);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//Set up the notification content intent to launch the app when clicked
        PendingIntent contentPendingIntent = PendingIntent.getActivity
                (getApplicationContext(),
                        2,//this is Notification Id
                        intent,
                        PendingIntent.FLAG_UPDATE_CURRENT);

        //02
        NotificationCompat.Builder builder = new NotificationCompat.Builder
                (this, THIRD_CHANNEL_ID)
                .setContentTitle("Job Service")
                .setContentText("Your Job ran to completion!")
                .setContentIntent(contentPendingIntent)
                .setSmallIcon(R.drawable.ic_job_running_foreground)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setAutoCancel(true);

        mNotifyManager.notify(0, builder.build());
        //02 ends

        return false;
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) {
        return true;
    }

    /**
     * Creates a Notification channel, for OREO and higher.
     */
    public void createNotificationChannel() {

        // Define notification manager object.
        mNotifyManager =
                (NotificationManager) getSystemService(NOTIFICATION_SERVICE);

        // Notification channels are only available in OREO and higher.
        // So, add a check on SDK version.
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {

            // Create the NotificationChannel with all the parameters.
            NotificationChannel notificationChannel = new NotificationChannel
                    (THIRD_CHANNEL_ID,
                            "Job Service notification",
                            NotificationManager.IMPORTANCE_HIGH);

            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription
                    ("Notifications from Job Service");

            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }
}
