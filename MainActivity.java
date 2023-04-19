package com.MYapp.home_workout_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.MYapp.home_workout_app.Fragments.Personal_Details;
import com.MYapp.home_workout_app.Fragments.Sekect_Gander;
import com.MYapp.home_workout_app.Liseners.Comunication_liserner;
import com.example.home_workout_app.R;

public class MainActivity extends AppCompatActivity implements Comunication_liserner {


    ImageView gander_select, personal_details;
    boolean back_frag = true;

    String TAG = "MY_FRAG";
    String TAG2 = "MY_FRAG2";

    boolean isChecked;
    Button next;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        gander_select = findViewById(R.id.gander_selec);
        personal_details = findViewById(R.id.detail_gen);

        loadFrag(new Sekect_Gander(),0);

        next = findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                            loadFrag(new Personal_Details(), 1);
                    }
                });
    }

    private void loadFrag(Fragment fragment, int i) {
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        FragmentManager.OnBackStackChangedListener listener = new FragmentManager.OnBackStackChangedListener() {
            @Override
            public void onBackStackChanged() {
                Sekect_Gander sekect_gander = (Sekect_Gander) getSupportFragmentManager().findFragmentByTag(TAG);
                Personal_Details personal_detail = (Personal_Details) getSupportFragmentManager().findFragmentByTag(TAG2);

                if (sekect_gander != null && sekect_gander.isVisible())
                {
                    back_frag = true;
                    next.setVisibility(View.VISIBLE);
                    gander_select.setImageDrawable(getDrawable(R.drawable.circle));
                    personal_details.setImageDrawable(getDrawable(R.drawable.circle_ring));
                }
                else if (personal_detail != null && personal_detail.isVisible())
                {
                    back_frag = false;
                    next.setVisibility(View.GONE);
                    gander_select.setImageDrawable(getDrawable(R.drawable.circle_ring));
                    personal_details.setImageDrawable(getDrawable(R.drawable.circle));
                }

            }
        };

        if (i == 0)
        {
            ft.replace(R.id.container, fragment,TAG);
            ft.addToBackStack(TAG);
        }
        else if (isChecked && i == 1)
        {
            ft.replace(R.id.container,fragment,TAG2);
            ft.addToBackStack(TAG2);
        }
        else
            Toast.makeText(this, "Please Select Your Gander", Toast.LENGTH_LONG).show();

        fm.addOnBackStackChangedListener(listener);
        ft.commit();

    }

    @Override
    public void onBackPressed() {
        if (back_frag == false)
        {
            super.onBackPressed();
        }
        else
        {
            finish();
        }
    }

    @Override
    public void OonAction(boolean b , int flag) {
        if (flag == 1)
            isChecked = b;
        else {
            startActivity(new Intent(MainActivity.this, Home_Acrivity.class));
            finish();
        }
    }
}