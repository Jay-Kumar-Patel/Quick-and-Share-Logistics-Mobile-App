package com.example.easy_transport;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class Delete extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText contact;
    Button search;
    DatabaseReference ref;
    ArrayList<Create_route_users> data;
    Custom_adapter_delete adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        getSupportActionBar().hide();
        contact = findViewById(R.id.eddecontact);
        search = findViewById(R.id.btndesearch);

        recyclerView = (RecyclerView) findViewById(R.id.recycledelete);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref = FirebaseDatabase.getInstance().getReference("Routes").child(contact.getText().toString());
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        data = new ArrayList<>();
                        if (snapshot.exists()){
                            for (DataSnapshot ds:snapshot.getChildren()){
                                data.add(ds.getValue(Create_route_users.class));
                            }
                            adapter = new Custom_adapter_delete(data,getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Delete.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

    }
}