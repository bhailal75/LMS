package com.example.bhoomi.lms.Student.Fragment;

import android.app.ProgressDialog;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.example.bhoomi.lms.R;

public class WebViewFragment extends Fragment {
    private WebView webView;
    private Toolbar toolbar;
    private boolean isLoad;
    private ProgressDialog progressDialog;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_webview, container, false);
        webView = view.findViewById(R.id.webview);
        toolbar = view.findViewById(R.id.webviewToolbar);
        progressDialog = new ProgressDialog(getActivity());
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        toolbar.setNavigationIcon(R.drawable.ic_back);
        Configuration config = getActivity().getResources().getConfiguration();
        if (config.getLayoutDirection() == View.LAYOUT_DIRECTION_RTL)
            toolbar.getNavigationIcon().setAutoMirrored(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().onBackPressed();
            }
        });
        String documentType = getArguments().getString("documentType");
        String myUrl = getArguments().getString("keyurl");
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setLoadWithOverviewMode(true);
        webView.getSettings().setUseWideViewPort(true);
        webView.getSettings().setBuiltInZoomControls(true);
        webView.getSettings().setAllowFileAccessFromFileURLs(true);
        webView.getSettings().setAllowUniversalAccessFromFileURLs(true);
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
        if (documentType.equals("file"))
            webView.loadUrl("http://docs.google.com/gview?embedded=true&url=" + myUrl);
        else
            webView.loadUrl(myUrl);
    }
}
