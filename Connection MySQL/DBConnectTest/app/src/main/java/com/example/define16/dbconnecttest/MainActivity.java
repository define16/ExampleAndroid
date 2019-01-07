package com.example.define16.dbconnecttest;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    // show data 버튼 눌렀을때
    public void select(View v) {
        Intent intent01 = new Intent(getApplicationContext(), GetDataActivity.class);
        startActivity(intent01);
    }

    // insert 버튼 눌렀을때
    public void insert(View v) {
        Intent intent02 = new Intent(getApplicationContext(), InsertDataActivity.class);
        startActivity(intent02);
    }

}