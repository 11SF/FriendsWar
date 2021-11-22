package com.example.friendswar;

import static com.example.friendswar.MainActivity.pcl;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

public class WinnerCallMe extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_winner_call_me);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        TextView textView = findViewById(R.id.WinnerTextCallMe);
        textView.setText(pcl.getWinner().getPlayerName());
    }
    public void goToCallMe(View v) {
        Intent myIntent = new Intent(this,CallMe.class);
        startActivity(myIntent);
    }

    public void goToMenu(View v) {
        Intent myIntent = new Intent(this,MainActivity.class);
        startActivity(myIntent);
    }

}