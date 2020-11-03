//package com.kenjoel.letsmeet.feed;
//
//import android.content.Intent;
//import android.os.Bundle;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.util.Log;
//import android.view.MenuItem;
//import android.widget.Toast;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.ChildEventListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//import com.kenjoel.letsmeet.profile.ProfileActivity;
//import com.kenjoel.letsmeet.R;
//import com.kenjoel.letsmeet.settings.SettingsActivity;
//import com.kenjoel.letsmeet.adapters.CardsAdapter;
//import com.kenjoel.letsmeet.models.cards;
//import com.lorentzos.flingswipe.SwipeFlingAdapterView;
//
//import java.util.ArrayList;
//
//
//import java.util.List;
//
//import butterknife.BindView;
//import butterknife.ButterKnife;
//
//public class FeedActivity extends AppCompatActivity{
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_feed);
//  }
//}