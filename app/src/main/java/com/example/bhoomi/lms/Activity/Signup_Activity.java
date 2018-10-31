package com.example.bhoomi.lms.Activity;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.Login.LoginResponse;
import com.example.bhoomi.lms.APIModel.Signup.SignupResponse;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Constants.AbstractMethods;

import retrofit2.Call;
import retrofit2.Callback;

public class Signup_Activity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener, AdapterView.OnItemSelectedListener {

//    String[] user_role = {"Select User Role", "Teacher", "Student"};

    private TextView textView_logoname, textView_signupText, textView_signInTxt;
    private TextInputEditText textInputEditText_username, textInputEditText_email, textInputEditText_pwd;
    private TextInputLayout textInputLayout_user, textInputLayout_email, textInputLayout_pwd;
    private Button button_signup;
    private ImageView imageView_iser, imageView_email, imageView_pwd;
    private String user_name;
    private String email;
    private String password;
    private APIService apiService;
    private String user_role_id;
    private String item;
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_);

        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(Signup_Activity.this);


        textView_logoname = findViewById(R.id.textView_logoName);
        textView_signupText = findViewById(R.id.textView_signup);
        textView_signInTxt = findViewById(R.id.signin_textView);

        textInputEditText_email = findViewById(R.id.email_textInputEditText);
        textInputEditText_username = findViewById(R.id.user_textInputEditText);
        textInputEditText_pwd = findViewById(R.id.pwd_textInputEdittext);

        textInputLayout_user = findViewById(R.id.textInput_user);
        textInputLayout_email = findViewById(R.id.textInput_email);
        textInputLayout_pwd = findViewById(R.id.textInput_pwd);

        button_signup = findViewById(R.id.btn_signUp);

        imageView_iser = findViewById(R.id.user_imgVw);
        imageView_email = findViewById(R.id.email_imgVw);
        imageView_pwd = findViewById(R.id.password_imgVw);

        button_signup.setOnClickListener(this);
        textView_signInTxt.setOnClickListener(this);

        textInputEditText_pwd.setOnFocusChangeListener(this);
        textInputEditText_email.setOnFocusChangeListener(this);
        textInputEditText_username.setOnFocusChangeListener(this);

//        String first = "Already have an account ? please";
//        String next = "<font color='#02b3e4'><b> login here</b></font>";

//        textView_signInTxt.setText(Html.fromHtml(first + " " + next));

        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            textInputEditText_email.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_username.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_pwd.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_pwd.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        }

        Typeface typeface_bold = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_b.ttf");
        textView_logoname.setTypeface(typeface_bold);
        textView_signupText.setTypeface(typeface_bold);

        Typeface typeface_reglr = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_r.ttf");
        textView_signInTxt.setTypeface(typeface_reglr);


        Typeface typeface_medium = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
        textInputLayout_user.setTypeface(typeface_medium);
        textInputLayout_pwd.setTypeface(typeface_medium);
        textInputLayout_email.setTypeface(typeface_medium);
        textInputEditText_pwd.setTypeface(typeface_medium);
        textInputEditText_email.setTypeface(typeface_medium);
        textInputEditText_username.setTypeface(typeface_medium);
        button_signup.setTypeface(typeface_medium);

        Spinner spin = (Spinner) findViewById(R.id.spinner_user_role);
        spin.setOnItemSelectedListener(this);

//Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.user_role_)) {

            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
                ((TextView) v).setTypeface(externalFont);

                return v;
            }

            @Override
            public boolean isEnabled(int position) {
                if (position == 0) {
                    return false;
                } else {
                    return true;
                }
            }

            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);

                Typeface externalFont = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
                ((TextView) v).setTypeface(externalFont);

                return v;
            }
        };

        //Setting the ArrayAdapter data on the Spinner
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
    }

    @Override
    public void onClick(View v) {

        if (v == button_signup) {
            user_name = textInputEditText_username.getText().toString();
            email = textInputEditText_email.getText().toString();
            password = textInputEditText_pwd.getText().toString();

            String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
            String MobilePattern = "[0-9]{10}";

            if (user_name.length() == 0) {
                textInputEditText_username.setError(getString(R.string.enter_user_name));
            } else if (email.length() == 0) {
                textInputEditText_email.setError(getString(R.string.enter_email_address));
            } else if (!email.matches(emailPattern)) {
                textInputEditText_email.setError(getString(R.string.invalid_email));
            } else if (password.length() < 6) {
                textInputEditText_pwd.setError(getString(R.string.password_length));
            } else {
                doSignUp(user_name, email, password);
            }


        } else {

            finish();
        }
    }

    private void doSignUp(String user_name, String email, String password) {

        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.getSignupData(email, password, user_name, user_role_id).enqueue(new Callback<SignupResponse>() {

            @Override
            public void onResponse(Call<SignupResponse> call, retrofit2.Response<SignupResponse> response) {

                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    System.out.println("datarespo " + response.body().getStatus() + " ");

                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        Toast.makeText(Signup_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();
                    } else {
                        Toast.makeText(Signup_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }

                }
            }

            @Override
            public void onFailure(Call<SignupResponse> call, Throwable t) {
                progressDialog.dismiss();

            }
        });
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {

        if (v == textInputEditText_username) {
            imageView_email.setImageResource(R.mipmap.envelope_dark);
            imageView_pwd.setImageResource(R.mipmap.padlock_dark);
            imageView_iser.setImageResource(R.mipmap.user_outline);
            AbstractMethods.hideKeyboard(this, v);


        } else if (v == textInputEditText_email) {
            imageView_email.setImageResource(R.mipmap.envelope);
            imageView_pwd.setImageResource(R.mipmap.padlock_dark);
            imageView_iser.setImageResource(R.mipmap.user_dark);
            AbstractMethods.hideKeyboard(this, v);
        } else if (v == textInputEditText_pwd) {
            imageView_email.setImageResource(R.mipmap.envelope_dark);
            imageView_pwd.setImageResource(R.mipmap.padlock);
            imageView_iser.setImageResource(R.mipmap.user_dark);
            AbstractMethods.hideKeyboard(this, v);
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item = (String) parent.getItemAtPosition(position);
        ((TextView) parent.getChildAt(0)).setTextColor(getResources().getColor(R.color.colorPrimary));

        if (position == 1) {
            user_role_id = "4";
        } else if (position == 2) {
            user_role_id = "5";
        } else {
            user_role_id = "";
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}
