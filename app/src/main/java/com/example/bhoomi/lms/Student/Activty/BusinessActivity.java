package com.example.bhoomi.lms.Student.Activty;

import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.bhoomi.lms.R;

public class BusinessActivity extends AppCompatActivity {

    private TextInputEditText textInputEditText_uname, textInputEditText_job, textInputEditText_email, textInputEditText_company,
                              textInputEditText_phone, textInputEditText_need;

    private TextInputLayout textInputLayout_uname, textInputLayout_job, textInputLayout_email, textInputLayout_company,
                             textInputLayout_phone, textInputLayout_need;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_business);

        textInputEditText_uname = findViewById(R.id.user_textInputEditText);
        textInputEditText_job = findViewById(R.id.job_textInputEditText);
        textInputEditText_email = findViewById(R.id.email_textInputEditText);
        textInputEditText_company = findViewById(R.id.company_textInputEditText);
        textInputEditText_phone = findViewById(R.id.phn_textInputEditText);
        textInputEditText_need = findViewById(R.id.need_textInputEditText);

        textInputLayout_uname = findViewById(R.id.textInput_user);
        textInputLayout_job = findViewById(R.id.textInput_job);
        textInputLayout_email = findViewById(R.id.textInput_email);
        textInputLayout_company = findViewById(R.id.textInput_company);
        textInputLayout_phone = findViewById(R.id.textInput_phn);
        textInputLayout_need = findViewById(R.id.textInput_need);



    }
}
