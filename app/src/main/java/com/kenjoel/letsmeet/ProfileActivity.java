package com.kenjoel.letsmeet;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ProfileActivity extends AppCompatActivity {

    private List<String> mEverything;

    @BindView(R.id.listView) ListView mListview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ButterKnife.bind(this);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String email = intent.getStringExtra("email");

        mEverything.add(name);
        mEverything.add(email);

        ProfileAdapter profileAdapter = new ProfileAdapter(this, android.R.layout.simple_list_item_1, mEverything);
        mListview.setAdapter(profileAdapter);
    }

}
