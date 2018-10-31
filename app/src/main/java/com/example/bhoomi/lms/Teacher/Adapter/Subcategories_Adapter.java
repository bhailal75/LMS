package com.example.bhoomi.lms.Teacher.Adapter;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.CategoryList.CaregoryList;
import com.example.bhoomi.lms.APIModel.CreateSubCategories.CreateSubcategories;
import com.example.bhoomi.lms.APIModel.DeleteSubcategory.DeleteSubcategory;
import com.example.bhoomi.lms.APIModel.SectionList.SectionList;
import com.example.bhoomi.lms.APIModel.TutorSubCategoryList.SubCategoryInfo;
import com.example.bhoomi.lms.APIModel.TutorSubCategoryList.TutorSubCategoryList;
import com.example.bhoomi.lms.APIModel.UpdateSubCategories.UpdateSubcategories;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyBoldText;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Teacher.Activity.ContactAdmin_Activity;
import com.example.bhoomi.lms.Teacher.Activity.CreateContent_Activity;
import com.example.bhoomi.lms.Teacher.Activity.CreateCourse_Activity;
import com.example.bhoomi.lms.Teacher.Activity.CreateSubCategories_Activity;
import com.example.bhoomi.lms.Teacher.Model.CategoryModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static android.content.Context.MODE_PRIVATE;

public class Subcategories_Adapter extends RecyclerView.Adapter<Subcategories_Adapter.ViewHolder> {

    private ArrayList<SubCategoryInfo> dataSet;
    private Context context;

    private APIService apiService;
    private ProgressDialog progressDialog;
    private String status;
    private CategoryAdapter categoryAdapter;
    ArrayList<com.example.bhoomi.lms.Teacher.Model.CategoryModel> categoryList;
    private String spinner_selected_item;

    private SharedPreferences sharedPreferences;
    private String user_id;


    public Subcategories_Adapter(Activity activity, ArrayList<SubCategoryInfo> categoryModelArrayList,String user_id) {
        this.context = activity;
        this.dataSet = categoryModelArrayList;
        this.user_id = user_id;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.layout_tutor_subcategory_list_item, parent, false);

