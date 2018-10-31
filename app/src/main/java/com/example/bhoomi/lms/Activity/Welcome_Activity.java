package com.example.bhoomi.lms.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.Fbsignin.FbResponse;
import com.example.bhoomi.lms.APIModel.GmailResponse.GmailResponse;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Activty.Dashboard_Activity;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.SharedPref;
import com.example.bhoomi.lms.Teacher.Activity.TutorDahboard;
import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Welcome_Activity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener, AdapterView.OnItemSelectedListener {

    private static final int RC_SIGN_IN = 007;
    private final String TAG = Welcome_Activity.this.getClass().getName();
    SharedPref session;
//    String[] user_role = {"Select User Role", "Tutor", "Student"};

    private TextView textView_logoName, textView_signWithEmail, textView_signWithFacebook, textView_signWithGmail, textView_signUpText, textView_or;
    private LinearLayout linearLayout_signInWithG, linearLayout_signInWithE, linearLayout_signInWithFb;
    private GoogleApiClient mGoogleApiClient;
    private ProgressDialog mProgressDialog;
    private CallbackManager callbackManager;
    private AccessToken accessToken;
    private LoginButton loginButton;
    private APIService apiService;
    private ProgressDialog progressDialog;
    private SharedPreferences sharedPreferences, sharedPreferences_once;
    private SharedPreferences.Editor editor;
    private String user_role_id;
    private String item, once_login;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(Welcome_Activity.this);

        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        sharedPreferences_once = getSharedPreferences("OnceShowUp", MODE_PRIVATE);
        once_login = sharedPreferences_once.getString("firstTime", "");

        if (once_login.equalsIgnoreCase("true")) {

        } else {
//            startActivity(new Intent(getApplicationContext(), AppIntroducing_Activity.class));
//            finish();
        }
        session = new SharedPref(Welcome_Activity.this);

        initParameters();
        initViews();

        try {
            PackageInfo info = getPackageManager().getPackageInfo(
                    "com.example.bhoomi.lms",
                    PackageManager.GET_SIGNATURES);
            for (android.content.pm.Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                Log.d("KeyHash:", Base64.encodeToString(md.digest(), Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {

        } catch (NoSuchAlgorithmException e) {

        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

        Locale current = getResources().getConfiguration().locale;

//        Locale locale = new Locale("ar");
        Configuration config = getBaseContext().getResources().getConfiguration();
        config.locale = current;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        textView_logoName = findViewById(R.id.textView_logoName);
        textView_signWithEmail = findViewById(R.id.textView_signinEmail);
//        textView_signWithFacebook = findViewById(R.id.textView_signinFacebook);
        textView_signWithGmail = findViewById(R.id.textView_signinGmail);
        textView_signUpText = findViewById(R.id.textView_signUpText);
        textView_or = findViewById(R.id.textView_or);

        linearLayout_signInWithE = findViewById(R.id.ll_signinWithEmail);
        linearLayout_signInWithG = findViewById(R.id.ll_signinWithGmail);
        linearLayout_signInWithFb = findViewById(R.id.ll_signinWithFb);

        Typeface typeface_bold = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_b.ttf");
        textView_logoName.setTypeface(typeface_bold);

        Typeface typeface_reglr = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_r.ttf");
        textView_signUpText.setTypeface(typeface_reglr);
        textView_or.setTypeface(typeface_reglr);

        Typeface typeface_medium = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
        textView_signWithGmail.setTypeface(typeface_medium);
//        textView_signWithFacebook.setTypeface(typeface_medium);
        textView_signWithEmail.setTypeface(typeface_medium);

//        String first = getString(R.string.new_here);
//        String next = "<font color='#02b3e4'><b>Create an Account</b></font>";
//
//        textView_signUpText.setText(Html.fromHtml(first + " " + next));

        linearLayout_signInWithE.setOnClickListener(this);
        textView_signUpText.setOnClickListener(this);
        linearLayout_signInWithG.setOnClickListener(this);

        AccessTokenTracker accessTokenTracker = new AccessTokenTracker() {
            @Override
            protected void onCurrentAccessTokenChanged(
                    AccessToken oldAccessToken,
                    AccessToken currentAccessToken) {

                if (currentAccessToken == null) {
                    Log.d(TAG, "User logged out successfully");
                }
            }
        };


    }

    private void initViews() {
        loginButton = (LoginButton) findViewById(R.id.activity_main_btn_login);

        loginButton.setReadPermissions(Arrays.asList(new String[]{"email", "user_birthday", "user_hometown", "public_profile", "user_photos"}));

        if (accessToken != null) {
//            getProfileData();
        } else {
//            rlProfileArea.setVisibility(View.GONE);
        }

        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getProfileData();
            }
            @Override
            public void onCancel() {
            }

            @Override
            public void onError(FacebookException exception) {
            }
        });
    }

    private void getProfileData() {
        try {
            accessToken = AccessToken.getCurrentAccessToken();
            GraphRequest request = GraphRequest.newMeRequest(accessToken, new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    System.out.println("FBrES " + response.toString());
                    try {
                        String name = object.getString("name");
                        String id = object.getString("id");
//                                String email = object.getString("email");
//                                String img = "https://graph.facebook.com/"+id+"/picture?type=large";
                        doSelectUserRole(" ", id, name, " ");
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Toast.makeText(Welcome_Activity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            });
            Bundle parameters = new Bundle();
            parameters.putString("fields", "id,name,link,birthday,gender,email");
            request.setParameters(parameters);
            request.executeAsync();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doSelectUserRole(final String email, final String id, final String name, final String img) {

        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_select_role, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        final AlertDialog alertbox = dialogBuilder.create();
        Button button = dialogView.findViewById(R.id.buttn_save);
        Spinner spin = (Spinner) dialogView.findViewById(R.id.spinner_select_role);
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_role_id.length() == 0) {
                    Toast.makeText(Welcome_Activity.this, R.string.select_user_role, Toast.LENGTH_SHORT).show();
                    LoginManager.getInstance().logOut();
                    alertbox.dismiss();
                } else {
                    alertbox.dismiss();
                    doFbSignIn(email, id, name, img);
                }
            }
        });
        alertbox.show();
    }
    private void doFbSignIn(final String email, String fb_id, String username, String avatar) {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        apiService.getFbData(email, fb_id, username, avatar, user_role_id).enqueue(new Callback<FbResponse>() {
            @Override
            public void onResponse(Call<FbResponse> call, Response<FbResponse> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        Toast.makeText(Welcome_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        System.out.println("getdata " + response.body().getUserData());
                        String uid = response.body().getUserData().get(0).getUserId();
                        String uname = response.body().getUserData().get(0).getUserName();
                        String u_roleid = response.body().getUserData().get(0).getUserRoleId();
                        String fb_id = response.body().getUserData().get(0).getFbId();
                        String u_email = response.body().getUserData().get(0).getUserEmail();
                        String u_img = response.body().getUserData().get(0).getProfile_image();
                        editor.putString(ConstantData.USER_ID, uid);
                        editor.putString(ConstantData.USER_NAME, uname);
                        editor.putString(ConstantData.U_FBID, fb_id);
                        editor.putString(ConstantData.U_ROLEID, u_roleid);
                        editor.putString(ConstantData.USER_EMAIL, u_email);
                        editor.putString(ConstantData.USER_IMAGE, u_img);
                        editor.commit();
                        session.createFbLoginSession(email);
                        if (u_roleid.equals("4")) {
                            startActivity(new Intent(getApplicationContext(), TutorDahboard.class));
                            finish();
                        } else if (u_roleid.equals("5")) {
                            startActivity(new Intent(getApplicationContext(), Dashboard_Activity.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(Welcome_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                }
            }

            @Override
            public void onFailure(Call<FbResponse> call, Throwable t) {
                Toast.makeText(Welcome_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if (v == linearLayout_signInWithE) {
            startActivity(new Intent(getApplicationContext(), Login_Activity.class));
            finish();
        } else if (v == textView_signUpText) {
            startActivity(new Intent(getApplicationContext(), Signup_Activity.class));
//            finish();
        } else if (v == linearLayout_signInWithG) {
            signIn();
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                handleSignInResult(result);
            } else {
                Toast.makeText(Welcome_Activity.this, R.string.auth_went_wrong, Toast.LENGTH_SHORT).show();
            }
        } else {
            callbackManager.onActivityResult(requestCode, resultCode, data);
        }
    }

    private void handleSignInResult(GoogleSignInResult result) {
        Log.d("TGSGS", "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            // Signed in successfully, show authenticated UI.
            GoogleSignInAccount acct = result.getSignInAccount();
            String personName = acct.getDisplayName();
//            String personPhotoUrl = acct.getPhotoUrl().toString();
            String personPhotoUrl = " ";
            String email = acct.getEmail();
            String id = acct.getId();
            updateUI(true, personName, email, personPhotoUrl, id);
        } else {
            // Signed out, show unauthenticated UI.
            updateUI(false, null, null, null, null);
        }
    }

    private void updateUI(boolean isSignedIn, String personName, String email, String personPhotoUrl, String id) {
        if (isSignedIn) {
            doSelectGUserRole(email, id, personName, personPhotoUrl);
        } else {
        }
    }

    private void doSelectGUserRole(final String email, final String id, final String personName, final String personPhotoUrl) {
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.layout_select_role, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(false);
        final AlertDialog alertbox = dialogBuilder.create();
        Button button = dialogView.findViewById(R.id.buttn_save);
        Spinner spin = (Spinner) dialogView.findViewById(R.id.spinner_select_role);
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
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (user_role_id.length() == 0) {
                    Toast.makeText(Welcome_Activity.this, R.string.select_user_role, Toast.LENGTH_SHORT).show();
                    signOut();
                } else {
                    alertbox.dismiss();
                    doGmailSignIn(email, id, personName, personPhotoUrl);
                }
            }
        });
        alertbox.show();
    }

    @Override
    protected void onStart() {
        super.onStart();

        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(mGoogleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
            Log.d("TAG", "Got cached sign-in");
            GoogleSignInResult result = opr.get();
            System.out.println("getresultdata " + result.toString());
            handleSignInResult(result);
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
            showProgressDialog();
            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
                @Override
                public void onResult(GoogleSignInResult googleSignInResult) {
                    hideProgressDialog();
                    handleSignInResult(googleSignInResult);
                }
            });
        }
    }

    private void showProgressDialog() {
        if (mProgressDialog == null) {
            mProgressDialog = new ProgressDialog(this);
            mProgressDialog.setMessage("Loading");
            mProgressDialog.setIndeterminate(true);
        }

        mProgressDialog.show();
    }

    private void hideProgressDialog() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.hide();
        }
    }

    public void initParameters() {
        accessToken = AccessToken.getCurrentAccessToken();
        callbackManager = CallbackManager.Factory.create();

    }


    private void doGmailSignIn(final String email, String gmail_id, String username, String avatar) {

        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.getGpData(email, gmail_id, username, avatar, user_role_id).enqueue(new Callback<GmailResponse>() {
            @Override
            public void onResponse(Call<GmailResponse> call, Response<GmailResponse> response) {

                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        Toast.makeText(Welcome_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();


                        String uid = response.body().getUserInfo().getUserId();
                        String uname = response.body().getUserInfo().getUserEmail();
                        String u_roleid = response.body().getUserInfo().getUserRoleId();
                        String gp_id = response.body().getUserInfo().getGplusId();
                        String u_email = response.body().getUserInfo().getUserEmail();
                        String u_img = response.body().getUserInfo().getProfile_image();


                        editor.putString(ConstantData.USER_ID, uid);
                        editor.putString(ConstantData.USER_NAME, uname);
                        editor.putString(ConstantData.U_GPID, gp_id);
                        editor.putString(ConstantData.U_ROLEID, u_roleid);
                        editor.putString(ConstantData.USER_EMAIL, u_email);
                        editor.putString(ConstantData.USER_IMAGE, u_img);
                        editor.commit();

                        session.createFbLoginSession(email);
                        if (u_roleid != null && u_roleid.equals("4")) {
                            startActivity(new Intent(getApplicationContext(), TutorDahboard.class));
                            finish();
                        } else if (u_roleid != null && u_roleid.equals("5")) {
                            startActivity(new Intent(getApplicationContext(), Dashboard_Activity.class));
                            finish();
                        }else if(u_roleid == null && user_role_id.equals("4")){
                            startActivity(new Intent(getApplicationContext(), TutorDahboard.class));
                            finish();
                        }else if(u_roleid == null && user_role_id.equals("5")){
                            startActivity(new Intent(getApplicationContext(), Dashboard_Activity.class));
                            finish();
                        }
                    } else {
                        progressDialog.dismiss();
                        Toast.makeText(Welcome_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<GmailResponse> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(Welcome_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void signOut() {

        Auth.GoogleSignInApi.signOut(mGoogleApiClient).setResultCallback(
                new ResultCallback<Status>() {
                    @Override
                    public void onResult(Status status) {
                        updateUI(false, null, null, null, null);
                    }
                });
    }

    @Override
    protected void onDestroy() {
        user_role_id = sharedPreferences.getString(ConstantData.U_ROLEID,"");
        super.onDestroy();
    }
}
