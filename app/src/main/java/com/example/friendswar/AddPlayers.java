package com.example.friendswar;

import static com.example.friendswar.MainActivity.pcl;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.ArrayList;

public class AddPlayers extends AppCompatActivity {
    MediaPlayer player;
    private ListPlayerAdapter mAdapter;
    private RecyclerView recyclerView;
    public static Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_players);
        next  = findViewById(R.id.nextBtn);

        //show player list
        recyclerView = (RecyclerView) findViewById(R.id.list_of_player);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new ListPlayerAdapter(pcl.getPlayers());
        recyclerView.setAdapter(mAdapter);
        if(pcl.getPlayers().size()>0) {
            Button next = findViewById(R.id.nextBtn);
            next.setVisibility(View.VISIBLE);
        }

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EditText textName = findViewById(R.id.inputPlayerName);
        Button add = findViewById(R.id.add_btn);
        textName.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {

                if(textName.getText().toString().length() > 0) {
                    add.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.yellow)));
                    add.setTextColor(getResources().getColor(R.color.white));
                    add.setEnabled(true);
                } else {
                    add.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.white)));
                    add.setTextColor(getResources().getColor(R.color.black));
                    add.setEnabled(false);
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            public void onTextChanged(CharSequence s, int start, int before, int count) {}
        });
        textName.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
               if(keyEvent.getAction() == keyEvent.ACTION_DOWN && i == keyEvent.KEYCODE_ENTER ) {
                   EditText textName = findViewById(R.id.inputPlayerName);
                   pcl.createPlayer(textName.getText().toString());
                   textName.setText("");
                   ArrayList<Player> p = pcl.getPlayers();
                   if(p.size()>0) {
                       Button next = findViewById(R.id.nextBtn);
                       next.setVisibility(View.VISIBLE);
                   } else {
                       Button next = findViewById(R.id.nextBtn);
                       next.setVisibility(View.INVISIBLE);
                   }
                   FrameLayout bg = findViewById(R.id.bg_add_player);
                   InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(bg.getWindowToken(), 0);
                   return true;
               }
               return false;
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        player.stop();
    }

    @Override
    protected void onResume() {
        super.onResume();
        player = MediaPlayer.create(getApplicationContext(), R.raw.mock);
        player.start();
        player.setLooping(true);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        player.stop();
    }

    public void addPlayer(View view) {
        EditText textName = findViewById(R.id.inputPlayerName);
        pcl.createPlayer(textName.getText().toString());
        textName.setText("");
        ArrayList<Player> p = pcl.getPlayers();
        if(p.size()>0) {
            Button next = findViewById(R.id.nextBtn);
            next.setVisibility(View.VISIBLE);
        } else {
            Button next = findViewById(R.id.nextBtn);
            next.setVisibility(View.INVISIBLE);
        }
        FrameLayout bg = findViewById(R.id.bg_add_player);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(bg.getWindowToken(), 0);
    }

    public void nextBtn(View view) {
        ArrayList<Player> p = pcl.getPlayers();
        if(p.size()<=0) {
            next.setVisibility(View.INVISIBLE);
        } else {
            Intent mySuperIntent = new Intent(AddPlayers.this, MainActivity.class);
            startActivity(mySuperIntent);
            player.stop();
        }
    }


}