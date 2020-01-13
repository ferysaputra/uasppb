package com.example.cemilan;

import com.example.cemilan.Model.Barang;
import com.example.cemilan.Model.BarangList;
import com.example.cemilan.Model.PayloadTrx;
import com.example.cemilan.Model.User;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

interface ApiInterface {
    @GET("login.php")
    Call<User> login(@Query("user") String user, @Query("pass") String pass);

    @GET("register.php")
    Call<String> register(@Query("user") String user, @Query("pass") String pass);

    @GET("select.php")
    Call<ArrayList<Barang>> barang();


    @Multipart
    @POST("trx.php")
    Call<String> trx(@Part("id") RequestBody id, @Part("data") RequestBody data);


}
