package com.kenjoel.letsmeet.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kenjoel.letsmeet.R;
import com.kenjoel.letsmeet.models.cards;
import com.squareup.picasso.Picasso;

import java.util.List;


public class CardsAdapter extends ArrayAdapter {


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
       switch (cardItem.getImageUrl()){
           case "default":
               Picasso.get().load(R.mipmap.avtr).into(imageView);
               break;
           default:
               Picasso.get().load(cardItem.getImageUrl()).into(imageView);
       }
       return theView;
       }
    }

