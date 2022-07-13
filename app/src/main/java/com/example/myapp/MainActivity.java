package com.example.myapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.sql.*;

public class MainActivity extends AppCompatActivity {

Button btok,btshow;
Connection c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getSupportActionBar().setTitle("Admin Dashboard");
//        tv=findViewById(R.id.tv);
        btok = findViewById(R.id.save);
        btshow = findViewById(R.id.show);


        btok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this,Save_data.class);
                startActivity(intent);
            }
        });
        btshow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent n = new Intent(MainActivity.this,Show_data.class);
                startActivity(n);
            }
        });

    }

}