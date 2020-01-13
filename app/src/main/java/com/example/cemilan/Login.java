package com.example.cemilan;

import android.content.Intent;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatAutoCompleteTextView;

import com.example.cemilan.Helper.Preferences;
import com.example.cemilan.Model.User;
import com.google.android.gms.auth.api.signin.GoogleSignInApi;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Login extends AppCompatActivity {

    private EditText Email, Pass;
    private FirebaseAuth mAuth;
    private AppCompatAutoCompleteTextView autoTextView;
    private AppCompatAutoCompleteTextView autoTextViewCustom;
    private CheckBox checkbox;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(Preferences.getId(Login.this) != 0){
            startActivity(new Intent(Login.this,Dashboard.class));
            finish();
        }
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();
        Email = findViewById(R.id.edit_email);
        Pass = findViewById(R.id.edit_pass);
        Button btnLogin = findViewById(R.id.btn_login);
        TextView tvregister = findViewById(R.id.tv_register);
        checkbox = findViewById(R.id.id_checkbox);
        

        checkbox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked) {
                    // show password
                    Pass.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                } else {
                    // hide password
                    Pass.setTransformationMethod(PasswordTransformationMethod.getInstance());
                }
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = Email.getText().toString();
                String password = Pass.getText().toString();
                if (user.equals("")) {
                    Toast.makeText(Login.this, "Username belum di isi", Toast.LENGTH_LONG).show();
                } else if (password.equals("")) {
                    Toast.makeText(Login.this, "Password belum di isi", Toast.LENGTH_LONG).show();
                } else {
                    Log.d("==========",user+password);
                    RetrofitClient.servicesApi().login(user,password).enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User > response) {
                            Log.d("======= message",response.body().toString());
                            User user = response.body();
                            Preferences.setLoginUser(Login.this,user.id,user.role);
                            startActivity(new Intent(Login.this,Dashboard.class));
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Log.d("======= message",t.toString());
                        }
                    });
//                    mAuth.signInWithEmailAndPassword(user, password)
//                            .addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        FirebaseUser user = mAuth.getCurrentUser();
//                                        Toast.makeText(Login.this, "Authentication Success.",
//                                                Toast.LENGTH_SHORT).show();
//                                        Intent i = new Intent(Login.this, Dashboard.class);
//                                        startActivity(i);
//
//                                    } else {
//                                        Toast.makeText(Login.this, "Authentication failed.",
//                                                Toast.LENGTH_SHORT).show();
//
//                                    }
//                                }
//                            });
                }
            }
        });

        tvregister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Login.this,Register.class);
                startActivity(i);
            }
        });
    }

    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }

}
