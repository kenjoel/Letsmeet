package com.kenjoel.letsmeet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;


public class CardsAdapter extends ArrayAdapter {

    private Context context;

    public CardsAdapter(Context context, int resource, List<cards>items) {
        super(context, resource, items);
    }

    public View getView(int position, View theView, ViewGroup parent){
        cards cardItem = (cards) getItem(position);

       if (theView == null) {
           theView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);

       }

       TextView name = theView.findViewById(R.id.name);
       ImageView imageView = theView.findViewById(R.id.imageViewCard);

       name.setText(cardItem.getName());
       imageView.setImageResource(R.mipmap.ic_launcher);
       return theView;
       }
    }

