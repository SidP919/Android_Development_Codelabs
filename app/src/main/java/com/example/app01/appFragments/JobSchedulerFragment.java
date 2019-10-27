package com.example.app01.appFragments;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.example.app01.JobSchedulerNotificationService;
import com.example.app01.R;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import static android.content.Context.JOB_SCHEDULER_SERVICE;

public class JobSchedulerFragment extends Fragment implements View.OnClickListener {

    private static final int JOB_ID = 0;
    Button scheduleJobs, cancelJobs;
    RadioGroup networkOptions;
    RadioButton noNetworkRB, anyNetworkRB, wifiRB;
    //03
    private JobScheduler mScheduler;
    //03 ends.
    //07
    //Switches for setting job options
    private Switch mDeviceIdleSwitch;
    private Switch mDeviceChargingSwitch;
    //07 ends.

    //11
    //Override deadline seekbar
    private SeekBar mSeekBar;

    //00
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_job_scheduler, null);
    }

    //00
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        scheduleJobs = view.findViewById(R.id.jobSchedulerApp_button_scheduleJobs);
        cancelJobs = view.findViewById(R.id.jobSchedulerApp_button_cancelJobs);

        scheduleJobs.setOnClickListener(this);
        cancelJobs.setOnClickListener(this);

        networkOptions = view.findViewById(R.id.jobSchedulerApp_radioGroup_networkOptions);
        noNetworkRB = view.findViewById(R.id.jobSchedulerApp_radioButton_noNetwork);
        anyNetworkRB = view.findViewById(R.id.jobSchedulerApp_radioButton_anyNetwork);
        wifiRB = view.findViewById(R.id.jobSchedulerApp_radioButton_wifiNetwork);

        //08
        mDeviceIdleSwitch = view.findViewById(R.id.jobSchedulerApp_switch_idle);
        mDeviceChargingSwitch = view.findViewById(R.id.jobSchedulerApp_switch_charging);
        //08 ends.

        //12
        mSeekBar = view.findViewById(R.id.jobSchedulerApp_seekBar);
        final TextView seekBarProgress = view.findViewById(R.id.jobSchedulerApp_seekBarProgress);
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                if (i > 0) {
                    seekBarProgress.setText(i + " s");
                } else {
                    seekBarProgress.setText("Not Set");
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
        //12 ends.

    }

    //01
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.jobSchedulerApp_button_scheduleJobs:
                scheduleTheJob();
                break;
            case R.id.jobSchedulerApp_button_cancelJobs:
                cancelTheJob();
                break;
        }
    }

    private void cancelTheJob() {
    }

    //02
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void scheduleTheJob() {
        int selectedNetworkID = networkOptions.getCheckedRadioButtonId();
        int selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;

        switch (selectedNetworkID) {
            case R.id.jobSchedulerApp_radioButton_noNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_NONE;
                break;
            case R.id.jobSchedulerApp_radioButton_anyNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_ANY;
                break;
            case R.id.jobSchedulerApp_radioButton_wifiNetwork:
                selectedNetworkOption = JobInfo.NETWORK_TYPE_UNMETERED;
        }

        //04
        mScheduler = (JobScheduler) getContext().getSystemService(JOB_SCHEDULER_SERVICE);

        ComponentName serviceName = new ComponentName(getActivity().getPackageName(),
                JobSchedulerNotificationService.class.getName());
        JobInfo.Builder builder = new JobInfo.Builder(JOB_ID, serviceName);
        builder.setRequiredNetworkType(selectedNetworkOption);
        //04 paused...

        //09
        builder.setRequiresDeviceIdle(mDeviceIdleSwitch.isChecked());
        builder.setRequiresCharging(mDeviceChargingSwitch.isChecked());
        //09 ends.

        //13
        int seekBarInteger = mSeekBar.getProgress();
        boolean seekBarSet = seekBarInteger > 0;
        if (seekBarSet) {
            builder.setOverrideDeadline(seekBarInteger * 1000);
        }//13

        //06
        boolean constraintSet = selectedNetworkOption != JobInfo.NETWORK_TYPE_NONE
                //10
                || mDeviceChargingSwitch.isChecked() || mDeviceIdleSwitch.isChecked()//10 ends.
                //13
                || seekBarSet;//13 ends.
        if (constraintSet) {//06

            //04 continues...
            JobInfo myJobInfo = builder.build();
            mScheduler.schedule(myJobInfo);
            Toast.makeText(getContext(), "Job Scheduled, job will run when " +
                    "the constraints are met.", Toast.LENGTH_SHORT).show();
            //04 ends go to NavigationDrawerActivity for //05

        }//06 ends.
    }
}
