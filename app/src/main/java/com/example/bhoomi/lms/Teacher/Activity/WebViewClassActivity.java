package com.example.bhoomi.lms.Teacher.Activity;

import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.webkit.PermissionRequest;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.bhoomi.lms.APIModel.BrainCertLaunchUrlResp;
import com.example.bhoomi.lms.APIModel.LiveClass.LiveClassResp;
import com.example.bhoomi.lms.R;
import com.example.bhoomi.lms.Retrofit.APIService;
import com.example.bhoomi.lms.Retrofit.APIUtils;
import com.example.bhoomi.lms.Retrofit.Config;
import com.example.bhoomi.lms.Student.Constants.ConstantData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewClassActivity extends AppCompatActivity {
    private WebView webView;
    private Toolbar toolbar;
    private boolean isLoad;
    private ProgressDialog progressDialog;
    private String class_id,course_name,lession;
    private APIService apiService;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private String myUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_view_class);
        webView = findViewById(R.id.webview);
        toolbar = findViewById(R.id.webviewToolbar);
        apiService = APIUtils.getAPIService();
        sharedPreferences = getSharedPreferences(ConstantData.PREF_NAME, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        progressDialog = new ProgressDialog(WebViewClassActivity.this);

        Intent intent = getIntent();
        class_id = intent.getStringExtra("class_id");
        course_name = intent.getStringExtra("course_name");
        lession = intent.getStringExtra("lession");
        toolbar.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar.getNavigationIcon().setAutoMirrored(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });




//        String documentType = getArguments().getString("documentType");
//        String myUrl = getArguments().getString("keyurl");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
        webView.setWebChromeClient(new WebChromeClient(){
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onPermissionRequest(final PermissionRequest request) {
                request.grant(request.getResources());
            }
        });
        getLaunchClassURL();
    }

    private void getLaunchClassURL() {
        progressDialog.setMessage(getString(R.string.please_wait));
        progressDialog.show();
        apiService.getBraincertLaunchURl(Config.BRAINCERT_KEY, class_id, sharedPreferences.getString(ConstantData.USER_ID, "")
                , sharedPreferences.getString(ConstantData.USER_NAME, "USER"), "1", course_name, lession).enqueue(new Callback<BrainCertLaunchUrlResp>() {
            @Override
            public void onResponse(Call<BrainCertLaunchUrlResp> call, Response<BrainCertLaunchUrlResp> response) {
               if (response.isSuccessful()) {
                    if (response.body().getStatus().equalsIgnoreCase("ok")) {
                        if (progressDialog != null)
                            progressDialog.dismiss();
                            myUrl = response.body().getEncryptedlaunchurl();
                            if (myUrl.length() > 0){
                                webView.setWebViewClient(new WebViewClient() {
                                    @Override
                                    public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                                        Log.i("TAG", "onReceivedError: ");
                                    }

                                    @Override
                                    public void onPageStarted(WebView view, String url, Bitmap favicon) {
                                        if (!isLoad) {
                                            isLoad = true;
                                            progressDialog.setMessage(getString(R.string.please_wait));
                                            progressDialog.show();
                                        }
                                    }

                                    @Override
                                    public void onPageFinished(WebView view, String url) {
                                        progressDialog.dismiss();
                                    }
                                });
//        if (documentType.equals("file"))
//            webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + myUrl);
//        else
            webView.loadUrl(myUrl);
                            }
                    }
                }
            }

            @Override
            public void onFailure(Call<BrainCertLaunchUrlResp> call, Throwable t) {
                if (progressDialog != null)
                    progressDialog.dismiss();
                Log.i("TAG", "onFailure: "+t.getMessage());
            }
        });
    }
}
