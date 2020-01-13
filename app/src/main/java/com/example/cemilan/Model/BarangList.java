package com.example.cemilan.Model;

import androidx.annotation.NonNull;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class BarangList implements Serializable {
    public Integer id;
    public Integer harga;
    public Integer total;
    public String nama;
}
