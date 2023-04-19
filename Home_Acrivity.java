package com.MYapp.home_workout_app;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.MYapp.home_workout_app.Fragments.Detail_frag;
import com.MYapp.home_workout_app.Fragments.Exersice_frag;
import com.MYapp.home_workout_app.Fragments.Record_frag;
import com.example.home_workout_app.R;

public class Home_Acrivity extends AppCompatActivity {


    RelativeLayout ex_bac , rec_bac , det_bac;
    TextView exe , rec , det;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_acrivity);

        ex_bac = findViewById(R.id.exercise_background);
        rec_bac = findViewById(R.id.record_background);
        det_bac = findViewById(R.id.detail_background);

        exe = findViewById(R.id.exercise);
        rec = findViewById(R.id.record);
        det = findViewById(R.id.detail);

        getSupportFragmentManager().beginTransaction().add(R.id.container,new Exersice_frag()).commit();
        setExerciseClick();



    }
    public void OnBottomSelected (View view)
    {
        switch (view.getId())
        {
            case R.id.exercise_background:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new Exersice_frag()).commit();
                setExerciseClick();
                break;
            case R.id.record_background:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new Record_frag()).commit();
                setRecordClick();
                break;
            case R.id.detail_background:
                getSupportFragmentManager().beginTransaction().replace(R.id.container,new Detail_frag()).commit();
                setDetailClick();
                break;
        }
    }


    public void setExerciseClick()
    {
        ex_bac.setBackgroundColor(getResources().getColor(R.color.white));
        exe.setTextColor(getResources().getColor(R.color.blue));

        rec_bac.setBackgroundColor(getResources().getColor(R.color.blue));
        rec.setTextColor(getResources().getColor(R.color.white));

        det_bac.setBackgroundColor(getResources().getColor(R.color.blue));
        det.setTextColor(getResources().getColor(R.color.white));
    }
    public void setRecordClick()
    {
        ex_bac.setBackgroundColor(getResources().getColor(R.color.blue));
        exe.setTextColor(getResources().getColor(R.color.white));

        rec_bac.setBackgroundColor(getResources().getColor(R.color.white));
        rec.setTextColor(getResources().getColor(R.color.blue));

        det_bac.setBackgroundColor(getResources().getColor(R.color.blue));
        det.setTextColor(getResources().getColor(R.color.white));
    }

    public void setDetailClick()
    {
        ex_bac.setBackgroundColor(getResources().getColor(R.color.blue));
        exe.setTextColor(getResources().getColor(R.color.white));

        rec_bac.setBackgroundColor(getResources().getColor(R.color.blue));
        rec.setTextColor(getResources().getColor(R.color.white));

        det_bac.setBackgroundColor(getResources().getColor(R.color.white));
        det.setTextColor(getResources().getColor(R.color.blue));
    }

}