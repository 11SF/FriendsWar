package com.example.friendswar;

import static com.example.friendswar.MainActivity.pcl;
import static com.example.friendswar.MainActivity.addOrder;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

import pl.droidsonroids.gif.GifImageView;

public class ChichiSticks extends AppCompatActivity implements SensorEventListener{
    SensorManager sensorManager;
    private static final int shake_threshold = 10;
    private static final int POLL_INTERVAL = 500;
    private Handler hdr = new Handler();
    Random rn = new Random();
    SensorInfo sensor_info = new SensorInfo();
    Boolean show_order = false;
    Button next;
    TextView textName;
    ImageView img;
    GifImageView gif;
    ArrayList<String> order;
    private Runnable pollTask = new Runnable() {
        public void run() {
            showOrder();
            hdr.postDelayed(pollTask, POLL_INTERVAL);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chichi_sticks);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        order = addOrder.getOrder();

        for (int i = 0; i < order.size(); i++) {
            System.out.println(order.get(i));
        }

        sensorManager=(SensorManager)getSystemService(SENSOR_SERVICE);
        if (sensorManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION) == null){
            System.out.println("No Sensor");
        }
        pcl.setToZeroTurn();
        textName = findViewById(R.id.playerNameChichi);
        textName.setText(pcl.getNextTurnPlayer().getPlayerName());
        img = findViewById(R.id.chichi_img);
        gif = findViewById(R.id.chichi_gif);
    }
    public void onBackPressed() {
        Intent myIntent = new Intent(this, MainActivity.class);
        startActivity(myIntent);
        sensorManager.unregisterListener(this);
        pcl.resetPlayerTurn();
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

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    public void nextPlayer(View view) {
        next.setVisibility(View.INVISIBLE);
        TextView textOrder = findViewById(R.id.order);
        textOrder.setText("คำสั่งก็คือ...");
        textName = findViewById(R.id.playerNameChichi);
        textName.setText(pcl.getNextTurnPlayer().getPlayerName());
        show_order = false;
    }

    public void showOrder() {
        if( (Math.abs(sensor_info.accX)>shake_threshold) || (Math.abs(sensor_info.accY)>shake_threshold) || (Math.abs(sensor_info.accZ)>shake_threshold) ) {
            if(!show_order) {
                show_order = true;
                int ID = rn.nextInt( (order.size()-1)- 0 + 1) + 0;
                TextView textOrder = findViewById(R.id.order);
                textOrder.setText(order.get(ID));
                next = findViewById(R.id.nextPlayerChichi);
                next.setVisibility(View.VISIBLE);
            }
        }//end if
        if( (Math.abs(sensor_info.accX)>4) || (Math.abs(sensor_info.accY)>10) || (Math.abs(sensor_info.accZ)>4) ) {
            img.setVisibility(View.GONE);
            gif.setVisibility(View.VISIBLE);
        } else {
            img.setVisibility(View.VISIBLE);
            gif.setVisibility(View.GONE);
        }

    }//end showDialog

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener((SensorEventListener) this, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_GAME);


        hdr.postDelayed(pollTask, POLL_INTERVAL);
    }//end onResume
}