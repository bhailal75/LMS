package com.example.bhoomi.lms.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.ForgotPassword.ForgotPassword;
import com.example.bhoomi.lms.APIModel.Login.LoginResponse;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Constants.AbstractMethods;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.SharedPref;
import com.example.bhoomi.lms.Teacher.Activity.TutorDahboard;
import com.google.android.gms.common.api.GoogleApiClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login_Activity extends AppCompatActivity implements View.OnFocusChangeListener, View.OnClickListener, AdapterView.OnItemSelectedListener {
//    String[] user_role = {"Select User Role", "Teacher", "Student"};

    SharedPref session;
    private TextView textView_logoname, textView_signinlabel, textView_forgotPwd, textView_signupTxt;
    private TextInputEditText textInputEditText_email, textInputEditText_pwd;
    private TextInputLayout textInputLayout_email, textInputLayout_pwd;
    private CheckBox checkBox_tNc;
    private Button button_signIn;
    private ImageView imageView_email, imageView_pwd;
    private String emailPattern;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private APIService apiService;
    private String email, password;
    private String user_role_id;
    private ProgressDialog progressDialog;
    private String item;
    private String u_role_id;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        apiService = APIUtils.getAPIService();


        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        session = new SharedPref(Login_Activity.this);
        progressDialog = new ProgressDialog(Login_Activity.this);


        textView_logoname = findViewById(R.id.textView_logoName);
        textView_signinlabel = findViewById(R.id.textView_signin);
        textView_forgotPwd = findViewById(R.id.forogtPwd_textView);
        textView_signupTxt = findViewById(R.id.signup_textView);

        textInputEditText_email = findViewById(R.id.email_textInputEditText);
        textInputEditText_pwd = findViewById(R.id.pwd_textInputEdittext);

        textInputLayout_email = findViewById(R.id.textInput_email);
        textInputLayout_pwd = findViewById(R.id.textInput_pwd);

        imageView_email = findViewById(R.id.email_imgVw);
        imageView_pwd = findViewById(R.id.password_imgVw);

        checkBox_tNc = findViewById(R.id.tnc_checkbox);

        button_signIn = findViewById(R.id.btn_signIn);

        Typeface typeface_bold = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_b.ttf");
        textView_logoname.setTypeface(typeface_bold);
        textView_signinlabel.setTypeface(typeface_bold);

        Typeface typeface_reglr = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_r.ttf");
        textView_signupTxt.setTypeface(typeface_reglr);
        checkBox_tNc.setTypeface(typeface_reglr);
        textView_forgotPwd.setTypeface(typeface_reglr);

        Typeface typeface_medium = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
        textInputLayout_pwd.setTypeface(typeface_medium);
        textInputLayout_email.setTypeface(typeface_medium);
        textInputEditText_pwd.setTypeface(typeface_medium);
        textInputEditText_email.setTypeface(typeface_medium);
        button_signIn.setTypeface(typeface_medium);


        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            textInputEditText_pwd.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_email.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_pwd.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        }
