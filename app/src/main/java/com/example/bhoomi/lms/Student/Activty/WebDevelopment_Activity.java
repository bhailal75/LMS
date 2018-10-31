package com.example.bhoomi.lms.Student.Activty;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.claudiodegio.msv.BaseMaterialSearchView;
import com.claudiodegio.msv.MaterialSearchView;
import com.claudiodegio.msv.OnSearchViewListener;
import com.example.bhoomi.lms.APIModel.SubcategoryList.CourseInfo;
import com.example.bhoomi.lms.APIModel.SubcategoryList.SubcatList;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Adapter.ParticularCourseListAdapter;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;

import java.util.ArrayList;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebDevelopment_Activity extends AppCompatActivity implements AdapterView.OnItemSelectedListener, View.OnClickListener, OnSearchViewListener {

    //    String[] rating={"price_high_to_low","price_low_to_high","ratings","newest"};
    String[] rating = {"Ratings", "Newest", "Price Low to High", "Price High to Low"};
    private RecyclerView recyclerView_courses;
    private ParticularCourseListAdapter mAdapter;
    private Toolbar toolbar_coursetype;
    private APIService apiService;
    private ProgressDialog progressDialog;
    private MenuItem itemToHide;
    private MenuItem itemToShow;
    private String spinner_selected_item;
    private String searchitem = "";
    private BaseMaterialSearchView searchView;
    private String category_id;
    private MyMediumText toolbar_title;
    private String price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_development_);
        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(WebDevelopment_Activity.this);
        Intent intent = getIntent();
        category_id = intent.getStringExtra("cat_id");
        toolbar_coursetype = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_coursemain);
        toolbar_coursetype.setTitle("");
        toolbar_coursetype.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_coursetype);
        toolbar_coursetype.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_coursetype.getNavigationIcon().setAutoMirrored(true);
        toolbar_coursetype.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        toolbar_title = findViewById(R.id.toolbar_title);
        toolbar_title.setText(intent.getStringExtra("cat_name"));
        searchView = (MaterialSearchView) findViewById(R.id.sv);
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            searchView.setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
            searchView.setTextDirection(View.TEXT_DIRECTION_RTL);
