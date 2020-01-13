package com.example.cemilan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.cemilan.Model.Barang;
import com.example.cemilan.Model.BarangList;

import java.util.ArrayList;
import java.util.HashSet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MenuCemilan extends AppCompatActivity implements OnClickListener {
    ArrayList<Barang> barang = new ArrayList<Barang>();

    ArrayList<BarangList> barangList = new ArrayList<BarangList>();

    TextView rago;
    int hargaTotal = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_cemilan);
        loadData();
        rago = (TextView) findViewById(R.id.Harga);
        findViewById(R.id.img_dorayaki).setOnClickListener(this);
        findViewById(R.id.img_dango).setOnClickListener(this);
        findViewById(R.id.img_french).setOnClickListener(this);
        findViewById(R.id.img_ramen1).setOnClickListener(this);
        findViewById(R.id.img_sushi).setOnClickListener(this);
        findViewById(R.id.img_tako).setOnClickListener(this);
        findViewById(R.id.Harga).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MenuCemilan.this, Pembayaran.class);
                i.putExtra("biaya", String.valueOf(hargaTotal));
                i.putExtra("barangList", barangList);
                startActivity(i);
            }
        });
    }

    @Override
    public void onClick(View v) {
        Integer index;
        switch (v.getId()) {
            case R.id.img_dorayaki:
                hargaTotal += barang.get(0).harga;
                index = indexOfBarang(barangList, barang.get(0).id);
                if (index != -1) {
                    barangList.get(index).total += 1;
                } else {
                    barangList.add(
                            new BarangList(
                                    barang.get(0).id,
                                    barang.get(0).harga,
                                   1,
                                    "Dorayak"));
                }
                break;
            case R.id.img_sushi:
                hargaTotal += barang.get(1).harga;
                index = indexOfBarang(barangList, barang.get(1).id);
                if (index != -1) {
                    barangList.get(index).total += 1;
                } else {
                    barangList.add(
                            new BarangList(
                                    barang.get(1).id,
                                    barang.get(1).harga,
                                    1,
                                    "Sushi"));
                }
                break;
            case R.id.img_ramen1:
                hargaTotal += barang.get(2).harga;
                index = indexOfBarang(barangList, barang.get(2).id);
                if (index != -1) {
                    barangList.get(index).total +=1;
                } else {
                    barangList.add(
                            new BarangList(
                                    barang.get(2).id,
                                    barang.get(2).harga,
                                    1,
                                    "Ramen"));
                }
                break;
            case R.id.img_dango:
                hargaTotal += barang.get(3).harga;
                index = indexOfBarang(barangList, barang.get(3).id);
                if (index != -1) {
                    barangList.get(index).total += 1;
                } else {
                    barangList.add(
                            new BarangList(
                                    barang.get(3).id,
                                    barang.get(3).harga,
                                    1,
                                    "Dango"));
                }
                break;
            case R.id.img_tako:
                hargaTotal += barang.get(1).harga;
                index = indexOfBarang(barangList, barang.get(4).id);
                if (index != -1) {
                    barangList.get(index).total += 1;
                } else {
                    barangList.add(
                            new BarangList(
                                    barang.get(4).id,
                                    barang.get(4).harga,
                                    1,
                                    "Tako"));
                }
                break;
            case R.id.img_french:
                hargaTotal += barang.get(5).harga;
                index = indexOfBarang(barangList, barang.get(5).id);
                if (index != -1) {
                    barangList.get(index).total += 1;
                } else {
                    barangList.add(
                            new BarangList(
                                    barang.get(5).id,
                                    barang.get(5).harga,
                                    1,
                                    "Kentang"));
                }
                break;
            default:
                hargaTotal = hargaTotal;
        }
        Log.d("=================","=====New======================");
        barangList.forEach(x -> {
            Log.d("======", x.toString());
        });
        rago.setText("Rp. " + String.valueOf(hargaTotal));
    }

    public void loadData() {
        RetrofitClient.servicesApi().barang().enqueue(new Callback<ArrayList<Barang>>() {
            @Override
            public void onResponse(Call<ArrayList<Barang>> call, Response<ArrayList<Barang>> response) {
                response.body().forEach(brg -> {
                    barang.add(brg);
                });
                TextView harga0 = findViewById(R.id.harga0);
                harga0.setText(barang.get(0).harga.toString());

                TextView harga1 = findViewById(R.id.harga1);
                harga1.setText(barang.get(1).harga.toString());

                TextView harga2 = findViewById(R.id.harga2);
                harga2.setText(barang.get(2).harga.toString());

                TextView harga3 = findViewById(R.id.harga3);
                harga3.setText(barang.get(3).harga.toString());

                TextView harga4 = findViewById(R.id.harga4);
                harga4.setText(barang.get(4).harga.toString());

                TextView harga5 = findViewById(R.id.harga5);
                harga5.setText(barang.get(5).harga.toString());

            }

            @Override
            public void onFailure(Call<ArrayList<Barang>> call, Throwable t) {

            }
        });
    }

    public Integer indexOfBarang(ArrayList<BarangList> barangList, Integer id) {
        if (barangList.size() == 0) return -1;
        final Integer[] idx = {0};
        final Integer[] idxFind = {-1};
        barangList.forEach(item -> {
            if (item.id == id) {
                idxFind[0] = idx[0];
            }
            idx[0] = idx[0] + 1;
        });

        return idxFind[0];
    }
}
