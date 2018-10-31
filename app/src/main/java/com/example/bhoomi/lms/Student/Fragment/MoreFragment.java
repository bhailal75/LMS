package com.example.bhoomi.lms.Student.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Adapter.MoreLecAdapter;
import com.example.bhoomi.lms.Student.Model.MoreLecModel;

import java.util.ArrayList;

public class MoreFragment extends Fragment {

    private RecyclerView recyclerView_more;
    private MoreLecAdapter mLectureAdapter;
    private ArrayList<MoreLecModel> moreModelArrayList;
    private MoreLecModel moreLecModel;

    static String[] moreArray = {"About this course", "Q & A",
            "Announcements","Add this course to favorites"};

    public MoreFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View view = inflater.inflate(R.layout.fragment_more, container, false);

        recyclerView_more = view.findViewById(R.id.recyclerView_more);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(),LinearLayoutManager.VERTICAL,false);
        recyclerView_more.setLayoutManager(linearLayoutManager); // set LayoutManager to RecyclerView

        moreLecModel = new MoreLecModel();
        moreModelArrayList = new ArrayList<>();

        for (int i = 0; i < moreArray.length; i ++)
        {
            moreModelArrayList.add(new MoreLecModel(moreArray[i]));
        }


        mLectureAdapter = new MoreLecAdapter(getActivity(),moreModelArrayList);
        recyclerView_more.setAdapter(mLectureAdapter);


        return view;
    }

}
