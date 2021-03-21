package com.arca.mineraarcasac;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

import com.arca.StringName.StringName;
import com.arca.mineraarcasac.Api.Api;
import com.arca.mineraarcasac.ApiArca.ApiClient;
import com.arca.mineraarcasac.Model_Usuario.Model;
import com.arca.mineraarcasac.Modelo_setCelular.reg_celular;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    BottomNavigationView navView;

    private String TokenGoogle;

    private SharedPreferences sharedPref;
    private boolean isGerente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);
        navView = findViewById(R.id.nav_view);
        sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        isGerente = sharedPref.getString((getString(R.string.Cargo_Usuario)), "").equals(StringName.id_nivel_Usuario_Mostrar_Menu_Navegacion)
                   ||  sharedPref.getString((getString(R.string.Cargo_Usuario)), "").equals(StringName.id_nivel_Usuario_Mostrar_Menu_Navegacion_2);

        MostrarNavegador(navView, isGerente);
        //navView.getMenu().getItem(0).setVisible(false);
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        // AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
        //        R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications,R.id.navigation_add_ocurrencias)
        //      .build();
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        //NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        NavigationUI.setupWithNavController(navView, navController);


    }

    private void MostrarNavegador(BottomNavigationView navView, boolean flags) {
        Log.d("Gerente  ;" , flags +"");
        navView.getMenu().getItem(0).setVisible(flags);
        navView.getMenu().getItem(1).setVisible(flags);
        navView.getMenu().getItem(2).setVisible(flags);
        if (flags) {
            System.out.println("::::::::::::::::: GOOGLE SERVICIOS ::::::::::::::::::::");
            GoogleServicios();
        }else {
            UnSuscribirse();
        }
    }

    private void GoogleServicios() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            // Create channel to show notifications.
            String channelId = getString(R.string.default_notification_channel_id);
            String channelName = getString(R.string.default_notification_channel_name);
            NotificationManager notificationManager =
                    getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(new NotificationChannel(channelId,
                    channelName, NotificationManager.IMPORTANCE_LOW));

            Suscribirse();
            getToken();
        }

    }
    private void UnSuscribirse(){
        FirebaseMessaging.getInstance().unsubscribeFromTopic(StringName.TOPIC_DROID);
    }

    private void Suscribirse() {

        // [START subscribe_topics]
        FirebaseMessaging.getInstance().subscribeToTopic(StringName.TOPIC_DROID)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = getString(R.string.msg_subscribed);
                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_failed);
                        }
                        Log.d(TAG, msg);
                       // Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END subscribe_topics]
    }

    private void getToken() {

        // Get token
        // [START retrieve_current_token]
        FirebaseInstanceId.getInstance().getInstanceId()
                .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                    @Override
                    public void onComplete(@NonNull Task<InstanceIdResult> task) {
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "getInstanceId failed", task.getException());
                            return;
                        }

                        // Get new Instance ID token
                        String token = task.getResult().getToken();
                        TokenGoogle= token;
                        // Log and toast
                        String msg = getString(R.string.msg_token_fmt, token);
                        Log.d(TAG, msg);
                        if (TokenGoogle != null) {
                            reg_celular();
                        }
                        //  Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        // [END retrieve_current_token]


    }

    private void reg_celular() {
        System.out.println(":::::::::::::: Registrando Celular :::::::::::::::");
        Api api = ApiClient.getClient().create(Api.class);
        Call<reg_celular> call = api.reg_celular(sharedPref.getString((getString(R.string.ID_Usuario)), ""),
                TokenGoogle,
                obtenerNombreDeDispositivo(),
                getIdAndroid());
        System.out.println("Id Isuario :  " + sharedPref.getString((getString(R.string.ID_Usuario)), ""));
        System.out.println("Token : " + TokenGoogle);
        System.out.println("Nombre Dispositivo : "+obtenerNombreDeDispositivo());
        System.out.println("Android ID  " + getIdAndroid());
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<reg_celular>() {
            @Override
            public void onResponse(Call<reg_celular> call, Response<reg_celular> response) {
                if (response.body().getMensaje().equals("Registro satisfactorio")) {
                    System.out.println("Dispositivo Registrado");
                    //Toast.makeText(MainActivity.this, "Dispositivo Registrado", Toast.LENGTH_LONG).show();
                } else {
                    System.out.println("Error al Registrar el Dispositivo");
                    //Toast.makeText(MainActivity.this, "Error al Registrar el Dispositivo", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<reg_celular> call, Throwable t) {
                System.out.println("Error al Registrae celrular"+t.getLocalizedMessage());
                //Toast.makeText(MainActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public String obtenerNombreDeDispositivo() {
        String fabricante = Build.MANUFACTURER;
        String modelo = Build.MODEL;
        if (modelo.startsWith(fabricante)) {
            return primeraLetraMayuscula(modelo);
        } else {
            return primeraLetraMayuscula(fabricante) + " " + modelo;
        }
    }


    private String primeraLetraMayuscula(String cadena) {
        if (cadena == null || cadena.length() == 0) {
            return "";
        }
        char primeraLetra = cadena.charAt(0);
        if (Character.isUpperCase(primeraLetra)) {
            return cadena;
        } else {
            return Character.toUpperCase(primeraLetra) + cadena.substring(1);
        }
    }

    private String getIdAndroid() {
        String androidId = Settings.Secure.getString(this.getContentResolver(), Settings.Secure.ANDROID_ID);
        Log.d("ID", "Android ID: " + androidId);
        return androidId;
    }


}
