package com.example.bhoomi.lms.Teacher.Activity;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.bhoomi.lms.APIModel.SectionList.SectionList;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Constants.AbstractMethods;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContactAdmin_Activity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private TextInputLayout textInputLayout_user, textInputLayout_email, textInputLayout_msg;
    private TextInputEditText textInputEditText_user, textInputEditText_email, textInputEditText_msg;

    private Button button_save;

    private ImageView imageView_user, imageView_email, imageView_msg;
    private android.support.v7.widget.Toolbar toolbar_contact;

    private APIService apiService;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_admin);

        toolbar_contact = findViewById(R.id.toolbar_contact);
        progressDialog = new ProgressDialog(ContactAdmin_Activity.this);

        toolbar_contact.setTitle("");
        toolbar_contact.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_contact);

        toolbar_contact.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_contact.getNavigationIcon().setAutoMirrored(true);
        toolbar_contact.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        textInputEditText_user = findViewById(R.id.user_textInputEditText);
        textInputEditText_email = findViewById(R.id.email_textInputEditText);
        textInputEditText_msg = findViewById(R.id.msg_textInputEditText);

        textInputLayout_user = findViewById(R.id.textInput_user);
        textInputLayout_email = findViewById(R.id.textInput_email);
        textInputLayout_msg = findViewById(R.id.textInput_msg);

        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            textInputEditText_user.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_email.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_msg.setTextDirection(View.TEXT_DIRECTION_RTL);
        }

        Typeface typeface_medium= Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
        textInputEditText_user.setTypeface(typeface_medium);
        textInputEditText_email.setTypeface(typeface_medium);
        textInputEditText_msg.setTypeface(typeface_medium);
        textInputLayout_user.setTypeface(typeface_medium);
        textInputLayout_email.setTypeface(typeface_medium);
        textInputLayout_msg.setTypeface(typeface_medium);

        imageView_user = findViewById(R.id.user_imgVw);
        imageView_email = findViewById(R.id.email_imgVw);
        imageView_msg = findViewById(R.id.msg_imgVw);

        button_save = findViewById(R.id.buttn_postq);

        button_save.setOnClickListener(this);

        textInputEditText_user.setOnFocusChangeListener(this);
        textInputEditText_email.setOnFocusChangeListener(this);
        textInputEditText_msg.setOnFocusChangeListener(this);

        apiService = APIUtils.getAPIService();


    }

    @Override
    public void onClick(View v) {

        if (v == button_save)
        {
            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

            if (textInputEditText_user.getText().toString().length()==0)
            {
                textInputEditText_user.setError(getString(R.string.enter_user_name));
            }
            else if (textInputEditText_email.getText().toString().length()==0)
            {
                textInputEditText_email.setError(getString(R.string.enter_email_address));
            }
            else if (!textInputEditText_email.getText().toString().matches(emailPattern))
            {
                textInputEditText_email.setError(getString(R.string.enter_valid_email));
            }
            else if (textInputEditText_msg.getText().toString().length()==0)
            {
                textInputEditText_msg.setError(getString(R.string.enter_message));
            }
            else
            {
                contactAdmin(textInputEditText_email.getText().toString(), textInputEditText_user.getText().toString(), textInputEditText_msg.getText().toString());
            }
        }
    }

    private void contactAdmin(String s, String toString, String string) {

        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.contactus(s,toString,string).enqueue(new Callback<SectionList>() {

            @Override
            public void onResponse(Call<SectionList> call, Response<SectionList> response) {

                progressDialog.dismiss();

                if (response.isSuccessful())
                {
                    if (response.body().getStatus().equalsIgnoreCase("Success"))
                    {
                        Toast.makeText(ContactAdmin_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else
                    {
                        Toast.makeText(ContactAdmin_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(ContactAdmin_Activity.this, response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<SectionList> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (v == textInputEditText_user)
        {
            imageView_email.setImageResource(R.mipmap.envelope_dark);
            imageView_msg.setImageResource(R.drawable.msg);
            imageView_user.setImageResource(R.mipmap.user_outline);
            AbstractMethods.hideKeyboard(this,v);
        }
        else if (v == textInputEditText_email)
        {
            imageView_email.setImageResource(R.mipmap.envelope);
            imageView_msg.setImageResource(R.drawable.msg);
            imageView_user.setImageResource(R.mipmap.user_dark);
            AbstractMethods.hideKeyboard(this,v);
        }
        else if (v == textInputEditText_msg)
        {
            imageView_email.setImageResource(R.mipmap.envelope_dark);
            imageView_msg.setImageResource(R.drawable.msg_b);
            imageView_user.setImageResource(R.mipmap.user_dark);
            AbstractMethods.hideKeyboard(this,v);
        }
    }
}
