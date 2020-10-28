package com.kenjoel.letsmeet;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;

public class SettingsActivity extends AppCompatActivity {

    private EditText nameProfile, phoneProfile;
    private Button mConfirmButton, mBackButton;

    private ImageView profileImage;

    private FirebaseAuth mAuth;

    private DatabaseReference mDatabaseReference;

    private String userId,name,phone,profileImageUrl;

    private String userSex;
    private Uri resultUri;

    @BindView(R.id.imageprofile) ImageView imageView;
    @BindView(R.id.nameprofile) EditText getNameProfile;
    @BindView(R.id.profilephone) EditText mPhone;
    @BindView(R.id.confirmBtn) Button mConfirmBtn;
    @BindView(R.id.backbtn) Button backBtn;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        userSex = getIntent().getStringExtra("userSex");

        nameProfile = getNameProfile;
        phoneProfile = mPhone;
        profileImage = imageView;
        mConfirmButton = mConfirmBtn;
        mBackButton = backBtn;

        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();

        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userSex).child(userId);
        getUserInfo();
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 1);
            }
        });
        mConfirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserInfo();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                return;
            }
        });
    }

    private void getUserInfo(){
        mDatabaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists() && snapshot.getChildrenCount() > 0){
                    Map<String, Object> map = (Map<String, Object>) snapshot.getValue();
                    if(map.get("name") != null){
                        name = map.get("name").toString();
                        nameProfile.setText(name);
                    }
                    if(map.get("phone") != null){
                        phone = map.get("phone").toString();
                        nameProfile.setText(phone);
                    }

                    if(map.get("profileImageUrl") != null){
                        profileImageUrl = map.get("profileImageUrl").toString();
                        Glide.with(getApplication()).load(profileImageUrl).into(profileImage);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    BottomNavigationView.OnNavigationItemSelectedListener navListener = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment selectedFragment = null;
            switch (item.getItemId()){
                case R.id.profile:
                    Intent intent = new Intent(SettingsActivity.this, ProfileActivity.class);
                    startActivity(intent);
                    finish();
                    break;
//                case R.id.friends:
//                    selectedFragment = new friends_fragment();
//                    break;
//                case R.id.messages:
//                    selectedFragment = new message_fragment();
//                    break;
            }

            getSupportFragmentManager().beginTransaction().replace(R.id.frameholder, selectedFragment).commit();

            return true;
        }


    };

    private void saveUserInfo() {
        name = nameProfile.getText().toString().trim();
        phone = phoneProfile.getText().toString().trim();
        Map userInfo = new HashMap();
        userInfo.put("name", name);
        userInfo.put("phone", phone);

        mDatabaseReference.updateChildren(userInfo);
        if (resultUri != null){
            final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImage").child(userId);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getApplication().getContentResolver(), resultUri);
            } catch (IOException e) {
                e.printStackTrace();
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 20, baos);
            final byte[] data = baos.toByteArray();

            UploadTask uploadTask = filepath.putBytes(data);
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(SettingsActivity.this,"Failed to add image", Toast.LENGTH_LONG);
                    finish();
                }
            });

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    filepath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {
                            Uri downloadUrl = uri;

                            Map userInfo = new HashMap();
                            userInfo.put("profileImageUrl", downloadUrl.toString());
                            mDatabaseReference.updateChildren(userInfo);
                            finish();
                            return;
                        }
                    });
                }
            });
        } else{
            finish();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 1 && resultCode == Activity.RESULT_OK){
            resultUri = data.getData();
            profileImage.setImageURI(resultUri);
        }
    }

}