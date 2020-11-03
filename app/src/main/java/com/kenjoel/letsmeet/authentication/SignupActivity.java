package com.kenjoel.letsmeet.authentication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.kenjoel.letsmeet.MainActivity;
import com.kenjoel.letsmeet.profile.ProfileActivity;
import com.kenjoel.letsmeet.R;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class SignupActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG =  "here is the value";
    @BindView(R.id.launch) Button mLauncher;
    @BindView(R.id.name) EditText mName;
    @BindView(R.id.email) EditText mEmail;
    @BindView(R.id.password) EditText mPassword;
    @BindView(R.id.confirm) EditText mConfirm;
    @BindView(R.id.radioGroup) RadioGroup mRadioGroup;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthStateListener;

    private RadioButton idHolder;

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
                    Intent intent = new Intent(SignupActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                }

            }
        };
    }

            @Override
            public void onClick(View v) {

                int selected = mRadioGroup.getCheckedRadioButtonId();
                idHolder =  findViewById(selected);
                Log.d(TAG, idHolder.toString());

                if(idHolder.getText() == null){
                    return;
                }

                final String name = mName.getText().toString().trim();
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
                            String userId = mAuth.getCurrentUser().getUid();
                            DatabaseReference UserReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
                            Map userInfo = new HashMap();
                            userInfo.put("name", name);
                            userInfo.put("gender", idHolder.getText().toString());
                            userInfo.put("profileImageUrl", "default");
                            UserReference.setValue(userInfo);
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
