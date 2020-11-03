package com.kenjoel.letsmeet.fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.kenjoel.letsmeet.R;
import com.kenjoel.letsmeet.models.cardsObject;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link friendsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class friendsFragment extends Fragment {


    private static final String TAG = "user keys are";
    private FirebaseAuth mAuth;
    private DatabaseReference databaseReference;
    private String userId;

    @BindView(R.id.recyclerView)
    RecyclerView mRecyclerView;




    public friendsFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static friendsFragment newInstance(String param1, String param2) {
        friendsFragment fragment = new friendsFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAuth = FirebaseAuth.getInstance();
        userId = mAuth.getUid();
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(userId).child("connections");


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_friends, container, false);
        ButterKnife.bind(this, v);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(v.getContext()));
        mRecyclerView.hasFixedSize();
//        fetch();
        getUserInfo();
        // Inflate the layout for this fragment
        return v;
    }


    private void getUserInfo() {
        final DatabaseReference userDb = FirebaseDatabase.getInstance().getReference().child("Users");
        Log.d(TAG, userDb.toString());

        FirebaseRecyclerOptions<cardsObject> options = new FirebaseRecyclerOptions.Builder<cardsObject>()
                .setQuery(userDb,cardsObject.class)
                .build();
        Log.i(TAG, "getUserInfoReturned:" + options.getSnapshots());

        final FirebaseRecyclerAdapter<cardsObject, cardsViewHolder> adapter =
                new FirebaseRecyclerAdapter<cardsObject, cardsViewHolder>(options) {
                    @NonNull
                    @Override
                    public cardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
                        return new cardsViewHolder(v);
                    }


                    @Override
                    protected void onBindViewHolder(@NonNull final cardsViewHolder holder, final int position, @NonNull cardsObject model) {
                        final String key = getRef(position).getKey();

                        userDb.addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                String name = "";
                                String phone = "";
                                String profileImageUrl = "";
                                String gender = "";

                                Log.i(TAG, snapshot.child(key).toString());

                                if(snapshot.child(key) != null){
                                    gender = snapshot.child(key).child("gender").getValue().toString();
                                    name = snapshot.child(key).child("name").getValue().toString();
                                    phone = snapshot.child(key).child("phone").getValue().toString();
                                    profileImageUrl = snapshot.child(key).child("profileImageUrl").getValue().toString();
                                }
                                holder.mNameView.setText(name);
                                holder.mNumberView.setText(phone);
                                Picasso.get().load(profileImageUrl).into(holder.mImageView);
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }
                };
        adapter.notifyDataSetChanged();
        mRecyclerView.setAdapter(adapter);
        adapter.startListening();
    }

    public class cardsViewHolder extends RecyclerView.ViewHolder {
        public ImageView mImageView;
        public TextView mNameView;
        public TextView mNumberView;
        public cardsViewHolder(@NonNull View itemView) {
            super(itemView);
            mImageView = itemView.findViewById(R.id.imageView);
            mNameView = itemView.findViewById(R.id.nameView);
            mNumberView = itemView.findViewById(R.id.numberView);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

}