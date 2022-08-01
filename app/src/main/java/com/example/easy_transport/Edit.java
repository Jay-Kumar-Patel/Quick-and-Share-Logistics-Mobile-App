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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;

import java.util.ArrayList;

public class Edit extends AppCompatActivity {

    EditText contact;
    Button search;
    RecyclerView recyclerView;
    DatabaseReference ref;
    ArrayList<Create_route_users> data;
    Custom_adapter_edit adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);

        getSupportActionBar().hide();

        contact = findViewById(R.id.ededcontact);
        search = findViewById(R.id.btnedsearch);

        recyclerView = (RecyclerView) findViewById(R.id.recycleedit);
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
                            adapter = new Custom_adapter_edit(data,getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Edit.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}