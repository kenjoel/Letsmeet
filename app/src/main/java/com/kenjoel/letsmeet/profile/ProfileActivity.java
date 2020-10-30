package com.kenjoel.letsmeet.profile;

//Instagram Access Token = IGQVJWR0djLUdyNW9IN2FjMFZAzWEhiUk4xY2V6MU1ZAZATc3WUtDTUtkU0NHU0pYczhjM2pDeUVNNTREZAWloY3lIaWhhNGc0RHJTOWVZALXpIYllqb0tULTU4NGwwYlhDLWIzNTNVR3Y0VjNKWGlTdHF1SAZDZD

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.kenjoel.letsmeet.R;
import com.kenjoel.letsmeet.authentication.LoginActivity;
import com.kenjoel.letsmeet.feed.FeedActivity;
import com.kenjoel.letsmeet.friends.FriendsActivity;
import com.kenjoel.letsmeet.settings.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "Users Sex is";
    private String userSex;


    @BindView(R.id.bottom_navigation) BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ButterKnife.bind(this);
        navigationView.setSelectedItemId(R.id.profile);
        navigationView.setOnNavigationItemSelectedListener(navListener);
//        userSex = getIntent().getStringExtra("userSex");
//        Log.i(TAG, userSex);

    }

    BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()){
                case R.id.feed:
                    Intent intent = new Intent(ProfileActivity.this, FeedActivity.class);
                    startActivity(intent);
                    break;

                case R.id.friends:
                    Intent fintent = new Intent(ProfileActivity.this, FriendsActivity.class);
                    startActivity(fintent);
                    break;
                case R.id.settings:
                    Intent sintent = new Intent(ProfileActivity.this, SettingsActivity.class);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_logout) {
            logout();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void logout() {
        FirebaseAuth.getInstance().signOut();
        Intent intent = new Intent(ProfileActivity.this, LoginActivity.class);
        startActivity(intent);
        finish();
        return;
    }


}
