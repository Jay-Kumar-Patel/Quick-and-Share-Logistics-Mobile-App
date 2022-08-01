package com.example.easy_transport;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Custom_adapter_search extends RecyclerView.Adapter<Custom_adapter_search.customviewholder> {

    ArrayList<Create_route_users> data;
    Context context;

    public Custom_adapter_search(ArrayList<Create_route_users> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public customviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_create_layout,parent,false);
        return new customviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customviewholder holder, int position) {

        final Create_route_users temp = data.get(position);

        holder.date.setText("Date : "+data.get(position).getDate());
        holder.contact.setText("Contact No. : "+data.get(position).getContact());
        holder.space.setText("Available space : "+data.get(position).getSpace());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context,search_next.class);
                intent.putExtra("contact",temp.getContact());
                intent.putExtra("vehicle",temp.getVehicle());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class customviewholder extends RecyclerView.ViewHolder{

        TextView date,space,contact;

        public customviewholder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.txtcrdate);
            space = itemView.findViewById(R.id.txtcrspace);
            contact = itemView.findViewById(R.id.txtcrcontact);
        }
    }
}
