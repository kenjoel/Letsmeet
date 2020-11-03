package com.kenjoel.letsmeet.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kenjoel.letsmeet.R;
import com.kenjoel.letsmeet.adapters.CardsAdapter;
import com.kenjoel.letsmeet.models.cards;
import com.lorentzos.flingswipe.SwipeFlingAdapterView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class feedFragment extends Fragment {

    private static final String TAG = "the Key" ;
    private CardsAdapter arrayAdapter;
    private int i;

    private String currentUserId;
    private DatabaseReference Users;

    private FirebaseAuth mAuth;

    private static String userSex;
    private String oppositeSex;
    private Object data;
    private FrameLayout frame;
    SwipeFlingAdapterView flingContainer;



    List<cards> rowItems;


    public feedFragment() {
        // Required empty public constructor
    }

    public static feedFragment newInstance(String param1, String param2) {
        feedFragment fragment = new feedFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Users = FirebaseDatabase.getInstance().getReference().child("Users");
        getGender();
        mAuth = FirebaseAuth.getInstance();
        currentUserId = mAuth.getUid();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_feed, container, false);;

//        ButterKnife.bind(this, view);
        rowItems = new ArrayList<>();
        arrayAdapter = new CardsAdapter(getActivity(), R.layout.item, rowItems);
        routeB(view);

        // Inflate the layout for this fragment
        return view;
    }

    private void routeB(View v){
        flingContainer = (SwipeFlingAdapterView) v.findViewById(R.id.frame);
        flingContainer.setAdapter(arrayAdapter);
        flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                // this is the simplest way to delete an object from the Adapter (/AdapterView)
                Log.d("LIST", "removed object!");
                rowItems.remove(0);
                arrayAdapter.notifyDataSetChanged();
            }

            @Override
            public void onLeftCardExit(Object dataObject) {
                Toast.makeText(getActivity(), "Nope", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onRightCardExit(Object dataObject) {
                cards obj = (cards) dataObject;
                String userId = obj.getUserId();
                Users.child(currentUserId).child("connections").child(userId).setValue(true);
                Toast.makeText(getActivity(), "User Followed", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                if(itemsInAdapter == 1){
                    Toast.makeText(getActivity(), "That's all for today check tomorrow", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onScroll(float scrollProgressPercent) {
            }
        });

        flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
            @Override
            public void onItemClicked(int itemPosition, Object dataObject) {
                Toast.makeText(getActivity(), "clicked", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void getGender() {
        final FirebaseUser userId = FirebaseAuth.getInstance().getCurrentUser();
        DatabaseReference mUserDb = Users.child(userId.getUid());
        mUserDb.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {
                    if(snapshot.child("gender") != null){
                        userSex = snapshot.child("gender").getValue().toString();
                        switch(userSex){
                            case "Male":
                                oppositeSex = "Female";
                                break;
                            case "Female":
                                oppositeSex = "Male";
                                break;
                        }
                        oppositeGenders();
                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public void oppositeGenders(){
        Users.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                if(snapshot.exists() && !snapshot.child("connections").hasChild(currentUserId) && snapshot.child("gender").getValue().toString().equals(oppositeSex)){
                    String profileImageUrl = "default";

                    if(!snapshot.child("profileImageUrl").getValue().equals("default")){
                        profileImageUrl = snapshot.child("profileImageUrl").getValue().toString();
                    }
                    cards cards = new cards(snapshot.child("name").getValue().toString(), snapshot.getKey(), profileImageUrl);
                    rowItems.add(cards);
                    arrayAdapter.notifyDataSetChanged();
                }

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot snapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }

        });
    }


}