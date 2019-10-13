package com.example.app01;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

public class CustomReceiver extends BroadcastReceiver {

    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";//Both the
    // sender(BroadcastRecieverFragment) and
    // the receiver(CustomReciever)
    // of a custom broadcast must agree on a unique action string for the Intent being broadcast.
    // It's a common practice to create a unique action string by prepending your action name with
    // your app's package name.
    // One of the simplest ways to get your app's package name is to use BuildConfig.APPLICATION_ID,
    // which returns the applicationId property's value from your module-level build.gradle file.

    @Override
    public void onReceive(Context context, Intent intent) {
        //When a broadcast receiver intercepts a broadcast that it's registered for,
        // the Intent is delivered to the receiver's onReceive() method.
        String intentAction = intent.getAction();
        if (intentAction != null) {
            String toastMessage = "unknown intent action";
            switch (intentAction) {
                case Intent.ACTION_POWER_CONNECTED:
                    toastMessage = "Power connected!";
                    break;
                case Intent.ACTION_POWER_DISCONNECTED:
                    toastMessage = "Power disconnected!";
                    break;
                case ACTION_CUSTOM_BROADCAST:
                    toastMessage = "Custom Broadcast Received";
                    break;
            }

            //Display the toast.
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show();
        }
    }
}
