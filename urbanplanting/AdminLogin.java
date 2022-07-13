package com.app.urbanplanting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class AdminLogin extends AppCompatActivity {
    EditText username, password;
    ProgressBar pBar;
    FirebaseAuth fAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_login);
        getSupportActionBar().hide();
    }

    public void AdminLoginClicked(View view) {
        pBar = findViewById(R.id.admin_pb);
        pBar.setVisibility(View.VISIBLE);
        username = findViewById(R.id.admin_login_email);
        password = findViewById(R.id.admin_login_password);
        fAuth = FirebaseAuth.getInstance();

        String userLogin = username.getText().toString();
        String passLogin = password.getText().toString();

        if (TextUtils.isEmpty(userLogin)) {
            username.setError("username is Required");
            pBar.setVisibility(View.GONE);
            return;
        } else if (TextUtils.isEmpty(passLogin)) {
            password.setError("Password is Required");
            pBar.setVisibility(View.GONE);
            return;
        } else if(!TextUtils.isEmpty(userLogin) && !TextUtils.isEmpty(passLogin)) {
            String emailLogin = userLogin+"@admin.com";
            fAuth.signInWithEmailAndPassword(emailLogin,passLogin).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {

                        Toast.makeText( AdminLogin.this, "Admin Logged In", Toast.LENGTH_SHORT).show();
                        pBar.setVisibility(View.GONE);
                        Intent intent = new Intent(getApplicationContext(),AdminPlants.class);
                        startActivity(intent);
                        finishAffinity();
                    } else {
                        Toast.makeText( AdminLogin.this, "Login Failed" + task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        pBar.setVisibility(View.GONE);

                    }

                }
            });


        }


    }
}