package com.arca.mineraarcasac.ApiArca;


import com.arca.StringName.StringName;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {

    //base url
    public static final String BASE_URL = StringName.URL_API_Arca + "/api/";
    private static Retrofit retrofit = null;

    static final int READ_BLOCK_SIZE = 100;


    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


}