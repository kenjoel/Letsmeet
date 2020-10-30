package com.kenjoel.letsmeet.friends;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.kenjoel.letsmeet.R;
import com.kenjoel.letsmeet.feed.FeedActivity;
import com.kenjoel.letsmeet.profile.ProfileActivity;
import com.kenjoel.letsmeet.settings.SettingsActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class FriendsActivity extends AppCompatActivity {

    @BindView(R.id.bottom_navigation)
    BottomNavigationView navigationView;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.friends);
        ButterKnife.bind(this);
        navigationView.setSelectedItemId(R.id.friends);
        navigationView.setOnNavigationItemSelectedListener(navListener);
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
//
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


}
