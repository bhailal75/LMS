package com.example.bhoomi.lms.Teacher.Activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.media.ThumbnailUtils;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.BaseColumns;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.ActivityCompat;
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

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.AddContent.AddContent;
import com.example.bhoomi.lms.APIModel.CategoryList.CaregoryList;
import com.example.bhoomi.lms.APIModel.Login.UserInfo;
import com.example.bhoomi.lms.APIModel.SectionList.SectionList;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Retrofit.Config;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Teacher.Adapter.CategoryAdapter;
import com.example.bhoomi.lms.Teacher.Adapter.CurriculumContextAdapter;
import com.example.bhoomi.lms.Teacher.Adapter.SubCategoryAdapter;
import com.example.bhoomi.lms.Teacher.Model.CategoryModel;
import com.example.bhoomi.lms.Teacher.Model.CurriculumContext;
import com.example.bhoomi.lms.Teacher.Model.PermissionHandler;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;

import java.io.ByteArrayOutputStream;
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

public class CreateContent_Activity extends AppCompatActivity implements View.OnClickListener {

    private Toolbar toolbar_addcontent;

    private TextInputEditText spin_curriculum_context;

    private CurriculumContextAdapter curriculumContextAdapterAdapter;
    ArrayList<CurriculumContext> categoryList;

    private String course_id;
    private String spinner_selected_item;
    private ProgressDialog progressDialog;

    private APIService apiService;

    private TextInputEditText textInputEditText_title;
    private TextInputLayout textInputLayout_title;
    private Button button_save;
    private Uri picUri;
    private ImageView imgview_addImage;

