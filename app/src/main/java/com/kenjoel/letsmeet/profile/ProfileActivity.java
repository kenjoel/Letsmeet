package com.kenjoel.letsmeet.profile;

//Instagram Access Token = IGQVJWR0djLUdyNW9IN2FjMFZAzWEhiUk4xY2V6MU1ZAZATc3WUtDTUtkU0NHU0pYczhjM2pDeUVNNTREZAWloY3lIaWhhNGc0RHJTOWVZALXpIYllqb0tULTU4NGwwYlhDLWIzNTNVR3Y0VjNKWGlTdHF1SAZDZD

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserInfo;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kenjoel.letsmeet.R;
import com.kenjoel.letsmeet.authentication.LoginActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private static final String TAG = "Users Sex is";
    private DatabaseReference UsersInfo;
    private String userSex, name, profileImageUrl, phone;
    private TextView nameProfile, numberProfile, genderProfile;
    private ImageView profileImage;


    @BindView(R.id.bottom_navigation) BottomNavigationView navigationView;
    @BindView(R.id.tv_name) TextView mUsername;
    @BindView(R.id.imageUser) ImageView mImageUser;
    @BindView(R.id.profileEmail) TextView profileEmail;
    @BindView(R.id.profileNumber) TextView profileNumber;
    @BindView(R.id.profileGender) TextView profileGender;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ButterKnife.bind(this);
        UsersInfo = FirebaseDatabase.getInstance().getReference().child("Users");
        numberProfile = profileNumber;
        nameProfile = mUsername;
        genderProfile = profileGender;
        profileImage = mImageUser;

        getInfo();
//        userSex = getIntent().getStringExtra("userSex");
//        Log.i(TAG, userSex);

    }

    private void getInfo() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = user.getUid();

        DatabaseReference France = UsersInfo.child(userUid);

        France.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.child("name").exists()){
                    name = snapshot.child("name").getValue().toString();
                    userSex = snapshot.child("gender").getValue().toString();
                    phone = snapshot.child("phone").getValue().toString();
                    profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                }else{
                    Toast.makeText(ProfileActivity.this, "No current user info", Toast.LENGTH_SHORT);
                }
                nameProfile.setText(name);
                numberProfile.setText(phone);
                genderProfile.setText(userSex);
                Picasso.get().load(profileImageUrl).into(profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


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
