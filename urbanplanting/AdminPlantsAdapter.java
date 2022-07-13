package com.app.urbanplanting;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.sql.Timestamp;
import java.util.ArrayList;

public class AdminPlantsAdapter extends RecyclerView.Adapter<AdminPlantsAdapter.MyViewHolder>{
    private ArrayList<PlantsModel> mList;
    Context context;

    private FirebaseDatabase rootnode;
    private DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    Dialog dialog;

    public AdminPlantsAdapter(ArrayList<PlantsModel> mList, Context context) {
        this.mList = mList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_plant, parent,false);
        return new MyViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        PlantsModel plantsModel = mList.get(position);
        holder.price.setText(plantsModel.getPrice());
        holder.name.setText(plantsModel.getPlantName());
        Glide.with(context).load(plantsModel.getImageUrl()).into(holder.plantImg);

        holder.plantClick.setOnClickListener(view -> {
            dialog = new Dialog(context);

            dialog.setContentView(R.layout.popup_admin_plants);
            // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

            ImageView plantImage = dialog.findViewById(R.id.plant_image_popup);
            EditText priceTv = dialog.findViewById(R.id.price_of_plant_popup);
            EditText names = dialog.findViewById(R.id.names);
            EditText description = dialog.findViewById(R.id.desc_popup);
            EditText watering = dialog.findViewById(R.id.water);
            EditText sunLight = dialog.findViewById(R.id.sunlight);
            EditText temp = dialog.findViewById(R.id.temperature);
            Button remove = dialog.findViewById(R.id.remove);
            Button update = dialog.findViewById(R.id.update);




            Glide.with(context).load(plantsModel.getImageUrl()).into(plantImage);
            priceTv.setText(plantsModel.getPrice());
            description.setText(plantsModel.getDescription());
            watering.setText(plantsModel.getWater());
            sunLight.setText(plantsModel.getSunlight());
            temp.setText(plantsModel.getTemperature());
            names.setText(plantsModel.getPlantName());

         remove.setOnClickListener(v->{
                rootnode = FirebaseDatabase.getInstance();
                firebaseAuth = FirebaseAuth.getInstance();
                reference = rootnode.getReference("ListedPlants");

                reference.child(plantsModel.getKey()).removeValue();
                Intent intent = new Intent(v.getContext(),AdminPlants.class);
                context.startActivity(intent);
                ((Activity) context).finishAffinity();

           });



            update.setOnClickListener(v -> {
               // AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());

                rootnode = FirebaseDatabase.getInstance();
                firebaseAuth = FirebaseAuth.getInstance();
                reference = rootnode.getReference("ListedPlants");

                String plantImg = plantsModel.getImageUrl();
                String plantPrice = priceTv.getText().toString();
                String plantDesc = description.getText().toString();
                String platWater = watering.getText().toString();
                String plantSunLight =sunLight.getText().toString();
                String plantTemp = temp.getText().toString();
                String plantName = names.getText().toString();
                String keyPlant = plantsModel.getKey();

               // Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                //String orderID = String.valueOf(timestamp.getTime());


                PlantsModel plantsModel1 = new PlantsModel(plantImg,plantName,plantPrice,plantDesc,platWater,plantSunLight,plantTemp,keyPlant);
                Intent intent = new Intent(v.getContext(),AdminPlants.class);
                context.startActivity(intent);
                ((Activity) context).finishAffinity();
                reference.child(keyPlant).setValue(plantsModel1);

                dialog.dismiss();
            });

            dialog.show();
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView plantImg;
        TextView name, price;
        LinearLayout plantClick;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            plantImg = itemView.findViewById(R.id.available_plant_img);
            name = itemView.findViewById(R.id.name_of_plant);
            price = itemView.findViewById(R.id.price_of_plant);
            plantClick = itemView.findViewById(R.id.view_plant);

        }
    }
}
