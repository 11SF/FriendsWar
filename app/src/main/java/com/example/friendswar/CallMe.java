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
    Button nextPlayer;
    Button startBtn;
    Button seeWinner;
    TextView warning;
    MediaPlayer ringtone;
    Vibrator v;
    int minTime;
    int timeMill = 0;
    boolean isPlaying = false;
    boolean startOrNot = false;
    boolean callingOrNot = false;
    boolean answeredOrNot = false;
    Double time[] = new Double[pcl.getPlayerSize()];
    int turn = 0;
    long tStart;
    long tEnd;
    long tDelta;
    double elapsedSeconds;

    private Runnable pollTask = new Runnable() {
        public void run() {
//            showWarning();
            if (startOrNot == true && sensor_info.proximity == 0) {
                startOrNot = false;
                waiting();
            }
            if (callingOrNot == true) {
                calling();
                if (callingOrNot == true && sensor_info.proximity > 0) {
                    calling();
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

//    public void showWarning() {
//        if(sensor_info.proximity == 0 && startOrNot == true ) {
//            warning.setVisibility(View.INVISIBLE);
//        }
//    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_me);
        ringtone = MediaPlayer.create(getApplicationContext(), R.raw.sony_ericsson_ringtone);
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
//        textValue = findViewById(R.id.time);
//        textValue.setText(String.valueOf(sensor_info.proximity));
        startBtn = findViewById(R.id.start);
        warning = findViewById(R.id.warningCallMe);
        seeWinner = findViewById(R.id.seeWinner);

        for (int i = 0; i < time.length; i++) {
            time[i] = 0.0;
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
        warning.setVisibility(View.VISIBLE);
        startOrNot = true;
        timeMill = 0;
    }

    public void waiting() {
        int WaitingTime = rn.nextInt((MaxWaitingTime) - (1500) + 1) + (1500);
        warning.setVisibility(View.INVISIBLE);
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
            tStart = System.currentTimeMillis();
            isPlaying = true;
            ringtone.start();
            ringtone.setLooping(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500, VibrationEffect.DEFAULT_AMPLITUDE));
        } else {
            v.vibrate(500);
        }
    }

    public void answer() {
        if (turn == pcl.getPlayerSize() - 1) {
            nextPlayer.setVisibility(View.GONE);
            seeWinner.setVisibility(View.VISIBLE);
        } else {
            nextPlayer.setVisibility(View.VISIBLE);
        }
        if(isPlaying) {
            isPlaying = false;
            ringtone.stop();
            ringtone.reset();
            ringtone = MediaPlayer.create(getApplicationContext(), R.raw.sony_ericsson_ringtone);

            tEnd = System.currentTimeMillis();
            tDelta = tEnd - tStart;
            elapsedSeconds = tDelta / 1000.0;
            time[turn] = elapsedSeconds;
            System.out.println(elapsedSeconds);
        }
    }

    public void findWinner(View v) {
        Double sortArrayTime[] = time.clone();
        Arrays.sort(sortArrayTime);
        for (int i = 0; i < time.length; i++) {
            if (time[i] == sortArrayTime[0]) {
                pcl.setWinner(pcl.getPlayerById(i));
                System.out.println(pcl.getWinner().getPlayerName());
                sensorManager.unregisterListener(this);
                hdr.removeCallbacks(pollTask);
                Intent myIntent = new Intent(this, WinnerCallMe.class);
                startActivity(myIntent);
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