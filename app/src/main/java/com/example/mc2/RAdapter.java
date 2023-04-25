package com.example.mc2;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RAdapter extends RecyclerView.Adapter<RAdapter.MyViewHolder>{

    private Context context;
    private ArrayList namev, agev, genderv;
    Button btnpic;
    ImageView frame;



    public RAdapter(Context context, ArrayList namev, ArrayList agev, ArrayList genderv) {
        this.context = context;
        this.namev = namev;
        this.agev = agev;
        this.genderv = genderv;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View v= LayoutInflater.from(context).inflate(R.layout.listentrycard,parent,false);
        return new MyViewHolder(v);


    }

    @Override
    public void onBindViewHolder(@NonNull RAdapter.MyViewHolder holder, int position) {

        holder.namev.setText(String.valueOf(namev.get(position)));
        holder.agev.setText(String.valueOf(agev.get(position)));
        holder.genderv.setText(String.valueOf(genderv.get(position)));

    }

    @Override
    public int getItemCount() {
        return namev.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView namev, agev,genderv;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            namev = itemView.findViewById(R.id.listname);
            agev = itemView.findViewById(R.id.listage);
            genderv = itemView.findViewById(R.id.listgender);
           // btnpic = itemView.findViewById(R.id.pic);


            };
        }
    }

