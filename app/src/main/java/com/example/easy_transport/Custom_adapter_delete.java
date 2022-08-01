package com.example.easy_transport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

public class Custom_adapter_delete extends RecyclerView.Adapter<Custom_adapter_delete.customviewholder>{

    ArrayList<Create_route_users> data;
    Context context;

    public Custom_adapter_delete(ArrayList<Create_route_users> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public customviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_delete_layout,parent,false);
        return new customviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customviewholder holder, int position) {

        holder.date.setText("Date : "+data.get(position).getDate());
        holder.route.setText("Route : "+data.get(position).getFrom()+" to "+data.get(position).getDest());

        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseDatabase.getInstance().getReference().child("Routes").child(data.get(position).getContact()).child(data.get(position).getRouteid()).removeValue();
                FirebaseDatabase.getInstance().getReference().child("Common Routes").child(data.get(position).getRouteid()).removeValue();
            }
        });

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class customviewholder extends RecyclerView.ViewHolder {

        TextView date,route;
        Button delete;

        public customviewholder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.txtdedate);
            route = itemView.findViewById(R.id.txtderoute);
            delete = itemView.findViewById(R.id.btndedelete);

        }
    }
}
