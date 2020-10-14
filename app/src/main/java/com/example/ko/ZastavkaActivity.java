package com.example.ko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

public class ZastavkaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zastavka);

        Thread thread = new Thread() {
            @Override
            public void run() {
                try {
                    sleep(5000);
                }catch (Exception e) {
                    e.printStackTrace();
                }finally {
                    startActivity(new Intent(ZastavkaActivity.this,
                            VibratChtoDelat.class));
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }
}