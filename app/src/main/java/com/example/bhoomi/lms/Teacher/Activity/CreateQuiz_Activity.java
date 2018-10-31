package com.example.bhoomi.lms.Teacher.Activity;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.bhoomi.lms.APIModel.CategoryList.CaregoryList;
import com.example.bhoomi.lms.APIModel.CourseList.CourseList;
import com.example.bhoomi.lms.APIModel.SubCategories.SubCategories;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Adapter.CourseAdapter;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Constants.SharedPref;
import com.example.bhoomi.lms.Teacher.Adapter.CategoryAdapter;
import com.example.bhoomi.lms.Teacher.Adapter.SubCategoryAdapter;
import com.example.bhoomi.lms.Teacher.Model.CategoryModel;
import com.example.bhoomi.lms.Teacher.Model.CourseListModel;
import com.example.bhoomi.lms.Teacher.Model.SubCategoryModel;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateQuiz_Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CAPTURE = 1;
    private static final int REQUEST_GALLERY = 2;

    ArrayList<CategoryModel> categoryList;
    ArrayList<SubCategoryModel> subCategoryList;
    ArrayList<CourseListModel> courseList;

    ImageView image_quiz;
    private CategoryModel categoryModel;
    private CategoryAdapter categoryAdapter;
    private SubCategoryAdapter subcategoryAdapter;
    private CourseListAdapter courseListAdapter;
    private String category_id;
    private Toolbar toolbar;
    private TextInputEditText textInputEditText_quiz_title;
    private TextInputEditText spin_category, spin_subcategory, spin_course;
    private String spinner_selected_item, spinner_selected_sub_item, spinner_selected_course_item;
    private TextInputEditText textInputEditText_syllabus;
    private Button button_create;
    private APIService apiService;
    private ProgressDialog progressDialog;
    private SharedPref session;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private Uri picUri;
    private String filePath;
    private File photoFile;
    private String path;

    public static boolean isConnectd(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isAvailable()
                && cm.getActiveNetworkInfo().isConnected()) {
            return true;
        }
        return false;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_quiz_);