//        String first = getString(R.string.new_user);
//        String next = "<font color='#02b3e4'><b>Sign up here</b></font>";
//
//        textView_signupTxt.setText(Html.fromHtml(first + " " + next));

        textInputEditText_email.setOnFocusChangeListener(this);
        textInputEditText_pwd.setOnFocusChangeListener(this);

        textView_forgotPwd.setOnClickListener(this);
        button_signIn.setOnClickListener(this);
        textView_signupTxt.setOnClickListener(this);

        emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

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
    public void onFocusChange(View v, boolean hasFocus) {

        if (v == textInputEditText_email) {
            imageView_email.setImageResource(R.mipmap.envelope);
            imageView_pwd.setImageResource(R.mipmap.padlock_dark);
//            AbstractMethods.hideKeyboard(this, v);
        } else {
            imageView_email.setImageResource(R.mipmap.envelope_dark);
            imageView_pwd.setImageResource(R.mipmap.padlock);
//            AbstractMethods.hideKeyboard(this, v);

        }
    }

    @Override
    public void onClick(View v) {

        if (v == textView_forgotPwd) {
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            LayoutInflater inflater = this.getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.layout_forogt_pwd, null);
            dialogBuilder.setView(dialogView);

            final TextInputEditText editText_forgotPwd = (TextInputEditText) dialogView.findViewById(R.id.email_textInput_forgotPwd);
            Button btn_forgotPwd = (Button) dialogView.findViewById(R.id.forgotPwd_btn);
            TextView textView_forgotPwd = dialogView.findViewById(R.id.textView_forgotPwd);
            TextInputLayout textInputLayout = dialogView.findViewById(R.id.textInput_email);
            ImageView imageView = dialogView.findViewById(R.id.imageView_close);

            Typeface typeface_reglr = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_r.ttf");
            editText_forgotPwd.setTypeface(typeface_reglr);
            textInputLayout.setTypeface(typeface_reglr);


            Typeface typeface_medium = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
            textView_forgotPwd.setTypeface(typeface_medium);
            btn_forgotPwd.setTypeface(typeface_medium);


            final AlertDialog alertDialog = dialogBuilder.create();

            btn_forgotPwd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View view) {

                    if (editText_forgotPwd.getText().toString().length() == 0) {
                        editText_forgotPwd.setError(getString(R.string.enter_valid_email));
                    } else if (!editText_forgotPwd.getText().toString().matches(emailPattern)) {
                        editText_forgotPwd.setError(getString(R.string.invalid_email));
                    } else {
                        doForGotPwd(editText_forgotPwd.getText().toString(), alertDialog);
                    }
                }
            });

            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.show();
        } else if (v == button_signIn) {

            doSignIn();
        } else if (v == textView_signupTxt) {
            startActivity(new Intent(getApplicationContext(), Signup_Activity.class));
        }
    }

    private void doForGotPwd(String s, final AlertDialog alertDialog) {

        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.getData(s).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                progressDialog.dismiss();

                if (response.body().getStatus().equalsIgnoreCase("Success")) {
                    alertDialog.dismiss();
                    Toast.makeText(Login_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(Login_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });


    }

    private void doSignIn() {

        email = textInputEditText_email.getText().toString();
        password = textInputEditText_pwd.getText().toString();

        if (email.length() == 0) {
            textInputEditText_email.setError(getString(R.string.enter_email));
        } else if (!email.matches(emailPattern)) {
            textInputEditText_email.setError(getString(R.string.invalid_email));
        } else if (password.length() == 0) {
            textInputEditText_pwd.setError(getString(R.string.enter_password));
        } else if (password.length() < 6) {
            textInputEditText_pwd.setError(getString(R.string.password_length));
        } else if (!checkBox_tNc.isChecked()) {
            Toast.makeText(this, getResources().getString(R.string.accept_t_n_c), Toast.LENGTH_SHORT).show();
        } else if (user_role_id.length() == 0) {
            Toast.makeText(this, getString(R.string.select_user_role), Toast.LENGTH_SHORT).show();
        } else {

            loadData(email, password);
        }
    }

    private void loadData(final String email, final String password) {

        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.getLoginData(email, password, user_role_id).enqueue(new Callback<LoginResponse>() {

            @Override
            public void onResponse(Call<LoginResponse> call, retrofit2.Response<LoginResponse> response) {

                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    System.out.println("datarespo " + response.body().getUserInfo() + " ");

                    if (response.body().getStatus().equalsIgnoreCase("Success")) {


//                        Toast.makeText(Login_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                        String uid = response.body().getUserInfo().getUserId();
                        String uname = response.body().getUserInfo().getUserName();
                        String pwd = response.body().getUserInfo().getUserPass();
                        String u_roleid = response.body().getUserInfo().getUserRoleId();
                        String img_url = response.body().getUserInfo().getProfileImage();
                        String u_email = response.body().getUserInfo().getUserEmail();

                        System.out.println("user_id " + img_url);

                        editor.putString(ConstantData.USER_ID, uid);
                        editor.putString(ConstantData.USER_NAME, uname);
                        editor.putString(ConstantData.PASSWORD, pwd);
                        editor.putString(ConstantData.U_ROLEID, u_roleid);
                        editor.putString(ConstantData.USER_EMAIL, u_email);
                        editor.putString(ConstantData.USER_IMAGE, img_url);
                        editor.commit();

                        u_role_id = sharedPreferences.getString(ConstantData.U_ROLEID, "");

                        if (u_role_id.equals("4")) {
                            session.createLoginSession(email, password);
                            startActivity(new Intent(getApplicationContext(), TutorDahboard.class));
                            finish();
                        } else if (u_role_id.equals("5")) {
                            session.createLoginSession(email, password);
                            startActivity(new Intent(getApplicationContext(), com.example.bhoomi.lms.Student.Activty.Dashboard_Activity.class));
                            finish();
                        }

                    } else {
                        Toast.makeText(Login_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
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

    @Override
    protected void onDestroy() {
        u_role_id = sharedPreferences.getString(ConstantData.U_ROLEID, "");
        super.onDestroy();
    }
}
