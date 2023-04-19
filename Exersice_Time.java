package com.MYapp.home_workout_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.home_workout_app.R;


import pl.droidsonroids.gif.GifImageView;

public class Exersice_Time extends AppCompatActivity {

    GifImageView imageView;
    TextView time;
    ProgressBar progressBar;
    Button button;
    int progress = 60;
    boolean checkStart = false;
    int restCount = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
         super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exersice_time);

        int position = getIntent().getIntExtra("posi",-1);

        imageView = findViewById(R.id.gif_animation);
        time = findViewById(R.id.time_watch);
        progressBar = findViewById(R.id.progress_bar);
        button = findViewById(R.id.start_button);

        setData(position);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = button.getText().toString();
                if (text.equals("Start"))
                {
                    checkStart = true;
                    button.setText("Pause");
                    countDouwm();
                }
                else
                {
                    checkStart = false;
                    button.setText("Start");
                    RestCount();

                }
            }
        });


    }

    private void RestCount() {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                while (checkStart == false)
                {
                    try {
                        Thread.sleep(1000);
                        restCount++;
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        thread.start();

    }

    private void countDouwm() {
        Handler handler = new Handler();
        handler.post(new Runnable() {
            @Override
            public void run() {

                if (progress > 0 && checkStart == true)
                {
                    progress--;
                    progressBar.setProgress(progress);
                    time.setText(String.valueOf(progress));

                    handler.postDelayed(this,1000);
                }
                else if (progress <= 0)
                {
                    startActivity(new Intent(Exersice_Time.this,Exersise_detail.class).putExtra("time",restCount+60));
                    finish();
                }
            }
        });
    }

    private void setData(int position) {
        int drawable = 0;
        if (position == 0)
        {
            drawable = (R.drawable.chest);
        }
        else if (position == 1)
        {
            drawable = (R.drawable.shoulder);
        }
        else if (position == 2)
        {
            drawable = (R.drawable.back);
        }
        else if (position == 3)
        {
            drawable = (R.drawable.legs);
        }
        else if (position == 4)
        {
            drawable = (R.drawable.hand);
        }
        else if (position == 5)
        {
            drawable = (R.drawable.abs);
        }
        Glide.with(this)
                .asGif()
                .load(drawable)
                .into(imageView);
    }

    @Override
    protected void onStop() {
        super.onStop();
        checkStart = false;
        button.setText("Pause");
    }
}