package com.example.bhoomi.lms.Student.Activty;

import android.graphics.Color;
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
import android.widget.Button;

import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Adapter.CategoriesAdapter;
import com.example.bhoomi.lms.Student.Adapter.QnAAdapter;
import com.example.bhoomi.lms.Student.Model.CategoryModel;
import com.example.bhoomi.lms.Student.Model.CourseModel;
import com.example.bhoomi.lms.Student.Model.QnAModel;

import java.util.ArrayList;

public class QnA_Activity extends AppCompatActivity {

    private Toolbar toolbar_qa;
    private RecyclerView recyclerView_qna;
    private QnAAdapter mqnaAdapter;
    private ArrayList<QnAModel> qnaArrayList;
    private QnAModel qnAModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qn_a_);


        toolbar_qa = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_qa);
        toolbar_qa.setTitle("");
        toolbar_qa.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_qa);

        toolbar_qa.setNavigationIcon(R.drawable.ic_back);

        toolbar_qa.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        qnAModel = new QnAModel();
        qnaArrayList = new ArrayList<>();

        recyclerView_qna = findViewById(R.id.recyclerView_qNa);


        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView_qna.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView


        for (int i = 0; i < 6; i ++)
        {
            qnAModel.setUsername("Franscaa");
            qnAModel.setQue("Help is neeeded to access documents");
            qnAModel.setLec_num("Lecture 1");
            qnAModel.setAns("Help is neeeded to access documents Help is neeeded to access documents Help is neeeded to access documents");
            qnAModel.setNumofResponse("2 Response");
            qnAModel.setTime("April 5, 3:20 am");
            qnaArrayList.add(qnAModel);
        }

        mqnaAdapter = new QnAAdapter(this,qnaArrayList);
        recyclerView_qna.setAdapter(mqnaAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_addqa, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.action_plus:

                LayoutInflater inflater = getLayoutInflater();
                View dialogView = inflater.inflate(R.layout.layout_add_qa, null);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setCancelable(true);
                final AlertDialog alertbox = dialogBuilder.create();

                Button button = dialogView.findViewById(R.id.buttn_postq);

                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertbox.dismiss();
                    }
                });

                alertbox.show();

                break;


            default:
                break;

        }

        return super.onOptionsItemSelected(item);
    }

}
