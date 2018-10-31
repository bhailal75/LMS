package com.example.bhoomi.lms.Student.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;

import com.example.bhoomi.lms.APIModel.EnrollLecture.LectureData;
import com.example.bhoomi.lms.APIModel.EnrollLecture.LectureVideosInfo;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Fragment.LecturesFragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LectureExpandableAdapter extends BaseExpandableListAdapter {

    private Activity _context;
    //    private List<String> _listDataHeader; // header titles
//    private HashMap<String, List<String>> _listDataChild;
    private List<LectureVideosInfo> listDataHeader;
    private HashMap<String, List<LectureData>> listDataChild;
    private MyMediumText txtListChild;
    private MyMediumText lblListHeader;
    private ImageView imgMarkAsComplteChild;
    private ViewLectureClickListner viewLectureClickListner;

    public LectureExpandableAdapter(Activity activity, List<LectureVideosInfo> listDataHeader,
                                    HashMap<String, List<LectureData>> listChildData, ViewLectureClickListner viewLectureClickListner) {
        this._context = activity;
        this.listDataHeader = listDataHeader;
        this.listDataChild = listChildData;
        this.viewLectureClickListner = viewLectureClickListner;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataHeader.get(groupPosition).getVideoInfo().get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(final int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {
        final LectureData lectureData = (LectureData) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.section_child_item, null);
        }

        txtListChild = (MyMediumText) convertView.findViewById(R.id.lblListItem);
        imgMarkAsComplteChild = convertView.findViewById(R.id.img_mark_complete_child);
//        if (lectureData.getIsComplete().equals("1"))
//            imgMarkAsComplteChild.setVisibility(View.VISIBLE);

        txtListChild.setText(lectureData.getContentType());
        imgMarkAsComplteChild.setVisibility(View.GONE);
        if (lectureData.getIsComplete().equals("1"))
            imgMarkAsComplteChild.setVisibility(View.VISIBLE);

        txtListChild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                viewLectureClickListner.onLectureClick(groupPosition, childPosition);
            }
        });
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return this.listDataHeader.get(groupPosition).getVideoInfo().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        LectureVideosInfo headerTitle = this.listDataHeader.get(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.section_header_item, null);
        }
        lblListHeader = (MyMediumText) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setText(headerTitle.getSectionTitle());
        ImageView imageView = convertView.findViewById(R.id.imageView_grpIndicator);
        if (isExpanded) {
            imageView.setImageResource(R.drawable.remove);
        } else {
            imageView.setImageResource(R.drawable.plus);
        }
        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    public interface ViewLectureClickListner {
        void onLectureClick(int groupPos, int childPos);
    }
}
