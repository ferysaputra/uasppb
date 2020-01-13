package com.example.cemilan.Model;

import androidx.annotation.NonNull;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {
    public Integer id;
    public String user;
    public String pass;
    public String role;

    @NonNull
    @Override
    public String toString() {
        return id+" "+user+" "+pass+" "+role;
    }
}
