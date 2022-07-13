package com.app.urbanplanting;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;


public class AvailablePlants extends AppCompatActivity {

    RecyclerView recyclerView;
    private ArrayList<PlantsModel> list;

    private AvailablePlantsAdapter myAdapter;

    private FirebaseDatabase rootnode;
    private DatabaseReference reference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_available_plants);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#519259")));

        setTitleColor(Color.parseColor("#ffffff"));
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_back);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Available Plants");

        rootnode = FirebaseDatabase.getInstance();
        reference = rootnode.getReference("ListedPlants");

        recyclerView = findViewById(R.id.datalist);
        recyclerView.setHasFixedSize(true);

        list = new ArrayList<>();


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


                        list.add(plantsModel);

                    }else{
                        Toast.makeText(AvailablePlants.this, "No Available Plants As Of Now", Toast.LENGTH_SHORT).show();
                    }

                }
                myAdapter = new AvailablePlantsAdapter(AvailablePlants.this,list);
                recyclerView.setAdapter(myAdapter);

                myAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                 onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    public void search(View view) {
        openOptionsMenu();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu,menu);

        MenuItem item = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) item.getActionView();
        searchView.setQueryHint("Search Plants...");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                myAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    public void Back(View view) {
        super.onBackPressed();
    }

    public void Home(View view) {
        super.onBackPressed();
    }
    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AvailablePlants.this, WelcomeActivity.class);
        startActivity(intent);
        finishAffinity();

        super.onBackPressed();
    }
}