package com.app.urbanplanting;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
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

public class OrderRequestAdapter extends RecyclerView.Adapter<OrderRequestAdapter.MyViewHolder> {

    private ArrayList<OrderModel> mList;
    Context context;

    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;



    Dialog dialog;

    public OrderRequestAdapter(Context context, ArrayList<OrderModel> mlist) {
        this.mList = mlist;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.item_order_request, parent,false);
        return new MyViewHolder(v);
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();

        OrderModel orderModel = mList.get(position);
        String pPrice = orderModel.getPrice();
        String tPrice = orderModel.getTotalPrice();
        int quantity = Integer.parseInt(tPrice) / Integer.parseInt(pPrice);
        holder.price.setText(pPrice+" x "+quantity+"  Total: "+ tPrice);
        holder.pname.setText(orderModel.getPlantName());
        holder.namecust.setText(orderModel.getNameOfCust());
        holder.address.setText(orderModel.getAddress());
        holder.phone.setText(orderModel.getPhone());
        Glide.with(context).load(orderModel.getImageUrl()).into(holder.plantImg);



        holder.delivered.setOnClickListener(view -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Item Delivered");
            builder.setMessage("Are you sure that this order is received?");
            builder.setPositiveButton("YES", (dialog, which) -> {
                databaseReference = firebaseDatabase.getReference("OrdersRequest");
                databaseReference.child(orderModel.getOrderId()).removeValue();
                Intent intent = new Intent(context.getApplicationContext(),OrderRequest.class);
                context.startActivity(intent);
                ((Activity)context).finishAffinity();

            });
            builder.setNegativeButton("NO", (dialog, which) -> {
                dialog.dismiss();
            });
            builder.create();
            builder.show();

        });




    }

    @Override
    public int getItemCount() {
        return mList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView plantImg;
        TextView pname, price,delivered,namecust,address,phone;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            plantImg = itemView.findViewById(R.id.order_plant_img);
            pname = itemView.findViewById(R.id.name_of_order_plant);
            price = itemView.findViewById(R.id.price_of_order_plant);
            delivered = itemView.findViewById(R.id.delivered);
            namecust = itemView.findViewById(R.id.name_of_cust);
            address = itemView.findViewById(R.id.address_of_cust);
            phone = itemView.findViewById(R.id.phone_of_cust);

        }
    }
}
