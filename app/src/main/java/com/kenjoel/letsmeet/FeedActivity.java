package com.kenjoel.letsmeet;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class FeedActivity extends AppCompatActivity {

        private ArrayList<String> al;
        private ArrayAdapter<String> arrayAdapter;
        private int i;


        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_feed);


            al = new ArrayList<>();
            al.add("php");
            al.add("c");
            al.add("python");
            al.add("java");
            al.add("html");
            al.add("c++");
            al.add("css");
            al.add("javascript");

            arrayAdapter = new ArrayAdapter<>(this, R.layout.item, R.id.helloText, al );

            SwipeFlingAdapterView = (SwipeFlingAdapterView) findViewById(R.id.frame);


            flingContainer.setAdapter(arrayAdapter);
            flingContainer.setFlingListener(new SwipeFlingAdapterView.onFlingListener() {
                @Override
                public void removeFirstObjectInAdapter() {
                    // this is the simplest way to delete an object from the Adapter (/AdapterView)
                    Log.d("LIST", "removed object!");
                    al.remove(0);
                    arrayAdapter.notifyDataSetChanged();
                }

                @Override
                public void onLeftCardExit(Object dataObject) {
                    //Do something on the left!
                    //You also have access to the original object.
                    //If you want to use it just cast it (String) dataObject
                    Toast.makeText(FeedActivity.this, "Nope",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onRightCardExit(Object dataObject) {
                    Toast.makeText(FeedActivity.this, "Yeah",Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onAdapterAboutToEmpty(int itemsInAdapter) {
                    // Ask for more data here
                    al.add("XML ".concat(String.valueOf(i)));
                    arrayAdapter.notifyDataSetChanged();
                    Log.d("LIST", "notified");
                    i++;
                }

                @Override
                public void onScroll(float scrollProgressPercent) {
                }
            });


            // Optionally add an OnItemClickListener
            flingContainer.setOnItemClickListener(new SwipeFlingAdapterView.OnItemClickListener() {
                @Override
                public void onItemClicked(int itemPosition, Object dataObject) {
                    Toast.makeText(FeedActivity.this, "clicked",Toast.LENGTH_SHORT).show();

                }
            });

        }

        static void makeToast(Context ctx, String s){
            Toast.makeText(ctx, s, Toast.LENGTH_SHORT).show();
        }


        @OnClick(R.id.right)
        public void right() {
            /**
             * Trigger the right event manually.
             */
            flingContainer.getTopCardListener().selectRight();
        }

        @OnClick(R.id.left)
        public void left() {
            flingContainer.getTopCardListener().selectLeft();
        }



    }
}