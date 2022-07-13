package com.app.urbanplanting;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.app.urbanplanting.R;

public class RegisterActivity extends AppCompatActivity {

    FirebaseAuth fAuth;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    ProgressBar loading;

    EditText fnameEt,emailEt,phoneEt,passwordEt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
    }

    public void RegisterClicked(View view) {
        loading = findViewById(R.id.progress_bar);
        loading.setVisibility(View.VISIBLE);
        fnameEt = findViewById(R.id.register_name);
        emailEt = findViewById(R.id.register_email);
        phoneEt = findViewById(R.id.register_phone);
        passwordEt = findViewById(R.id.register_password);

        fAuth = FirebaseAuth.getInstance();

        String name = fnameEt.getText().toString();
        String email = emailEt.getText().toString();
        String phone = phoneEt.getText().toString();
        String pass = passwordEt.getText().toString();

        if (TextUtils.isEmpty(name)){
            fnameEt.setError("Name is Required");
            loading.setVisibility(View.GONE);
            return;
        }else if (TextUtils.isEmpty(email)){
            emailEt.setError("Email is Required");
            loading.setVisibility(View.GONE);
            return;
        }else if (TextUtils.isEmpty(phone)){
            phoneEt.setError("Phone is Required");
            loading.setVisibility(View.GONE);
        }else if (phone.length() >11){
            phoneEt.setError("Invalid Phone Number");
            loading.setVisibility(View.GONE);
            return;
        }else if (TextUtils.isEmpty(pass)){
            passwordEt.setError("Password is Required");
            loading.setVisibility(View.GONE);
            return;
        } else if (pass.length() < 6){
            passwordEt.setError("Too short Password!");
            loading.setVisibility(View.GONE);
            return;
        }else if (!TextUtils.isEmpty(name) && !TextUtils.isEmpty(email) && !TextUtils.isEmpty(phone) &&!TextUtils.isEmpty(pass)){

            fAuth.createUserWithEmailAndPassword(email,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if (task.isSuccessful()){
                        Toast.makeText( RegisterActivity.this,"Registration is Successful",Toast.LENGTH_SHORT).show();
                        rootNode = FirebaseDatabase.getInstance();
                        reference = rootNode.getReference("users");

                        String userId = fAuth.getCurrentUser().getUid();

                           UserModel userModel = new UserModel(name,email,phone);

                            reference.child(userId).setValue(userModel);
                        loading.setVisibility(View.GONE);
                        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                    }else{
                        Toast.makeText( RegisterActivity.this,"Error Occurred"+task.getException().getMessage(),Toast.LENGTH_SHORT).show();
                        loading.setVisibility(View.GONE);

                    }

                }
            });

        }


    }

    public void LoginHere(View view) {
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        finishAffinity();
    }

}