package com.kenjoel.letsmeet;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class ProfileAdapter extends ArrayAdapter {

    private Context mContext;
    private List<String> mDetails;

    public ProfileAdapter(Context mcontext, int resource, List<String> mdetails){
        super(mcontext, resource);
        this.mContext = mcontext;
        this.mDetails = mdetails;
    }

    @Override
    public Object getItem(int position){
        String mdetails = mDetails.get(position);
        return mdetails;
    }

    @Override
    public int getCount(){
        return mDetails.size();
    }
}
