package com.app.urbanplanting;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.facebook.login.LoginManager;
import com.google.firebase.auth.FirebaseAuth;

import com.app.urbanplanting.R;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import static android.content.ContentValues.TAG;

public class WelcomeActivity extends AppCompatActivity {

    TextView nameUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        getSupportActionBar().hide();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String id = FirebaseAuth.getInstance().getCurrentUser().getUid();


        String username = user.getDisplayName();
        nameUser = findViewById(R.id.nameUserDisplay);

        if(TextUtils.isEmpty(username) || username == ""){
            goToUser(id);
        }else{
            nameUser.setText(username);
        }
    }

    private void goToUser(String id) {
        DatabaseReference sRef = FirebaseDatabase.getInstance().getReference("users");
        DatabaseReference sIdRef = sRef.child(id);
        DatabaseReference nameRef = sIdRef.child("fullname");

        nameRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!dataSnapshot.exists()){
                    return;
                }
                String sName = dataSnapshot.getValue().toString();
                if (!TextUtils.isEmpty(sName)){
                    nameUser.setVisibility(View.VISIBLE);
                    nameUser.setText(sName);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.w(TAG, "onCancelled", databaseError.toException());
            }
        });
    }

    public void AvailablePlants(View view) {
        Intent intent = new Intent(WelcomeActivity.this, AvailablePlants.class);
        startActivity(intent);
    }

    public void MyPlants(View view) {
        Intent intent = new Intent(WelcomeActivity.this, MyPlants.class);
        startActivity(intent);
    }

    public void About(View view) {
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set title
        builder.setTitle("About us");
        //set message
        builder.setMessage("The Urban Planting Solution is a unique application that can help user's to plant or to make a garden in the urban area using recycled plastic bottles or any reusable thing\n\n" +
                "Team: Assorted\nJorge C. Arogar\nBenjo O. Bayaban\nKhyle Vincent Dalisay\nSheila Mae Nastor");
        //negative no
        builder.setNegativeButton("OK", (dialog, which) -> {
            //dismiss dialog
            dialog.dismiss();
        });
        builder.show();
    }

    public void Logout(View view) {
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set title
        builder.setTitle("Exit");
        //set message
        builder.setMessage("Are you sure you want to logout?");
        //positive yes
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish activity
                FirebaseAuth.getInstance().signOut();
                LoginManager.getInstance().logOut();
                Intent intent = new Intent(WelcomeActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        //negative no
        builder.setNegativeButton("NO", (dialog, which) -> {
            //dismiss dialog
            dialog.dismiss();
        });
        builder.show();
    }

    @Override
    public void onBackPressed() {
        //alert dialog
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        //set title
        builder.setTitle("Exit");
        //set message
        builder.setMessage("Are you sure you want to Exit?");
        //positive yes
        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                //finish activity
                Intent exit = new Intent(Intent.ACTION_MAIN);
                exit.addCategory(Intent.CATEGORY_HOME);
                exit.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(exit);
                finishAffinity();
                System.exit(0);
            }
        });
        //negative no
        builder.setNegativeButton("NO", (dialog, which) -> {
            //dismiss dialog
            dialog.dismiss();
        });
        builder.show();
    }

    public void Tips(View view) {
        Intent intent = new Intent(WelcomeActivity.this,Tips.class);
        startActivity(intent);
    }
}