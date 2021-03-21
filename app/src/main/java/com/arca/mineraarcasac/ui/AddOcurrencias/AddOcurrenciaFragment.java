package com.arca.mineraarcasac.ui.AddOcurrencias;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.arca.mineraarcasac.Api.Api;
import com.arca.mineraarcasac.ApiArca.ApiClient;
import com.arca.mineraarcasac.Modelo_AddOcurrencia.AddOcurrencias;
import com.arca.mineraarcasac.Modelo_Nivel_Ocurrencias.getNivelOcurrencias;
import com.arca.mineraarcasac.Modelo_Tipo_Ocurrencias.getTipoOcurrencias;
import com.arca.mineraarcasac.R;
import com.toptoche.searchablespinnerlibrary.SearchableSpinner;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOcurrenciaFragment extends Fragment {

    EditText editNombreAddocurrencias;
    EditText editComentarioAddocurrencias;


    private SharedPreferences sharedPref;

    ImageButton btnAceptar;
    ImageButton btnCancelar;

    private AddOcurrenciasViewModel addOcurrenciasViewModel;

    SearchableSpinner SpinersearchTipo;
    ArrayAdapter adapterSearchTipo;


    SearchableSpinner SpinersearchNivel;
    ArrayAdapter adapterSearchNivel;

    private HashMap<Integer, getNivelOcurrencias> MapaNivelOcurrencias = new HashMap<>();
    ArrayList ArrayNivelOcurrencias = new ArrayList();

    private HashMap<Integer, getTipoOcurrencias> MapaTipoOcurrencias = new HashMap<>();
    ArrayList ArrayTipoOcurrencias = new ArrayList();


    private  int PosicionSpinnerNivelOcurrencias;
    private  int PosicionSpinnerTipoOcurrencias;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        addOcurrenciasViewModel = ViewModelProviders.of(this).get(AddOcurrenciasViewModel.class);
        View root = inflater.inflate(R.layout.fragment_add_ocurrencias, container, false);

        sharedPref = root.getContext().getSharedPreferences(
                getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SpinersearchTipo = root.findViewById(R.id.spinnerSearchTipo);
        SpinersearchNivel = root.findViewById(R.id.spinnerSearchNivel);

        editNombreAddocurrencias= root.findViewById(R.id.editNombreOcurrencia);
        editComentarioAddocurrencias= root.findViewById(R.id.editComentario);
        btnAceptar = root.findViewById(R.id.btn_guardar_Ocurrencias);
        btnCancelar = root.findViewById(R.id.btn_cancelar_Ocurrencias);

        LlenarSpinner();

        SpinersearchNivel.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setPosicionSpinnerNivelOcurrencias(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        SpinersearchTipo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                setPosicionSpinnerTipoOcurrencias(position);
                Log.d("Tipo Ocurrencia :",position +"");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(editComentarioAddocurrencias.getText()!=null && editComentarioAddocurrencias.getText().length()>0
                    &&editNombreAddocurrencias.getText()!=null && editNombreAddocurrencias.getText().length()>0){
                    DialogoAceptar ();
                }else {
                    Toast.makeText(getActivity(), "Debe Ingresar un Nombre para la Ocurrencia y un comentario sobre su contenido !!!",
                            Toast.LENGTH_LONG).show();
                }


            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogoCancelar();
            }
        });

        return root;
    }

    private  void DialogoCancelar(){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.getContext());
        dialog.setTitle("Limpiar Formulario");
        dialog.setMessage("Desea cancelar y limpiar el formulario.");
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.icon_arca);


        dialog.setPositiveButton(
                "Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int
                            id) {
                        // Continue with some operation
                        LimpiarOcurrencias();
                    }
                });


        dialog.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });

        AlertDialog alert = dialog.create();
        alert.show();

    }

    private void DialogoAceptar (){
        AlertDialog.Builder dialog = new AlertDialog.Builder(this.getContext());
        dialog.setTitle("Enviar Ocurrencias");
        dialog.setMessage("Desea enviar esta Ocurrencia.");
        dialog.setCancelable(true);
        dialog.setIcon(R.drawable.icon_arca);

        dialog.setPositiveButton(
                "Si",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int
                            id) {
                        // Continue with some operation
                        AgregarOcurrencias();
                    }
                });


        dialog.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        // User cancelled the dialog
                        dialog.cancel();
                    }
                });

        AlertDialog alert = dialog.create();
        alert.show();

    }

    private void LimpiarOcurrencias(){
        editComentarioAddocurrencias.setText("");
        editNombreAddocurrencias.setText("");
        LlenarSpinner();
    }

    private void LlenarSpinner(){
        ArrayNivelOcurrencias.clear();
        ArrayTipoOcurrencias.clear();

        MapaNivelOcurrencias.clear();
        MapaTipoOcurrencias.clear();

        addOcurrenciasViewModel.getNivelOcurrenciasServer().observe(this, new Observer<ArrayList<getNivelOcurrencias>>() {
            @Override
            public void onChanged(ArrayList<getNivelOcurrencias> getNivelOcurrencias) {
                int i=0;
                for (getNivelOcurrencias getNivel : getNivelOcurrencias) {
                    ArrayNivelOcurrencias.add(getNivel.getNombre().toUpperCase());
                    MapaNivelOcurrencias.put(i,getNivel);
                    i++;
                }

                adapterSearchNivel = new ArrayAdapter(getContext(), R.layout.spinner_item_nivel, ArrayNivelOcurrencias);
                SpinersearchNivel.setAdapter(adapterSearchNivel);
                SpinersearchNivel.setTitle("Selec. Nivel".toUpperCase());
                SpinersearchNivel.setPositiveButton("Aceptar".toUpperCase());
            }
        });
        addOcurrenciasViewModel.getTipoOcurrenciasServer().observe(this, new Observer<ArrayList<getTipoOcurrencias>>() {
            @Override
            public void onChanged(ArrayList<getTipoOcurrencias> getTipoOcurrencias) {
                int i=0;
                for (getTipoOcurrencias getTipo : getTipoOcurrencias) {
                    ArrayTipoOcurrencias.add(getTipo.getNombre().toUpperCase());
                    MapaTipoOcurrencias.put(i,getTipo);
                    i++;
                }
                adapterSearchTipo = new ArrayAdapter(getContext(), R.layout.spinner_item_tipo, ArrayTipoOcurrencias);
                SpinersearchTipo.setAdapter(adapterSearchTipo);
                SpinersearchTipo.setTitle("Selec. Tipo".toUpperCase());
                SpinersearchTipo.setPositiveButton("Aceptar".toUpperCase());
            }
        });

    }

    private void AgregarOcurrencias(){

        Api api = ApiClient.getClient().create(Api.class);
        Call<AddOcurrencias> call = api.AddOcurrencias(sharedPref.getString((getString(R.string.ID_Usuario)), ""),
                                                        editNombreAddocurrencias.getText().toString(),
                                                        MapaTipoOcurrencias.get(getPosicionSpinnerTipoOcurrencias()).getCodigo(),
                                                        MapaNivelOcurrencias.get(getPosicionSpinnerNivelOcurrencias()).getCodigo(),
                                                        editComentarioAddocurrencias.getText().toString()
                                                        );
        Log.wtf("URL Called", call.request().url() + "");
        Log.wtf("URL Called", call.request().toString() + "");

        call.enqueue(new Callback<AddOcurrencias>() {
            @Override
            public void onResponse(Call<AddOcurrencias> call, Response<AddOcurrencias> response) {
                if(response.body().getMensaje().equals("Registro satisfactorio")){
                    Toast.makeText(getContext(), "Ocurrencia Enviada", Toast.LENGTH_LONG).show();
                    LimpiarOcurrencias();
                } else{
                    Toast.makeText(getContext(), "Ocurrencia no Enviada", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<AddOcurrencias> call, Throwable t) {
                Toast.makeText(getContext(), t.getLocalizedMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }


    public int getPosicionSpinnerNivelOcurrencias() {
        return PosicionSpinnerNivelOcurrencias;
    }

    public void setPosicionSpinnerNivelOcurrencias(int posicionSpinnerNivelOcurrencias) {
        PosicionSpinnerNivelOcurrencias = posicionSpinnerNivelOcurrencias;
    }

    public int getPosicionSpinnerTipoOcurrencias() {
        return PosicionSpinnerTipoOcurrencias;
    }

    public void setPosicionSpinnerTipoOcurrencias(int posicionSpinnerTipoOcurrencias) {
        PosicionSpinnerTipoOcurrencias = posicionSpinnerTipoOcurrencias;
    }
}
