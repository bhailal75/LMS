package com.example.bhoomi.lms.Teacher.Activity;

import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.telecom.Call;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;


import com.example.bhoomi.lms.APIModel.CreateCourse.CreateCourse;
import com.example.bhoomi.lms.APIModel.CreateQuiz.CreateQuizResp;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Retrofit.Config;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Teacher.Adapter.LevelAdapter;

import java.io.File;
import java.util.Calendar;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Callback;
import retrofit2.Response;

public class ContinueCreateQuiz_Activity extends AppCompatActivity implements View.OnClickListener {

//    String[] result = {getString(R.string.student_can_see_the_result), getString(R.string.yes), getString(R.string.no)};
//    String[] anskey = {getString(R.string.student_can_see_answer_key),  getString(R.string.yes), getString(R.string.no)};
//    String[] Public = {getString(R.string.puublic),getString(R.string.yes), getString(R.string.no)};
    private Toolbar toolbar;
    private Button button_create;
    private String cat_id, sub_cat_id, course_id, quiz_title, syllabus, quiz_image,image_name;
    private EditText edittext_price, edittext_time;
    private int pos_answer,pos_result,pos_public;
    private TextInputEditText textInputEditText_passingscore, textInputEditText_que, textInputEditText_retake, text_student_see_result, text_student_see_answer, text_public;
    private TextInputLayout textInput_passingScore, textInput_que, textInput_retake,t_public,t_see_answer,t_see_result;
    private APIService apiService;
    private ProgressDialog progressDialog;
    private String[] strYesNoList = new String[2];
    private LevelAdapter levelAdapter;
    private RequestBody fileBody;
    private RequestBody  t_cat_id, t_sub_cat_id, t_course_id, t_quiz_title, t_syllabus;
    private RequestBody t_price,t_time,t_total_que,t_pass_score,t_retake_allow,t_result,t_answer,t__public,t_status,t_user_id;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private File photoFile;
    private Bitmap bitmap;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_create_quiz_);
        toolbar = findViewById(R.id.toolbar_quiz_continue);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar.getNavigationIcon().setAutoMirrored(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(ContinueCreateQuiz_Activity.this);

        strYesNoList[0] = getString(R.string.yes);
        strYesNoList[1] = getString(R.string.no);

        edittext_price = findViewById(R.id.edittext_price);
        edittext_time = findViewById(R.id.edittext_time);
        textInputEditText_passingscore = findViewById(R.id.passingscore_textInputEditText);
        textInputEditText_que = findViewById(R.id.que_textInputEditText);
        textInputEditText_retake = findViewById(R.id.retake_textInputEditText);
        text_student_see_result = findViewById(R.id.textInputEditText_student_can_see_result);
        text_student_see_answer = findViewById(R.id.textInputEditText_student_can_see_answer);
        text_public = findViewById(R.id.textInputEditText_public);


        textInput_passingScore = findViewById(R.id.textInput_passingScore);
        textInput_que = findViewById(R.id.textInput_que);
        textInput_retake = findViewById(R.id.textInput_retake);

        t_public = (TextInputLayout) findViewById(R.id.t_public);
        t_see_result = (TextInputLayout)findViewById(R.id.t_see_result);
        t_see_answer = (TextInputLayout) findViewById(R.id.t_see_answer);

        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            edittext_price.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_passingscore.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_que.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_retake.setTextDirection(View.TEXT_DIRECTION_RTL);
            text_student_see_answer.setTextDirection(View.TEXT_DIRECTION_RTL);
            text_student_see_result.setTextDirection(View.TEXT_DIRECTION_RTL);
            text_public.setTextDirection(View.TEXT_DIRECTION_RTL);
            t_see_answer.setTextDirection(View.TEXT_DIRECTION_RTL);
            t_see_result.setTextDirection(View.TEXT_DIRECTION_RTL);
        }


        Typeface typeface_medium = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
        textInput_passingScore.setTypeface(typeface_medium);
        textInput_que.setTypeface(typeface_medium);
        textInput_retake.setTypeface(typeface_medium);

        // ////


