package com.example.easy_transport;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Search extends AppCompatActivity {

    RecyclerView recyclerView;
    EditText from,dest;
    Button search;
    DatabaseReference ref;
    ArrayList<Create_route_users> data;
    Custom_adapter_search adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        getSupportActionBar().hide();

        from = findViewById(R.id.edsefrom);
        dest = findViewById(R.id.edsedest);
        search = findViewById(R.id.btnsesearch);

        recyclerView = (RecyclerView) findViewById(R.id.recyclesearch);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ref = FirebaseDatabase.getInstance().getReference("Common Routes");

        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ref.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        data = new ArrayList<>();
                        if (snapshot.exists()){
                            for (DataSnapshot ds:snapshot.getChildren()){
                                Create_route_users user = ds.getValue(Create_route_users.class);
                                if (user.getFrom().equals(from.getText().toString()) && user.getDest().equals(dest.getText().toString())){
                                    data.add(ds.getValue(Create_route_users.class));
                                }
                            }
                            adapter = new Custom_adapter_search(data,getApplicationContext());
                            recyclerView.setAdapter(adapter);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Search.this,error.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}
