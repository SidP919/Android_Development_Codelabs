package com.example.app01.appFragments;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.app01.BuildConfig;
import com.example.app01.CustomReceiver;
import com.example.app01.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

public class BroadcastRecieverFragment extends Fragment {

    private static final String ACTION_CUSTOM_BROADCAST =
            BuildConfig.APPLICATION_ID + ".ACTION_CUSTOM_BROADCAST";//Both the sender and receiver
    private CustomReceiver mReceiver;
    // of a custom broadcast must agree on a unique action string for the Intent being broadcast.
    // It's a common practice to create a unique action string by prepending your action name with
    // your app's package name.
    // One of the simplest ways to get your app's package name is to use BuildConfig.APPLICATION_ID,
    // which returns the applicationId property's value from your module-level build.gradle file.

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_broadcast_reciever, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mReceiver = new CustomReceiver();

        IntentFilter filter = new IntentFilter();//Intent filters specify the types of
        // intents a component can receive. They are used in filtering out the intents based on
        // Intent values like action and category.

        filter.addAction(Intent.ACTION_POWER_CONNECTED);
        filter.addAction(Intent.ACTION_POWER_DISCONNECTED);//When the system receives an
        // Intent as a broadcast,
        // it searches the broadcast receivers based on
        // the action value specified in the IntentFilter object.

        //Your receiver is active and able to receive broadcasts as long as your current Activity
        // is running. Now, Register the receiver using the activity context.
        getContext().registerReceiver(mReceiver, filter);

        view.findViewById(R.id.sendBroadcastButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Create a new Intent, with your custom action string as the argument.
                Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);

                // send the broadcast using the LocalBroadcastManager class
                LocalBroadcastManager.getInstance(getContext()).sendBroadcast(customBroadcastIntent);
            }
        });

        //If you register a broadcast receiver dynamically,
        // you must unregister the receiver when it is no longer needed.
        // In our app, the receiver only needs to respond to the custom broadcast
        // when the app is running, so you can register the action in onCreate() and
        // unregister it in onDestroy().
        LocalBroadcastManager.getInstance(getContext()).registerReceiver(mReceiver, new IntentFilter(ACTION_CUSTOM_BROADCAST));
    }

    //override the onDestroy() method and unregister your receiver.
    // To save system resources and avoid leaks,
    // dynamic receivers must be unregistered when they are no longer
    // needed or before the corresponding activity or app is destroyed,
    // depending on the context used.
    @Override
    public void onDestroy() {
        //Unregister the receiver
        getContext().unregisterReceiver(mReceiver);
        LocalBroadcastManager.getInstance(getContext()).unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
