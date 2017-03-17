package com.example.administrator.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Start();
    }

    private void Start()
    {
         new Thread(){
             @Override
             public void run() {
                 super.run();
                 try {
                     Thread.sleep(1500);
                     Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                     startActivity(intent);
                     finish();
                 } catch (InterruptedException e) {
                     e.printStackTrace();
                 }
             }
         }.start();
    }
}
