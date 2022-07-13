package com.app.urbanplanting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class OrderRequest extends AppCompatActivity {
    RecyclerView recyclerView;
    private ArrayList<OrderModel> list;

    private OrderRequestAdapter myAdapter;

    private FirebaseDatabase rootnode;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_request);
        getSupportActionBar().hide();




        rootnode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        reference = rootnode.getReference("OrdersRequest");

        recyclerView = findViewById(R.id.order_request_rv);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new OrderRequestAdapter(this,list);

        recyclerView.setAdapter(myAdapter);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()){
                    Toast.makeText(OrderRequest.this, "", Toast.LENGTH_SHORT).show();
                }
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (snapshot.hasChildren()){
                        OrderModel orderModel = new OrderModel();

                        orderModel.setImageUrl(dataSnapshot.child("imageUrl").getValue().toString());
                        orderModel.setPlantName(dataSnapshot.child("plantName").getValue().toString());
                        orderModel.setPrice(dataSnapshot.child("price").getValue().toString());
                        orderModel.setTotalPrice(dataSnapshot.child("totalPrice").getValue().toString());
                        orderModel.setNameOfCust(dataSnapshot.child("nameOfCust").getValue().toString());
                        orderModel.setAddress(dataSnapshot.child("address").getValue().toString());
                        orderModel.setPhone(dataSnapshot.child("phone").getValue().toString());
                        orderModel.setOrderId(dataSnapshot.child("orderId").getValue().toString());


                        list.add(orderModel);

                    }else{
                        Toast.makeText(OrderRequest.this, "No Orders Yet", Toast.LENGTH_SHORT).show();
                    }

                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(OrderRequest.this,AdminPlants.class);
        startActivity(intent);
        finishAffinity();
    }
}