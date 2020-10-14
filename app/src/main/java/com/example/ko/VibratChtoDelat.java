package com.example.ko;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class VibratChtoDelat extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vibrat_chto_delat);
    }

    public void readComments(View view) {
        startActivity(new Intent(
                VibratChtoDelat.this,
                ChitatOtzivi.class
        ));
    }

    public void writeComments(View view) {
        startActivity(new Intent(
                VibratChtoDelat.this,
                NapisatOtziv.class
        ));
    }
}