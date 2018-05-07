package com.callinganim.demo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {


    private CallingAnimLayout callAnim;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        callAnim = findViewById(R.id.callAnim);
    }

    public void startAnim(View view){
        callAnim.startAnim();
    }
    public void stopAnim(View view){
        callAnim.stopAnim();
    }



}
