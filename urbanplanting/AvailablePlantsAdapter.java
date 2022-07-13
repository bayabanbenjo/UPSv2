package com.app.urbanplanting;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;



public class AvailablePlantsAdapter extends RecyclerView.Adapter<AvailablePlantsAdapter.MyViewHolder> implements Filterable {
    private ArrayList<PlantsModel> mList;
    ArrayList<PlantsModel> mlistFull;
    Context context;

    private FirebaseDatabase rootnode;
    private DatabaseReference reference;
    FirebaseAuth firebaseAuth;

    Dialog dialog;

    public AvailablePlantsAdapter(Context context, ArrayList<PlantsModel> mlist) {
        this.context = context;
        this.mlistFull= mlist;
        this.mList = new ArrayList<>(mlistFull);
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

        holder.plantClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog = new Dialog(context);

                dialog.setContentView(R.layout.popup_plants);
               // dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                ImageView plantImage = dialog.findViewById(R.id.plant_image_popup);
                TextView priceTv = dialog.findViewById(R.id.price_of_plant_popup);
                TextView description = dialog.findViewById(R.id.desc_popup);
                TextView watering = dialog.findViewById(R.id.water);
                TextView sunLight = dialog.findViewById(R.id.sunlight);
                TextView temp = dialog.findViewById(R.id.temperature);
                Button add = dialog.findViewById(R.id.add_plant_btn);




                Glide.with(context).load(plantsModel.getImageUrl()).into(plantImage);
                priceTv.setText(plantsModel.getPrice());
                description.setText(plantsModel.getDescription());
                watering.setText(plantsModel.getWater());
                sunLight.setText(plantsModel.getSunlight());
                temp.setText(plantsModel.getTemperature());



                add.setOnClickListener(v -> {

                    AlertDialog.Builder alert = new AlertDialog.Builder(v.getContext());

                    rootnode = FirebaseDatabase.getInstance();
                    firebaseAuth = FirebaseAuth.getInstance();
                    String userId = firebaseAuth.getCurrentUser().getUid();
                    reference = rootnode.getReference(userId+"/AddedPlants");

                    String plantImg = plantsModel.getImageUrl();
                    String plantPrice = plantsModel.getPrice();
                    String plantDesc = plantsModel.getDescription();
                    String platWater = plantsModel.getWater();
                    String plantSunLight = plantsModel.getSunlight();
                    String plantTemp = plantsModel.getTemperature();
                    String plantName = plantsModel.getPlantName();

                    final EditText edittext = new EditText(v.getContext());
                  //  alert.setMessage("Enter Your Message");
                    alert.setTitle("Quantity");

                    alert.setView(edittext);

                    alert.setPositiveButton("Request Order", (dialog, whichButton) -> {
                        String quantity = edittext.getText().toString();
                        edittext.setInputType(InputType.TYPE_CLASS_NUMBER);
                        try {
                            Integer.parseInt(quantity);
                            int totalPrice = Integer.parseInt(plantPrice) * Integer.parseInt(quantity);
                            OrderPlantsModel addPlants = new OrderPlantsModel(plantImg,plantName,plantPrice,String.valueOf(totalPrice),plantDesc,platWater,plantSunLight,plantTemp);
                            reference.child(plantName).setValue(addPlants);

                            Toast.makeText(context, "Successfully Added", Toast.LENGTH_SHORT).show();
                        } catch (Exception e) {
                            Toast.makeText(context, "Invalid Quantity", Toast.LENGTH_SHORT).show();
                            return;
                        }


                    });

                    alert.setNegativeButton("Cancel", (dialog, whichButton) -> {
                        // what ever you want to do with No option.
                    });

                    alert.show();



                    dialog.dismiss();
                });

                dialog.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }
    private final Filter filter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            ArrayList<PlantsModel> filteredList = new ArrayList<>();

            if (charSequence == null || charSequence.length() == 0){
                filteredList.addAll(mlistFull);
            }else {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (PlantsModel plantsModel : mlistFull){
                    if (plantsModel.getPlantName().toLowerCase().contains(filterPattern)){
                        filteredList.add(plantsModel);
                    }
                }
            }

            FilterResults filterResults = new FilterResults();
            filterResults.values = filteredList;
            filterResults.count = filteredList.size();

            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            mList.clear();
            mList.addAll((ArrayList)filterResults.values);
            notifyDataSetChanged();

        }
    };


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
