package com.arca.mineraarcasac.Modelo_Tipo_Ocurrencias;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListTipoOcurrencias {

    @SerializedName("TipoOcurrencia")
    private ArrayList<getTipoOcurrencias> ArrayTipoOcurrencia;


    public ArrayList<getTipoOcurrencias> getArrayTipoOcurrencia() {
        return ArrayTipoOcurrencia;
    }

    public void setArrayTipoOcurrencia(ArrayList<getTipoOcurrencias> arrayTipoOcurrencia) {
        ArrayTipoOcurrencia = arrayTipoOcurrencia;
    }
}