//        Spinner spin_result = (Spinner)findViewById(R.id.spinner_result);
//       // spin_result.setOnItemSelectedListener(this);
//
//
//        Spinner spin_anskey = (Spinner)findViewById(R.id.spinner_anskey);
//        Spinner spin_public = (Spinner)findViewById(R.id.spinner_public);


        button_create = findViewById(R.id.buttn_create);
        button_create.setOnClickListener(this);
        edittext_time.setOnClickListener(this);
        t_public.setOnClickListener(this);
        t_see_answer.setOnClickListener(this);
        t_see_result.setOnClickListener(this);
        text_student_see_answer.setOnClickListener(this);
        text_student_see_result.setOnClickListener(this);
        text_public.setOnClickListener(this);

        Intent intent = getIntent();
//        bitmap = this.getIntent().getParcelableExtra("bitmap");
        cat_id = intent.getStringExtra("cat_id");
        sub_cat_id = intent.getStringExtra("sub_cat_id");
        course_id = intent.getStringExtra("course_id");
        quiz_title = intent.getStringExtra("quiz_title");
        syllabus = intent.getStringExtra("syllabus");
        quiz_image = intent.getStringExtra("quiz_image");
        image_name = intent.getStringExtra("image_name");
        Uri myUri = Uri.parse(quiz_image);
        photoFile = new File(getRealPathFromURI(myUri));
        }



    public String getRealPathFromURI(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
            cursor.close();
            return result;
        }
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        result = cursor.getString(idx);
        cursor.close();
        return result;
    }

    @Override
    public void onClick(View v) {
        if (v == edittext_time) {

            Calendar mcurrentTime = Calendar.getInstance();
            int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
            int minute = mcurrentTime.get(Calendar.MINUTE);
            int second = mcurrentTime.get(Calendar.SECOND);
            TimePickerDialog mTimePicker;
            mTimePicker = new TimePickerDialog(ContinueCreateQuiz_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                    edittext_time.setText(selectedHour + ":" + selectedMinute + ":" + "00");
                }
            }, hour, minute, true);//Yes 24 hour time
            mTimePicker.setTitle( R.string.select_time);
            mTimePicker.show();
        } else if (v == button_create) {
            if (edittext_price.getText().length() == 0) {
                edittext_price.setError(getString(R.string.enter_price));
            }else if(edittext_time.getText().length() == 0){
                Toast.makeText(this, R.string.select_time, Toast.LENGTH_SHORT).show();
            } else if (textInputEditText_passingscore.getText().length() == 0 ) {
                textInputEditText_passingscore.setError(getString(R.string.enter_passing_score));
            }else if (textInputEditText_que.getText().length() == 0){
                textInputEditText_que.setError(getString(R.string.enter_total_question));
            }else if(textInputEditText_retake.getText().length() == 0){
                textInputEditText_retake.setError(getString(R.string.enter_retake_allowed));
            }else if (text_student_see_result.getText().length() ==0){
                Toast.makeText(this, R.string.student_can_see_the_result, Toast.LENGTH_SHORT).show();
            }else if (text_student_see_answer.getText().length() ==0){
                Toast.makeText(this, R.string.student_can_see_answer_key, Toast.LENGTH_SHORT).show();
            }else if (text_public.getText().length() ==0){
                Toast.makeText(this, R.string.select_public, Toast.LENGTH_SHORT).show();
            }else{
                createQuiz();
            }

//            RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), photoFile);
//
//            // MultipartBody.Part is used to send also the actual file name
//            MultipartBody.Part body = MultipartBody.Part.createFormData(Config.FEATURE_IMAGE, photoFile.getName(), requestFile);


        } else if (v == text_student_see_result) {
            LayoutInflater inflater = (LayoutInflater) ContinueCreateQuiz_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View titleView = inflater.inflate(R.layout.custom_title, null);
            ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText("Select ");
            levelAdapter = new LevelAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, strYesNoList);
            AlertDialog alertDialog = new AlertDialog.Builder(ContinueCreateQuiz_Activity.this)
                    .setTitle(R.string.student_can_see_the_result)
                    .setAdapter(levelAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            text_student_see_result.setText(strYesNoList[position]);
                            pos_result = position;
                        }
                    }).create();
            alertDialog.show();

        } else if (v == text_student_see_answer) {
            LayoutInflater inflater = (LayoutInflater) ContinueCreateQuiz_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View titleView = inflater.inflate(R.layout.custom_title, null);
            ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText("Select ");
            levelAdapter = new LevelAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, strYesNoList);
            AlertDialog alertDialog = new AlertDialog.Builder(ContinueCreateQuiz_Activity.this)
                    .setTitle(R.string.student_can_see_answer_key)
                    .setAdapter(levelAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            text_student_see_answer.setText(strYesNoList[position]);
                            pos_answer = position;
                        }
                    }).create();
            alertDialog.show();
        } else if (v == text_public) {
            LayoutInflater inflater = (LayoutInflater) ContinueCreateQuiz_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View titleView = inflater.inflate(R.layout.custom_title, null);
            ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText("Public");
            levelAdapter = new LevelAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, strYesNoList);
            AlertDialog alertDialog = new AlertDialog.Builder(ContinueCreateQuiz_Activity.this)
                    .setTitle(R.string.public_)
                    .setAdapter(levelAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            text_public.setText(strYesNoList[position]);
                            pos_public = position;
                        }
                    }).create();
            alertDialog.show();
        }
    }

    private void createQuiz() {
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), photoFile);

