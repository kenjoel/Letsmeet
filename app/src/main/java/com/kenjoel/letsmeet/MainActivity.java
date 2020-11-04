package com.kenjoel.letsmeet;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.Navigation;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kenjoel.letsmeet.authentication.LoginActivity;
import com.kenjoel.letsmeet.authentication.SignupActivity;
import com.kenjoel.letsmeet.fragments.feedFragment;
import com.kenjoel.letsmeet.fragments.friendsFragment;
import com.kenjoel.letsmeet.fragments.profile_fragment;
import com.kenjoel.letsmeet.fragments.settingsFragment;
import com.kenjoel.letsmeet.profile.ProfileActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "And the user iss, drumroll";
    private DrawerLayout drawerLayout;
    private FirebaseAuth mAuth;
    private DatabaseReference UsersInfo;

    final String user = FirebaseAuth.getInstance().getCurrentUser().getUid();

    @BindView(R.id.imageViewCard)
    ImageView imageView;
    @BindView(R.id.name)
    TextView mName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);
        Log.i(TAG, user);

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
                Intent intor = new Intent(MainActivity.this, settings.class);
                startActivity(intor);
                finish();
                return true;

            case R.id.friends:
                Intent intr = new Intent(MainActivity.this, friends.class);
                startActivity(intr);
                finish();
                return true;

            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }



}




