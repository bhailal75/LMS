package com.example.bhoomi.lms.Student.Fragment;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.example.bhoomi.lms.APIModel.EnrollLecture.LectureData;
import com.example.bhoomi.lms.APIModel.EnrollLecture.LectureResp;
import com.example.bhoomi.lms.APIModel.EnrollLecture.LectureVideosInfo;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Adapter.LectureExpandableAdapter;
import com.example.bhoomi.lms.Student.Constants.ConstantData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

@SuppressLint("ValidFragment")
public class LecturesFragment extends Fragment implements LectureExpandableAdapter.ViewLectureClickListner {

    private ExpandableListView expandableListView;
    private ArrayList<LectureVideosInfo> lecturesModelArrayList;
    private APIService apiService;
    private String course_id;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private PlayVideoClickListner playVideoClickListner;
    private List<String> listDataHeader;
    private HashMap<String, List<LectureData>> listDataChild;
    private LectureExpandableAdapter listAdapter;

    public LecturesFragment() {}
    public LecturesFragment(ArrayList<LectureVideosInfo> lecturesModelArrayList, String course_id) {
        this.lecturesModelArrayList = lecturesModelArrayList;
        this.course_id = course_id;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_lectures, container, false);

        apiService = APIUtils.getAPIService();
        sharedPreferences = getActivity().getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        playVideoClickListner = (PlayVideoClickListner) getActivity();
        expandableListView = view.findViewById(R.id.expandable_lectures);
        lecturesModelArrayList = new ArrayList<>();
        listDataHeader = new ArrayList<>();
        listDataChild = new HashMap<String, List<LectureData>>();
        getLectureData();
        return view;
    }

    private void getLectureData() {
        apiService.getMyLecture(sharedPreferences.getString(ConstantData.USER_ID, ""), course_id).enqueue(new Callback<LectureResp>() {
            @Override
            public void onResponse(Call<LectureResp> call, Response<LectureResp> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        if (response.body().getCourseVideosInfo() != null && response.body().getCourseVideosInfo().size() > 0) {
                            for (int i = 0; i < response.body().getCourseVideosInfo().size(); i++) {
                                listDataHeader.add(response.body().getCourseVideosInfo().get(i).getSectionTitle());
                                for (int j = 0; j < response.body().getCourseVideosInfo().get(i).getVideoInfo().size(); j++) {
                                    if (response.body().getCourseVideosInfo().get(j).getVideoInfo() != null && response.body().getCourseVideosInfo().get(j).getVideoInfo().size() > 0)
                                        listDataChild.put(response.body().getCourseVideosInfo().get(i).getSectionTitle(), response.body().getCourseVideosInfo().get(j).getVideoInfo());
                                }
                            }
                            listAdapter = new LectureExpandableAdapter(getActivity(), response.body().getCourseVideosInfo(), listDataChild,LecturesFragment.this);
                            expandableListView.setAdapter(listAdapter);
                            lecturesModelArrayList.addAll(response.body().getCourseVideosInfo());
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LectureResp> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    @Override
    public void onLectureClick(int groupPos, int childPos) {
        playVideoClickListner.onFileClick(groupPos,childPos);
    }

    public interface PlayVideoClickListner {
        void onFileClick(int groupPos,int childPos);
    }
}