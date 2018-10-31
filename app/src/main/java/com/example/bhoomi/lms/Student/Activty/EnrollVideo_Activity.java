package com.example.bhoomi.lms.Student.Activty;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.bhoomi.lms.APIModel.AddMarkAsComplete.AddMarkAsCompleteResp;
import com.example.bhoomi.lms.APIModel.EnrollLecture.LectureResp;
import com.example.bhoomi.lms.APIModel.EnrollLecture.LectureVideosInfo;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Student.Constants.ConstantData;
import com.example.bhoomi.lms.Student.Fragment.LecturesFragment;
import com.example.bhoomi.lms.Student.Fragment.MoreFragment;
import com.example.bhoomi.lms.Student.Fragment.WebViewFragment;
import com.example.bhoomi.lms.Teacher.Activity.CreateQuiz_Activity;
import com.mylhyl.circledialog.CircleDialog;
import com.mylhyl.circledialog.callback.ConfigButton;
import com.mylhyl.circledialog.callback.ConfigDialog;
import com.mylhyl.circledialog.params.ButtonParams;
import com.mylhyl.circledialog.params.DialogParams;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.YouTubePlayerView;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.listeners.YouTubePlayerInitListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.player.playerUtils.FullScreenHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tcking.github.com.giraffeplayer2.GiraffePlayer;
import tcking.github.com.giraffeplayer2.VideoInfo;
import tcking.github.com.giraffeplayer2.VideoView;

public class EnrollVideo_Activity extends AppCompatActivity implements LecturesFragment.PlayVideoClickListner, View.OnClickListener {
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private Button button_continue;
    private String course_id;
    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private ArrayList<LectureVideosInfo> lecturesModelArrayList;
    private ProgressDialog progressDialog;
    private TextView createdBy, courseName, sectionName, sectionTitle;
    private FrameLayout frameLayout;
    private VideoView videoView;
    private ImageView tool_imageView;
    private RelativeLayout content_relative;
    private int groupPos, childPos;
    private YouTubePlayerView youtubePlayerView;
    private ImageView imageViewCompleteOption;
    private String tempmark;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enroll_video_);
        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        Intent intent = getIntent();
        course_id = intent.getStringExtra("course_id");
        lecturesModelArrayList = new ArrayList<>();
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        content_relative = findViewById(R.id.content_relative);
        tool_imageView = findViewById(R.id.img_back);
        frameLayout = findViewById(R.id.frame_layout);
        videoView = findViewById(R.id.video_view);
        youtubePlayerView = findViewById(R.id.youtube_player_view);
        getLifecycle().addObserver(youtubePlayerView);
        imageViewCompleteOption = findViewById(R.id.image_option_complete_course);
        progressDialog = new ProgressDialog(EnrollVideo_Activity.this);
        createdBy = findViewById(R.id.text_createdBy);
        courseName = findViewById(R.id.courseName);
        sectionName = findViewById(R.id.sectionName);
        sectionTitle = findViewById(R.id.sectionTitle);
        getLectureData();
