package com.arca.mineraarcasac;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.BlendMode;
import android.graphics.BlendModeColorFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    private final int DURACION_SPLASH = 4000;
    private ProgressBar Barra_Progreso;
    private TextView tTextoVersion_;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_splash);
        Barra_Progreso = findViewById(R.id.progressBar);
        tTextoVersion_=(TextView)findViewById(R.id.version_id_v);
        tTextoVersion_.setText("Version : "+GetVersion(this.getBaseContext()));
    }

    @Override
    protected void onStart() {
        super.onStart();

        new Handler().postDelayed(new Runnable() {
            public void run() {
                // Cuando pasen los 3 segundos, pasamos a la actividad principal de la aplicación

                Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }

            ;
        }, DURACION_SPLASH);

    }
    private String GetVersion(Context contex){
        String version="";
        try {
            PackageInfo pInfo = contex.getPackageManager().getPackageInfo(contex.getPackageName(), 0);
            version = pInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("VErsion :  " + version);
        return version;
    }
}
