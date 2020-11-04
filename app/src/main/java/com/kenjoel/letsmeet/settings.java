package com.kenjoel.letsmeet;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.kenjoel.letsmeet.authentication.LoginActivity;
import com.kenjoel.letsmeet.fragments.feedFragment;
import com.kenjoel.letsmeet.fragments.profile_fragment;
import com.kenjoel.letsmeet.fragments.settingsFragment;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class settings extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "food sharing, bla bla";
    private EditText nameProfile, phoneProfile;
    private Button mConfirmButton, mBackButton;

    private ImageView profileImage;

    private DatabaseReference mDatabaseReference;

    private String userId,name,phone,profileImageUrl;

    private String userSex;
    private Uri resultUri;

    private DrawerLayout drawerLayout;

    @BindView(R.id.imageprofile) ImageView imageView;
    @BindView(R.id.nameprofile) EditText getNameProfile;
    @BindView(R.id.profilephone) EditText mPhone;
    @BindView(R.id.confirmBtn) Button mConfirmBtn;
    @BindView(R.id.backbtn) Button backBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings2);
        ButterKnife.bind(this);

        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        // Inflate the layout for this fragment
        nameProfile = getNameProfile;
        phoneProfile = mPhone;
        profileImage = imageView;
        mConfirmButton = mConfirmBtn;
        mBackButton = backBtn;
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
                Intent intent = new Intent(settings.this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        getUserInfo();

        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ButterKnife.bind(this);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.nav_app_bar_open_drawer_description, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }
    public void onBackPressed(){
        if (drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.profile:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameNav, new profile_fragment()).commit();
                break;
            case R.id.feed:
                getSupportFragmentManager().beginTransaction().replace(R.id.frameNav, new feedFragment()).commit();
                break;
            case R.id.friends:
                Intent intr = new Intent(settings.this, friends.class);
                startActivity(intr);
                finish();
                return true;

            case R.id.action_logout:
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(settings.this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
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
                        Log.i(TAG, name);
                    }
                    if(map.get("phone") != null){
                        phone = map.get("phone").toString();
                        phoneProfile.setText(phone);
                    }

                    if(map.get("gender") != null){
                        userSex = map.get("gender").toString();
                    }
                    if(map.get("profileImageUrl") != null){
                        profileImageUrl = map.get("profileImageUrl").toString();

                        switch (profileImageUrl){
                            case "default":
                                Picasso.get().load(R.mipmap.avtr).into(imageView);
                                break;
                            default:
                                Picasso.get().load(profileImageUrl).into(imageView);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void saveUserInfo() {
        name = nameProfile.getText().toString().trim();
        phone = phoneProfile.getText().toString().trim();
        Map userInfo = new HashMap();
        userInfo.put("name", name);
        userInfo.put("phone", phone);

        mDatabaseReference.updateChildren(userInfo);
        if (resultUri != null){
            final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImage").child("ZyA83AiJtNQwiTb57G8p5iVjgiG2");
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(settings.this.getContentResolver(), resultUri);
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
                    Toast.makeText(settings.this,"Failed to add image", Toast.LENGTH_LONG);
                    return;
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
                        }
                    });
                }
            });
        } else{

            return;
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