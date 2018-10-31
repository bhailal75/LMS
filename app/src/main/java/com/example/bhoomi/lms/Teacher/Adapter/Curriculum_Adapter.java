package com.example.bhoomi.lms.Teacher.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.SectionList.CourseSectionsInfo;
import com.example.bhoomi.lms.APIModel.SectionList.SectionList;
import com.example.bhoomi.lms.APIModel.SubCategories.SubCategoryInfo;
import com.example.bhoomi.lms.APIModel.UpdateSection.UpdateSection;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Activty.WebDevelopment_Activity;
import com.example.bhoomi.lms.Student.Adapter.CategoriesAdapter;
import com.example.bhoomi.lms.Student.Constants.MyBoldText;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Model.CategoryModel;
import com.example.bhoomi.lms.Teacher.Activity.CreateContent_Activity;
import com.example.bhoomi.lms.Teacher.Activity.Curriculum_Activity;
import com.example.bhoomi.lms.Teacher.Model.DeleteSectionResp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Curriculum_Adapter extends RecyclerView.Adapter<Curriculum_Adapter.ViewHolder> {

    private ArrayList<CourseSectionsInfo> dataSet;
    private Context context;

    private APIService apiService;
    private ProgressDialog progressDialog;

    public Curriculum_Adapter(Activity activity, ArrayList<CourseSectionsInfo> categoryModelArrayList) {
        this.context = activity;
        this.dataSet = categoryModelArrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_tutor_curriculum_list_item, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        final CourseSectionsInfo categoryModel = dataSet.get(position);


        holder.textView_catName.setText(categoryModel.getSectionName());
        holder.textView_detail.setText(categoryModel.getSectionTitle());

        System.out.println("viewSectionid " + categoryModel.getSectionId() + " " + categoryModel.getCourseId());

        holder.imageView_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSection(categoryModel.getSectionId());
            }
        });

        holder.imageView_update.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                View dialogView = inflater.inflate(R.layout.layout_update_curriculam, null);
                AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                dialogBuilder.setView(dialogView);
                dialogBuilder.setCancelable(true);
                final AlertDialog alertbox = dialogBuilder.create();

                Button button = dialogView.findViewById(R.id.buttn_save);

                final TextInputEditText textInputEditText_name = dialogView.findViewById(R.id.qname_textInputEditText);
                TextInputLayout textInputLayout_name = dialogView.findViewById(R.id.textInput_qname);

                final TextInputEditText textInputEditText = dialogView.findViewById(R.id.qtitle_textInputEditText);
                TextInputLayout textInputLayout = dialogView.findViewById(R.id.textInput_qtitle);
                ImageView imageView = dialogView.findViewById(R.id.close_dialog);

                textInputEditText.setText(categoryModel.getSectionTitle());
                textInputEditText_name.setText(categoryModel.getSectionName());

                Configuration config = context.getResources().getConfiguration();
                if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                    textInputEditText_name.setTextDirection(View.TEXT_DIRECTION_RTL);
                    textInputLayout_name.setTextDirection(View.TEXT_DIRECTION_RTL);
                    textInputEditText.setTextDirection(View.TEXT_DIRECTION_RTL);
                    textInputLayout.setTextDirection(View.TEXT_DIRECTION_RTL);
                }

                Typeface typeface_medium = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_m.ttf");
                textInputEditText.setTypeface(typeface_medium);
                textInputLayout.setTypeface(typeface_medium);
                textInputEditText_name.setTypeface(typeface_medium);
                textInputLayout_name.setTypeface(typeface_medium);
                button.setTypeface(typeface_medium);

                imageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertbox.dismiss();
                    }
                });
                button.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        updateCurriculum(textInputEditText_name.getText().toString(), textInputEditText.getText().toString(), alertbox, categoryModel.getSectionId(), categoryModel.getCourseId());
                    }
                });

                alertbox.show();

            }
        });
    }

    private void deleteSection(String sectionId) {
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        apiService.deleteSection(sectionId).enqueue(new Callback<DeleteSectionResp>() {
            @Override
            public void onResponse(Call<DeleteSectionResp> call, Response<DeleteSectionResp> response) {
                progressDialog.dismiss();

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        ((Activity) context).recreate();
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<DeleteSectionResp> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateCurriculum(String s, String s1, final AlertDialog alertbox, String sectionId, String courseId) {
        progressDialog.setMessage("Please wait..");
        progressDialog.show();
        System.out.println("updtectionid " + sectionId + " " + courseId);
        apiService.updateSection(sectionId, s1, s, courseId).enqueue(new Callback<UpdateSection>() {
            @Override
            public void onResponse(Call<UpdateSection> call, Response<UpdateSection> response) {
                progressDialog.dismiss();
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        alertbox.dismiss();
                        ((Activity) context).recreate();
                    } else {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<UpdateSection> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(context, t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void updateCurriculum(List<CourseSectionsInfo> courseSectionsInfo) {
        dataSet = (ArrayList<CourseSectionsInfo>) courseSectionsInfo;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private MyMediumText textView_detail;
        private MyBoldText textView_catName;
        private ImageView imageView_update, imageView_delete;


        public ViewHolder(View itemView) {
            super(itemView);

            textView_catName = itemView.findViewById(R.id.text_title);
            textView_detail = itemView.findViewById(R.id.text_desc);
            imageView_update = itemView.findViewById(R.id.img_update);
            imageView_delete = itemView.findViewById(R.id.img_delete);

            apiService = APIUtils.getAPIService();
            progressDialog = new ProgressDialog(context);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int i = getAdapterPosition();

                    Intent intent = new Intent(context, CreateContent_Activity.class);
                    intent.putExtra("course_id", dataSet.get(i).getCourseId());
                    context.startActivity(intent);
                    ((Activity) context).finish();

                }
            });
        }
    }
}

