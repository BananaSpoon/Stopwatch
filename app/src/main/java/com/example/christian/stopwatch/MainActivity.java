package com.example.christian.stopwatch;

import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.*;
import android.view.MenuItem;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private int seconds = 0;
    private boolean running;
    private boolean stop;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if(savedInstanceState != null){ //resets the temp memory when app is restarted
            seconds = savedInstanceState.getInt("seconds");
            running = savedInstanceState.getBoolean("running");
            stop = savedInstanceState.getBoolean("stop");

        }
timerstart();
    }

    TextView timerview;
    Handler timehandler = new Handler();
    String totaltime;

    public void startbuttonOnClick(View view) { ///starts timer
        running = true;
    }

    public void pausebuttonOnClick(View view) { ////pause timer
        running = false;
    }

    public void resetbuttonOnClick(View view) {  ///reset timer
        running = false;
        seconds = 0;
    }

    @Override
    protected void onStart() {  ///visible but not ready for interaction
        super.onStart();
        running = stop;
    }

    @Override
    protected void onResume() {   ///ready for interaction
        super.onResume();
        running = stop;
    }

    @Override
    protected void onPause() {   ///activity on background
        super.onPause();
        stop = running;
        running = false;
    }

    @Override
    protected void onStop() {  ///no longer visible
        super.onStop();
        stop = running;
        running = false;
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState) { ///saves to temp memory
        savedInstanceState.putInt("seconds", seconds);
        savedInstanceState.putBoolean("running", running);
        savedInstanceState.putBoolean("stop", stop);
    }

    private void timerstart() {
        timerview = (TextView) findViewById(R.id.timeview);
        timehandler.post(new Runnable() { ////sets the text on the timer to zero
            @Override
            public void run() {
                int hour = seconds / 3600;
                int minute = (seconds % 3600) / 60;
                int sec = seconds % 60;
                totaltime = String.format("%d:%02d:%02d", hour, minute, sec);
                timerview.setText(totaltime);
                if (running) {
                    seconds++;
                }
                    timehandler.postDelayed(this, 1000);
            }
        });
    }
}
