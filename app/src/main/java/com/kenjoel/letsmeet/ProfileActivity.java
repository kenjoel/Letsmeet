package com.kenjoel.letsmeet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

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
    }



}
