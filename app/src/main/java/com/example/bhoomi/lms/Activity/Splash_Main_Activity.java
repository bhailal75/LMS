package com.example.bhoomi.lms.Activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Activty.Dashboard_Activity;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Teacher.Activity.TutorDahboard;

public class Splash_Main_Activity extends AppCompatActivity {
    private final int SPLASH_DISPLAY_LENGTH = 2000;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String u_role_id, u_email, u_pass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                u_role_id = sharedPreferences.getString(ConstantData.U_ROLEID,"0");

                if(u_role_id != null && u_role_id.equals("4")) {
                    startActivity(new Intent(getApplicationContext(), TutorDahboard.class));
//                    editor.putString("firstTime", "true").apply();
                    finish();
                }else if (u_role_id != null && u_role_id.equals("5")) {
                    startActivity(new Intent(getApplicationContext(), Dashboard_Activity.class));
//                    editor.putString("firstTime", "true").apply();
                    finish();
                }else{
                    startActivity(new Intent(getApplicationContext(),Welcome_Activity.class));
                    finish();
                }




//                startActivity(new Intent(Splash_Main_Activity.this, AppIntroducing_Activity.class));
//                finish();
            }
        }, SPLASH_DISPLAY_LENGTH);
    }
}
