package com.arca.mineraarcasac.Modelo_Nivel_Ocurrencias;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class ListNivelOcurrencias {
    @SerializedName("NivelOcurrencia")
    private ArrayList<getNivelOcurrencias> ArrayNivelOcurrencias;

    public ArrayList<getNivelOcurrencias> getArrayNivelOcurrencias() {
        return ArrayNivelOcurrencias;
    }

    public void setArrayNivelOcurrencias(ArrayList<getNivelOcurrencias> arrayNivelOcurrencias) {
        ArrayNivelOcurrencias = arrayNivelOcurrencias;
    }
}
