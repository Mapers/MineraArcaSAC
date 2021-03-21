package com.arca.mineraarcasac.Modelo_Nivel_Ocurrencias;

import com.google.gson.annotations.SerializedName;

public class getNivelOcurrencias {
    @SerializedName("codigo")
    private String Codigo;

    @SerializedName("nombre")
    private String Nombre;

    public String getCodigo() {
        return Codigo;
    }

    public void setCodigo(String codigo) {
        Codigo = codigo;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String nombre) {
        Nombre = nombre;
    }
}
