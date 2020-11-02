package com.kenjoel.letsmeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.kenjoel.letsmeet.authentication.LoginActivity;
import com.kenjoel.letsmeet.authentication.SignupActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;

//    @BindView(R.id.creation) Button mCreateAccountButton;
//    @BindView(R.id.login) Button mLoginButtton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_app_bar_open_drawer_description, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

//        mCreateAccountButton.setOnClickListener(this);
//        mLoginButtton.setOnClickListener(this);
    }
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

//    @Override
//    public void  onClick(View v){
//        if(v == mCreateAccountButton){
//            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
//            startActivity(intent);
//            Toast.makeText( MainActivity.this,"fill in your details", Toast.LENGTH_LONG).show();
//        }else if(v == mLoginButtton){
//            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//            startActivity(intent);
//        }
//    }



}