        ViewHolder myViewHolder = new ViewHolder(view);
        return myViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {

        final SubCategoryInfo categoryModel = dataSet.get(position);
        holder.textView_subcatName.setText(categoryModel.getSubCatName());
        holder.textview_cat.setText(categoryModel.getCategory_name());
        holder.textView_detail.setText(categoryModel.getCreatedByName());


        if (categoryModel.getSubCatActive().equalsIgnoreCase("1"))
        {
            holder.textview_stts.setText(R.string.active);
        }
        else
        {
            holder.textview_stts.setText(R.string.deactive);
        }


        holder.delete_imageView.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                progressDialog.setMessage(context.getString(R.string.please_wait));
                progressDialog.show();

                System.out.println("getsubid "+ categoryModel.getId());
                if (user_id.equals(dataSet.get(position).getCreatedBy())) {

                    apiService.deleteSubCategory(categoryModel.getId()).enqueue(new Callback<DeleteSubcategory>() {

                        @Override
                        public void onResponse(Call<DeleteSubcategory> call, Response<DeleteSubcategory> response) {

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
                        public void onFailure(Call<DeleteSubcategory> call, Throwable t) {
                            progressDialog.dismiss();
                        }
                    });
                }else{
                    progressDialog.dismiss();
                    Toast.makeText(context, R.string.delete_only_own_data, Toast.LENGTH_SHORT).show();
                }
            }
        });

        holder.update_imageview.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (user_id.equals(dataSet.get(position).getCreatedBy())) {
                    LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                    final View dialogView = inflater.inflate(R.layout.layout_update_subcategory, null);
                    AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(context);
                    dialogBuilder.setView(dialogView);
                    dialogBuilder.setCancelable(true);
                    final AlertDialog alertbox = dialogBuilder.create();
                    ImageView imageView = dialogView.findViewById(R.id.close_dialog);
                    imageView.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            alertbox.dismiss();
                        }
                    });

                    final EditText editText = dialogView.findViewById(R.id.spinner_category);

                    final TextInputEditText textInputEditText = dialogView.findViewById(R.id.qtitle_textInputEditText);
                    final TextInputLayout textInput_qtitle = dialogView.findViewById(R.id.textInput_qtitle);


                    final Button buttonsave = dialogView.findViewById(R.id.buttn_save);

                    final RadioButton radio_active = dialogView.findViewById(R.id.radio_active);
                    final RadioButton radio_deactive = dialogView.findViewById(R.id.radio_deactive);
                    final RadioGroup radioStts = dialogView.findViewById(R.id.radiogrp_stts);

                    Configuration config =context.getResources().getConfiguration();
                    if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
                        textInputEditText.setTextDirection(View.TEXT_DIRECTION_RTL);
                        editText.setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
                        editText.setTextDirection(View.TEXT_DIRECTION_RTL);
                    }

                    textInputEditText.setText(categoryModel.getSubCatName());

                    if (categoryModel.getSubCatActive().equalsIgnoreCase("1")) {
                        radioStts.check(R.id.radio_active);
                        status = "1";
                    } else {
                        radioStts.check(R.id.radio_deactive);
                        status = "0";
                    }

                    spinner_selected_item = categoryModel.getCatId();

                    editText.setText(categoryModel.getCategory_name());

                    radioStts.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

                        @Override
                        public void onCheckedChanged(RadioGroup group, int checkedId) {

                            int checkedRadioButtonId = radioStts.getCheckedRadioButtonId();
                            RadioButton radioBtn = (RadioButton) dialogView.findViewById(checkedRadioButtonId);

                            if (radioBtn.getText().equals(context.getString(R.string.active))) {
                                status = "1";
                            } else {
                                status = "0";
                            }
                        }
                    });


                    Typeface typeface_medium = Typeface.createFromAsset(context.getAssets(), "fonts/ubuntu_m.ttf");
                    editText.setTypeface(typeface_medium);
                    textInputEditText.setTypeface(typeface_medium);
                    textInput_qtitle.setTypeface(typeface_medium);
                    buttonsave.setTypeface(typeface_medium);
                    radio_active.setTypeface(typeface_medium);
                    radio_deactive.setTypeface(typeface_medium);

                    editText.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {

                            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                            View titleView = inflater.inflate(R.layout.custom_title, null);

                            ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText(R.string.select_category);
                            AlertDialog alertDialog = new AlertDialog.Builder(context)
                                    .setTitle(R.string.select_category)
                                    .setAdapter(categoryAdapter, new DialogInterface.OnClickListener() {

                                        @Override
                                        public void onClick(DialogInterface dialog, int position) {


                                            editText.setText(categoryList.get(position).getCat_name());
                                            spinner_selected_item = categoryList.get(position).getCat_id();

                                            dialog.dismiss();
                                        }

                                    }).create();
                            alertDialog.show();

                        }
                    });


                    Button button = dialogView.findViewById(R.id.buttn_save);

                    button.setOnClickListener(new View.OnClickListener() {

                        @Override
                        public void onClick(View v) {
                            if ( textInputEditText.getText().length() == 0){
                                textInputEditText.setError(context.getString(R.string.enter_sub_category));
                            }
                            updateSubCategories(categoryModel.getId(), spinner_selected_item, textInputEditText.getText().toString(), status, alertbox);
                        }
                    });

                    alertbox.show();

                }else{
                    Toast.makeText(context, context.getString(R.string.update_only_own_data), Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    private void updateSubCategories(String id, String spinner_selected_item, String s, String status, final AlertDialog alertbox) {


        progressDialog.setMessage(context.getString(R.string.please_wait));
        progressDialog.show();

        System.out.println("catname "+ id + " "+ spinner_selected_item+"  "+ s + " "+ status + " "+ user_id);
        apiService.updateSubCategory(id,spinner_selected_item,s,status, user_id).enqueue(new Callback<UpdateSubcategories>() {

            @Override
            public void onResponse(Call<UpdateSubcategories> call, Response<UpdateSubcategories> response) {
                progressDialog.dismiss();

                if (response.isSuccessful())
                {
                    if (response.body().getStatus().equalsIgnoreCase("Success"))
                    {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        alertbox.dismiss();
                        ((Activity)context).recreate();
                    }
                    else
                    {
                        Toast.makeText(context, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
                else
                {
                    Toast.makeText(context, response.message(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UpdateSubcategories> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }


    @Override
    public int getItemCount() {
        return dataSet.size();
    }

    public void updateData(List<SubCategoryInfo> subCategoryInfo) {
        dataSet = (ArrayList<SubCategoryInfo>) subCategoryInfo;
        notifyDataSetChanged();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {


        private MyMediumText textView_detail,textview_cat,textview_stts;
        private MyBoldText textView_subcatName;
        private ImageView delete_imageView, update_imageview;


        public ViewHolder(View itemView) {
            super(itemView);


            textView_subcatName = itemView.findViewById(R.id.text_title);
            textview_cat = itemView.findViewById(R.id.text_subtitle);
            textView_detail = itemView.findViewById(R.id.text_desc);
            textview_stts = itemView.findViewById(R.id.text_stts);

            delete_imageView = itemView.findViewById(R.id.delete_imageView);
            update_imageview = itemView.findViewById(R.id.update_imageview);

            progressDialog = new ProgressDialog(context);
            apiService = APIUtils.getAPIService();

            sharedPreferences = context.getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
            user_id = sharedPreferences.getString(ConstantData.USER_ID,"");

            categoryList = new ArrayList<>();

            doGetCategoryList();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

//                    context.startActivity(new Intent(context, CreateContent_Activity.class));

                }
            });




        }
    }

    private void doGetCategoryList() {


        apiService.getData().enqueue(new Callback<CaregoryList>() {

            @Override
            public void onResponse(Call<CaregoryList> call, Response<CaregoryList> response) {


                if (response.isSuccessful())
                {
                    categoryList.clear();
                    if (response.body().getStatus().equalsIgnoreCase("Success"))
                    {

                        for (int i = 0; i < response.body().getCategoryInfo().size(); i++)
                        {
                            CategoryModel categoryModel = new CategoryModel();
                            categoryModel.setCat_name(response.body().getCategoryInfo().get(i).getCategoryName());
                            categoryModel.setCat_id(response.body().getCategoryInfo().get(i).getCategoryId());

                            categoryList.add(categoryModel);
                        }

                        categoryAdapter = new CategoryAdapter(context, android.R.layout.simple_spinner_item, categoryList);
                        categoryAdapter.notifyDataSetChanged();


                    }
                    else
                    {

                    }
                }
                else
                {
                }

            }

            @Override
            public void onFailure(Call<CaregoryList> call, Throwable t) {
            }
        });

    }
}