//        setupViewPager(viewPager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        Typeface typeface_rglr = Typeface.createFromAsset(getAssets(), "fonts/ubuntu_m.ttf");

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TextView tv = (TextView) LayoutInflater.from(this).inflate(R.layout.layout_tabtitle, null);
            tv.setTypeface(typeface_rglr);
            tabLayout.getTabAt(i).setCustomView(tv);
        }
        button_continue = findViewById(R.id.buttn_continuecard);
        button_continue.setTypeface(typeface_rglr);
        button_continue.setOnClickListener(this);
        imageViewCompleteOption.setOnClickListener(this);
    }

    private void getLectureData() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        apiService.getMyLecture(sharedPreferences.getString(ConstantData.USER_ID, ""), course_id).enqueue(new Callback<LectureResp>() {
            @Override
            public void onResponse(Call<LectureResp> call, Response<LectureResp> response) {
                if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("Success")) {
                        if (response.body().getCourseVideosInfo() != null && response.body().getCourseVideosInfo().size() > 0) {
                            progressDialog.dismiss();
                            lecturesModelArrayList.addAll(response.body().getCourseVideosInfo());
                            setupViewPager(viewPager);
                            Glide.with(EnrollVideo_Activity.this)
                                    .load(response.body().getCourseImage())
                                    .placeholder(R.drawable.profile_icon)
                                    .into(tool_imageView);
                            createdBy.setText(response.body().getUserName());
                            courseName.setText(response.body().getCourseTitle());
                        } else {
                            progressDialog.dismiss();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<LectureResp> call, Throwable t) {
                Log.i("TAG", "onFailure: " + t.getMessage());
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }
            }
        });
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new LecturesFragment(lecturesModelArrayList, course_id), "Lectures");
        adapter.addFragment(new MoreFragment(), "More");
        viewPager.setAdapter(adapter);
    }

    @Override
    public void onFileClick(int groupPos, int childPos) {
        this.groupPos = groupPos;
        this.childPos = childPos;

        sectionName.setText(lecturesModelArrayList.get(groupPos).getSectionName());
        sectionTitle.setText(lecturesModelArrayList.get(groupPos).getSectionTitle());
        if (lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).getContentType().equals("video")) {
            frameLayout.setVisibility(View.INVISIBLE);
            youtubePlayerView.setVisibility(View.INVISIBLE);
            videoView.setVisibility(View.VISIBLE);
            imageViewCompleteOption.setVisibility(View.VISIBLE);
            videoView.getVideoInfo().setAspectRatio(VideoInfo.AR_MATCH_PARENT)
                    .setTitle(lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).getVideoTitle())
                    .setShowTopBar(false)
                    .setFullScreenAnimation(true)
                    .setPortraitWhenFullScreen(false);
            videoView.setVideoPath(lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).getVideoLink()).getPlayer().start();
