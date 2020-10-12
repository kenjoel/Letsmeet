package com.kenjoel.letsmeet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.launch) Button mLauncher;
    @BindView(R.id.name) EditText mName;
    @BindView(R.id.email) EditText mEmail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.confirm) EditText mConfirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.bind(this);
        mLauncher.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){

        if(v == mLauncher){
            String name = mName.getText().toString();
            String email = mEmail.getText().toString();
            String pass = mPassword.getText().toString();
            String confirm = mConfirm.getText().toString();

            Intent intent = new Intent(SignupActivity.this, ProfileActivity.class );
            intent.putExtra("name",  name);
            intent.putExtra( "email", email);
            intent.putExtra("pass", pass);
            intent.putExtra("confirm", confirm);
            startActivity(intent);
            Toast.makeText(SignupActivity.this, "Welcome" + name, Toast.LENGTH_LONG).show();


        }


    }
}
