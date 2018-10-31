package com.example.bhoomi.lms.Student.Activty;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;
import android.text.SpannableStringBuilder;
import android.text.style.TypefaceSpan;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;

import com.example.bhoomi.lms.R;

public class Setting_Activity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar_setting;
    private LinearLayout linearLayout_videoqlty;

    private SwitchCompat switchCompat_bglec;
    private CheckBox checkBox_stud1, checkBox_stud2, checkBox_teacher1, checkBox_teacher2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_);

        toolbar_setting = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_setting);
        toolbar_setting.setTitle("");
        toolbar_setting.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_setting);

        toolbar_setting.setNavigationIcon(R.drawable.ic_back);

        toolbar_setting.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar_setting.showOverflowMenu();
        switchCompat_bglec = findViewById(R.id.bglecture_switch);
        checkBox_stud1 = findViewById(R.id.checkbox__student1);
        checkBox_stud2 = findViewById(R.id.checkbox__student2);
        checkBox_teacher1 = findViewById(R.id.checkbox__teacher1);
        checkBox_teacher2 = findViewById(R.id.checkbox__teacher2);

        Typeface typeface_rglr = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_r.ttf");
        checkBox_stud1.setTypeface(typeface_rglr);
        checkBox_stud2.setTypeface(typeface_rglr);
        checkBox_teacher1.setTypeface(typeface_rglr);
        checkBox_teacher2.setTypeface(typeface_rglr);
        switchCompat_bglec.setTypeface(typeface_rglr);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_privacyPlicy:
                break;
            case R.id.action_tnc:
                break;
            case R.id.action_intellicual_privacyPlicy:
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {

    }
}
