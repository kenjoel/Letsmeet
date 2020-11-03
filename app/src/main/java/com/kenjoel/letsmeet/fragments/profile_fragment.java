package com.kenjoel.letsmeet.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kenjoel.letsmeet.R;
import com.kenjoel.letsmeet.profile.ProfileActivity;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;


public class profile_fragment extends Fragment {


    private static final String TAG = "Users Sex is";
    private DatabaseReference UsersInfo;
    private TextView nameProfile, numberProfile, genderProfile;
    private ImageView profileImage;


    @BindView(R.id.tv_name) TextView mUsername;
    @BindView(R.id.imageUser) ImageView mImageUser;
    @BindView(R.id.profileEmail) TextView profileEmail;
    @BindView(R.id.profileNumber) TextView profileNumber;
    @BindView(R.id.profileGender) TextView profileGender;


    public profile_fragment() {
        // Required empty public constructor
    }


    public static profile_fragment newInstance(String param1, String param2) {
        profile_fragment fragment = new profile_fragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        UsersInfo = FirebaseDatabase.getInstance().getReference().child("Users");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_profile_fragment, container, false);
        ButterKnife.bind(this, v);
        numberProfile = profileNumber;
        nameProfile = mUsername;
        genderProfile = profileGender;
        profileImage = mImageUser;
        getInfo();
        // Inflate the layout for this fragment
        return v;
    }


    private void getInfo() {
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userUid = user.getUid();

        DatabaseReference France = UsersInfo.child(userUid);

        France.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                String userSex="";
                String name="";
                String profileImageUrl="";
                String phone="";
                if(snapshot.child("name").exists()){
                    name = snapshot.child("name").getValue().toString();
                    userSex = snapshot.child("gender").getValue().toString();
                    phone = snapshot.child("phone").getValue().toString();
                    profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                }else{
                    Toast.makeText(getActivity(), "No current user info", Toast.LENGTH_SHORT);
                }
                nameProfile.setText(name);
                numberProfile.setText(phone);
                genderProfile.setText(userSex);
                Picasso.get().load(profileImageUrl).into(profileImage);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

    }


}