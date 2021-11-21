package com.example.friendswar;

import static com.example.friendswar.MainActivity.pcl;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.media.MediaPlayer;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;

import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class CallMe extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    private static final int MaxWaitingTime = 7000;
    private static final int POLL_INTERVAL = 100;
    private Handler hdr = new Handler();
    final Handler handler = new Handler();
    Random rn = new Random();
    SensorInfo sensor_info = new SensorInfo();
    TextView textName;
    TextView textValue;
    Button nextPlayer;
    Button startBtn;
    Button seeWinner;
    MediaPlayer ringtone;
    Vibrator v;
    int minTime;
    int timeMill = 0;
    boolean isPlaying = false;
    boolean startOrNot = false;
    boolean callingOrNot = false;
    boolean answeredOrNot = false;
    int time[] = new int[pcl.getPlayerSize()];
    int turn = 0;


    private Runnable pollTask = new Runnable() {
        public void run() {
            showsensor();
            if (startOrNot == true && sensor_info.proximity == 0) {
                startOrNot = false;
                waiting();
            }
            if (callingOrNot == true) {
                calling();
                if (callingOrNot == true && sensor_info.proximity > 0) {
                    calling();
                    timeMill += 100;
                    handler.postDelayed(this, 100);
                    answeredOrNot = true;
                }
            }
            if (answeredOrNot == true && sensor_info.proximity == 0) {
                answer();
                callingOrNot = false;
                answeredOrNot = false;
            }
            hdr.postDelayed(pollTask, POLL_INTERVAL);
        }
    };

    public void showsensor() {
        textValue.setText(String.valueOf(sensor_info.proximity));
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_me);
        ringtone = MediaPlayer.create(getApplicationContext(), R.raw.mock);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY) == null) {
            System.out.println("No Sensor");
        }
        pcl.setToZeroTurn();
        textName = findViewById(R.id.playerNameCallMe);
        textName.setText(pcl.getNextTurnPlayer().getPlayerName());
        nextPlayer = findViewById(R.id.nextPlayerCallMe);
        textValue = findViewById(R.id.time);
        textValue.setText(String.valueOf(sensor_info.proximity));
        startBtn = findViewById(R.id.start);
        seeWinner = findViewById(R.id.winnerIs);

        for (int i = 0; i < time.length; i++) {
            time[i] = 0;
        }
    }

    public void onBackPressed() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
        sensorManager.unregisterListener(this);
        hdr.removeCallbacks(pollTask);
    }

    @Override
    protected void onPause() {
        super.onPause();
        sensorManager.unregisterListener(this);
        hdr.removeCallbacks(pollTask);
    }

    public void onSensorChanged(SensorEvent event) {
        int type = event.sensor.getType();
        if (type == Sensor.TYPE_PROXIMITY) {
            sensor_info.proximity = event.values[0];
        }
    }//end onSensorChanged

    public void nextPlayer(View view) {
        turn++;
        nextPlayer.setVisibility(View.INVISIBLE);
        textName = findViewById(R.id.playerNameCallMe);
        textName.setText(pcl.getNextTurnPlayer().getPlayerName());
        startBtn.setVisibility(View.VISIBLE);
    }

    public void start(View view) {
        startBtn.setVisibility(View.INVISIBLE);
        startOrNot = true;
        timeMill = 0;
    }

    public void waiting() {
        int WaitingTime = rn.nextInt((MaxWaitingTime) - (1500) + 1) + (1500);
        if (sensor_info.proximity == 0) {
            try {
                TimeUnit.MILLISECONDS.sleep(WaitingTime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        callingOrNot = true;
    }

    public void calling() {
        //add ringtone
        if(!isPlaying) {
            isPlaying = true;
            ringtone.start();
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }

    public void answer() {
        time[turn] = timeMill;
        if (turn == pcl.getPlayerSize() - 1) {
            seeWinner.setVisibility(View.VISIBLE);
        }
        if(isPlaying) {
            isPlaying = false;
            ringtone.stop();
            ringtone.reset();
            ringtone = MediaPlayer.create(getApplicationContext(), R.raw.mock);
        }
//        isPlaying = false;
//        System.out.println(isPlaying);
        nextPlayer.setVisibility(View.VISIBLE);
        textName.setText(String.valueOf(timeMill));
    }

    public void findWinner(View view) {
        System.out.println(123);
        int sortArrayTime[] = time.clone();
        Arrays.sort(sortArrayTime);
        for (int i = 0; i < time.length; i++) {
            if (time[i] == sortArrayTime[0]) {
                System.out.println(pcl.getPlayerById(i).getPlayerName());
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(this, sensorManager.getDefaultSensor(Sensor.TYPE_PROXIMITY),
                SensorManager.SENSOR_DELAY_NORMAL);

        hdr.postDelayed(pollTask, POLL_INTERVAL);
    }

    static class SensorInfo {
        float proximity;
    }//end class SensorInfo
}