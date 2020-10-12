package com.kenjoel.letsmeet;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.List;

public class ProfileAdapter extends ArrayAdapter {

    private Context mContext;
    private String[] mName;
    private String[] mEmail;

    public ProfileAdapter(Context mcontext, int resource, String[] mName, String[] mEmail){
        super(mcontext, resource);
        this.mContext = mcontext;
        this.mName = mName;
        this.mEmail = mEmail;
    }

    @Override
    public Object getItem(int position){
        String mdetails = mName[position];
        String email = mEmail[position];
        return String.format("%s Welcome to Connect, check your email %s to activate account", mdetails, email);
    }

    @Override
    public int getCount(){
        return mName.length;
    }
}
