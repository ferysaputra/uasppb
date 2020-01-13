package com.example.cemilan;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.cemilan.Helper.Preferences;
import com.example.cemilan.Model.BarangList;
import com.example.cemilan.Model.PayloadTrx;
import com.example.cemilan.Model.User;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Pembayaran extends AppCompatActivity {
    int totalBiaya = 0;
    ArrayList<BarangList> barangList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.pembayaran);
        String biaya = getIntent().getStringExtra("biaya");
        barangList = (ArrayList<BarangList>) getIntent().getSerializableExtra("barangList");
        totalBiaya = Integer.valueOf(biaya);
        TextView x = findViewById(R.id.total_biaya);
        EditText Et = findViewById(R.id.edt_bayar);
        Et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                EditText kembalian = findViewById(R.id.edt_kemabalian);
                if (String.valueOf(s) != "") {
                    int uangRakyat = Integer.valueOf(String.valueOf(s));
                    int totalKalkulasi = uangRakyat - totalBiaya;
                    if (totalKalkulasi > 0) {
                        kembalian.setText(String.valueOf(totalKalkulasi));
                    }
                }

            }
        });
        x.setText(biaya);
    }

    public void bayar(View view) {

//        Intent i = new Intent(Pembayaran.this, MenuCemilan.class);
//        startActivity(i);
//        this.finish();
//        Toast.makeText(getApplicationContext(), "Pembayaran Sukses", Toast.LENGTH_LONG).show();
        try {
            Gson gson = new Gson();
            RetrofitClient.servicesApi().trx(
                    RequestBody.create(
                            MediaType.parse("text/plain"),
                            Preferences.getId(Pembayaran.this).toString()
                    ),
                    RequestBody.create(
                            MediaType.parse("text/plain"), gson.toJson(barangList))
            ).enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {

                    Log.d("========", gson.toJson(barangList));
                    Log.d("========", response.raw().toString());
                    Intent browserIntent = new Intent(
                            Intent.ACTION_VIEW,
                            Uri.parse("https://cemilan-ku.000webhostapp.com/shownote.php?data=" + gson.toJson(barangList)));
                    startActivity(browserIntent);
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    Log.d("========", t.toString() + "==jlkjlkjl;k======dk");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void print(View view) {
        final String nota[] = {""};

        nota[0] += "=============NOTA=================\n";
        barangList.forEach(item -> {
            nota[0] += "Nama Barang = " + item.nama + "   Total Bayar =" + item.total + "\n";
        });
        stringtopdf(nota[0]);
    }

    public void stringtopdf(String data) {
        String extstoragedir = this.getExternalFilesDir(null).toString();
        Log.d("===", extstoragedir);
        File folder = new File(extstoragedir, "pdf");
        if (!folder.exists()) {
            boolean bool = folder.mkdir();
        }
        try {
            final File file = new File(folder, "Test.pdf");
            file.createNewFile();
            FileOutputStream fOut = new FileOutputStream(file);

            PdfDocument document = new PdfDocument();
            PdfDocument.PageInfo pageInfo = new
                    PdfDocument.PageInfo.Builder(1000, 1000, 1).create();
            PdfDocument.Page page = document.startPage(pageInfo);
            Canvas canvas = page.getCanvas();
            Paint paint = new Paint();

            canvas.drawText(data, 10, 10, paint);

            document.finishPage(page);
            document.writeTo(fOut);
            document.close();

        } catch (IOException e) {
            Log.d("========", e.toString());
            Log.i("error", e.getLocalizedMessage());
        }
    }
}
