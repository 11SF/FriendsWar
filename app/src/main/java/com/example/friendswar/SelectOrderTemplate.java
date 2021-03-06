package com.example.friendswar;

import static com.example.friendswar.MainActivity.addOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;

public class SelectOrderTemplate extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_order_template);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

    public void goChichi(View v) {
        addOrder.setOrderSelect("1");
        Intent myIntent = new Intent(this, ChichiSticks.class);
        startActivity(myIntent);
    }
    public void goToAddOrder(View v) {
        addOrder.setOrderSelect("2");
        Intent myIntent = new Intent(this, AddOrderPage.class);
        startActivity(myIntent);
    }
}