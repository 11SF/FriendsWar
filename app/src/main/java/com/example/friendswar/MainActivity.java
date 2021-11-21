package com.example.friendswar;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    public static PlayerController pcl = new PlayerController();
    public static AddOrder addOrder = new AddOrder();
    MediaPlayer player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        TextView playerSize = findViewById(R.id.player_size_text);
        playerSize.setText("จำนวนผู้เล่น " + String.valueOf(pcl.getPlayerSize()) + " คน");
        //Play sound
        player = MediaPlayer.create(getApplicationContext(), R.raw.minimal_lo_fi);
        player.start();
        player.setLooping(true);
    }

    public void shakeItBtn (View v) {
        Intent myIntent = new Intent(this, ShakeIt.class);
        startActivity(myIntent);
        player.stop();
    }
    public void callMeBtn (View v) {
        Intent myIntent = new Intent(this, CallMe.class);
        startActivity(myIntent);
        player.stop();
    }
    public void chichiBtn (View v) {
        Intent myIntent = new Intent(this, AddOrderPage.class);
        startActivity(myIntent);
        player.stop();
    }
    public void toAddPlayerPage (View v) {
        Intent myIntent = new Intent(this, AddPlayers.class);
        startActivity(myIntent);
        player.stop();
    }
}