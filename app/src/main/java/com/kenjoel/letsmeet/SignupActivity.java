package com.kenjoel.letsmeet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    @BindView(R.id.launch) Button mLauncher;
    @BindView(R.id.name) EditText mName;
    @BindView(R.id.email) EditText mEmail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.confirm) EditText mConfirm;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);
        ButterKnife.bind(this);
        mLauncher.setOnClickListener(this);

        mAuth = FirebaseAuth.getInstance();
        firebaseAuthStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {

                final FirebaseUser user = mAuth.getInstance().getCurrentUser();

                if(user != null){
                    Intent intent = new Intent(SignupActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }

            }
        };
    }

    @Override
    public void onClick(View v){

        if(v == mLauncher){
            String email = mEmail.getText().toString();
            String pass = mPassword.getText().toString();

            mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(!task.isSuccessful()){
                        Toast.makeText(SignupActivity.this, "SignUp error ", Toast.LENGTH_SHORT).show();
                    }
                }
            });
//            Intent intent = new Intent(SignupActivity.this, ProfileActivity.class );
//            intent.putExtra("name",  name);
//            intent.putExtra( "email", email);
//            intent.putExtra("pass", pass);
//            intent.putExtra("confirm", confirm);
//            startActivity(intent);

        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthStateListener);
    }
}
