package com.example.cemilan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.GridLayout;
import android.widget.ImageButton;
import android.widget.Switch;
import android.widget.Toast;

import com.example.cemilan.Helper.Preferences;

public class Dashboard extends AppCompatActivity implements View.OnClickListener {
    private CardView cemilan, call, sms,admin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dashboard);
        //Add Cardview yang ada di dashboard
        call = findViewById(R.id.callcenter);
        sms = findViewById(R.id.smscenter);
        cemilan = findViewById(R.id.cemilan);
        admin = findViewById(R.id.admin);
        //admin = findViewById(R.id.recycleview_admin);
        //tambah clicklistener
        cemilan.setOnClickListener(this);
        admin.setOnClickListener(this);
        sms.setOnClickListener(this);
        call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_CALL);
                i.setData(Uri.parse("tel:089510213499"));
                if (checkSelfPermission(Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                startActivity(i);
            }
        });

    }
    @Override
    public void onClick(View v) {
        Intent i;
        switch (v.getId()) {
            case R.id.cemilan:
                i = new Intent(this, MenuCemilan.class);
                startActivity(i);
                break;
            case R.id.callcenter:
                i = new Intent(this, Dashboard.class);
                startActivity(i);
                break;
            case R.id.smscenter:
                i = new Intent(this, SmsCenter.class);
                startActivity(i);
                break;

            case  R.id.admin:{
                if(Preferences.getRole(Dashboard.this)=="admin"){
                    Toast.makeText(Dashboard.this,"Anda Admin",Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(Dashboard.this,"Anda Bukan Admin",Toast.LENGTH_SHORT).show();
                }
            }
            default:
                break;
        }
    }
    public void maps(View view) {
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com/maps"));
        startActivity(intent);
    }

    public void logout(View it){
        Preferences.clearLoggedInUser(Dashboard.this);
        Intent intent =new Intent(Dashboard.this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();// finish the current activity
    }
}
