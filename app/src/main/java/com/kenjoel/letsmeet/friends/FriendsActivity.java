package com.kenjoel.letsmeet.friends;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kenjoel.letsmeet.R;
import com.kenjoel.letsmeet.models.cards;
import com.kenjoel.letsmeet.models.cardsObject;
import com.kenjoel.letsmeet.profile.ProfileActivity;
import com.kenjoel.letsmeet.settings.SettingsActivity;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class    FriendsActivity extends AppCompatActivity {
    private static final String TAG = "user keys are";
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String userId;

    @BindView(R.id.bottom_navigation) BottomNavigationView navigationView;
    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        ButterKnife.bind(this);
        navigationView.setSelectedItemId(R.id.friends);
        navigationView.setOnNavigationItemSelectedListener(navListener);

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("connections");
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.hasFixedSize();
//        fetch();
        getUserInfo();

    }

//    private void fetch(){
//        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                    getUserInfo(snapshot.getKey());
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    private void getUserInfo() {
        final DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users");
        Log.d(TAG, userDb.toString());

        FirebaseRecyclerOptions<cardsObject> options = new FirebaseRecyclerOptions.Builder<cardsObject>()
                .setQuery(userDb,cardsObject.class)
                .build();
        Log.i(TAG, "getUserInfoReturned:" + options.getSnapshots());

        final FirebaseRecyclerAdapter<cardsObject, cardsViewHolder> adapter =
        new FirebaseRecyclerAdapter<cardsObject, cardsViewHolder>(options) {
            @NonNull
            @Override
            public cardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                return new cardsViewHolder(v);
            }


            @Override
            protected void onBindViewHolder(@NonNull final cardsViewHolder holder, final int position, @NonNull cardsObject model) {
                final String key = getRef(position).getKey();

                userDb.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String name = "";
                        String phone = "";
                        String profileImageUrl = "";
                        String gender = "";

                        Log.i(TAG, snapshot.child(key).toString());

                        if(snapshot.child(key) != null){
                            gender = snapshot.child(key).child("gender").getValue().toString();
                            name = snapshot.child(key).child("name").getValue().toString();
                            phone = snapshot.child(key).child("phone").getValue().toString();
                            profileImageUrl = snapshot.child(key).child("profileImageUrl").getValue().toString();
                        }
                        holder.mNameView.setText(name);
                        holder.mNumberView.setText(phone);
                        Picasso.get().load(profileImageUrl).into(holder.mImageView);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        };
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }
    BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.profile:
                    Intent intent = new Intent(FriendsActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                    break;
                case R.id.friends:
                    Intent fintent = new Intent(FriendsActivity.this, SettingsActivity.class);
                    startActivity(fintent);
                    break;
                case R.id.settings:
                    Intent sintent = new Intent(FriendsActivity.this, SettingsActivity.class);
                    startActivity(sintent);
                    finish();
                    break;

//                case R.id.messages:
//                    selectedFragment = new message_fragment();
//                    break;
            }

            return true;
        }


    };

    public class cardsViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mNameView;
        public TextView mNumberView;
        public cardsViewHolder(@NonNull View itemView) {
            super(itemView);
             mImageView = itemView.findViewById(R.id.imageView);
             mNameView = itemView.findViewById(R.id.nameView);
             mNumberView = itemView.findViewById(R.id.numberView);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
    }

}
