package com.kenjoel.letsmeet;

//Instagram Access Token = IGQVJWR0djLUdyNW9IN2FjMFZAzWEhiUk4xY2V6MU1ZAZATc3WUtDTUtkU0NHU0pYczhjM2pDeUVNNTREZAWloY3lIaWhhNGc0RHJTOWVZALXpIYllqb0tULTU4NGwwYlhDLWIzNTNVR3Y0VjNKWGlTdHF1SAZDZD

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private String[] mName;
    private String[] mEmail;

    @BindView(R.id.thisList) ListView mListview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_activity);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");

        this.mName = new String[]{name};
        this.mEmail = new String[]{email};

        ProfileAdapter profileAdapter = new ProfileAdapter(this, android.R.layout.simple_list_item_1, mName, mEmail);
        mListview.setAdapter(profileAdapter);
        Toast.makeText(ProfileActivity.this,  name + " Welcome your account was created successfully ", Toast.LENGTH_LONG).show();


    }



}
