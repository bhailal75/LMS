package com.example.bhoomi.lms.Teacher.Activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.AddScheduleClass.AddScheduleClassResp;
import com.example.bhoomi.lms.APIModel.BraincertResp;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Retrofit.Config;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Teacher.Adapter.LevelAdapter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Schedule_LivaClass_Activity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private TextInputEditText edtClassTitle, edtKeyward, edtDate, edtStartTime, edtEndTime, edtTimeZone, edtPrice;
    private TextView btnCreate;
    private String formate;
    private Calendar calendar;
    private int minute, hour, second;
    private APIService apiService;
    private ProgressDialog progressDialog;
    private int class_id;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private long unixDate, unixStartTime, unixEndTime;
    private String[] strTimeZoneList = new String[2];
    private LevelAdapter levelAdapter;
    private int timezone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule__liva_class);
        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialog = new ProgressDialog(Schedule_LivaClass_Activity.this);
        calendar = Calendar.getInstance();

        strTimeZoneList[0] = getString(R.string.local_time);
        strTimeZoneList[1] = getString(R.string.Kuwait_time);

        toolbar = findViewById(R.id.toolbar_schedule_live_class);
        toolbar.setTitle("");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar.getNavigationIcon().setAutoMirrored(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        edtClassTitle = findViewById(R.id.edt_class_title);
        edtKeyward = findViewById(R.id.edt_keyword);
        edtDate = findViewById(R.id.edt_date);
        edtStartTime = findViewById(R.id.edt_start_time);
        edtEndTime = findViewById(R.id.edt_end_time);
        edtTimeZone = findViewById(R.id.edt_time_zone);
        edtPrice = findViewById(R.id.edt_price);
        btnCreate = findViewById(R.id.btn_create_live_class);

        edtTimeZone.setOnClickListener(this);
        edtDate.setOnClickListener(this);
        edtStartTime.setOnClickListener(this);
        edtEndTime.setOnClickListener(this);
        btnCreate.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if (v == edtDate) {
            DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    calendar.set(Calendar.YEAR, year);
                    calendar.set(Calendar.MONTH, monthOfYear);
                    calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                    String myFormat = "yyyy-MM-dd"; //In which you need put here
                    SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);
                    edtDate.setText(sdf.format(calendar.getTime()));
                    try {
                        Date date1 = sdf.parse(sdf.format(calendar.getTime()));
                        unixDate = (long) date1.getTime() / 1000;
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
            };
            new DatePickerDialog(Schedule_LivaClass_Activity.this, date, calendar
                    .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)).show();

        } else if (v == edtStartTime) {
//            if (unixDate > 0) {
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                second = calendar.get(Calendar.SECOND);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Schedule_LivaClass_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = selectedHour + ":" + selectedMinute;
                        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                        Date date = null;
                        try {
                            date = fmt.parse(time);
                            unixStartTime = (long) date.getTime() / 1000;
//                            unixStartTime = unixDate + unixStartTime;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");
                        String formattedTime = fmtOut.format(date);
                        edtStartTime.setText(formattedTime);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle(R.string.select_time);
                mTimePicker.show();
//            }else{
//                Toast.makeText(this, "Select date", Toast.LENGTH_SHORT).show();
//            }
        } else if (v == edtEndTime) {
//            if (unixDate > 0) {
                hour = calendar.get(Calendar.HOUR_OF_DAY);
                minute = calendar.get(Calendar.MINUTE);
                second = calendar.get(Calendar.SECOND);
                TimePickerDialog mTimePicker;
                mTimePicker = new TimePickerDialog(Schedule_LivaClass_Activity.this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                        String time = selectedHour + ":" + selectedMinute;
                        SimpleDateFormat fmt = new SimpleDateFormat("HH:mm");
                        Date date = null;
                        try {
                            date = fmt.parse(time);
                            unixEndTime = (long) date.getTime() / 1000;
//                            unixEndTime = unixDate + unixEndTime;
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        SimpleDateFormat fmtOut = new SimpleDateFormat("hh:mm aa");
                        String formattedTime = fmtOut.format(date);
                        edtEndTime.setText(formattedTime);
                    }
                }, hour, minute, false);//Yes 24 hour time
                mTimePicker.setTitle(R.string.select_time);
                mTimePicker.show();
//            }else{
//                Toast.makeText(this, "Select date", Toast.LENGTH_SHORT).show();
//            }
        } else if (v == edtTimeZone) {
            LayoutInflater inflater = (LayoutInflater) Schedule_LivaClass_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View titleView = inflater.inflate(R.layout.custom_title, null);
            ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText(getString(R.string.timezone));
            levelAdapter = new LevelAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, strTimeZoneList);
            AlertDialog alertDialog = new AlertDialog.Builder(Schedule_LivaClass_Activity.this)
                    .setTitle(R.string.public_)
                    .setAdapter(levelAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            edtTimeZone.setText(strTimeZoneList[position]);
                            if (position == 1)
                                timezone = 49;
                            else if (position == 0)
                                timezone = 16;
                        }
                    }).create();
            alertDialog.show();
        } else if (v == btnCreate) {
            if (edtClassTitle.getText().toString().trim().length() < 1) {
                edtClassTitle.setError("Enter Class title");
            } else if (edtKeyward.getText().toString().trim().length() < 1) {
                edtKeyward.setError("Enter Keyword");
            } else if (edtDate.getText().toString().trim().length() < 1) {
                edtDate.setError("Select Date");
            } else if (edtStartTime.getText().toString().trim().length() < 1) {
                edtStartTime.setError("Select start time");
            } else if (edtEndTime.getText().toString().trim().length() < 1) {
                edtEndTime.setError("Select End time");
            } else if (edtTimeZone.getText().toString().trim().length() < 1) {
                edtTimeZone.setError("Select TimeZone");
            } else if (edtPrice.getText().toString().trim().length() < 1) {
                edtPrice.setError("Enter Price");
            } else {
                API_fire_Braincert();
            }
        }
    }

    private void API_fire_Braincert() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        apiService.getBraincert(Config.BRAINCERT_KEY, timezone, edtClassTitle.getText().toString(), edtDate.getText().toString(),
                edtStartTime.getText().toString(), edtEndTime.getText().toString(), "USD", "0", "1",
                "1", "1,2,3", "1", "1", "1").enqueue(new Callback<BraincertResp>() {
            @Override
            public void onResponse(Call<BraincertResp> call, Response<BraincertResp> response) {
                if (response.body().getStatus().equals("ok")) {
                    class_id = response.body().getClassId();
                    add_class();
                }

            }

            @Override
            public void onFailure(Call<BraincertResp> call, Throwable t) {
                progressDialog.dismiss();
                Log.d("TAG", "onFailure: ");
            }
        });
    }

    private void add_class() {
        apiService.getAddScheduleClass(""+timezone, edtKeyward.getText().toString(), edtPrice.getText().toString(),
                "1", edtClassTitle.getText().toString(), sharedPreferences.getString(ConstantData.USER_ID, ""), ""+unixDate,
                ""+unixStartTime, ""+unixEndTime, ""+class_id).enqueue(new Callback<AddScheduleClassResp>() {

            @Override
            public void onResponse(Call<AddScheduleClassResp> call, Response<AddScheduleClassResp> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        Toast.makeText(Schedule_LivaClass_Activity.this, "Add Class", Toast.LENGTH_SHORT).show();
                        progressDialog.dismiss();
                        finish();
                    }
                }
            }

            @Override
            public void onFailure(Call<AddScheduleClassResp> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }
}
