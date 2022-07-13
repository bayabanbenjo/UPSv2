package com.app.urbanplanting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

import com.app.urbanplanting.R;

public class MyPlants extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<MyPlantsModel> list;

    private MyPlantsAdapter myAdapter;

    private FirebaseDatabase rootnode;
    private DatabaseReference reference;
    private FirebaseAuth firebaseAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_plants);
        getSupportActionBar().hide();

        rootnode = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        String userId = firebaseAuth.getCurrentUser().getUid();
        reference = rootnode.getReference(userId+"/AddedPlants");

        recyclerView = findViewById(R.id.datalist_myplants);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        list = new ArrayList<>();
        myAdapter = new MyPlantsAdapter(this,list);

        recyclerView.setAdapter(myAdapter);

        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (snapshot.hasChildren()){
                        MyPlantsModel myPlantsModel = new MyPlantsModel();

                        myPlantsModel.setImageUrl(dataSnapshot.child("imageUrl").getValue().toString());
                        myPlantsModel.setPlantName(dataSnapshot.child("plantName").getValue().toString());
                        myPlantsModel.setDescription(dataSnapshot.child("description").getValue().toString());
                        myPlantsModel.setPrice(dataSnapshot.child("price").getValue().toString());
                        myPlantsModel.setQuantPrice(dataSnapshot.child("quantPrice").getValue().toString());
                        myPlantsModel.setSunlight(dataSnapshot.child("sunlight").getValue().toString());
                        myPlantsModel.setWater(dataSnapshot.child("water").getValue().toString());
                        myPlantsModel.setTemperature(dataSnapshot.child("temperature").getValue().toString());


                        list.add(myPlantsModel);

                    }else{
                        Toast.makeText(MyPlants.this, "No Plants Added Yet\nGo to Available Plants now!!", Toast.LENGTH_SHORT).show();
                    }

                }
                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void Back(View view) {
        Intent intent = new Intent(MyPlants.this,WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    public void Home(View view) {

        Intent intent = new Intent(MyPlants.this,WelcomeActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MyPlants.this,WelcomeActivity.class);
        startActivity(intent);
        finish();
    }
}