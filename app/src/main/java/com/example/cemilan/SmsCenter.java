package com.example.cemilan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SmsCenter extends AppCompatActivity {

    Button button;
    EditText editText,editText2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sms_center);
        if(ContextCompat.checkSelfPermission(SmsCenter.this,
                Manifest.permission.SEND_SMS)!= PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(SmsCenter.this,
                    Manifest.permission.SEND_SMS)){
                ActivityCompat.requestPermissions(SmsCenter.this,
                        new String[]{Manifest.permission.SEND_SMS},1);
            }
            else{
                ActivityCompat.requestPermissions(SmsCenter.this,
                        new String[]{Manifest.permission.SEND_SMS},1);
            }
        }
        else{
            //tidak usah dijalankan
        }
        button = (Button)findViewById(R.id.button);
        editText = (EditText)findViewById(R.id.editText);
        editText2 = (EditText)findViewById(R.id.editText2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String number = editText.getText().toString();
                String sms = editText2.getText().toString();
                try {
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(number, null, sms, null, null);
                    Toast.makeText(SmsCenter.this,"Sent!!",Toast.LENGTH_SHORT).show();
                }catch (Exception e){
                    Toast.makeText(SmsCenter.this,"Sent!!",Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case 1 :{
                if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    if(ContextCompat.checkSelfPermission(SmsCenter.this, Manifest.permission.SEND_SMS)==
                            PackageManager.PERMISSION_GRANTED){
                        Toast.makeText(this, "permission granted!!",Toast.LENGTH_SHORT).show();
                    }
                }
                else{
                    Toast.makeText(this, " No permission granted!!",Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }
}
