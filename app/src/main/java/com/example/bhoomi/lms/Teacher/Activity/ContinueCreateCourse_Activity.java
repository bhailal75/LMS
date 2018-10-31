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
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
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

import com.android.volley.RequestQueue;
import com.example.bhoomi.lms.APIModel.AddMarkAsComplete.AddMarkAsCompleteResp;
import com.example.bhoomi.lms.APIModel.CreateCourse.CreateCourse;
import com.example.bhoomi.lms.APIModel.Tier.TierData;
import com.example.bhoomi.lms.APIModel.Tier.TierResp;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Retrofit.Config;
import com.example.bhoomi.lms.Student.Activty.Dashboard_Activity;
import com.example.bhoomi.lms.Student.Activty.Profile_Activity;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Teacher.Adapter.LevelAdapter;
import com.example.bhoomi.lms.Teacher.Adapter.PriceAdapter;
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

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ContinueCreateCourse_Activity extends AppCompatActivity implements View.OnClickListener {

    private static final int REQUEST_CAPTURE = 1;
    private static final int REQUEST_GALLERY = 2;
    ImageView imgview_addImage;
    private Button button_create;
    private String cat_id, sub_cat_id, course_title, course_intro, course_desc, course_req;
    private TextInputLayout textInputLayout_line1, textInputLayout_line2, textInputLayout_price;
    private TextInputEditText textInputEditText_line1, textInputEditText_line2, textInputEditText_price, textInputEditText_discount, spin_level, spin_price;
    private Uri picuri;
    private Uri picUri;
    private String filePath;
    private File photoFile;
    private String path;

    private APIService apiService;
    private ProgressDialog progressDialog;
    private RequestBody fileBody;
    private RequestBody t_cat_id, t_sub_cat_id, t_course_title, t_course_desc, t_course_intro, t_course_req, t_i_learn, t_price, t_user_id, t_discount, t_level,t_tier;

    private Toolbar toolbar_create_course;
    private LevelAdapter levelAdapter;
    private PriceAdapter priceAdapter;
    private String[] strLevelList = new String[3];
    private String[] pricearray;

    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<TierData> tierArrayList;
    private ArrayList<String> tiernameList;
    private String tier_price;
    private String tier_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_continue_create_course_);

        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialog = new ProgressDialog(ContinueCreateCourse_Activity.this);

        Intent intent = getIntent();

        cat_id = intent.getStringExtra("cat_id");
        sub_cat_id = intent.getStringExtra("sub_cat_id");
        course_title = intent.getStringExtra("course_title");
        course_intro = intent.getStringExtra("course_intro");
        course_desc = intent.getStringExtra("course_desc");
        course_req = intent.getStringExtra("course_requi");
//        Log.i("TAG", "onCreate: "+sharedPreferences.getString(ConstantData.USER_ID,""));

        toolbar_create_course = findViewById(R.id.toolbar_create_course);
        toolbar_create_course.setTitle("");
        toolbar_create_course.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_create_course);

        toolbar_create_course.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_create_course.getNavigationIcon().setAutoMirrored(true);
        toolbar_create_course.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        button_create = findViewById(R.id.buttn_create);
        button_create.setOnClickListener(this);
        textInputLayout_line1 = findViewById(R.id.textInput_line1);
        textInputLayout_line2 = findViewById(R.id.textInput_line2);
        textInputLayout_price = findViewById(R.id.textInput_price);
        textInputEditText_line1 = findViewById(R.id.line1_textInputEditText);
        textInputEditText_line2 = findViewById(R.id.line2_textInputEditText);
//        textInputEditText_price = findViewById(R.id.price_textInputEditText);
        spin_price = findViewById(R.id.price_textInputEditText);
        textInputEditText_discount = findViewById(R.id.discount_textInputEditText);

        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            textInputEditText_line1.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_line2.setTextDirection(View.TEXT_DIRECTION_RTL);
//            textInputEditText_price.setTextDirection(View.TEXT_DIRECTION_RTL);
            spin_price.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_discount.setTextDirection(View.TEXT_DIRECTION_RTL);
        }

        strLevelList[0] = getString(R.string.introductory);
        strLevelList[1] = getString(R.string.intermediate);
        strLevelList[2] = getString(R.string.advanced);

        tierArrayList = new ArrayList<>();
        tiernameList = new ArrayList<>();
        spin_level = findViewById(R.id.spinner_level);
        spin_level.setOnClickListener(this);
        spin_price.setOnClickListener(this);

        Typeface typeface_medium = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
        textInputLayout_line1.setTypeface(typeface_medium);
        textInputLayout_line2.setTypeface(typeface_medium);
        textInputLayout_price.setTypeface(typeface_medium);
        textInputEditText_line1.setTypeface(typeface_medium);
        textInputEditText_line2.setTypeface(typeface_medium);
