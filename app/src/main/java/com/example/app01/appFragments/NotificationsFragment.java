package com.example.app01.appFragments;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.app01.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import static android.content.Context.NOTIFICATION_SERVICE;

public class NotificationsFragment extends Fragment implements View.OnClickListener {

    //01
    private final static String PRIMARY_CHANNEL_ID = "primary_notification_channel";//Every
    // notification channel must be associated with an ID that is unique within your package.
    // You use this channel ID later,
    // to post your notifications.

    //02
    private static final int NOTIFICATION_ID = 0;//You need to associate the notification with a
    // notification ID so that your code can update or cancel
    // the notification in the future.

    private static final String NOTIFICATION_FRAGMENT_INTENT_ID = "notificationFragmentIntentId";

    //03
    private Button notifyMeButton;
    private Button updateButton;
    private Button cancelButton;

    //04
    // The Android system uses the NotificationManager class to deliver notifications to the user
    private NotificationManager mNotifyManager;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_notifications, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //05
        notifyMeButton = view.findViewById(R.id.notifyMeButton);
        notifyMeButton.setOnClickListener(this);

        updateButton = view.findViewById(R.id.updateNotificationButton);
        updateButton.setOnClickListener(this);

        cancelButton = view.findViewById(R.id.cancelNotificationButton);
        cancelButton.setOnClickListener(this);

        //15
        setNotificationButtonState(true, false, false);

        //06
        // you must call createNotificationChannel(). If you miss this step, your app crashes!
        createNotificationChannel();
    }

    //05
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.notifyMeButton:
                //07
                sendNotifications();
                break;
            case R.id.updateNotificationButton:
                //13
                updateNotification();
                break;
            case R.id.cancelNotificationButton:
                //14
                cancelNotification();
                break;
        }
    }

    //06
    //Notification channels are only available in API 26 and higher,
    // add a condition to check for the device's API version.
    public void createNotificationChannel() {
        mNotifyManager = (NotificationManager)
                getContext().getSystemService(NOTIFICATION_SERVICE);
        if (android.os.Build.VERSION.SDK_INT >=
                android.os.Build.VERSION_CODES.O) {
            // Create a NotificationChannel
            NotificationChannel notificationChannel = new NotificationChannel(PRIMARY_CHANNEL_ID,
                    "Mascot Notification", NotificationManager
                    .IMPORTANCE_HIGH);

            // configure the notificationChannel object's initial settings
            notificationChannel.enableLights(true);
            notificationChannel.setLightColor(Color.RED);
            notificationChannel.enableVibration(true);
            notificationChannel.setDescription("Notification from Mascot");
            mNotifyManager.createNotificationChannel(notificationChannel);
        }
    }

    //09
    //Notifications are created using the NotificationCompat.Builder class,
    // which allows you to set the content and behavior of the notification.
    // A notification can contain the following elements:-----
    //
    //Icon (required), which you set in your code using the setSmallIcon() method.
    //Title (optional), which you set using setContentTitle().
    //Detail text (optional), which you set using setContentText().
    private NotificationCompat.Builder getNotificationBuilder() {

        //10 From here:

        //when the user taps the notification,
        // your app sends a content intent that launches the NotificationsFragment
        Intent notificationIntent = new Intent(getContext(), getActivity().getClass());
        notificationIntent.putExtra(NOTIFICATION_FRAGMENT_INTENT_ID, 3);

        //By using a PendingIntent to communicate with another app, you are telling that app to execute some predefined code at some point in the future. It's like the other app can perform an action on behalf of your app.
        PendingIntent notificationPendingIntent = PendingIntent.getActivity(getContext(),
                NOTIFICATION_ID, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        //10 Till above.

        //09
        NotificationCompat.Builder notifyBuilder = new NotificationCompat.Builder(getContext(), PRIMARY_CHANNEL_ID)
                .setContentTitle("There's a new notification for you:--")
                .setContentText("Now press the update button.")
                .setSmallIcon(R.drawable.profile_icon_img_foreground);

        //below line is also under //10:
        notifyBuilder.setContentIntent(notificationPendingIntent)
                .setAutoCancel(true);//Setting auto-cancel to true closes the notification when
        // user taps on it.
        //now go to onCreateMethod of NavigationDrawerActivity to see point //11

        //12
        notifyBuilder.setPriority(NotificationCompat.PRIORITY_HIGH);//HIGH or MAX priority
        // notifications are delivered as "heads up" notifications, which drop down on
        // top of the user's active screen. Use MAX rarely unless you want the user to be irritated.

        //12
        notifyBuilder.setDefaults(NotificationCompat.DEFAULT_ALL);//The high-priority notification
        // will not drop down in front of the active screen unless both the priority and
        // the defaults are set.
        // Setting the priority alone is not enough.

        //09
        return notifyBuilder;
    }

    //08
    private void sendNotifications() {
        setNotificationButtonState(false, true, true);//15
        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();
        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    //14
    public void updateNotification() {

        setNotificationButtonState(false, false, true);//15
        Bitmap androidImage = BitmapFactory
                .decodeResource(getResources(), R.drawable.mascot_1);//converts drawable to bitmap

        NotificationCompat.Builder notifyBuilder = getNotificationBuilder();

        notifyBuilder.setStyle(new NotificationCompat.BigPictureStyle()
                .bigPicture(androidImage)
                .setBigContentTitle("I just got updated :)")).setContentText("Now press the cancel button.");

        mNotifyManager.notify(NOTIFICATION_ID, notifyBuilder.build());
    }

    //15
    void setNotificationButtonState(Boolean isNotifyEnabled,
                                    Boolean isUpdateEnabled,
                                    Boolean isCancelEnabled) {
        notifyMeButton.setEnabled(isNotifyEnabled);
        updateButton.setEnabled(isUpdateEnabled);
        cancelButton.setEnabled(isCancelEnabled);
    }

    //13
    //cancel the notification
    public void cancelNotification() {
        setNotificationButtonState(true, false, false);//15
        mNotifyManager.cancel(NOTIFICATION_ID);
    }

}
