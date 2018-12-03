package com.example.nothing.zdyprogressbar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final CustomProgressBar customProgressBar = findViewById(R.id.customProgressBar);
        customProgressBar.setMaxAngle(360);
        customProgressBar.setProgressValue(100 , 100);
        Button button = findViewById(R.id.mBtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customProgressBar.startAnim();
            }
        });
    }
}
