package com.kenjoel.letsmeet;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.launch) Button mLauncher;
    @BindView(R.id.name) TextView mName;
    @BindView(R.id.email) TextView mEmail;
    @BindView(R.id.phone) TextView mPhone;
    @BindView(R.id.password) TextView mPassword;
    @BindView(R.id.confirm) TextView mConfirm;

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
            Intent intent = new Intent(SignupActivity.this, ProfileActivity.class );
            intent.putExtra("name", mName);
//            intent.putExtra("email", mEmail);


        }


    }
}
