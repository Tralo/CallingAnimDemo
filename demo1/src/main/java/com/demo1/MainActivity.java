package com.demo1;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    private LVCircularZoom llv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        llv = findViewById(R.id.llv);
    }

    public void startAnim(View view){
        llv.startAnim();
    }
}
