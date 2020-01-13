package com.example.cemilan;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Register extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private EditText etEmail, etPassword;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth = FirebaseAuth.getInstance();
        
        final EditText etEmail = findViewById(R.id.et_email1);
        final EditText etPassword = findViewById(R.id.et_password1);

        Button btnReg = findViewById(R.id.btn_register);
        btnReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String Email = etEmail.getText().toString();
                String Password = etPassword.getText().toString();

                if (Email.equals("")) {
                    Toast.makeText(Register.this, "Silahkan Isikan Email", Toast.LENGTH_LONG).show();
                } else if (Password.equals("")) {
                    Toast.makeText(Register.this, "Silahkan Isikan Password", Toast.LENGTH_LONG).show();
                }else {
                    RetrofitClient.servicesApi().register(Email,Password).enqueue(new Callback<String>() {
                        @Override
                        public void onResponse(Call<String> call, Response<String> response) {
                            Log.d("ini message ==========",response.body());
                        }

                        @Override
                        public void onFailure(Call<String> call, Throwable t) {
                            Log.d("ini message ==========",t.toString());
                        }
                    });
                }
//                else {
//                    mAuth.createUserWithEmailAndPassword(Email, Password)
//                            .addOnCompleteListener(Register.this, new OnCompleteListener<AuthResult>() {
//                                @Override
//                                public void onComplete(@NonNull Task<AuthResult> task) {
//                                    if (task.isSuccessful()) {
//                                        FirebaseUser user = mAuth.getCurrentUser();
//                                        Toast.makeText(Register.this, "Authentication Success."
//                                                , Toast.LENGTH_SHORT).show();
//                                    } else {
//                                        Toast.makeText(Register.this, "Authentication failed."
//                                                , Toast.LENGTH_SHORT).show();
//                                    }
//                                }
//                            });
//                }
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
    }
}