//            searchView.setTextDirection(View.TEXT_DIRECTION_RTL);
        }
        searchView.setOnSearchViewListener(this); // this class implements OnSearchViewListener
        recyclerView_courses = findViewById(R.id.recyclerView_courses);
        recyclerView_courses.setHasFixedSize(true);
        recyclerView_courses.setNestedScrollingEnabled(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView_courses.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView
        mAdapter = new ParticularCourseListAdapter(this, new ArrayList<CourseInfo>(0));
        recyclerView_courses.setAdapter(mAdapter);
        doGetSubCatList(spinner_selected_item, searchitem, price);
    }

    private void doGetSubCatList(String spinner_selected_item, String s, String price) {
        apiService.getSubCatData(category_id, s, spinner_selected_item, price).enqueue(new Callback<SubcatList>() {
            @Override
            public void onResponse(Call<SubcatList> call, Response<SubcatList> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        mAdapter.updateSubCatList(response.body().getCourseInfo());
                        System.out.println("filtercourse " + response.body().getCourseInfo());
                    } else {
                        Toast.makeText(WebDevelopment_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(WebDevelopment_Activity.this, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<SubcatList> call, Throwable t) {
                System.out.println("throwableDaat " + t.getMessage());
                Toast.makeText(WebDevelopment_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_filter, menu);
        MenuItem item = menu.findItem(R.id.action_search);
        searchView.setMenuItem(item);
        itemToShow = menu.findItem(R.id.action_filter);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_search:
                break;
            case R.id.action_filter:
               filterDialog();
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void filterDialog() {
        price = null;
        LayoutInflater inflater = getLayoutInflater();
        final View dialogView = inflater.inflate(R.layout.layout_sort_dialog, null);
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setCancelable(true);
        final AlertDialog alertbox = dialogBuilder.create();
        TextView textView_sortby, textView_price, textView_level, textView_features, textView_numCourse;
        final RadioGroup radioGroup_paid, radioGroup_level, radioGroup_features;
        Button button_reset, button_applyFilter;
        RadioButton radioButton_free, radioButton_paid, radioButton_alllvl, radioButton_beginner, radioButton_expert, radioButton_caption, radioButton_quizz;
        textView_sortby = dialogView.findViewById(R.id.textView_sortby);
        textView_numCourse = dialogView.findViewById(R.id.textView_numOfCourse);
        textView_price = dialogView.findViewById(R.id.textView_price);
        textView_level = dialogView.findViewById(R.id.textView_level);
        textView_features = dialogView.findViewById(R.id.textView_features);
        radioButton_paid = dialogView.findViewById(R.id.radio_price_paid);
        radioButton_free = dialogView.findViewById(R.id.radio_price_free);
        radioButton_alllvl = dialogView.findViewById(R.id.radio_level_all);
        radioButton_beginner = dialogView.findViewById(R.id.radio_level_beginner);
        radioButton_expert = dialogView.findViewById(R.id.radio_level_expert);
        radioButton_caption = dialogView.findViewById(R.id.radio_feature_caption);
        radioButton_quizz = dialogView.findViewById(R.id.radio_features_quiz);
        radioGroup_paid = dialogView.findViewById(R.id.radio_grp_price);
        radioGroup_paid.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioButtonId = radioGroup_paid.getCheckedRadioButtonId();
                RadioButton radioBtn = (RadioButton) dialogView.findViewById(checkedRadioButtonId);
                price = String.valueOf(radioBtn.getText());
                if (String.valueOf(radioBtn.getText()).equals("مجاني"))
                    price = "Free";
                if (String.valueOf(radioBtn.getText()).equals("مدفوع"))
                    price = "Paid";
            }
        });
        button_reset = dialogView.findViewById(R.id.button_Reset);
        button_applyFilter = dialogView.findViewById(R.id.button_applyFilter);
        Spinner spin = (Spinner) dialogView.findViewById(R.id.simple_spinner);
        spin.setOnItemSelectedListener(this);
//Creating the ArrayAdapter instance having the bank name list
        ArrayAdapter aa = new ArrayAdapter(this, android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.rating)) {
            public View getView(int position, View convertView, ViewGroup parent) {
                View v = super.getView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_r.ttf");
                ((TextView) v).setTypeface(externalFont);
               return v;
            }
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                View v = super.getDropDownView(position, convertView, parent);
                Typeface externalFont = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_r.ttf");
                ((TextView) v).setTypeface(externalFont);
                return v;
            }
        };
        //Setting the ArrayAdapter data on the Spinner
        aa.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spin.setAdapter(aa);
        alertbox.show();
       Typeface typeface_bold = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_b.ttf");
        textView_numCourse.setTypeface(typeface_bold);
        button_applyFilter.setTypeface(typeface_bold);
        button_reset.setTypeface(typeface_bold);
        Typeface typeface_reglr = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_r.ttf");
        radioButton_paid.setTypeface(typeface_reglr);
        radioButton_free.setTypeface(typeface_reglr);
        radioButton_alllvl.setTypeface(typeface_reglr);
        radioButton_beginner.setTypeface(typeface_reglr);
        radioButton_expert.setTypeface(typeface_reglr);
        radioButton_caption.setTypeface(typeface_reglr);
        radioButton_quizz.setTypeface(typeface_reglr);
        Typeface typeface_medium = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
        textView_sortby.setTypeface(typeface_medium);
        textView_price.setTypeface(typeface_medium);
        textView_features.setTypeface(typeface_medium);
        textView_level.setTypeface(typeface_medium);
       button_reset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGetSubCatList(null,"",null);
                alertbox.dismiss();
            }
        });
        button_applyFilter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doGetSubCatList(spinner_selected_item, searchitem, price);
                alertbox.dismiss();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        spinner_selected_item = rating[position];
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }

    @Override
    public void onClick(View v) {
    }

    @Override
    public void onSearchViewShown() {
    }

    @Override
    public void onSearchViewClosed() {
    }

    @Override
    public boolean onQueryTextSubmit(String s) {
        return false;
    }

    @Override
    public void onQueryTextChange(String s) {
        searchitem = s;
        doGetSubCatList(spinner_selected_item, s, price);
    }
}
