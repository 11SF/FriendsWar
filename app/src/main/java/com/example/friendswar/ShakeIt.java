package com.example.friendswar;

import androidx.appcompat.app.AppCompatActivity;

import static com.example.friendswar.MainActivity.addOrder;
import static com.example.friendswar.MainActivity.pcl;

import android.content.Context;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class ShakeIt extends AppCompatActivity implements SensorEventListener {
    SensorManager sensorManager;
    // high33 middle20 low10
    private static final int shake_h = 40;
    private static final int shake_m = 20;
    private static final int shake_l = 12;
    private static final int POLL_INTERVAL = 200;
    private Handler hdr = new Handler();
    Vibrator v;
    Random rn = new Random();
    SensorInfo sensor_info = new SensorInfo();
    Button nextPlayer;
    TextView textName;
    TextView textValue;
    int target;
    int totalValue = 0;

    private Runnable pollTask = new Runnable() {
        public void run() {
            shakeIt();
            hdr.postDelayed(pollTask, POLL_INTERVAL);
            if(totalValue >= target) {
                // To Winner Page
                textValue.setText("Win");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shake_it);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) == null){
            System.out.println("No Sensor");
        }

        pcl.setToZeroTurn();
        nextPlayer = findViewById(R.id.nextPlayerShakeIt);
        textName = findViewById(R.id.playerNameShake);
        textName.setText(pcl.getNextTurnPlayer().getPlayerName());
        textValue = findViewById(R.id.total_value);
        textValue.setText(String.valueOf(totalValue));

        // target of Value
        int numberOfPlayer = pcl.getPlayerSize();
        target = rn.nextInt( (numberOfPlayer*888) - (numberOfPlayer*777) + 1) + (numberOfPlayer*777);

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

    static class SensorInfo{
        float accX, accY, accZ;
    }//end class SensorInfo

    public void onSensorChanged(SensorEvent event){
        int type = event.sensor.getType();
        if (type == Sensor.TYPE_ACCELEROMETER) {
            sensor_info.accX=event.values[0];
            sensor_info.accY=event.values[1];
            sensor_info.accZ=event.values[2];
        }
    }//end onSensorChanged

    public void shakeIt() {
        if( (Math.abs(sensor_info.accX)>shake_h) || (Math.abs(sensor_info.accY)>shake_h) || (Math.abs(sensor_info.accZ)>shake_h) ) {
            totalValue += 30;
            nextPlayer.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(200);
            }
        }//end if
        else if( (Math.abs(sensor_info.accX)>shake_m) || (Math.abs(sensor_info.accY)>shake_m) || (Math.abs(sensor_info.accZ)>shake_m) ) {
            totalValue += 12;
            nextPlayer.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(200);
            }
        }//end if
        else if( (Math.abs(sensor_info.accX)>shake_l) || (Math.abs(sensor_info.accY)>shake_l) || (Math.abs(sensor_info.accZ)>shake_l) ) {
            totalValue += 20;
            nextPlayer.setVisibility(View.VISIBLE);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                v.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE));
            } else {
                v.vibrate(200);
            }
        }//end if
        textValue = findViewById(R.id.total_value);
        textValue.setText(String.valueOf(totalValue));
    }

    public void nextPlayer(View view) {
        nextPlayer.setVisibility(View.INVISIBLE);
        textName = findViewById(R.id.playerNameShake);
        textName.setText(pcl.getNextTurnPlayer().getPlayerName());
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) { }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_NORMAL);

        hdr.postDelayed(pollTask, POLL_INTERVAL);
    }//end onResume
}