//        sendImageInterface = (SendImageInterface) getApplicationContext();

        toolbar = findViewById(R.id.toolbar_quiz);

        textInputEditText_quiz_title = findViewById(R.id.textInputEditText_quiz_title);
        textInputEditText_syllabus = findViewById(R.id.textInputEditText_syllabus);
        spin_category = findViewById(R.id.spinner_category);
        spin_category.setOnClickListener(this);
        spin_subcategory = findViewById(R.id.spinner_subcategory);
        spin_subcategory.setOnClickListener(this);
        spin_course = findViewById(R.id.spinner_course);
        spin_course.setOnClickListener(this);
        image_quiz = findViewById(R.id.image_quiz);
        image_quiz.setOnClickListener(this);
        button_create = findViewById(R.id.buttn_continue);
        button_create.setOnClickListener(this);
        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(CreateQuiz_Activity.this);
        session = new SharedPref(CreateQuiz_Activity.this);
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        spinner_selected_sub_item = "";
        spinner_selected_item = "";
        spinner_selected_course_item = "";
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

        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            textInputEditText_quiz_title.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_syllabus.setTextDirection(View.TEXT_DIRECTION_RTL);
            spin_category.setTextDirection(View.TEXT_DIRECTION_RTL);
            spin_subcategory.setTextDirection(View.TEXT_DIRECTION_RTL);
            spin_course.setTextDirection(View.TEXT_DIRECTION_RTL);
        }

        categoryList = new ArrayList<>();
        subCategoryList = new ArrayList<>();
        courseList = new ArrayList<>();
        doGetCategoryList();
       doGetCourseList();
    }

    @Override
    public void onClick(View v) {
        if (v == button_create) {
            if (textInputEditText_quiz_title.getText().toString().trim().length() == 0) {
                textInputEditText_quiz_title.setError(getString(R.string.enter_quiz_title));
            }else if (spinner_selected_item.length() == 0) {
                spin_category.setError(getString(R.string.select_subcategory));
            } else if (spinner_selected_sub_item.length() == 0) {
                spin_subcategory.setError(getString(R.string.select_sub_category));
            } else if (spinner_selected_course_item.length() == 0) {
                spin_course.setError(getString(R.string.select_course));
            } else if (textInputEditText_syllabus.getText().toString().trim().length() == 0) {
                textInputEditText_syllabus.setError(getString(R.string.enter_syllabus));
            } else if (picUri == null) {
                Toast.makeText(this, R.string.select_image, Toast.LENGTH_SHORT).show();
            } else if (image_quiz.getDrawable() == null) {
                Toast.makeText(this, R.string.drop_some_image, Toast.LENGTH_SHORT).show();
            } else {
                Intent intent = new Intent(getApplicationContext(), ContinueCreateQuiz_Activity.class);
                intent.putExtra("cat_id", spinner_selected_item);
                intent.putExtra("sub_cat_id", spinner_selected_sub_item);
                intent.putExtra("course_id", spinner_selected_course_item);
                intent.putExtra("quiz_title", textInputEditText_quiz_title.getText().toString());
                intent.putExtra("syllabus", textInputEditText_syllabus.getText().toString());
//                intent.putExtra("quiz_image", image_quiz.getDrawable().toString());
                intent.putExtra("quiz_image", picUri.toString());
                intent.putExtra("image_name",photoFile.getName());

                startActivity(intent);
                finish();

            }


        } else if (v == spin_category) {
            LayoutInflater inflater = (LayoutInflater) CreateQuiz_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View titleView = inflater.inflate(R.layout.custom_title, null);

            ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText(R.string.select_category);
            AlertDialog alertDialog = new AlertDialog.Builder(CreateQuiz_Activity.this)
                    .setTitle(R.string.select_category)
                    .setAdapter(categoryAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            spin_subcategory.setEnabled(true);
                            subCategoryList.clear();
                            spin_subcategory.setText("");
                            spin_category.setText(categoryList.get(position).getCat_name());
                            spinner_selected_item = categoryList.get(position).getCat_id();
                            if (!isConnectd(CreateQuiz_Activity.this)) {
                                Toast.makeText(CreateQuiz_Activity.this, R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
                            } else {
                                doGetSubCategoryList(spinner_selected_item);
                            }
                            dialog.dismiss();
                        }
                    }).create();
            alertDialog.show();

        } else if (v == spin_subcategory) {
            LayoutInflater inflater = (LayoutInflater) CreateQuiz_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View titleView = inflater.inflate(R.layout.custom_title, null);

            ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText(R.string.select_sub_category);
            AlertDialog alertDialog = new AlertDialog.Builder(CreateQuiz_Activity.this)
                    .setTitle(R.string.select_sub_category)
                    .setAdapter(subcategoryAdapter, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {

                            spin_subcategory.setText(subCategoryList.get(position).getSub_cat_name());
                            spinner_selected_sub_item = subCategoryList.get(position).getSub_cat_id();

                            if (!isConnectd(CreateQuiz_Activity.this)) {
//                                displayAlert();
                                Toast.makeText(CreateQuiz_Activity.this, R.string.check_internet_connection, Toast.LENGTH_SHORT).show();
                            } else {
//                                spinner_city.setText("");
                                doGetSubCategoryList(category_id);
                            }
                            // TODO: user specific action
                            dialog.dismiss();
                        }
                    }).create();
            alertDialog.show();

        } else if (v == spin_course) {

            LayoutInflater inflater = (LayoutInflater) CreateQuiz_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View titleView = inflater.inflate(R.layout.custom_title, null);

            ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText(R.string.select_course);
            AlertDialog alertDialog = new AlertDialog.Builder(CreateQuiz_Activity.this)
                    .setTitle(R.string.select_course)
                    .setAdapter(courseListAdapter, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {

                            spin_course.setText(courseList.get(position).getCourse_name());
                            spinner_selected_course_item = courseList.get(position).getCourse_id();
//
                            // TODO: user specific action
                            dialog.dismiss();
                        }
                    }).create();
            alertDialog.show();

        } else if (v == image_quiz) {
            selectImage();
        }
    }



    private void selectImage() {
        final String[] items = {getString(R.string.camera), getString(R.string.gallery)};
        new CircleDialog.Builder(CreateQuiz_Activity.this)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        params.animStyle = R.style.dialogWindowAnim;
                    }
                })
                .setTitle(getString(R.string.select_image))
                .setTitleColor(getResources().getColor(R.color.colorPrimary))
                .setItems(items, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int
                            position, long id) {

                        if (position == 0) {
                            String fileName = "Camera_Example.jpg";
                            ContentValues values = new ContentValues();
                            values.put(MediaStore.Images.Media.TITLE, fileName);
                            values.put(MediaStore.Images.Media.DESCRIPTION, "Image capture by camera");

                            picUri = getContentResolver().insert(
                                    MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);

                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            intent.putExtra(MediaStore.EXTRA_OUTPUT, picUri);
                            intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
                            startActivityForResult(intent, REQUEST_CAPTURE);
                        } else {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, REQUEST_GALLERY);
                        }
                    }
                })
                .setNegative(getString(R.string.cancel), null)
                .configNegative(new ConfigButton() {
                    @Override
                    public void onConfig(ButtonParams params) {
                        params.textColor = getResources().getColor(R.color.colorPrimaryDark);
                    }
                })
                .show();
    }

    private void doGetCategoryList() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.getData().enqueue(new Callback<CaregoryList>() {

            @Override
            public void onResponse(Call<CaregoryList> call, Response<CaregoryList> response) {
                if (progressDialog !=null){
                    progressDialog.dismiss();
                }
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        for (int i = 0; i < response.body().getCategoryInfo().size(); i++) {
                            CategoryModel categoryModel = new CategoryModel();
                            categoryModel.setCat_name(response.body().getCategoryInfo().get(i).getCategoryName());
                            categoryModel.setCat_id(response.body().getCategoryInfo().get(i).getCategoryId());
                            categoryList.add(categoryModel);
                        }
                        categoryAdapter = new CategoryAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, categoryList);
                        categoryAdapter.notifyDataSetChanged();
                    } else {
                    }
                } else {
                }
            }
            @Override
            public void onFailure(Call<CaregoryList> call, Throwable t) {
                progressDialog.dismiss();
            }
        });

    }

    private void doGetSubCategoryList(String category_id) {

        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.show();

        apiService.getSubCategories(category_id).enqueue(new Callback<SubCategories>() {

            @Override
            public void onResponse(Call<SubCategories> call, Response<SubCategories> response) {

                progressDialog.dismiss();
                if (response.isSuccessful()) {

                    if (response.body().getStatus().equalsIgnoreCase("Success")) {


                        for (int i = 0; i < response.body().getSubCategoryInfo().size(); i++) {
                            SubCategoryModel subCategoryModel = new SubCategoryModel();
                            subCategoryModel.setSub_cat_name(response.body().getSubCategoryInfo().get(i).getSubCatName());
                            subCategoryModel.setSub_cat_id(response.body().getSubCategoryInfo().get(i).getCatId());

                            subCategoryList.add(subCategoryModel);

                        }

                        System.out.println("subcatlist " + subCategoryList.size());
                        subcategoryAdapter = new SubCategoryAdapter(CreateQuiz_Activity.this, android.R.layout.simple_spinner_item, subCategoryList);
                        subcategoryAdapter.notifyDataSetChanged();

                    } else {

                    }
                }

            }

            @Override
            public void onFailure(Call<SubCategories> call, Throwable t) {
                progressDialog.dismiss();
                System.out.println("throwableDaat " + t.getMessage());

            }
        });
    }
    private void doGetCourseList() {

        progressDialog.setMessage(getResources().getString(R.string.please_wait));
        progressDialog.show();

        apiService.getCourseData().enqueue(new Callback<CourseList>() {
            @Override
            public void onResponse(Call<CourseList> call, Response<CourseList> response) {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        for (int i = 0; i < response.body().getCoursesInfo().size(); i++) {
                            CourseListModel courseModel = new CourseListModel();
                            courseModel.setCourse_name(response.body().getCoursesInfo().get(i).getCourseTitle());
                            courseModel.setCourse_id(response.body().getCoursesInfo().get(i).getCourseId());

                            courseList.add(courseModel);
                        }
                        System.out.println("CourseList: " + courseList.size());
                        courseListAdapter = new CourseListAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, courseList);
                        courseListAdapter.notifyDataSetChanged();
                    } else {

                    }
                } else {

                }
            }

            @Override
            public void onFailure(Call<CourseList> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {

            try {

                picUri = data.getData();
                filePath = getPath(this, picUri);

                photoFile = saveBitmapToFile(new File(filePath));
                path = photoFile.getPath();

                image_quiz.setImageURI(picUri);


            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK) {

            String imageId = convertImageUriToFile(picUri, this);

//                photoFile = new File(picUri.getPath());

            photoFile = new File(path);

            new LoadImagesFromSDCard().execute("" + imageId);


        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, R.string.camera_picture_was_not_taken, Toast.LENGTH_SHORT).show();
        } else { // Result was a failure
            Toast.makeText(this, R.string.picture_was_not_taken, Toast.LENGTH_SHORT).show();
        }


    }

    private String getPath(Context context, Uri contentUri) {
        String[] proj = {MediaStore.Images.Media.DATA};
        CursorLoader loader = new CursorLoader(getApplicationContext(), contentUri, proj, null, null, null);
        Cursor cursor = loader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String result = cursor.getString(column_index);
        cursor.close();
        return result;
    }

    public File saveBitmapToFile(File file) {
        try {
            // BitmapFactory options to downsize the image
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            o.inSampleSize = 6;
            // factor of downsizing the image

            FileInputStream inputStream = new FileInputStream(file);
            //Bitmap selectedBitmap = null;
            BitmapFactory.decodeStream(inputStream, null, o);
            inputStream.close();

            // The new size we want to scale to
            final int REQUIRED_SIZE = 25;

            // Find the correct scale value. It should be the power of 2.
            int scale = 1;
            while (o.outWidth / scale / 2 >= REQUIRED_SIZE &&
                    o.outHeight / scale / 2 >= REQUIRED_SIZE) {
                scale *= 2;
            }

            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            inputStream = new FileInputStream(file);

            Bitmap selectedBitmap = BitmapFactory.decodeStream(inputStream, null, o2);
            inputStream.close();

            // here i override the original image file
            file.createNewFile();
            FileOutputStream outputStream = new FileOutputStream(file);
            selectedBitmap.compress(Bitmap.CompressFormat.JPEG, 100, outputStream);
            return file;

        } catch (Exception e) {
            return null;
        }
    }

    public String convertImageUriToFile(Uri imageUri, Activity activity) {

        Cursor cursor = null;
        int imageID = 0;

        try {

            /*********** Which columns values want to get *******/
            String[] proj = {
                    MediaStore.Images.Media.DATA,
                    MediaStore.Images.Media._ID,
                    MediaStore.Images.Thumbnails._ID,
                    MediaStore.Images.ImageColumns.ORIENTATION
            };

            cursor = activity.managedQuery(

                    imageUri,         //  Get data for specific image URI
                    proj,             //  Which columns to return
                    null,             //  WHERE clause; which rows to return (all rows)
                    null,             //  WHERE clause selection arguments (none)
                    null              //  Order-by clause (ascending by name)

            );

            //  Get Query Data

            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID);
            int columnIndexThumb = cursor.getColumnIndexOrThrow(MediaStore.Images.Thumbnails._ID);
            int file_ColumnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);


            int size = cursor.getCount();

            /*******  If size is 0, there are no images on the SD Card. *****/

            if (size == 0) {
                Log.d("TAG", "No image");
            } else {

                int thumbID = 0;
                if (cursor.moveToFirst()) {

                    /**************** Captured image details ************/
                    /*****  Used to show image on view in LoadImagesFromSDCard class ******/
                    imageID = cursor.getInt(columnIndex);

                    thumbID = cursor.getInt(columnIndexThumb);

                    path = cursor.getString(file_ColumnIndex);
                    Log.d("path", path);

                    String CapturedImageDetails = " CapturedImageDetails : \n\n"
                            + " ImageID :" + imageID + "\n"
                            + " ThumbID :" + thumbID + "\n"
                            + " Path :" + path + "\n";

                }
            }
            if (imageUri != null) {
                cursor = managedQuery(imageUri, proj, null, null, null);
            }
            if ((cursor != null) && (cursor.moveToLast())) {
                ContentResolver cr = getContentResolver();
                int i = cr.delete(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, BaseColumns._ID + "/" + cursor.getString(3), null);
                Log.d("This", "Number of column deleted : " + i);
            }

        } finally {
            if (cursor != null) {
                cursor.close();
            }

            return "" + imageID;
        }
    }

    public class LoadImagesFromSDCard extends AsyncTask<String, Void, Void> {

        Bitmap mBitmap;
        private ProgressDialog Dialog = new ProgressDialog(CreateQuiz_Activity.this);

        protected void onPreExecute() {
            /****** NOTE: You can call UI Element here. *****/

            // Progress Dialog
            Dialog.setMessage(" Loading image from Sdcard..");
            Dialog.show();
        }


        // Call after onPreExecute method
        protected Void doInBackground(String... urls) {

            Bitmap bitmap = null;
            Bitmap newBitmap = null;
            Uri uri = null;


            try {


                uri = Uri.withAppendedPath(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "" + urls[0]);
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(uri));

                if (bitmap != null) {

                    newBitmap = Bitmap.createScaledBitmap(bitmap, 170, 170, true);

                    bitmap.recycle();

                    if (newBitmap != null) {

                        mBitmap = newBitmap;
                    }
                }
            } catch (IOException e) {

                cancel(true);
            }

            return null;
        }


        protected void onPostExecute(Void unused) {

            Dialog.dismiss();

            if (mBitmap != null) {
                image_quiz.setImageURI(picUri);
            } else {
                Toast.makeText(CreateQuiz_Activity.this, R.string.picture_was_not_taken, Toast.LENGTH_SHORT).show();
            }

        }

    }
}
