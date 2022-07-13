package com.app.urbanplanting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class Tips extends AppCompatActivity {

    LinearLayout ns,ps,vil,pr,port,drain,water,soil,grow,fun;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tips);
        getSupportActionBar().hide();

        ns = findViewById(R.id.nospace);
        ps = findViewById(R.id.plantselect);
        vil = findViewById(R.id.village);
        pr = findViewById(R.id.plantryt);

        port = findViewById(R.id.portable);
        drain = findViewById(R.id.drain);
        water = findViewById(R.id.water);
        soil = findViewById(R.id.soil);
        grow = findViewById(R.id.growup);
        fun = findViewById(R.id.havefun);
    }

    public void Back(View view) {
        onBackPressed();
    }

    public void NoSpace(View view) {
        if (ns.getVisibility() == View.GONE){
            ns.setVisibility(View.VISIBLE);
        }else{
            ns.setVisibility(View.GONE);
        }
    }

    public void PlantSelect(View view) {
        if (ps.getVisibility() == View.GONE){
            ps.setVisibility(View.VISIBLE);
        }else{
            ps.setVisibility(View.GONE);
        }
    }

    public void Village(View view) {
        if (vil.getVisibility() == View.GONE){
            vil.setVisibility(View.VISIBLE);
        }else{
            vil.setVisibility(View.GONE);
        }
    }

    public void PlantRight(View view) {
        if (pr.getVisibility() == View.GONE){
            pr.setVisibility(View.VISIBLE);
        }else{
            pr.setVisibility(View.GONE);
        }
    }

    public void Portable(View view) {
        if (port.getVisibility() == View.GONE){
            port.setVisibility(View.VISIBLE);
        }else{
            port.setVisibility(View.GONE);
        }
    }

    public void Drain(View view) {
        if (drain.getVisibility() == View.GONE){
            drain.setVisibility(View.VISIBLE);
        }else{
            drain.setVisibility(View.GONE);
        }
    }

    public void Water(View view) {
        if (water.getVisibility() == View.GONE){
            water.setVisibility(View.VISIBLE);
        }else{
            water.setVisibility(View.GONE);
        }
    }

    public void Soil(View view) {
        if (soil.getVisibility() == View.GONE){
            soil.setVisibility(View.VISIBLE);
        }else{
            soil.setVisibility(View.GONE);
        }
    }

    public void GrowUp(View view) {
        if (grow.getVisibility() == View.GONE){
            grow.setVisibility(View.VISIBLE);
        }else{
            grow.setVisibility(View.GONE);
        }
    }

    public void Fun(View view) {
        if (fun.getVisibility() == View.GONE){
            fun.setVisibility(View.VISIBLE);
        }else{
            fun.setVisibility(View.GONE);
        }
    }
}