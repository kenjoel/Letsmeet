package com.kenjoel.letsmeet;

<<<<<<< HEAD
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @BindView(R.id.creation) Button mCreateAccountButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mCreateAccountButton.setOnClickListener(this);
    }

    @Override
    public void  onClick(View v){
        if(v == mCreateAccountButton){
            Intent intent = new Intent(MainActivity.this, SignupActivity.class);
            startActivity(intent);
        }
    }
}



=======
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
}
>>>>>>> 07f3ef2b5e715fcbbecaf2cc14bb564f90759a12
