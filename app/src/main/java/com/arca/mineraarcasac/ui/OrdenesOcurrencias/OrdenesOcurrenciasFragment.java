package com.arca.mineraarcasac.ui.OrdenesOcurrencias;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.arca.StringName.StringName;
import com.arca.mineraarcasac.R;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static androidx.constraintlayout.widget.Constraints.TAG;

public class OrdenesOcurrenciasFragment extends Fragment {

    private OrdenesOcurrenciasViewModel ordenesOcurrenciasViewModel;
    private WebView webView;
    private SharedPreferences sharedPref;
    private ProgressDialog progressDialog;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ordenesOcurrenciasViewModel =
                ViewModelProviders.of(this).get(OrdenesOcurrenciasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_ocurrencias_pendientes, container, false);


        progressDialog = new ProgressDialog(getContext(), R.style.MyAlertDialogStyle);
        progressDialog.setIcon(R.drawable.icon_arca);
        progressDialog.setMessage("cargando ...");
        progressDialog.setTitle("Minera");

        sharedPref = root.getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        webView = root.findViewById(R.id.ordenes_aprobar);
        webView.setWebViewClient(new WebViewClient());
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                progressDialog.show();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                Log.d(TAG, "onPageFinished: for " + url);
                progressDialog.dismiss();
            }

            @Override
            public void onPageCommitVisible(WebView view, String url) {
                super.onPageCommitVisible(view, url);
                Log.d(TAG, "onPageCommitVisible: for " + url);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                Log.d(TAG, "shouldOverrideUrlLoading: for " + url);
                return super.shouldOverrideUrlLoading(view, url);
            }

        });
        try {
            String postData = "usuario=" + URLEncoder.encode(sharedPref.getString((getString(R.string.ID_Usuario)), ""), "UTF8")
                    //+ "&"+
                    //"cargo=" + URLEncoder.encode(sharedPref.getString((getString(R.string.ID_Nivel_Usuario)), ""), "UTF8")
                    ;
            Log.d("Data_Log",sharedPref.getString((getString(R.string.ID_Usuario)), ""));
            Log.d("Data_Log",sharedPref.getString((getString(R.string.ID_Nivel_Usuario)), ""));
            webView.postUrl(StringName.Historial_Aprobar, postData.getBytes()); // pasando el Id del Usuario que se a registrado
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }


        final TextView textView = root.findViewById(R.id.text_notifications);
        ordenesOcurrenciasViewModel.getText().observe(this, new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}