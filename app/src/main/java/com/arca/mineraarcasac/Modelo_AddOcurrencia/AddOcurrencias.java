package com.arca.mineraarcasac.Modelo_AddOcurrencia;

import com.google.gson.annotations.SerializedName;

public class AddOcurrencias {

    @SerializedName("usuario")
    private String usuario;

    @SerializedName("nombre")
    private String nombre;

    @SerializedName("tipo")
    private String tipo;

    @SerializedName("nivel")
    private String nivel;

    @SerializedName("comentario")
    private String comentario;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }
}
