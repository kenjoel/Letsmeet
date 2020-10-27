package com.kenjoel.letsmeet;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Patterns;
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

                if (user != null) {
                    Intent intent = new Intent(SignupActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }


            }
        };
    }

            @Override
            public void onClick(View v) {
                String name = mName.getText().toString().trim();
                String email = mEmail.getText().toString().trim();
                String pass = mPassword.getText().toString().trim();
                String confirm = mConfirm.getText().toString().trim();

                validName(name);
                validEmail(email);
                passWordValid(pass, confirm);

                mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(SignupActivity.this, "SignUp error ", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(SignupActivity.this, "Welcome Sign Up Success", Toast.LENGTH_LONG).show();
                        }
                    }
                });
            }

            public boolean validName(String mname){
            if(mname.equals("")){
                mName.setError("Please Enter a valid name");
                return false;
            }
            return true;
            }


            public boolean passWordValid(String password, String confirmPass){
             if(password.length() < 6){
                 mPassword.setError("Your password must be above 6");
                 return  false;
             }else if(!password.equals(confirmPass)){
                 mPassword.setError("Your passwords do not match");
                 return false;
             }
             return true;
            }


            public boolean validEmail(String email){
             if(email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                 return true;
             }else {
                 return false;
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
