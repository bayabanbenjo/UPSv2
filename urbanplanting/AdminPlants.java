package com.app.urbanplanting;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
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

public class AdminPlants extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<PlantsModel> mlist;

    private AdminPlantsAdapter myAdapter;

    private FirebaseDatabase rootnode;
    private DatabaseReference reference;
    Button logout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_plants);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#519259")));

        setTitleColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setTitle("Listed Plants");

        logout = findViewById(R.id.adminLogout);

        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("ListedPlants");

        recyclerView = findViewById(R.id.datalist);
        recyclerView.setHasFixedSize(true);

        mlist = new ArrayList<>();


        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2,GridLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(gridLayoutManager);




        reference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    if (snapshot.hasChildren()){
                        PlantsModel plantsModel = new PlantsModel();

                        plantsModel.setImageUrl(dataSnapshot.child("imageUrl").getValue().toString());
                        plantsModel.setPlantName(dataSnapshot.child("plantName").getValue().toString());
                        plantsModel.setDescription(dataSnapshot.child("description").getValue().toString());
                        plantsModel.setPrice(dataSnapshot.child("price").getValue().toString());
                        plantsModel.setSunlight(dataSnapshot.child("sunlight").getValue().toString());
                        plantsModel.setWater(dataSnapshot.child("water").getValue().toString());
                        plantsModel.setTemperature(dataSnapshot.child("temperature").getValue().toString());
                        plantsModel.setKey(dataSnapshot.child("key").getValue().toString());


                        mlist.add(plantsModel);

                    }else{
                        Toast.makeText(AdminPlants.this, "No Available Plants As Of Now", Toast.LENGTH_SHORT).show();
                    }

                }
                myAdapter = new AdminPlantsAdapter(mlist, AdminPlants.this);
                recyclerView.setAdapter(myAdapter);

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        logout.setOnClickListener(view -> {
            FirebaseAuth.getInstance().signOut();
            Intent intent = new Intent(AdminPlants.this, LoginActivity.class);
            startActivity(intent);
            finish();
        });



    }

    public void ViewRequest(View view) {
            Intent intent = new Intent(AdminPlants.this, OrderRequest.class);
            startActivity(intent);
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AdminPlants.this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    public void Add(View view) {
        Intent intent = new Intent(AdminPlants.this, AddPlant.class);
        startActivity(intent);
    }
}