//            videoView.getPlayer().setDisplayModel(GiraffePlayer.DISPLAY_FLOAT);
        } else if (lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).getContentType().equals("youtube")) {
            frameLayout.setVisibility(View.INVISIBLE);
            youtubePlayerView.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.INVISIBLE);
            imageViewCompleteOption.setVisibility(View.VISIBLE);
            youtubePlayerView.getPlayerUIController().showFullscreenButton(false);
            youtubePlayerView.initialize(new YouTubePlayerInitListener() {
                @Override
                public void onInitSuccess(@NonNull final YouTubePlayer initializedYouTubePlayer) {
                    initializedYouTubePlayer.addListener(new AbstractYouTubePlayerListener() {
                        @Override
                        public void onReady() {
                            String youtubeLink = lecturesModelArrayList.get(EnrollVideo_Activity.this.groupPos).getVideoInfo().get(EnrollVideo_Activity.this.childPos).getYoutubeLink();
                            String[] temp = youtubeLink.split("/");  // https://youtu.be/TyAnhZinv6s
                            String videoId = temp[temp.length - 1]; //TyAnhZinv6s
                            initializedYouTubePlayer.loadVideo(videoId, 0);
                        }
                    });
                }
            }, true);
        } else if (lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).getContentType().equals("quiz")) {
            frameLayout.setVisibility(View.VISIBLE);
            videoView.setVisibility(View.INVISIBLE);
            youtubePlayerView.setVisibility(View.INVISIBLE);
            imageViewCompleteOption.setVisibility(View.VISIBLE);
            button_continue.setText(getString(R.string.start_quiz));
        } else if (lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).getContentType().equals("file")) {
            frameLayout.setVisibility(View.VISIBLE);
            youtubePlayerView.setVisibility(View.INVISIBLE);
            videoView.setVisibility(View.INVISIBLE);
            imageViewCompleteOption.setVisibility(View.VISIBLE);
            button_continue.setText(getString(R.string.open_document));
        }
    }

    @Override
    public void onClick(View v) {
        if (v == button_continue) {
            if (button_continue.getText().toString().equals(getString(R.string.open_document))) {
                WebViewFragment webViewFragment = new WebViewFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("documentType", "file");
                bundle1.putString("keyurl", lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).getVideoLink());
                webViewFragment.setArguments(bundle1);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.content_relative, webViewFragment, webViewFragment.getClass().getSimpleName());
                ft.addToBackStack(webViewFragment.getClass().getSimpleName());
                ft.commit();
            } else if (button_continue.getText().toString().equals(getString(R.string.start_quiz))) {
                WebViewFragment webViewFragment = new WebViewFragment();
                Bundle bundle1 = new Bundle();
                bundle1.putString("documentType", "quiz");
                bundle1.putString("keyurl", lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).getQuizWebView());
                webViewFragment.setArguments(bundle1);
                FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
                ft.add(R.id.content_relative, webViewFragment, webViewFragment.getClass().getSimpleName());
                ft.addToBackStack(webViewFragment.getClass().getSimpleName());
                ft.commit();
            } else {
                Toast.makeText(this, "Select Lecture Document", Toast.LENGTH_SHORT).show();
            }
        } else if (v == imageViewCompleteOption) {
            if (lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).getIsComplete().equals("0")) {
                final String[] items = {getString(R.string.mark_as_complete)};
                new CircleDialog.Builder(EnrollVideo_Activity.this)
                        .configDialog(new ConfigDialog() {
                            @Override
                            public void onConfig(DialogParams params) {
                                params.animStyle = R.style.dialogWindowAnim;
                            }
                        })
                        .setItems(items, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int
                                    position, long id) {
                                if (position == 0) {
                                    progressDialog.setMessage(getString(R.string.please_wait));
                                    progressDialog.show();
                                    apiService.getAddMarkAsComplete(sharedPreferences.getString(ConstantData.USER_ID, ""),
                                            lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).getVideoId(),
                                            lecturesModelArrayList.get(groupPos).getSectionId(),
                                            "1").enqueue(new Callback<AddMarkAsCompleteResp>() {
                                        @Override
                                        public void onResponse(Call<AddMarkAsCompleteResp> call, Response<AddMarkAsCompleteResp> response) {
                                            if (response.body().getStatus().equalsIgnoreCase("Success")) {
                                                progressDialog.dismiss();
                                                lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).setIsComplete(response.body().getIsComplete());
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<AddMarkAsCompleteResp> call, Throwable t) {
                                            Log.i("TAG", "onFailure: " + t.getMessage());
                                            if (progressDialog != null) {
                                                progressDialog.dismiss();
                                            }
                                        }
                                    });
                                } else {
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
            } else {
                final String[] items = {getString(R.string.mark_as_incomplete)};
                new CircleDialog.Builder(EnrollVideo_Activity.this)
                        .configDialog(new ConfigDialog() {
                            @Override
                            public void onConfig(DialogParams params) {
                                params.animStyle = R.style.dialogWindowAnim;
                            }
                        })
                        .setItems(items, new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int
                                    position, long id) {

                                if (position == 0) {
                                    progressDialog.setMessage(getString(R.string.please_wait));
                                    progressDialog.show();
                                    apiService.getAddMarkAsComplete(sharedPreferences.getString(ConstantData.USER_ID, ""),
                                            lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).getVideoId(),
                                            lecturesModelArrayList.get(groupPos).getSectionId(),
                                            "0").enqueue(new Callback<AddMarkAsCompleteResp>() {
                                        @Override
                                        public void onResponse(Call<AddMarkAsCompleteResp> call, Response<AddMarkAsCompleteResp> response) {
                                            if (response.body().getStatus().equalsIgnoreCase("Success")) {
                                                progressDialog.dismiss();
                                                lecturesModelArrayList.get(groupPos).getVideoInfo().get(childPos).setIsComplete(response.body().getIsComplete());

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<AddMarkAsCompleteResp> call, Throwable t) {
                                            Log.i("TAG", "onFailure: " + t.getMessage());
                                            if (progressDialog != null) {
                                                progressDialog.dismiss();
                                            }
                                        }
                                    });

                                } else {

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
        }
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
}
