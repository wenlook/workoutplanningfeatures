package com.MYapp.home_workout_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.MYapp.home_workout_app.Database.Details_database;
import com.example.home_workout_app.R;

public class Splash_Screen extends AppCompatActivity {

    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        getSupportActionBar().hide();

        Details_database database = new Details_database(this);
        MyAsyncTasks tasks = new MyAsyncTasks();
        tasks.execute(database);

    }

    private void startNextActivity() {
        if (name.equals("name"))
            startActivity(new Intent(Splash_Screen.this,MainActivity.class));
        else
            startActivity(new Intent(Splash_Screen.this,Home_Acrivity.class));
    }

    class MyAsyncTasks extends AsyncTask<Details_database,Void,Boolean>
    {
        SQLiteDatabase sqLiteDatabase;
        @Override
        protected Boolean doInBackground(Details_database... voids) {
            try {
                sqLiteDatabase = voids[0].getReadableDatabase();
                Cursor cursor = sqLiteDatabase.query("DETAIL_DB",new String[]{"NAME"},"_id=?",new String[]{String.valueOf(1)},null,null,null);
                if (cursor.moveToFirst())
                {
                    name = cursor.getString(0);
                    return true;
                }
                else
                    return false;
            }catch (Exception e)
            {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if (!aBoolean)
            {
                startActivity(new Intent(Splash_Screen.this,MainActivity.class));
            }
            else {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startNextActivity();
                        finish();
                    }
                },2000);
            }
        }
    }
}