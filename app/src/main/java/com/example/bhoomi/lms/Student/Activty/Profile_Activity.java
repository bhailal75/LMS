package com.example.bhoomi.lms.Student.Activty;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
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
import android.os.StrictMode;
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
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.example.bhoomi.lms.APIModel.ChangePassword.ChangePassword;
import com.example.bhoomi.lms.APIModel.UpdateProfile.UpdateProfile;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Retrofit.Config;
import com.example.bhoomi.lms.Student.Constants.AbstractMethods;
import com.example.bhoomi.lms.Student.Constants.CircularImageView;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Constants.FileCache;
import com.example.bhoomi.lms.Student.Constants.MyMediumText;
import com.example.bhoomi.lms.Student.Model.Utils;
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
import java.io.InputStream;
import java.net.HttpURLConnection;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Profile_Activity extends AppCompatActivity implements View.OnClickListener, View.OnFocusChangeListener {

    private static final int REQUEST_CAPTURE = 1;
    private static final int REQUEST_GALLERY = 2;
    FileCache fileCache;
    private MyMediumText textView_updt, textView_chngPwd;
    private Toolbar toolbar_profile;
    private ImageView imageView_chngProfile, imageView_profilepic, imageview_closeDialog;
    private Uri picUri, imageUri;
    private String path;
    private String filePath;
    private File photoFile;
    private ImageView imageView_iser, imageView_email, imageView_pwd;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private APIService apiService;
    private RadioGroup radioGroup;


    private TextInputLayout textInputLayout_uname, textInputLayout_pwd, textInputLayout_email, textInputLayout_newPwd, textInputLayout_oldPwd, textInputLayout_address;
    private TextInputEditText textInputEditText_uname, textInputEditText_pwd, textInputEditText_email, textInputEditText_olPwd, textInputEditText_newPwd, textInputEditText_retypePwd, textInputEditText_address;

    private ProgressDialog progressDialog;
    private String user_id, uemail, u_img, u_name, u_pwd;
    private MultipartBody.Part body;
    private RequestBody fileBody;
    private RequestBody uname_textBody, pwd_textBody, uid_textBody, u_address, u_gender;

    private CircularImageView circularImageView;
    private String gender;
    private Bitmap bitmap;
    private byte[] inputData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        fileCache = new FileCache(this);
        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();

        user_id = sharedPreferences.getString(ConstantData.USER_ID, "");
        uemail = sharedPreferences.getString(ConstantData.USER_EMAIL, "");
        u_name = sharedPreferences.getString(ConstantData.USER_NAME, "");
        u_img = sharedPreferences.getString(ConstantData.USER_IMAGE, "");
        u_pwd = sharedPreferences.getString(ConstantData.USER_PHONE, "");
        progressDialog = new ProgressDialog(Profile_Activity.this);
        textView_updt = findViewById(R.id.textView_update);
        textView_chngPwd = findViewById(R.id.textView_chngpwd);
        imageView_chngProfile = findViewById(R.id.imgView_chngProfile);
        imageView_profilepic = findViewById(R.id.updt_profilePic);
        textInputEditText_uname = findViewById(R.id.user_textInputEdittext);
        textInputEditText_email = findViewById(R.id.email_textInputEdittext);
        textInputEditText_pwd = findViewById(R.id.pwd_textInputEdittext);
        textInputEditText_address = findViewById(R.id.add_textInputEdittext);

        textInputLayout_uname = findViewById(R.id.textInput_uname);
        textInputLayout_email = findViewById(R.id.textInput_email);
        textInputLayout_pwd = findViewById(R.id.textInput_pwd);
        textInputLayout_address = findViewById(R.id.textInput_add);

        imageView_iser = findViewById(R.id.uname_imgVw);
        imageView_email = findViewById(R.id.email_imgVw);
        imageView_pwd = findViewById(R.id.pwd_imgVw);

        radioGroup = findViewById(R.id.radio_grp_gender);
        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                int checkedRadioButtonId = radioGroup.getCheckedRadioButtonId();
                RadioButton radioBtn = (RadioButton) findViewById(checkedRadioButtonId);
                gender = String.valueOf(radioBtn.getText());
            }
        });
        circularImageView = findViewById(R.id.updt_profilePic);
        if (uemail.length() != 0) {
            textInputEditText_email.setText(uemail);
        }
        if (u_pwd.length() != 0) {
            textInputEditText_pwd.setText(u_pwd);
        }
        if (u_name.length() != 0) {
            textInputEditText_uname.setText(u_name);
        }
        if (u_img.length() != 0) {
            Glide.with(getApplicationContext())
                    .load(u_img)
                    .asBitmap()
                    .into(new SimpleTarget<Bitmap>(300, 300) {
                              @Override
                              public void onResourceReady(Bitmap resource, GlideAnimation glideAnimation) {
                                  circularImageView.setImageBitmap(resource);
                                  bitmap = resource;

                                  String result = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "", "");
                                  picUri = Uri.parse(result);
                                  saveImageInDB(picUri);
                                  filePath = getPath(Profile_Activity.this, picUri);
                                  photoFile = new File(filePath);
                              }
                          }

                    );
        }
        Configuration config = getApplicationContext().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL) {
            textInputLayout_uname.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputLayout_pwd.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputLayout_email.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_pwd.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_email.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_uname.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_address.setTextDirection(View.TEXT_DIRECTION_RTL);
            textInputEditText_pwd.setTextAlignment(View.TEXT_ALIGNMENT_VIEW_START);
        }


        Typeface typeface_medium = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
        textInputLayout_uname.setTypeface(typeface_medium);
        textInputLayout_pwd.setTypeface(typeface_medium);
        textInputLayout_email.setTypeface(typeface_medium);
        textInputEditText_pwd.setTypeface(typeface_medium);
        textInputEditText_email.setTypeface(typeface_medium);
        textInputEditText_uname.setTypeface(typeface_medium);
        textInputEditText_address.setTypeface(typeface_medium);

        textInputEditText_email.setKeyListener(null);


        textView_updt.setOnClickListener(this);
        textView_chngPwd.setOnClickListener(this);
        imageView_chngProfile.setOnClickListener(this);

        textInputEditText_pwd.setOnFocusChangeListener(this);
        textInputEditText_email.setOnFocusChangeListener(this);
        textInputEditText_uname.setOnFocusChangeListener(this);

        toolbar_profile = findViewById(R.id.toolbar_profile);

        toolbar_profile.setTitle("");
        toolbar_profile.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar_profile);
        toolbar_profile.setNavigationIcon(R.drawable.ic_back);
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar_profile.getNavigationIcon().setAutoMirrored(true);
        toolbar_profile.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

    private boolean saveImageInDB(Uri picUri) {
        try {
            InputStream iStream = getContentResolver().openInputStream(picUri);
            inputData = Utils.getBytes(iStream);
            return true;
        } catch (IOException ioe) {
            Log.e("TAG", "<saveImageInDB> Error : " + ioe.getLocalizedMessage());
            return false;
        }
    }


    @Override
    public void onClick(View v) {
        if (v == textView_updt) {
            String MobilePattern = "[0-9]{10}";


            if (textInputEditText_uname.getText().toString().length() == 0) {
                textInputEditText_uname.setError(getString(R.string.enter_username));
            } else if (textInputEditText_email.getText().toString().length() == 0) {
                textInputEditText_email.setError(getString(R.string.enter_email_address));
            } else if (!textInputEditText_pwd.getText().toString().matches(MobilePattern)) {
                textInputEditText_pwd.setError(getString(R.string.enter_valid_phone));
            } else if (picUri == null) {
                Toast.makeText(this, R.string.select_profile_image, Toast.LENGTH_SHORT).show();
            } else if (textInputEditText_address.getText().toString().length() == 0) {
                textInputEditText_address.setError(getString(R.string.enter_address));
            } else {

                progressDialog.setMessage(getString(R.string.please_wait));
                progressDialog.show();

                RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), photoFile);

                // MultipartBody.Part is used to send also the actual file name
                MultipartBody.Part body = MultipartBody.Part.createFormData(Config.PROFILE_IMAGE, photoFile.getName(), requestFile);

                fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), photoFile);

                uname_textBody = RequestBody.create(
                        MediaType.parse("text/plain"),
                        textInputEditText_uname.getText().toString());
                pwd_textBody = RequestBody.create(
                        MediaType.parse("text/plain"),
                        textInputEditText_pwd.getText().toString());
                uid_textBody = RequestBody.create(
                        MediaType.parse("text/plain"),
                        user_id);
                u_address = RequestBody.create(
                        MediaType.parse("text/plain"),
                        textInputEditText_address.getText().toString());
                u_gender = RequestBody.create(
                        MediaType.parse("text/plain"),
                        gender);

                apiService.uploadImage(uname_textBody, pwd_textBody, uid_textBody, body, u_gender, u_address
                ).enqueue(new Callback<UpdateProfile>() {

                    @Override
                    public void onResponse(Call<UpdateProfile> call, Response<UpdateProfile> response) {

                        progressDialog.dismiss();

                        if (response.isSuccessful()) {
                            if (response.body().getStatus().equalsIgnoreCase("Success")) {

                                String img = response.body().getProfileInfo().get(0).getProfileImage();
                                String uname = response.body().getProfileInfo().get(0).getUserName();
                                String uemail = response.body().getProfileInfo().get(0).getUserEmail();
                                String upwd = response.body().getProfileInfo().get(0).getUserPass();
                                String uid = response.body().getProfileInfo().get(0).getUserId();
                                String u_rolie_id = response.body().getProfileInfo().get(0).getUserRoleId();
                                String u_phone = response.body().getProfileInfo().get(0).getUserPhone();


                                editor.putString(ConstantData.USER_ID, uid).commit();
                                editor.putString(ConstantData.USER_NAME, uname).commit();
                                editor.putString(ConstantData.PASSWORD, upwd).commit();
                                editor.putString(ConstantData.U_ROLEID, u_rolie_id).commit();
                                editor.putString(ConstantData.USER_EMAIL, uemail).commit();
                                editor.putString(ConstantData.USER_IMAGE, img).commit();
                                editor.putString(ConstantData.USER_PHONE, u_phone).commit();

                                Toast.makeText(Profile_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();

                                startActivity(new Intent(getApplicationContext(), Dashboard_Activity.class));
                                finish();
                            } else {

                                Toast.makeText(Profile_Activity.this, String.valueOf(response.body().getMessage()), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                        }
                    }

                    @Override
                    public void onFailure(Call<UpdateProfile> call, Throwable t) {
                        progressDialog.dismiss();
                        Toast.makeText(Profile_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });


            }
        } else if (v == textView_chngPwd) {
            LayoutInflater inflater = getLayoutInflater();
            View dialogView = inflater.inflate(R.layout.layout_chngpwd_dlg, null);
            AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
            dialogBuilder.setView(dialogView);
            dialogBuilder.setCancelable(true);
            final AlertDialog alertbox = dialogBuilder.create();

            Button button = dialogView.findViewById(R.id.buttn_chngcard);

            textInputEditText_newPwd = dialogView.findViewById(R.id.newpwd_textInputEdittext);
            textInputEditText_olPwd = dialogView.findViewById(R.id.oldpwd_textInputEdittext);
            textInputEditText_retypePwd = dialogView.findViewById(R.id.retypepwd_textInputEdittext);
            imageview_closeDialog = dialogView.findViewById(R.id.close_dialog);

            textInputLayout_newPwd = findViewById(R.id.textInput_newpwd);
            textInputLayout_oldPwd = findViewById(R.id.textInput_oldpwd);

            Typeface typeface_rglr = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");
//            textInputLayout_newPwd.setTypeface(typeface_rglr);
//            textInputLayout_oldPwd.setTypeface(typeface_rglr);
            textInputEditText_olPwd.setTypeface(typeface_rglr);
            textInputEditText_newPwd.setTypeface(typeface_rglr);
            textInputEditText_retypePwd.setTypeface(typeface_rglr);
            button.setTypeface(typeface_rglr);
            imageview_closeDialog.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    alertbox.dismiss();
                }
            });

            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (textInputEditText_olPwd.getText().length() == 0)
                        textInputEditText_olPwd.setError(getString(R.string.old_pwd));
                    else if (textInputEditText_newPwd.getText().length() == 0)
                        textInputEditText_newPwd.setError(getString(R.string.new_pwd));
                    else if (textInputEditText_newPwd.getText().length() < 6)
                        textInputEditText_newPwd.setError(getString(R.string.password_length));
                    else if (textInputEditText_retypePwd.getText().length() == 0)
                        textInputEditText_retypePwd.setError(getString(R.string.re_type_new_passwod));
                    else if (!textInputEditText_newPwd.getText().toString().equals(textInputEditText_retypePwd.getText().toString())) {
                        textInputEditText_retypePwd.getText().clear();
                        textInputEditText_retypePwd.setError(getString(R.string.new_pwd_retype_pwd_not_match));
                    } else {

                        progressDialog.setMessage(getString(R.string.please_wait));
                        progressDialog.show();

                        System.out.println("rawdata " + textInputEditText_olPwd.getText().toString() + " " + textInputEditText_newPwd.getText().toString() + " " + user_id);
                        apiService.getChngPwdData(textInputEditText_olPwd.getText().toString(), textInputEditText_newPwd.getText().toString(), user_id).enqueue(new Callback<ChangePassword>() {
                            @Override
                            public void onResponse(Call<ChangePassword> call, Response<ChangePassword> response) {
                                progressDialog.dismiss();
                                if (response.isSuccessful()) {
                                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                                        alertbox.dismiss();
                                        Toast.makeText(Profile_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(Profile_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(Profile_Activity.this, response.body().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }

                            @Override
                            public void onFailure(Call<ChangePassword> call, Throwable t) {
                                progressDialog.dismiss();
                                Toast.makeText(Profile_Activity.this, t.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                }
            });
            alertbox.show();
        } else if (v == imageView_chngProfile) {
            selectImage();
        }
    }


    private void selectImage() {

        final String[] items = {getString(R.string.camera), getString(R.string.gallery)};
        new CircleDialog.Builder(Profile_Activity.this)
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
                .setNegative("Cancel", null)
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
                filePath = getPath(this, picUri);

                photoFile = saveBitmapToFile(new File(filePath));
                path = photoFile.getPath();

                circularImageView.setImageURI(picUri);


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

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (v == textInputEditText_uname) {
            imageView_email.setImageResource(R.mipmap.envelope_dark);
            imageView_pwd.setImageResource(R.drawable.phone);
            imageView_iser.setImageResource(R.mipmap.user_outline);
            AbstractMethods.hideKeyboard(this, v);


        } else if (v == textInputEditText_email) {
            imageView_email.setImageResource(R.mipmap.envelope);
            imageView_pwd.setImageResource(R.drawable.phone);
            imageView_iser.setImageResource(R.mipmap.user_dark);
            AbstractMethods.hideKeyboard(this, v);


        } else if (v == textInputEditText_pwd) {

            imageView_email.setImageResource(R.mipmap.envelope_dark);
            imageView_pwd.setImageResource(R.drawable.phone_c);
            imageView_iser.setImageResource(R.mipmap.user_dark);
            AbstractMethods.hideKeyboard(this, v);

        }
    }

    public Bitmap getBitmapFromURL(String src) {
        try {
            java.net.URL url = new java.net.URL(src);
            HttpURLConnection connection = (HttpURLConnection) url
                    .openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        cursor.moveToFirst();
        int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
        return cursor.getString(idx);
    }

    public class LoadImagesFromSDCard extends AsyncTask<String, Void, Void> {

        Bitmap mBitmap;
        private ProgressDialog Dialog = new ProgressDialog(Profile_Activity.this);

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
                circularImageView.setImageURI(picUri);
            } else {
                Toast.makeText(Profile_Activity.this, R.string.picture_was_not_taken, Toast.LENGTH_SHORT).show();
            }

        }

    }
}