//        textInputEditText_price.setTypeface(typeface_medium);
        spin_price.setTypeface(typeface_medium);
        textInputEditText_discount.setTypeface(typeface_medium);
        spin_level.setTypeface(typeface_medium);

        imgview_addImage = findViewById(R.id.imgview_addImage);
        imgview_addImage.setOnClickListener(this);


        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        apiService.getTierData().enqueue(new Callback<TierResp>() {
            @Override
            public void onResponse(Call<TierResp> call, Response<TierResp> response) {
                if (response.body().getStatus().equalsIgnoreCase("Success")) {
                    progressDialog.dismiss();
//                        lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).setIsComplete(response.body().getIsComplete());
                    tierArrayList.addAll(response.body().getTierInfo());
                    for (int i = 0; i < response.body().getTierInfo().size(); i++) {
                        tiernameList.add(response.body().getTierInfo().get(i).getTierName());
                    }
                    pricearray = new String[tiernameList.size()];
                    pricearray = tiernameList.toArray(pricearray);


                }
            }

            @Override
            public void onFailure(Call<TierResp> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
                if (progressDialog != null) {
                    progressDialog.dismiss();

                }
            }
        });


    }

    @Override
    public void onClick(View v) {
        if (v == button_create) {
//            startActivity(new Intent(getApplicationContext(),Curriculum_Activity.class));
            if (textInputEditText_line1.getText().length() == 0) {
                textInputEditText_line1.setError(getString(R.string.line1));
            } else if (textInputEditText_line2.getText().toString().length() == 0) {
                textInputEditText_line2.setError(getString(R.string.line2));
            }
//            else if (textInputEditText_price.getText().toString().length()==0) {
//                textInputEditText_price.setError(getString(R.string.enter_price));
            else if (spin_price.getText().toString().length() == 0) {
                spin_price.setError(getString(R.string.select_tier));
            } else if (textInputEditText_discount.getText().toString().length() == 0) {
                textInputEditText_discount.setError(getString(R.string.enter_discount));
            } else if (picUri == null) {
                Toast.makeText(this, R.string.select_image, Toast.LENGTH_SHORT).show();
            } else if (spin_level.length() == 0) {
                spin_level.setError(getString(R.string.select_level));
            } else {
                createCourse();
            }
        } else if (v == imgview_addImage) {
            selectImage();
        } else if (v == spin_level) {
            LayoutInflater inflater = (LayoutInflater) ContinueCreateCourse_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View titleView = inflater.inflate(R.layout.custom_title, null);
            ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText(R.string.select_level);
            levelAdapter = new LevelAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, strLevelList);
            AlertDialog alertDialog = new AlertDialog.Builder(ContinueCreateCourse_Activity.this)
                    .setTitle(R.string.select_level)
                    .setAdapter(levelAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            spin_level.setText(strLevelList[position]);
                        }
                    }).create();
            alertDialog.show();
        } else if (v == spin_price) {
            LayoutInflater inflater = (LayoutInflater) ContinueCreateCourse_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View titleView = inflater.inflate(R.layout.custom_title, null);
            ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText(R.string.select_tier);
            priceAdapter = new PriceAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, pricearray);
            AlertDialog alertDialog = new AlertDialog.Builder(ContinueCreateCourse_Activity.this)
                    .setTitle(R.string.select_tier)
                    .setAdapter(priceAdapter, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int position) {
                            spin_price.setText(tierArrayList.get(position).getTierName());
                            tier_price = tierArrayList.get(position).getPrice();
                            tier_id = tierArrayList.get(position).getTierId();
                        }
                    }).create();
            alertDialog.show();
        }
   }

    private void selectImage() {
        final String[] items = {getString(R.string.camera), getString(R.string.gallery)};
        new CircleDialog.Builder(ContinueCreateCourse_Activity.this)
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

    private void createCourse() {

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), photoFile);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData(Config.FEATURE_IMAGE, photoFile.getName(), requestFile);

        fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), photoFile);

        t_cat_id = RequestBody.create(
                MediaType.parse("text/plain"),
                cat_id);
        t_sub_cat_id = RequestBody.create(
                MediaType.parse("text/plain"),
                sub_cat_id);
        t_course_title = RequestBody.create(
                MediaType.parse("text/plain"),
                course_title);
        t_course_intro = RequestBody.create(
                MediaType.parse("text/plain"),
                course_intro);
        t_course_desc = RequestBody.create(
                MediaType.parse("text/plain"),
                course_desc);
        t_course_req = RequestBody.create(
                MediaType.parse("text/plain"),
                course_req);
        t_i_learn = RequestBody.create(
                MediaType.parse("text/plain"),
                textInputEditText_line1.getText().toString() + " " + textInputEditText_line2.getText().toString());
       //                textInputEditText_price.getText().toString());
        t_price = RequestBody.create(
                MediaType.parse("text/parse"),
                tier_price);
        t_user_id = RequestBody.create(
                MediaType.parse("text/plain"),
                sharedPreferences.getString(ConstantData.USER_ID, "").toString());
        t_discount = RequestBody.create(
                MediaType.parse("text/plain"),
                textInputEditText_discount.getText().toString());
        t_level = RequestBody.create(
                MediaType.parse("text/parse"),
                spin_level.getText().toString());

        t_tier = RequestBody.create(
                MediaType.parse("text/plain"),
                tier_id);

        apiService.createCourse(t_cat_id, t_sub_cat_id, t_course_title, t_course_intro, t_course_desc, t_course_req, t_i_learn, body, t_price, t_user_id, t_discount, t_level,t_tier).enqueue(new Callback<CreateCourse>() {

            @Override
            public void onResponse(Call<CreateCourse> call, Response<CreateCourse> response) {

                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        System.out.println("courseId " + response.body().getCoursesInfo().get(0).getCourseId());
                        Intent intent = new Intent(getApplicationContext(), Curriculum_Activity.class);
                        intent.putExtra("course_id", response.body().getCoursesInfo().get(0).getCourseId());
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(ContinueCreateCourse_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<CreateCourse> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
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

                imgview_addImage.setImageURI(picUri);


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
        private ProgressDialog Dialog = new ProgressDialog(ContinueCreateCourse_Activity.this);

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
                imgview_addImage.setImageURI(picUri);
            } else {
                Toast.makeText(ContinueCreateCourse_Activity.this, R.string.picture_was_not_taken, Toast.LENGTH_SHORT).show();
            }

        }

    }


}
