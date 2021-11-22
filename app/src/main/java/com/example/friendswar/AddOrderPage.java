package com.example.friendswar;

import static com.example.friendswar.MainActivity.pcl;
import static com.example.friendswar.MainActivity.addOrder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;

import java.util.ArrayList;

public class AddOrderPage extends AppCompatActivity {
    private ListOrderAdapter mAdapter;
    private RecyclerView recyclerView;
    public static Button nextAddOrder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_order_page);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        EditText textName = findViewById(R.id.inputOrder);
        Button add = findViewById(R.id.add_btn);
        nextAddOrder = findViewById(R.id.nextBtnToChichi);

        recyclerView = (RecyclerView) findViewById(R.id.list_of_order);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        mAdapter = new ListOrderAdapter(addOrder.getOrderEdit());
        recyclerView.setAdapter(mAdapter);
        if(addOrder.getOrderEdit().size()>0) {
            nextAddOrder.setVisibility(View.VISIBLE);
        }

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
                    EditText textName = findViewById(R.id.inputOrder);
                    addOrder.addOrder(textName.getText().toString());
                    textName.setText("");
                    if(addOrder.getOrderEdit().size()>0) {
                        nextAddOrder.setVisibility(View.VISIBLE);
                    } else {
                        nextAddOrder.setVisibility(View.INVISIBLE);
                    }
                    FrameLayout bg = findViewById(R.id.bg_add_order);
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(bg.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });
    }

    public void addOrder(View view) {
        EditText textName = findViewById(R.id.inputOrder);
        addOrder.addOrder(textName.getText().toString());
        textName.setText("");
        if(addOrder.order.size()>0) {
            nextAddOrder.setVisibility(View.VISIBLE);
        }
        FrameLayout bg = findViewById(R.id.bg_add_order);
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(bg.getWindowToken(), 0);
    }

    public void nextBtn(View view) {
        Intent mySuperIntent = new Intent(AddOrderPage.this, ChichiSticks.class);
        startActivity(mySuperIntent);
    }
}