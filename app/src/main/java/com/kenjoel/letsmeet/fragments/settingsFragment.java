package com.kenjoel.letsmeet.fragments;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

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
import com.kenjoel.letsmeet.R;
import com.kenjoel.letsmeet.profile.ProfileActivity;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;

public class settingsFragment extends Fragment {

    private static final String TAG = "food";
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

    public settingsFragment() {
        // Required empty public constructor
    }

    public static settingsFragment newInstance(String param1, String param2) {
        settingsFragment fragment = new settingsFragment();
        Bundle args = new Bundle();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getCurrentUser().getUid();
        Log.i(TAG, userId);
        mDatabaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId);
        getUserInfo();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_settings, container, false);
        ButterKnife.bind(this, v);
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
                try {
                    finalize();
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        mBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fm = getActivity().getSupportFragmentManager();
                fm.popBackStack();
            }
        });

        return v;
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
            final StorageReference filepath = FirebaseStorage.getInstance().getReference().child("profileImage").child(userId);
            Bitmap bitmap = null;

            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getApplication().getContentResolver(), resultUri);
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
                    Toast.makeText(getActivity(),"Failed to add image", Toast.LENGTH_LONG);
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
                            FragmentManager fm = getActivity().getSupportFragmentManager();
                            fm.popBackStack();
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