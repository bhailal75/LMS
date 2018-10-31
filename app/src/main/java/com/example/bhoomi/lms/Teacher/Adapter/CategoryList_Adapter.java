package com.example.bhoomi.lms.Teacher.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.DeleteCategory.DeleteCategory;
import com.example.bhoomi.lms.APIModel.DeleteSubcategory.DeleteSubcategory;
import com.example.bhoomi.lms.APIModel.TutorCategoryList.CategoryInfo;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyBoldText;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Model.CategoryModel;
import com.example.bhoomi.lms.Teacher.Activity.CreateContent_Activity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CategoryList_Adapter extends RecyclerView.Adapter<CategoryList_Adapter.ViewHolder> {

    private ArrayList<CategoryInfo> dataSet;
    private Context context;
    private APIService apiService;
    private ProgressDialog progressDialog;
    private String user_id;
    private String status;
    private ViewCategoryClickListner viewCategoryClickListner;

    public CategoryList_Adapter(Activity activity, ArrayList<CategoryInfo> categoryModelArrayList, String user_id,ViewCategoryClickListner viewCategoryClickListner) {
        this.context = activity;
        this.dataSet = categoryModelArrayList;
        this.user_id = user_id;
        this.viewCategoryClickListner = viewCategoryClickListner;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_tutor_category_list_item, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final CategoryInfo categoryModel = dataSet.get(position);

        holder.textView_catName.setText(categoryModel.getCategoryName());
        holder.textView_detail.setText(categoryModel.getCreated_by_name());

        if (categoryModel.getActive().equalsIgnoreCase("1")) {
            holder.textView_stts.setText(R.string.active);
        } else {
            holder.textView_stts.setText(R.string.deactive);
        }
        holder.imageView_edit.setTag(position);

        holder.imageView_Del.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                progressDialog.setMessage(context.getString(R.string.please_wait));
                progressDialog.show();
                if (user_id.equals(dataSet.get(position).getCreatedBy())) {
                    apiService.deleteCategory(categoryModel.getCategoryId()).enqueue(new Callback<DeleteCategory>() {
                        @Override
                        public void onResponse(Call<DeleteCategory> call, Response<DeleteCategory> response) {
                            progressDialog.dismiss();
                            if (response.isSuccessful()) {
                                if (response.body().getStatus().equalsIgnoreCase("Success")) {
                                    notifyDataSetChanged();
                                    dataSet.remove(position);
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            } else {
                                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<DeleteCategory> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                } else {
                    progressDialog.dismiss();
                    Toast.makeText(context, R.string.delete_only_own_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

//        holder.imageView_edit.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                progressDialog.setMessage(context.getString(R.string.please_wait));
//                progressDialog.show();
//                if (user_id.equals(dataSet.get(position).getCreatedBy())) {
//                    LayoutInflater inflater = context.getLayoutInflater();
//                    final View dialogView = inflater.inflate(R.layout.layout_add_category, null);
//                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
//                    dialogBuilder.setView(dialogView);
//                    dialogBuilder.setCancelable(true);
//                    final AlertDialog alertbox = dialogBuilder.create();
//
//                    final TextInputEditText textInputEditText_name = dialogView.findViewById(R.id.qtitle_textInputEditText);
//                    TextInputLayout textInputLayout = dialogView.findViewById(R.id.textInput_qtitle);
//                    ImageView imageView = dialogView.findViewById(R.id.close_dialog);
//                    imageView.setOnClickListener(new View.OnClickListener() {
//                        @Override
//                        public void onClick(View v) {
//                            alertbox.dismiss();
//                        }
//                    });
//
//                    Typeface externalFont = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_r.ttf");
//                    textInputEditText_name.setTypeface(externalFont);
//                    textInputLayout.setTypeface(externalFont);
//
//
//                    final RadioGroup radioGroup = dialogView.findViewById(R.id.radiogrp_stts);
//
//                    radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
//
//                        @Override
//                        public void onCheckedChanged(RadioGroup group, int checkedId) {
//
//                            int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
//                            RadioButton radioBtn = (RadioButton) dialogView.findViewById(checkedRadioButtonId);
//
//                            if (radioBtn.getText().equals("Active")) {
//                                status = "1";
//                            } else {
//                                status = "0";
//                            }
//
//                        }
//                    });
//
//                    Button button = dialogView.findViewById(R.id.buttn_save);
//
//                    button.setOnClickListener(new View.OnClickListener() {
//
//                        @Override
//                        public void onClick(View v) {
//
//                            updateCategory(textInputEditText_name.getText().toString(), alertbox, status);
//                        }
//                    });
//
//                    alertbox.show();


//                    apiService.updateCategory(categoryModel.getCategoryId()).enqueue(new Callback<DeleteCategory>() {
//                        @Override
//                        public void onResponse(Call<DeleteCategory> call, Response<DeleteCategory> response) {
//                            progressDialog.dismiss();
//                            if (response.isSuccessful()) {
//                                if (response.body().getStatus().equalsIgnoreCase("Success")) {
//                                    notifyDataSetChanged();
//                                    dataSet.remove(position);
//                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                } else {
//                                    Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
//                                }
//                            } else {
//                                Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
//                            }
//                        }
//
//                        @Override
//                        public void onFailure(Call<DeleteCategory> call, Throwable t) {
//                            progressDialog.dismiss();
//                        }
////                    });
//                } else {
//                    progressDialog.dismiss();
//                    Toast.makeText(context, "Plz delete only your item", Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
    }



    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void updateData(List<CategoryInfo> categoryInfo) {
        dataSet = (ArrayList<CategoryInfo>) categoryInfo;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private MyMediumText textView_detail, textView_stts;
        private MyBoldText textView_catName;
        private ImageView imageView_Del,imageView_edit;

        public ViewHolder(View itemView) {
            super(itemView);
            textView_catName = itemView.findViewById(R.id.text_title);
            textView_detail = itemView.findViewById(R.id.text_desc);
            textView_stts = itemView.findViewById(R.id.text_stts);
            imageView_Del = itemView.findViewById(R.id.imageView_del);
            imageView_edit = itemView.findViewById(R.id.edit_category);
            imageView_edit.setOnClickListener(this);
            progressDialog = new ProgressDialog(context);
            apiService = APIUtils.getAPIService();
            }

        @Override
        public void onClick(View v) {
            if (v == imageView_edit){
                if (viewCategoryClickListner != null){
                    int pos = (int) v.getTag();
                    viewCategoryClickListner.onCategoryClick(pos);
                }
            }
        }
    }

    public interface ViewCategoryClickListner {
        void onCategoryClick(int pos);
    }
}
