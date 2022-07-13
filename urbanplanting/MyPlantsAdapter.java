package com.app.urbanplanting;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

import com.app.urbanplanting.R;

public class MyPlantsAdapter extends RecyclerView.Adapter<MyPlantsAdapter.MyViewHolder> {

    private ArrayList<MyPlantsModel> mList;
    Context context;

    private FirebaseAuth firebaseAuth;



    Dialog dialog;

    public MyPlantsAdapter(Context context, ArrayList<MyPlantsModel> mlist) {
        this.mList = mlist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_my_plant, parent,false);
        return new MyViewHolder(v);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        firebaseAuth = FirebaseAuth.getInstance();
        String userID = firebaseAuth.getCurrentUser().getUid();

        MyPlantsModel myPlantsModel = mList.get(position);
        String pPrice = myPlantsModel.getPrice();
        String tPrice = myPlantsModel.getQuantPrice();
        int quantity = Integer.parseInt(tPrice) / Integer.parseInt(pPrice);
        holder.price.setText(pPrice+" x "+quantity+"  Total: "+ tPrice);
        holder.name.setText(myPlantsModel.getPlantName());
        holder.desc.setText(myPlantsModel.getDescription());
        Glide.with(context).load(myPlantsModel.getImageUrl()).into(holder.plantImg);

        holder.myScroll.setOnTouchListener((view, motionEvent) -> {

            view.getParent().requestDisallowInterceptTouchEvent(true);
            view.onTouchEvent(motionEvent);
            return true;
        });

        holder.checkout.setOnClickListener(view -> {
            Intent intent = new Intent(view.getContext(),PlaceOrder.class);
            intent.putExtra("plantName", myPlantsModel.getPlantName());
            intent.putExtra("plantPrice", myPlantsModel.getPrice());
            intent.putExtra("totalPrice", myPlantsModel.getQuantPrice());
            intent.putExtra("plantImg", myPlantsModel.getImageUrl());
            intent.putExtra("userID",userID);
            view.getContext().startActivity(intent);

        });




    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView plantImg;
        TextView name, price,desc,checkout;
        ScrollView myScroll;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            plantImg = itemView.findViewById(R.id.my_plant_img);
            name = itemView.findViewById(R.id.name_of_my_plant);
            price = itemView.findViewById(R.id.price_of_my_plant);
            desc = itemView.findViewById(R.id.desc_my_plant);
            myScroll = (ScrollView) itemView.findViewById(R.id.scroll);
            checkout = itemView.findViewById(R.id.checkout);

        }
    }
}
