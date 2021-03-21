package com.arca.mineraarcasac.Api;

import com.arca.mineraarcasac.Model_Usuario.Model;
import com.arca.mineraarcasac.Modelo_AddOcurrencia.AddOcurrencias;
import com.arca.mineraarcasac.Modelo_Nivel_Ocurrencias.ListNivelOcurrencias;
import com.arca.mineraarcasac.Modelo_Tipo_Ocurrencias.ListTipoOcurrencias;
import com.arca.mineraarcasac.Modelo_setCelular.reg_celular;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface Api {

    @POST("api_login.php")
    @FormUrlEncoded
    Call<Model> login(@Field("usuario") String username, @Field("clave") String password);

    @POST("api_regcel.php")
    @FormUrlEncoded
    Call<reg_celular> reg_celular(@Field("usuario") String usuario, @Field("google") String google
                            , @Field("modelo") String modelo , @Field("dispositivo") String dispositivo);

    @POST("api_nivelocurrencias.php")
    Call<ListNivelOcurrencias> getNivelOcurrencias();

    @POST("api_tipoocurrencias.php")
    Call<ListTipoOcurrencias> getTipoOcurrencias();

    @POST("api_regocu.php")
    @FormUrlEncoded
    Call<AddOcurrencias> AddOcurrencias(@Field("usuario") String usuario, @Field("nombre") String nombre
            , @Field("tipo") String tipo , @Field("nivel") String nivel, @Field("comentario") String comentario);
}
