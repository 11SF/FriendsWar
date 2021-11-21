package com.example.friendswar;

import static com.example.friendswar.MainActivity.pcl;
import static com.example.friendswar.MainActivity.addOrder;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;

public class AddOrderPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_page);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EditText textName = findViewById(R.id.inputOrder);
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
    }

    public void addOrder(View view) {
        EditText textName = findViewById(R.id.inputOrder);
        addOrder.addOrder(textName.getText().toString());
        textName.setText("");
        if(addOrder.order.size()>0) {
            Button next = findViewById(R.id.nextBtnToChichi);
            next.setVisibility(View.VISIBLE);
        }
    }

    public void nextBtn(View view) {
        Intent mySuperIntent = new Intent(AddOrderPage.this, ChichiSticks.class);
        startActivity(mySuperIntent);
    }
}