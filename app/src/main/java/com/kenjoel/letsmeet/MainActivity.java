package com.kenjoel.letsmeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.creation) Button mCreateAccountButton;
    @BindView(R.id.login) Button mLoginButtton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCreateAccountButton.setOnClickListener(this);
        mLoginButtton.setOnClickListener(this);
    }

    @Override
    public void  onClick(View v){
        if(v == mCreateAccountButton){
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
            Toast.makeText( MainActivity.this,"fill in your details", Toast.LENGTH_LONG).show();
        }else if(v == mLoginButtton){
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        }
    }



}




