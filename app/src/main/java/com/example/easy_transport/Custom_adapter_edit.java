package com.example.easy_transport;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Custom_adapter_edit extends RecyclerView.Adapter<Custom_adapter_edit.customviewholder>{

    ArrayList<Create_route_users> data;
    Context context;

    public Custom_adapter_edit(ArrayList<Create_route_users> data, Context context) {
        this.data = data;
        this.context = context;
    }

    @NonNull
    @Override
    public customviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_edit_layout,parent,false);
        return new Custom_adapter_edit.customviewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull customviewholder holder, final int position) {
        holder.date.setText("Date : "+data.get(position).getDate());
        holder.route.setText("Route : "+data.get(position).getFrom()+" to "+data.get(position).getDest());

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.route.getContext())
                        .setContentHolder(new ViewHolder(R.layout.cus_edit))
                        .setExpanded(true,1110)
                        .create();

                View myview = dialogPlus.getHolderView();
                EditText de = myview.findViewById(R.id.txtcuseddest);
                EditText da = myview.findViewById(R.id.txtcuseddate);
                EditText sp = myview.findViewById(R.id.txtcusedspace);
                EditText veh = myview.findViewById(R.id.txtcusedvehicle);
                Button up = myview.findViewById(R.id.btncusededit);

                de.setText(data.get(position).getDest());
                da.setText(data.get(position).getDate());
                sp.setText(data.get(position).getSpace());
                veh.setText(data.get(position).getVehicle());

                dialogPlus.show();

                up.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("dest",de.getText().toString());
                        map.put("date",da.getText().toString());
                        map.put("space",sp.getText().toString());
                        map.put("vehicle",veh.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Routes").child(data.get(position).getContact()).child(data.get(position).getRouteid()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {
                                        FirebaseDatabase.getInstance().getReference("Common Routes").child(data.get(position).getRouteid()).updateChildren(map);
                                        dialogPlus.dismiss();
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                dialogPlus.dismiss();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    class customviewholder extends RecyclerView.ViewHolder{
        TextView date,route;
        Button edit;

        public customviewholder(@NonNull View itemView) {
            super(itemView);

            date = itemView.findViewById(R.id.txteddate);
            route = itemView.findViewById(R.id.txtedroute);
            edit = itemView.findViewById(R.id.btnededit);
        }
    }
}
