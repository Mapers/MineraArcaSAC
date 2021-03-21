package com.arca.mineraarcasac;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Build;
import android.os.Bundle;
import android.os.Vibrator;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.arca.StringName.StringName;
import com.arca.mineraarcasac.Api.Api;
import com.arca.mineraarcasac.ApiArca.ApiClient;
import com.arca.mineraarcasac.Model_Usuario.Model;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends AppCompatActivity {

    EditText username_input, password_input;
    Button btnLogin;
    Vibrator v;
    final Context c = this;
    private ProgressDialog progressDialog;

    private static final String TAG = "LoginActivity";

    private SharedPreferences sharedPref;

    private String TokenGoogle = null;

    private CheckBox chekGuardar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_login);

        username_input = (EditText) findViewById(R.id.edit_user);
        password_input = (EditText) findViewById(R.id.edit_Pass);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        chekGuardar = findViewById(R.id.check_guardar);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);

        progressDialog = new ProgressDialog(this, R.style.MyAlertDialogStyle);
        progressDialog.setIcon(R.drawable.icon_arca);
        progressDialog.setMessage("CARGANDO ...");
        progressDialog.setTitle("CONTRATA MINERA ARCA S.A.C.");

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressDialog.show();
                validateUserData();

               /* startActivity(new Intent(getBaseContext(), MainActivity.class)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP
                                | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                Intent.FLAG_ACTIVITY_NO_HISTORY));*/
            }
        });
        sharedPref = this.getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        CargarShareData();
    }


    private void validateUserData() {

        //first getting the values
        final String username = username_input.getText().toString();
        final String password = password_input.getText().toString();

        //checking if username is empty
        if (TextUtils.isEmpty(username)) {
            username_input.setError("INGRESE NOMBRE DE USUARIO");
            username_input.requestFocus();
            // Vibrate for 100 milliseconds
            v.vibrate(100);
            btnLogin.setEnabled(true);
            // progressDialog.dismiss();
            return;
        }
        //checking if password is empty
        if (TextUtils.isEmpty(password)) {
            password_input.setError("INGRESE TU CONTRASEÑA");
            password_input.requestFocus();
            //Vibrate for 100 milliseconds
            v.vibrate(100);
            btnLogin.setEnabled(true);
            //progressDialog.dismiss();
            return;
        }

        //Login User if everything is fine
        loginUser(username, password);

    }

    private void CargarShareData() {
        username_input.setText(sharedPref.getString((getString(R.string.Nombre_Usuario)), ""));
        password_input.setText(sharedPref.getString((getString(R.string.Clave_Usuario)), ""));
        chekGuardar.setChecked(sharedPref.getBoolean((getString(R.string.Chek_Seleccionado)), false));
    }

    private void loginUser(String username, String password) {

        //making Api call
        Api api = ApiClient.getClient().create(Api.class);
        Call<Model> login = api.login(username, password);
        Log.wtf("URL Called", login.request() + "");
        Log.wtf("URL Called", login.request().url() + "");
        login.enqueue(new Callback<Model>() {
            @Override
            public void onResponse(Call<Model> call, Response<Model> response) {
                if (response.body() != null) {
                    if (response.body().getMensaje().equals("Ingreso satisfactorio")) {
                        startActivity(new Intent(getBaseContext(), MainActivity.class)
                                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP
                                        | Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                                        Intent.FLAG_ACTIVITY_NO_HISTORY));
                        if (chekGuardar.isChecked()) {
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(getString(R.string.Nombre_Usuario), username_input.getText().toString());
                            editor.putString(getString(R.string.Clave_Usuario), password_input.getText().toString());
                            editor.putString(getString(R.string.ID_Nivel_Usuario), response.body().getNivel());
                            editor.putString(getString(R.string.ID_Usuario), response.body().getCodigo());
                            editor.putString(getString(R.string.Cargo_Usuario), response.body().getCargo());
                            editor.putBoolean(getString(R.string.Chek_Seleccionado), true);
                            editor.commit();
                        } else {
                            SharedPreferences.Editor editor = sharedPref.edit();
                            editor.putString(getString(R.string.Nombre_Usuario), "");
                            editor.putString(getString(R.string.Clave_Usuario), "");
                            editor.putString(getString(R.string.ID_Nivel_Usuario), response.body().getNivel());
                            editor.putString(getString(R.string.ID_Usuario), response.body().getCodigo());
                            editor.putString(getString(R.string.Cargo_Usuario), response.body().getCargo());
                            editor.putBoolean(getString(R.string.Chek_Seleccionado), false);
                            editor.commit();
                        }


                        progressDialog.dismiss();
                    } else if (response.body().getMensaje().equals("Usuario o Clave Incorrectos")) {
                        Toast.makeText(LoginActivity.this, "Error al Ingresar las Credenciales", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    } else {
                        Toast.makeText(LoginActivity.this, "Error en la Conexion a la Base de Datos ", Toast.LENGTH_LONG).show();
                        progressDialog.dismiss();
                    }


                }else {
                    Toast.makeText(LoginActivity.this, "Error en la Conexion con el Servidor ", Toast.LENGTH_LONG).show();
                    progressDialog.dismiss();
                }

            }

            @Override
            public void onFailure(Call<Model> call, Throwable t) {
                Toast.makeText(LoginActivity.this, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                progressDialog.dismiss();
            }
        });


    }


}