    private static final int REQUEST_CAPTURE = 1;
    private static final int REQUEST_GALLERY = 2;
    private String path;
    private String filePath;
    private File photoFile;
    private RequestBody fileBody;
    private String type;
    private RequestBody t_course_id, t_section_id, t_title, t_content_type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_content_);

        Intent intent = getIntent();
        course_id = intent.getStringExtra("course_id");

        apiService = APIUtils.getAPIService();
        progressDialog = new ProgressDialog(CreateContent_Activity.this);

        categoryList = new ArrayList<>();

        toolbar_addcontent = (android.support.v7.widget.Toolbar) findViewById(R.id.toolbar_addcontent);
        toolbar_addcontent.setTitle("");
        toolbar_addcontent.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_addcontent);

        toolbar_addcontent.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_addcontent.getNavigationIcon().setAutoMirrored(true);

        toolbar_addcontent.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        spin_curriculum_context = (TextInputEditText) findViewById(R.id.spinner_curriculum_section);
        spin_curriculum_context.setOnClickListener(this);

        textInputEditText_title = findViewById(R.id.title_textInputEditText);
        textInputLayout_title = findViewById(R.id.textInput_coursetitle);

        imgview_addImage = findViewById(R.id.imgview_addImage);
        imgview_addImage.setOnClickListener(this);

        button_save = findViewById(R.id.buttn_create);
        button_save.setOnClickListener(this);

        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            textInputEditText_title.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputLayout_title.setTextDirection(View.TEXT_DIRECTION_RTL);
            spin_curriculum_context.setTextDirection(View.TEXT_DIRECTION_RTL);
        }


        Typeface typeface_medium= Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
        textInputEditText_title.setTypeface(typeface_medium);
        textInputLayout_title.setTypeface(typeface_medium);
        spin_curriculum_context.setTypeface(typeface_medium);

        button_save.setTypeface(typeface_medium);

        getSectionData(course_id);
    }

    private void getSectionData(String course_id) {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();

        apiService.sectionList(course_id).enqueue(new Callback<SectionList>() {

            @Override
            public void onResponse(Call<SectionList> call, Response<SectionList> response) {

                progressDialog.dismiss();

                if (response.isSuccessful())
                {

                    if (response.body().getStatus().equalsIgnoreCase("Success"))
                    {

                        for (int i = 0; i < response.body().getCourseSectionsInfo().size(); i++)
                        {
                            CurriculumContext categoryModel = new CurriculumContext();
                            categoryModel.setSection_name(response.body().getCourseSectionsInfo().get(i).getSectionName());
                            categoryModel.setSection_id(response.body().getCourseSectionsInfo().get(i).getSectionId());

                            categoryList.add(categoryModel);
                        }

                        curriculumContextAdapterAdapter = new CurriculumContextAdapter(CreateContent_Activity.this, android.R.layout.simple_spinner_item, categoryList);
                        curriculumContextAdapterAdapter.notifyDataSetChanged();


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
            public void onFailure(Call<SectionList> call, Throwable t) {
                progressDialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == spin_curriculum_context)
        {
            LayoutInflater inflater = (LayoutInflater) CreateContent_Activity.this.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View titleView = inflater.inflate(R.layout.custom_title, null);

            ((MyMediumText) titleView.findViewById(R.id.custom_title)).setText(R.string.select_curriculum_content);
            AlertDialog alertDialog = new AlertDialog.Builder(CreateContent_Activity.this)
                    .setCustomTitle(titleView)
                    .setAdapter(curriculumContextAdapterAdapter, new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int position) {

                            spin_curriculum_context.setText(categoryList.get(position).getSection_name());
                            spinner_selected_item = categoryList.get(position).getSection_id();

                            dialog.dismiss();
                        }

                    }).create();
            alertDialog.show();
        }

        else if (v == button_save)
        {
            if (spin_curriculum_context != null && spin_curriculum_context.length()==0)
            {
                Toast.makeText(this, R.string.select_curriculum_section, Toast.LENGTH_SHORT).show();
            }
            else if (textInputEditText_title.getText().toString().length()==0)
            {
                textInputEditText_title.setError(getString(R.string.Enter_title));
            }
            else if (picUri == null)
            {
                Toast.makeText(this, R.string.select_media_file, Toast.LENGTH_SHORT).show();
            }
            else
            {
                doSaveCourse();
            }
        }
        else if (v == imgview_addImage)
        {
            selectImage();

        }
    }

    private void selectImage() {
        final String[] items = {getString(R.string.select_image), getString(R.string.select_video)};
            new CircleDialog.Builder(CreateContent_Activity.this)
                .configDialog(new ConfigDialog() {
                    @Override
                    public void onConfig(DialogParams params) {
                        params.animStyle = R.style.dialogWindowAnim;
                    }
                })
                .setTitle(getString(R.string.select_type))
                .setTitleColor(getResources().getColor(R.color.colorPrimary))
                .setItems(items, new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int
                            position, long id) {

                        if (position == 0) {
                            Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                            startActivityForResult(galleryIntent, REQUEST_CAPTURE);
                        } else {

                            if (Build.VERSION.SDK_INT >= 23) {
                                String[] PERMISSIONS = {Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE};
                                if (!hasPermissions(CreateContent_Activity.this, PERMISSIONS)) {
                                    ActivityCompat.requestPermissions(CreateContent_Activity.this, PERMISSIONS, 201);
                                } else {
                                    if (PermissionHandler.CheckPermission("Storage", Manifest.permission.WRITE_EXTERNAL_STORAGE, CreateContent_Activity.this, REQUEST_GALLERY)) {
                                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                                        startActivityForResult(intent, 201);
                                    }
                                }


                            } else {
                                if (PermissionHandler.CheckPermission("Storage", Manifest.permission.WRITE_EXTERNAL_STORAGE, CreateContent_Activity.this, REQUEST_GALLERY)) {

                                    Intent intent = new Intent();
                                    intent.setType("video/*");
                                    intent.setAction(Intent.ACTION_GET_CONTENT);
                                    startActivityForResult(Intent.createChooser(intent, "Select a Video "), REQUEST_GALLERY);
                                }
                            }

//
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_GALLERY && resultCode == RESULT_OK) {

            try {

                picUri = data.getData();
//                filePath = getPath(this, picUri);
//
//                photoFile = saveBitmapToFile(new File(filePath));
//                path = photoFile.getPath();

                Uri selectedImageUri = data.getData();

                path = getPath(selectedImageUri);

                photoFile = new File(path);


                long length = photoFile.length();

                length = length / 1024000;

                if (length < 20) {
                    /*isVideo = true;
                    relativeImage.setVisibility(View.VISIBLE);*/
                    Glide.with(CreateContent_Activity.this).load(Uri.fromFile(new File(path))).into(imgview_addImage);

                    System.out.println("imgUri " + Uri.fromFile(new File(path)));

                    Bitmap bitmap = ThumbnailUtils.createVideoThumbnail(Uri.fromFile(new File(path)).getPath(), MediaStore.Video.Thumbnails.FULL_SCREEN_KIND);

                    Uri tempUri = getImageUri(getApplicationContext(), bitmap);
                    System.out.println("bitmapImg " + tempUri);

                    path = getPath(tempUri);
                    // CALL THIS METHOD TO GET THE ACTUAL PATH
                    photoFile = new File(path);
                    imgview_addImage.setImageURI(picUri);
                    type = "Video";

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

        } else if (requestCode == REQUEST_CAPTURE && resultCode == RESULT_OK) {

            picUri = data.getData();
                filePath = getPath(this, picUri);

                photoFile = saveBitmapToFile(new File(filePath));
                path = photoFile.getPath();

            imgview_addImage.setImageURI(picUri);
            type = "Image";

//            String imageId = convertImageUriToFile(picUri, this);
//
////                photoFile = new File(picUri.getPath());
//
//            photoFile = new File(path);


//            new LoadImagesFromSDCard().execute("" + imageId);


        } else if (resultCode == RESULT_CANCELED) {
            Toast.makeText(this, R.string.camera_picture_was_not_taken, Toast.LENGTH_SHORT).show();
        } else { // Result was a failure
            Toast.makeText(this, R.string.picture_was_not_taken, Toast.LENGTH_SHORT).show();
        }


    }

    private Uri getImageUri(Context applicationContext, Bitmap bitmap) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(applicationContext.getContentResolver(), bitmap, "Title", null);
        return Uri.parse(path);
    }

    private String getPath(Uri selectedImageUri) {

        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
        if (cursor != null) {

            // HERE YOU WILL GET A NULLPOINTER IF CURSOR IS NULL
            // THIS CAN BE, IF YOU USED OI FILE MANAGER FOR PICKING THE MEDIA

            int column_index = cursor
                    .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            return cursor.getString(column_index);
        } else
            return null;

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
        private ProgressDialog Dialog = new ProgressDialog(CreateContent_Activity.this);

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
                Toast.makeText(CreateContent_Activity.this, R.string.picture_was_not_taken, Toast.LENGTH_SHORT).show();
            }

        }

    }

    private void doSaveCourse() {

        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), photoFile);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body = MultipartBody.Part.createFormData(Config.MEDIA, photoFile.getName(), requestFile);

        fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), photoFile);

        t_course_id = RequestBody.create(
                MediaType.parse("text/plain"),
                course_id);
        t_section_id = RequestBody.create(
                MediaType.parse("text/plain"),
                spinner_selected_item);
        t_title = RequestBody.create(
                MediaType.parse("text/plain"),
                textInputEditText_title.getText().toString());
        t_content_type = RequestBody.create(
                MediaType.parse("text/plain"),
                type);

        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();


        apiService.addContent(t_course_id,t_section_id,t_content_type,t_title,body).enqueue(new Callback<AddContent>() {
            @Override
            public void onResponse(Call<AddContent> call, Response<AddContent> response) {


                progressDialog.dismiss();

                if (response.isSuccessful())
                {

                    if (response.body().getStatus().equalsIgnoreCase("Success"))
                    {
                        Toast.makeText(CreateContent_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                        finish();

                    }else
                    {
                        Toast.makeText(CreateContent_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Toast.makeText(CreateContent_Activity.this, response.message(), Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onFailure(Call<AddContent> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(CreateContent_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

    private boolean hasPermissions(CreateContent_Activity onItemClickListener, String[] permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && onItemClickListener != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(onItemClickListener, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {

            case REQUEST_GALLERY:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if (PermissionHandler.CheckPermission("Storage", android.Manifest.permission.READ_EXTERNAL_STORAGE, CreateContent_Activity.this, 201)) {
//                        new UploadVideo().execute();
                    }
                } else {
                    if (PermissionHandler.CheckPermission("Storage", android.Manifest.permission.READ_EXTERNAL_STORAGE, CreateContent_Activity.this, 201)) {
                        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Video.Media.EXTERNAL_CONTENT_URI);
                        startActivityForResult(intent, 201);
                    }
                }
                break;
            default:
        }
    }

}
