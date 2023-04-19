package com.MYapp.home_workout_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.home_workout_app.R;

import pl.droidsonroids.gif.GifImageView;

public class Exercises_Actvity extends AppCompatActivity {

    TextView textView;
    GifImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exercises_actvity);

        int position = getIntent().getIntExtra("pos",-1);
        textView = findViewById(R.id.exercise_type);
        imageView =findViewById(R.id.animation_gif);
        setData(position);

        CardView cardView = findViewById(R.id.cardView);
        cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Exercises_Actvity.this,Exersice_Time.class).putExtra("posi",position));
            }
        });
    }

    private void setData(int position) {
        int drawable = 0;
        if (position == 0)
        {
            textView.setText("Chest");
            drawable = (R.drawable.chest);
        }
        else if (position == 1)
        {
            textView.setText("Shoulder");
            drawable = (R.drawable.shoulder);
        }
        else if (position == 2)
        {
            textView.setText("Back");
            drawable = (R.drawable.back);
        }
        else if (position == 3)
        {
            textView.setText("Legs");
            drawable = (R.drawable.legs);
        }
        else if (position == 4)
        {
            textView.setText("Hands");
            drawable = (R.drawable.hand);
        }
        else if (position == 5)
        {
            textView.setText("Abs");
            drawable = (R.drawable.abs);
        }
        Glide.with(this)
                .asGif()
                .load(drawable)
                .into(imageView);
    }
}