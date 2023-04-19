package com.MYapp.home_workout_app;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import com.MYapp.home_workout_app.Database.Details_database;
import com.MYapp.home_workout_app.Utiles.Current_date;
import com.bumptech.glide.disklrucache.DiskLruCache;
import com.example.home_workout_app.R;

import java.util.Locale;

public class Exersise_detail extends AppCompatActivity {
    int time;
    double weightKg = 70;
    String preDate;
    int preTime;
    double preWeight;
    double precal;
    double totalCaloriesBurned;
    double weightLossInKg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exersise_detail);

        time = getIntent().getIntExtra("time",-1);
       setTime();
       setCalories();
    }

    private void setCalories() {
        double metValueExercise = 8;
        double metValueRest = 1.5;
        int exerciseDurationMinutes = 1;
        double restDuration = time - 60;
        double restDurationMinutes = restDuration / 60;

        Toast.makeText(this, String.valueOf(restDurationMinutes), Toast.LENGTH_SHORT).show();

        MYAsyncTasks tasks = new MYAsyncTasks();
        tasks.execute();

        double caloriesBurnedPerMinuteDuringExercise = (metValueExercise * weightKg * 3.5) / 200;
        double caloriesBurnedPerMinuteAtRest = (metValueRest * weightKg * 3.5) / 200;

        totalCaloriesBurned = (caloriesBurnedPerMinuteDuringExercise * exerciseDurationMinutes) + (caloriesBurnedPerMinuteAtRest * restDurationMinutes);


        double caloriesPerPoundOfFat = 3500.0;
        double weightLossInPounds = totalCaloriesBurned / caloriesPerPoundOfFat;
        weightLossInKg = weightLossInPounds * 0.453592; // convert to kilograms

        MYAsyncTasksUpdate tasksUpdate = new MYAsyncTasksUpdate();
        tasksUpdate.execute();

        String calForm = String.format("%.3f",totalCaloriesBurned);

        TextView calories =findViewById(R.id.calories);
        calories.setText(calForm);




    }

    private void setTime() {
        int min = time / 60;
        int sec = time % 60;

        String timeS = String.format(Locale.getDefault(),"%02d:%02d", min , sec);

        TextView timeUsed = findViewById(R.id.time_used);
        timeUsed.setText(timeS);
    }

    class  MYAsyncTasks extends AsyncTask<Void,Void,String>
    {
        Details_database details_database = new Details_database(Exersise_detail.this);

        @Override
        protected String doInBackground(Void... voids) {
            try {
                SQLiteDatabase database = details_database.getReadableDatabase();
                Cursor cursor = database.query("DETAIL_DB",new String[]{"WEIGHT,DATE,CALORIES,WEIGHT_G,DURATION"},"_id=?",new String[]{String.valueOf(1)},null,null,null);
                if (cursor.moveToFirst())
                {
                    String weight = cursor.getString(0);
                    preDate = cursor.getString(1);
                    precal = Double.valueOf(cursor.getString(2));
                    preWeight = Double.valueOf(cursor.getString(3));
                    preTime = Integer.valueOf(cursor.getString(4));

                    database.close();
                    cursor.close();

                    return weight;
                }
                return null;
            }catch (Exception e)
            {
                return null;
            }
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (s != null)
            {
                weightKg = Double.valueOf(s);
            }
            else
            {
                Toast.makeText(Exersise_detail.this, "Something Wrong", Toast.LENGTH_SHORT).show();
            }
        }
    }

    class  MYAsyncTasksUpdate extends AsyncTask<Void,Void,Boolean>
    {
        Details_database details_database = new Details_database(Exersise_detail.this);

        @Override
        protected Boolean doInBackground(Void... voids) {
            try {
                SQLiteDatabase database = details_database.getReadableDatabase();
                int check;
                ContentValues values = new ContentValues();
                if (preDate.equals(Current_date.getDate())) {
                    values.put("CALORIES",totalCaloriesBurned+precal);
                    values.put("WEIGHT_G",preWeight+weightLossInKg);
                    values.put("DURATION",preTime+time);
                }
                else
                {
                    values.put("CALORIES",totalCaloriesBurned);
                    values.put("WEIGHT_G",weightLossInKg);
                    values.put("DURATION",time);
                }

                check = database.update("DETAIL_DB",values,"_id=?",new String[]{String.valueOf(1)});

                if (check > 0)
                {
                    return true;
                }
                return false;
            }catch (Exception e)
            {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean s) {
            super.onPostExecute(s);

        }
    }
}