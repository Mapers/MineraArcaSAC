package com.arca.mineraarcasac.Modelo_setCelular;

import com.google.gson.annotations.SerializedName;

public class reg_celular {

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("google")
    private String google;

    @SerializedName("modelo")
    private String modelo;

    @SerializedName("dispositivo")
    private String dispositivo;

    @SerializedName("mensaje")
    private String mensaje;


    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getDispositivo() {
        return dispositivo;
    }

    public void setDispositivo(String dispositivo) {
        this.dispositivo = dispositivo;
    }
}
