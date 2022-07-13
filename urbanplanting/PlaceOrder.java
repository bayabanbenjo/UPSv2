package com.app.urbanplanting;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.opengl.ETC1;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;

public class PlaceOrder extends AppCompatActivity {
    private FirebaseDatabase rootnode;
    private DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    EditText name,address,phone;
    CheckBox cod;

    Button orderNow;


    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_place_order);
        getSupportActionBar().hide();

        Intent data = getIntent();

        rootnode = FirebaseDatabase.getInstance();


        firebaseAuth = FirebaseAuth.getInstance();
        reference = rootnode.getReference("OrdersRequest");

        name = findViewById(R.id.nameOrder);
        address = findViewById(R.id.addressOrder);
        phone = findViewById(R.id.phoneOrder);
        cod = findViewById(R.id.cashondelivery);
        orderNow = findViewById(R.id.ordernow);

        orderNow.setClickable(false);

        cod.setOnClickListener(view -> {
            if (cod.isChecked()){
                orderNow.setEnabled(true);
                orderNow.setClickable(true);
            }else {
                orderNow.setEnabled(false);
                orderNow.setClickable(false);
            }

        });

        orderNow.setOnClickListener(view -> {
            String Name = name.getText().toString();
            String Number = phone.getText().toString();
            String Address = address.getText().toString();

            String pImg=data.getStringExtra("plantImg");
            String pName=data.getStringExtra("plantName");
            String pPrice=data.getStringExtra("plantPrice");
            String tPrice=data.getStringExtra("totalPrice");
            String uID=data.getStringExtra("userID");

            if (TextUtils.isEmpty(Name)){
                name.setError("Required");
            }
            if (TextUtils.isEmpty(Number)){
                phone.setError("Required");
            }
            if (TextUtils.isEmpty(Address)){
                address.setError("Required");
            }

            if (!TextUtils.isEmpty(Name) && !TextUtils.isEmpty(Number) && !TextUtils.isEmpty(Address)){

                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String orderID = String.valueOf(timestamp.getTime());

                OrderModel orderModel = new OrderModel(pImg,pName,pPrice,tPrice,Name,Address,Number,orderID);
                reference.child(orderID).setValue(orderModel);

                Toast.makeText(this, "Order Success, kindly wait for your order", Toast.LENGTH_SHORT).show();
                goRemove(uID,pName);
            }


        });




    }

    private void goRemove(String uID, String pName) {
        reference = rootnode.getReference(uID+"/AddedPlants");
        reference.child(pName).removeValue();

        Intent intent = new Intent(PlaceOrder.this,MyPlants.class);
        startActivity(intent);
        finishAffinity();

    }
}