package com.kenjoel.letsmeet.feed;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kenjoel.letsmeet.profile.ProfileActivity;
import com.kenjoel.letsmeet.R;
import com.kenjoel.letsmeet.settings.SettingsActivity;
import com.kenjoel.letsmeet.adapters.CardsAdapter;
import com.kenjoel.letsmeet.models.cards;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;


import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FeedActivity extends AppCompatActivity {

    private static final String TAG = "the Key" ;
    private CardsAdapter arrayAdapter;
    private int i;

    private String currentUserId;
    private DatabaseReference Users;

    private FirebaseAuth mAuth;

    private static String userSex;
    private String oppositeSex;



    List<cards>rowItems;

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        ButterKnife.bind(this);
        navigationView.setSelectedItemId(R.id.feed);
        navigationView.setOnNavigationItemSelectedListener(navListener);
        Users = FirebaseDatabase.getInstance().getReference().child("Users");
        getGender();

        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getUid();

        rowItems = new ArrayList<>();
        arrayAdapter = new CardsAdapter(this, R.layout.item, rowItems);

        SwipeFlingAdapterView flingContainer = (SwipeFlingAdapterView) findViewById(R.id.frame);


        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                Users.child(userId).child("connections").child("Nada").child(currentUserId).setValue(true);
                Toast.makeText(FeedActivity.this, "Nope", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                Users.child(userId).child("connections").child("Yes").child(currentUserId).setValue(true);
                Toast.makeText(FeedActivity.this, "Yeah", Toast.LENGTH_SHORT).show();
                matchMet(userId);
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });


        // Optionally add an OnItemClickListener
        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(FeedActivity.this, "clicked", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void matchMet(String userId) {
        DatabaseReference currentuser = Users.child(currentUserId).child("connections").child("Yes").child(userId);
        currentuser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    Toast.makeText(FeedActivity.this, "User Saved", Toast.LENGTH_LONG).show();
                    Users.child(snapshot.getKey()).child("connections").child("saved").child(currentUserId).setValue(true);
                    Users.child(currentUserId).child("connections").child("saved").child(snapshot.getKey()).setValue(true);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.profile:
                    Intent intent = new Intent(FeedActivity.this, ProfileActivity.class);
                    intent.putExtra("userSex", userSex);
                    startActivity(intent);
                    finish();
                    break;
//                case R.id.friends:
//                    selectedFragment = new friends_fragment();
//                    break;
//
                case R.id.settings:
                    Intent sintent = new Intent(FeedActivity.this, SettingsActivity.class);
                    sintent.putExtra("userSex", userSex);
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


    private void getGender() {
        final FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mUserDb = Users.child(userId.getUid());
        mUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if(snapshot.child("gender") != null){
                        userSex = snapshot.child("gender").getValue().toString();
                        switch(userSex){
                            case "Male":
                                oppositeSex = "Female";
                                break;
                            case "Female":
                                oppositeSex = "Male";
                                break;
                        }
                        oppositeGenders();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void oppositeGenders(){
        Users.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists() && !snapshot.child("connections").child("Nada").hasChild(currentUserId) &&  !snapshot.child("connections").child("Yes").hasChild(currentUserId) && snapshot.child("gender").getValue().toString().equals(oppositeSex)){
                    String profileImageUrl = "default";

                    if(!snapshot.child("profileImageUrl").getValue().equals("default")){
                        profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                    }
                    cards cards = new cards(snapshot.child("name").getValue().toString(), snapshot.getKey(), profileImageUrl);
                    rowItems.add(cards);
                    arrayAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }

}