//         MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData(Config.EXAM_IMAGE, image_name, requestFile);
        fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), photoFile);

        t_cat_id = RequestBody.create(
                MediaType.parse("text/plain"),
                cat_id);
        t_sub_cat_id = RequestBody.create(
                MediaType.parse("text/plain"),
                sub_cat_id);
        t_course_id = RequestBody.create(
                MediaType.parse("text/plain"),
                course_id);
        t_quiz_title = RequestBody.create(
                MediaType.parse("text/plain"),
                quiz_title);
        t_syllabus = RequestBody.create(
                MediaType.parse("text/plain"),
                syllabus);

        t_price = RequestBody.create(
                MediaType.parse("text/plain"),
                edittext_price.getText().toString());

        t_time = RequestBody.create(
                MediaType.parse("text/plain"),
                edittext_time.getText().toString());
        t_total_que = RequestBody.create(
                MediaType.parse("text/plain"),
                textInputEditText_que.getText().toString());

        t_pass_score = RequestBody.create(
                MediaType.parse("text/plain"),
                textInputEditText_passingscore.getText().toString());

        t_status = RequestBody.create(
                MediaType.parse("text/plain"),
                "1");

        t_retake_allow = RequestBody.create(
                MediaType.parse("text/plain"),
                textInputEditText_retake.getText().toString());
        t_result = RequestBody.create(
                MediaType.parse("text/plain"),
               ""+pos_result);
        t_answer = RequestBody.create(
                MediaType.parse("text/plain"),
                ""+pos_answer);

        t__public = RequestBody.create(
                MediaType.parse("text/plain"),
               ""+pos_public);
        t_user_id = RequestBody.create(
                MediaType.parse("text/plain"),
                sharedPreferences.getString(ConstantData.USER_ID, "").toString());

        apiService.createQuiz(t_quiz_title, t_cat_id, t_sub_cat_id, t_course_id, t_syllabus, t_price, t__public, t_time, t_total_que, t_retake_allow, t_result, t_answer, t_status, t_pass_score, body, t_user_id).enqueue(new Callback<CreateQuizResp>() {
            @Override
            public void onResponse(retrofit2.Call<CreateQuizResp> call, Response<CreateQuizResp> response) {
                if (response.isSuccessful())
                {
                    if (response.body().getStatus().equalsIgnoreCase("Success"))
                    {
                        Toast.makeText(ContinueCreateQuiz_Activity.this, "Quiz created successfully", Toast.LENGTH_SHORT).show();
                        finish();
//                        Intent intent = new Intent(getApplicationContext(),Curriculum_Activity.class);
//                        intent.putExtra("quiz_id",response.body().getCreateQuizData().get(0).getCourseId());
//                        startActivity(intent);
//                        finish();
                    }
                    else
                    {
                        Toast.makeText(ContinueCreateQuiz_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(retrofit2.Call<CreateQuizResp> call, Throwable t) {
                Log.i("TAG", "onFailure: "+t.getMessage());
            }
        });
    }


}
