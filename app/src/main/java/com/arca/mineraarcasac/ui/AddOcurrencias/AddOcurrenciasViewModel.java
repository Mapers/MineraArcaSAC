package com.arca.mineraarcasac.ui.AddOcurrencias;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.arca.mineraarcasac.Api.Api;
import com.arca.mineraarcasac.ApiArca.ApiClient;
import com.arca.mineraarcasac.Modelo_Nivel_Ocurrencias.ListNivelOcurrencias;
import com.arca.mineraarcasac.Modelo_Nivel_Ocurrencias.getNivelOcurrencias;
import com.arca.mineraarcasac.Modelo_Tipo_Ocurrencias.ListTipoOcurrencias;
import com.arca.mineraarcasac.Modelo_Tipo_Ocurrencias.getTipoOcurrencias;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddOcurrenciasViewModel extends ViewModel {

    private MutableLiveData<ArrayList<getTipoOcurrencias>> mGetTipoOcurrencias;
    private MutableLiveData<ArrayList<getNivelOcurrencias>> mGetTipoNivel;

    public AddOcurrenciasViewModel() {
        mGetTipoOcurrencias= new MutableLiveData<>();
        mGetTipoNivel= new MutableLiveData<>();
        TipoOcurrencias();
        NivelOcurrencias();
    }

    private void TipoOcurrencias() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ListTipoOcurrencias> call = api.getTipoOcurrencias();
        Log.wtf("URL Called", call.request().url() + "");

        call.enqueue(new Callback<ListTipoOcurrencias>() {
            @Override
            public void onResponse(Call<ListTipoOcurrencias> call, Response<ListTipoOcurrencias> response) {
                mGetTipoOcurrencias.setValue(response.body().getArrayTipoOcurrencia());
            }

            @Override
            public void onFailure(Call<ListTipoOcurrencias> call, Throwable t) {

            }
        });
    }

    private void NivelOcurrencias() {
        Api api = ApiClient.getClient().create(Api.class);
        Call<ListNivelOcurrencias> call = api.getNivelOcurrencias();
        call.enqueue(new Callback<ListNivelOcurrencias>() {
            @Override
            public void onResponse(Call<ListNivelOcurrencias> call, Response<ListNivelOcurrencias> response) {
                mGetTipoNivel.setValue(response.body().getArrayNivelOcurrencias());
            }

            @Override
            public void onFailure(Call<ListNivelOcurrencias> call, Throwable t) {

            }
        });
    }

    public LiveData<ArrayList<getTipoOcurrencias>> getTipoOcurrenciasServer() {
        return mGetTipoOcurrencias;
    }

    public LiveData<ArrayList<getNivelOcurrencias>> getNivelOcurrenciasServer() {
        return mGetTipoNivel;
    }

}
