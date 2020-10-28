package com.kenjoel.letsmeet;

//Instagram Access Token = IGQVJWR0djLUdyNW9IN2FjMFZAzWEhiUk4xY2V6MU1ZAZATc3WUtDTUtkU0NHU0pYczhjM2pDeUVNNTREZAWloY3lIaWhhNGc0RHJTOWVZALXpIYllqb0tULTU4NGwwYlhDLWIzNTNVR3Y0VjNKWGlTdHF1SAZDZD

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {


    @BindView(R.id.bottom_navigation) BottomNavigationView navigationView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ButterKnife.bind(this);
        navigationView.setOnNavigationItemSelectedListener(navListener);
        getSupportFragmentManager().beginTransaction().replace(R.id.frameholder, new profile_fragment()).commit();
    }

    BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = new profile_fragment();

            switch (item.getItemId()){
                case R.id.profile:
                    selectedFragment = new profile_fragment();
                    break;

                case R.id.feed:
                    Intent intent = new Intent(ProfileActivity.this, FeedActivity.class);
                    startActivity(intent);
                    break;
//
//                case R.id.friends:
//                    selectedFragment = new friends_fragment();
//                    break;
//
//                case R.id.settings:
//                    selectedFragment = new settings_fragment();
//                    break;
//
//                case R.id.messages:
//                    selectedFragment = new message_fragment();
//                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frameholder, selectedFragment).commit();

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
