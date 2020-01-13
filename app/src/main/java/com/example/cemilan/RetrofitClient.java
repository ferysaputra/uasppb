package com.example.cemilan;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

public class RetrofitClient {
    static String BASR_URL = "";
    static private Retrofit retrofit = null;

    static Retrofit getClient(){
        Gson gson = new GsonBuilder()
                .setLenient()
                .create();
        if(retrofit == null){
            retrofit =  new Retrofit.Builder()
                    .baseUrl("http://cemilan-ku.000webhostapp.com/")
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .build();
        }
        return retrofit;
    }

    static ApiInterface servicesApi(){
        return getClient().create(ApiInterface.class);
    }

}
