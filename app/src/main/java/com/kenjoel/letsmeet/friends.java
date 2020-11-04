package com.kenjoel.letsmeet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kenjoel.letsmeet.authentication.LoginActivity;
import com.kenjoel.letsmeet.fragments.feedFragment;
import com.kenjoel.letsmeet.fragments.friendsFragment;
import com.kenjoel.letsmeet.fragments.profile_fragment;
import com.kenjoel.letsmeet.fragments.settingsFragment;
import com.kenjoel.letsmeet.models.cardsObject;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class friends extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "she got here successfully";
    private DrawerLayout drawerLayout;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friends);
        ButterKnife.bind(this);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(false);
        getUserInfo();

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_app_bar_open_drawer_description, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameNav, new profile_fragment()).commit();
                break;
            case R.id.feed:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameNav, new feedFragment()).commit();
                break;
            case R.id.settings:
                Intent intr = new Intent(friends.this, settings.class);
                startActivity(intr);
                finish();
                return true;


            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(friends.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }


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

                                        gender = snapshot.child(key).child("gender").getValue(String.class);
                                        name = snapshot.child(key).child("name").getValue(String.class);
                                        phone = snapshot.child(key).child("phone").getValue(String.class);
                                        profileImageUrl = snapshot.child(key).child("profileImageUrl").getValue(String.class);

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
    public void onStart() {
        super.onStart();
    }



}