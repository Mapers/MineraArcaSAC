package com.arca.mineraarcasac.Model_Usuario;

import com.google.gson.annotations.SerializedName;

public class Model {
    @SerializedName("nivel")
    private String nivel;

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("clave")
    private String clave;

    @SerializedName("mensaje")
    private String mensaje;

    @SerializedName("codigo")
    private String codigo;

    @SerializedName("cargo")
    private String cargo;

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClave() {
        return clave;
    }

    public void setClave(String clave) {
        this.clave = clave;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public String getCargo() {
        return cargo;
    }

    public void setCargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }
}

